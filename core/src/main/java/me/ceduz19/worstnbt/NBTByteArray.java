package me.ceduz19.worstnbt;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface NBTByteArray extends NBTCollection<NBTNumeric.Byte> {

    static @NotNull NBTByteArray create(byte[] a) {
        return WorstNBT.byteArray(a);
    }

    byte[] getAsByteArray();

    @Override
    default @NotNull NBTType getElementType() {
        return NBTType.BYTE;
    }

    @Override
    default @NotNull NBTType getType() {
        return NBTType.BYTE_ARRAY;
    }
}
