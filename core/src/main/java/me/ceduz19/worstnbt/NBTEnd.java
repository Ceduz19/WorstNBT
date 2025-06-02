package me.ceduz19.worstnbt;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface NBTEnd extends NBT {

    static @NotNull NBTEnd create() {
        return WorstNBT.end();
    }

    @Override
    default @NotNull NBTType getType() {
        return NBTType.END;
    }
}
