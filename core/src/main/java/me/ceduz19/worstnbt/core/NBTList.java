package me.ceduz19.worstnbt.core;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface NBTList extends NBTCollection<NBT> {

    static @NotNull NBTList create() {
        return WorstNBT.list();
    }

    @NotNull NBTCompound getCompound(int var1);

    @NotNull NBTList getList(int var1);

    short getShort(int var1);

    int getInt(int var1);

    int[] getIntArray(int var1);

    long[] getLongArray(int var1);

    double getDouble(int var1);

    float getFloat(int var1);

    @NotNull String getString(int var1);

    @Override
    default @NotNull NBTType getType() {
        return NBTType.LIST;
    }
}
