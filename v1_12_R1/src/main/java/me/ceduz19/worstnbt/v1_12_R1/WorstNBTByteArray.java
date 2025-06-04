package me.ceduz19.worstnbt.v1_12_R1;

import me.ceduz19.worstnbt.NBT;
import me.ceduz19.worstnbt.NBTByteArray;
import me.ceduz19.worstnbt.NBTNumeric;
import me.ceduz19.worstnbt.util.ReflectionUtils;
import net.minecraft.server.v1_12_R1.NBTTagByteArray;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.Arrays;

class WorstNBTByteArray extends AbstractList<NBTNumeric.Byte> implements NBTByteArray {

    private static final Field FIELD_DATA = ReflectionUtils.getField(NBTTagByteArray.class, "data", true);
    private final NBTTagByteArray handle;

    WorstNBTByteArray(byte[] a) {
        this(new NBTTagByteArray(a));
    }

    WorstNBTByteArray(NBTTagByteArray handle) {
        this.handle = handle;
    }

    @Override
    public byte[] getAsByteArray() {
        return this.handle.c();
    }

    @Override
    public int size() {
        return this.handle.c().length;
    }

    @Override
    public NBTNumeric.Byte get(int index) {
        return new WorstNBTNumeric.Byte(this.handle.c()[index]);
    }

    @Override
    public @NotNull NBTNumeric.Byte set(int index, @NotNull NBTNumeric.Byte value) {
        NBTNumeric.Byte old = get(index);
        this.setNBT(index, value);
        return old;
    }

    @Override
    public void add(int index, @NotNull NBTNumeric.Byte value) {
        this.addNBT(index, value);
    }

    @Override
    public @NotNull NBTNumeric.Byte remove(int index) {
        NBTNumeric.Byte old = get(index);
        if (FIELD_DATA != null)
            ReflectionUtils.writeField(FIELD_DATA, this.handle, ArrayUtils.remove(getAsByteArray(), index));
        return old;
    }

    @Override
    public boolean setNBT(int index, @NotNull NBT value) {
        if (!(value instanceof NBTNumeric)) return false;

        getAsByteArray()[index] = ((NBTNumeric)value).getAsByte();
        return true;
    }

    @Override
    public boolean addNBT(int index, @NotNull NBT value) {
        if (!(value instanceof NBTNumeric) || FIELD_DATA == null) return false;

        ReflectionUtils.writeField(FIELD_DATA, this.handle, ArrayUtils.add(getAsByteArray(), index, ((NBTNumeric)value).getAsByte()));
        return false;
    }

    @Override
    public @NotNull Object getHandle() {
        return this.handle;
    }

    @Override
    public int hashCode() {
        return this.handle.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof WorstNBTByteArray) return this.handle.equals(((WorstNBTByteArray)obj).handle);
        if (obj instanceof NBTTagByteArray) return this.handle.equals(obj);

        if (!obj.getClass().isArray()) return false;

        Class<?> type = obj.getClass().getComponentType();
        if (type == byte.class) return Arrays.equals(getAsByteArray(), (byte[])obj);

        if (type != Byte.class) return false;

        Byte[] wrapped = (Byte[])obj;
        byte[] primitive = new byte[wrapped.length];
        for (int i = 0; i < primitive.length; ++i) primitive[i] = wrapped[i];

        return Arrays.equals(getAsByteArray(), primitive);
    }

    @Override
    public String toString() {
        return this.handle.toString();
    }
}
