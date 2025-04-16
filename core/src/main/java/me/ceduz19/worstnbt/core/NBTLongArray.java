package me.ceduz19.worstnbt.core;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface NBTLongArray extends NBTCollection<NBTNumeric.Long> {

    static @NotNull NBTLongArray create(long[] a) {
        return WorstNBT.longArray(a);
    }

    long[] getAsLongArray();

    @Override
    default @NotNull NBTType getElementType() {
        return NBTType.LONG;
    }

    @Override
    default @NotNull NBTType getType() {
        return NBTType.LONG_ARRAY;
    }
}
