package me.ceduz19.worstnbt.v1_12_R1;

import me.ceduz19.worstnbt.NBT;
import me.ceduz19.worstnbt.NBTLongArray;
import me.ceduz19.worstnbt.NBTNumeric;
import me.ceduz19.worstnbt.util.ReflectionUtils;
import net.minecraft.server.v1_12_R1.NBTTagLongArray;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Objects;

class WorstNBTLongArray extends AbstractList<NBTNumeric.Long> implements NBTLongArray {

    private static final Field FIELD_DATA = ReflectionUtils.getField(NBTTagLongArray.class, "b", true);
    private final NBTTagLongArray handle;

    private Object array = null;
    private boolean modified = false;

    WorstNBTLongArray(long[] a) {
        this.handle = new NBTTagLongArray(a);
        this.array = a;
    }

    WorstNBTLongArray(NBTTagLongArray handle) {
        this.handle = handle;
        refresh(true);
    }

    @Override
    public int size() {
        return getArray().length;
    }

    @Override
    public long[] getAsLongArray() {
        return getArray();
    }

    @Override
    public NBTNumeric.Long get(int index) {
        return new WorstNBTNumeric.Long(getArray()[index]);
    }

    @Override
    public @NotNull NBTNumeric.Long set(int index, @NotNull NBTNumeric.Long value) {
        long old = getArray()[index];
        this.setNBT(index, value);
        return new WorstNBTNumeric.Long(old);
    }

    @Override
    public void add(int index, @NotNull NBTNumeric.Long value) {
        this.addNBT(index, value);
    }

    @Override
    public @NotNull NBTNumeric.Long remove(int index) {
        long old = getArray()[index];

        ReflectionUtils.writeField(Objects.requireNonNull(FIELD_DATA), this.handle, ArrayUtils.remove(getArray(), index));
        modified = true;

        return new WorstNBTNumeric.Long(old);
    }

    @Override
    public boolean setNBT(int index, @NotNull NBT value) {
        if (!(value instanceof NBTNumeric)) return false;

        Array.set(getArray(), index, ((NBTNumeric) value).getAsLong());
        modified = true;

        return true;
    }

    @Override
    public boolean addNBT(int index, @NotNull NBT value) {
        if (!(value instanceof NBTNumeric)) return false;

        ReflectionUtils.writeField(Objects.requireNonNull(FIELD_DATA), this.handle, ArrayUtils.add(getArray(), index, ((NBTNumeric) value).getAsLong()));
        modified = true;

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
        if (type == long.class) return Arrays.equals(getArray(), (long[]) obj);

        if (type != Long.class) return false;

        Long[] wrapped = (Long[]) obj;
        long[] primitive = new long[wrapped.length];
        for (int i = 0; i < primitive.length; ++i) primitive[i] = wrapped[i];

        return Arrays.equals(getArray(), primitive);
    }

    @Override
    public String toString() {
        return this.handle.toString();
    }

    private long[] getArray() {
        refresh(false);

        if (array == null) throw new IllegalStateException("unable to get long array");

        return (long[]) array;
    }

    private void refresh(boolean init) {
        if (!init && !modified) return;

        modified = false;
        array = FIELD_DATA == null ? null : ReflectionUtils.readField(FIELD_DATA, handle);
    }
}
