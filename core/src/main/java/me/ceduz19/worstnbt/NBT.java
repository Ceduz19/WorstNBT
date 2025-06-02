package me.ceduz19.worstnbt;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface NBT {

    boolean isEmpty();

    @NotNull NBTType getType();

    @NotNull Object getHandle();
}
