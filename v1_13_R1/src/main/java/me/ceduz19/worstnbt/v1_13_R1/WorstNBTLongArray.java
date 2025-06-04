package me.ceduz19.worstnbt.v1_13_R1;

import me.ceduz19.worstnbt.NBT;
import me.ceduz19.worstnbt.NBTLongArray;
import me.ceduz19.worstnbt.NBTNumeric;
import me.ceduz19.worstnbt.util.ReflectionUtils;
import net.minecraft.server.v1_13_R1.NBTBase;
import net.minecraft.server.v1_13_R1.NBTTagLongArray;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.Arrays;

class WorstNBTLongArray extends AbstractList<NBTNumeric.Long> implements NBTLongArray {

    private static final Field FIELD_DATA = ReflectionUtils.getField(NBTTagLongArray.class, "f", true);
    private final NBTTagLongArray handle;

    WorstNBTLongArray(long[] a) {
        this.handle = new NBTTagLongArray(a);
    }

    WorstNBTLongArray(NBTTagLongArray handle) {
        this.handle = handle;
    }

    @Override
    public int size() {
        return this.handle.size();
    }

    @Override
    public long[] getAsLongArray() {
        return this.handle.d();
    }

    @Override
    public NBTNumeric.Long get(int index) {
        return new WorstNBTNumeric.Long(this.handle.get(index));
    }

    @Override
    public @NotNull NBTNumeric.Long set(int index, @NotNull NBTNumeric.Long value) {
        NBTNumeric.Long old = get(index);
        this.setNBT(index, value);
        return old;
    }

    @Override
    public void add(int index, @NotNull NBTNumeric.Long value) {
        this.addNBT(index, value);
    }

    @Override
    public @NotNull NBTNumeric.Long remove(int index) {
        NBTNumeric.Long old = get(index);

        this.handle.b(index);

        return old;
    }

    @Override
    public boolean setNBT(int index, @NotNull NBT value) {
        if (!(value instanceof NBTNumeric)) return false;

        this.handle.a(index, (NBTBase) value.getHandle());

        return true;
    }

    @Override
    public boolean addNBT(int index, @NotNull NBT value) {
        if (!(value instanceof NBTNumeric) || FIELD_DATA == null) return false;

        ReflectionUtils.writeField(FIELD_DATA, this.handle, ArrayUtils.add(getAsLongArray(), index, ((NBTNumeric) value).getAsLong()));
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
        if (obj instanceof WorstNBTLongArray) return this.handle.equals(((WorstNBTLongArray) obj).handle);
        if (obj instanceof NBTTagLongArray) return this.handle.equals(obj);

        if (!obj.getClass().isArray()) return false;

        Class<?> type = obj.getClass().getComponentType();
        if (type == long.class) return Arrays.equals(getAsLongArray(), (long[]) obj);

        if (type != Long.class) return false;

        Long[] wrapped = (Long[]) obj;
        long[] primitive = new long[wrapped.length];
        for (int i = 0; i < primitive.length; ++i) primitive[i] = wrapped[i];

        return Arrays.equals(getAsLongArray(), primitive);
    }

    @Override
    public String toString() {
        return this.handle.toString();
    }

}
