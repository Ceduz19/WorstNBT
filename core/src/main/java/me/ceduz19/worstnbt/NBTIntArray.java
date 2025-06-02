package me.ceduz19.worstnbt;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface NBTIntArray extends NBTCollection<NBTNumeric.Int> {

    static @NotNull NBTIntArray create(int[] a) {
        return WorstNBT.intArray(a);
    }

    int[] getAsIntArray();

    @Override
    default @NotNull NBTType getElementType() {
        return NBTType.INT;
    }

    @Override
    default @NotNull NBTType getType() {
        return NBTType.INT_ARRAY;
    }
}
