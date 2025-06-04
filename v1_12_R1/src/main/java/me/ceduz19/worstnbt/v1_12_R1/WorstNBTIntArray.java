package me.ceduz19.worstnbt.v1_12_R1;

import me.ceduz19.worstnbt.NBT;
import me.ceduz19.worstnbt.NBTIntArray;
import me.ceduz19.worstnbt.NBTNumeric;
import me.ceduz19.worstnbt.util.ReflectionUtils;
import net.minecraft.server.v1_12_R1.NBTTagIntArray;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.Arrays;

class WorstNBTIntArray extends AbstractList<NBTNumeric.Int> implements NBTIntArray {

    private static final Field FIELD_DATA = ReflectionUtils.getField(NBTTagIntArray.class, "data", true);
    private final NBTTagIntArray handle;

    WorstNBTIntArray(int[] a) {
        this(new NBTTagIntArray(a));
    }

    WorstNBTIntArray(NBTTagIntArray handle) {
        this.handle = handle;
    }

    @Override
    public int size() {
        return this.handle.d().length;
    }

    @Override
    public int[] getAsIntArray() {
        return this.handle.d();
    }

    @Override
    public NBTNumeric.Int get(int index) {
        return new WorstNBTNumeric.Int(this.handle.d()[index]);
    }

    @Override
    public @NotNull NBTNumeric.Int set(int index, @NotNull NBTNumeric.Int value) {
        NBTNumeric.Int old = get(index);
        this.setNBT(index, value);
        return old;
    }

    @Override
    public void add(int index, @NotNull NBTNumeric.Int value) {
        this.addNBT(index, value);
    }

    @Override
    public @NotNull NBTNumeric.Int remove(int index) {
        NBTNumeric.Int old = get(index);
        if (FIELD_DATA != null) ReflectionUtils.writeField(FIELD_DATA, this.handle, ArrayUtils.remove(this.handle.d(), index));
        return old;
    }

    @Override
    public boolean setNBT(int index, @NotNull NBT value) {
        if (!(value instanceof NBTNumeric)) return false;

        Array.set(getAsIntArray(), index, ((NBTNumeric)value).getAsByte());
        return true;
    }

    @Override
    public boolean addNBT(int index, @NotNull NBT value) {
        if (!(value instanceof NBTNumeric) || FIELD_DATA == null) return false;

        ReflectionUtils.writeField(FIELD_DATA, this.handle, ArrayUtils.add(getAsIntArray(), index, ((NBTNumeric)value).getAsByte()));
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
        if (obj instanceof WorstNBTIntArray) return this.handle.equals(((WorstNBTIntArray)obj).handle);
        if (obj instanceof NBTTagIntArray) return this.handle.equals(obj);

        if (!obj.getClass().isArray()) return false;

        Class<?> type = obj.getClass().getComponentType();
        if (type == int.class) return Arrays.equals(getAsIntArray(), (int[])obj);

        if (type != Integer.class) return false;

        Integer[] wrapped = (Integer[])obj;
        int[] primitive = new int[wrapped.length];
        for (int i = 0; i < primitive.length; ++i) primitive[i] = wrapped[i];

        return Arrays.equals(getAsIntArray(), primitive);
    }

    @Override
    public String toString() {
        return this.handle.toString();
    }
}
