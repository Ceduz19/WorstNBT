package me.ceduz19.worstnbt.core.internal;

import me.ceduz19.worstnbt.core.*;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@ApiStatus.Internal
public interface NBTInternal {

    @NotNull NBT toWorst(@NotNull Object var1);

    @NotNull NBTByteArray byteArray(byte[] var1);

    @NotNull NBTCompound compound();

    @NotNull NBTEnd end();

    @NotNull NBTIntArray intArray(int[] var1);

    @NotNull NBTList list();

    @NotNull NBTLongArray longArray(long[] var1);

    @NotNull NBTNumeric.Byte byteNum(byte var1);

    @NotNull NBTNumeric.Short shortNum(short var1);

    @NotNull NBTNumeric.Int intNum(int var1);

    @NotNull NBTNumeric.Long longNum(long var1);

    @NotNull NBTNumeric.Float floatNum(float var1);

    @NotNull NBTNumeric.Double doubleNum(double var1);

    @NotNull NBTString string(@NotNull String var1);

    @NotNull NBTCompound fromItemStack(@NotNull ItemStack var1);

    @NotNull NBTCompound fromEntity(@NotNull Entity var1);

    default @NotNull NBTCompound fromFile(@NotNull File file) throws IOException {
        return this.fromInputStream(Files.newInputStream(file.toPath()));
    }

    @NotNull NBTCompound fromInputStream(@NotNull InputStream var1) throws IOException;
}
