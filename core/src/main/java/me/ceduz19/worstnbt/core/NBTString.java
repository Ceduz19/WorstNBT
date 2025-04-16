package me.ceduz19.worstnbt.core;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface NBTString extends NBT {

    static @NotNull NBTString create(@NotNull String s) {
        return WorstNBT.string(s);
    }

    @NotNull String getAsString();

    @Override
    default @NotNull NBTType getType() {
        return NBTType.STRING;
    }
}
