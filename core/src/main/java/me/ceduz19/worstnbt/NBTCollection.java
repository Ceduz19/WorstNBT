package me.ceduz19.worstnbt;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@ApiStatus.NonExtendable
public interface NBTCollection<T extends NBT> extends NBT, List<T> {

    boolean setNBT(int var1, @NotNull NBT var2);

    boolean addNBT(int var1, @NotNull NBT var2);

    @NotNull NBTType getElementType();
}
