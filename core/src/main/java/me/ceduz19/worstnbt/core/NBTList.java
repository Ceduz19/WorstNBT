package me.ceduz19.worstnbt.core;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.NonExtendable
public interface NBTList extends NBTCollection<NBT> {

    static @NotNull NBTList create() {
        return WorstNBT.list();
    }

    @NotNull NBTCompound getCompound(int index);

    @NotNull NBTList getList(int index);

    short getShort(int index);

    int getInt(int index);

    int[] getIntArray(int index);

    long[] getLongArray(int index);

    double getDouble(int index);

    float getFloat(int index);

    @NotNull String getString(int index);

    @Override
    default @NotNull NBTType getType() {
        return NBTType.LIST;
    }

    default @Nullable NBTCompound getCompoundOrNull(int index) {
        return getElementType() == NBTType.COMPOUND ? getCompound(index) : null;
    }

    default @Nullable NBTList getListOrNull(int index) {
        return getElementType() == NBTType.LIST ? getList(index) : null;
    }
}
