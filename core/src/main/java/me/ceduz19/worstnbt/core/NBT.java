package me.ceduz19.worstnbt.core;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface NBT {

    boolean isEmpty();

    @NotNull NBTType getType();

    @NotNull Object getHandle();
}
