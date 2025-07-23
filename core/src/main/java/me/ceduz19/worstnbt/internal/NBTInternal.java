package me.ceduz19.worstnbt.internal;

import me.ceduz19.worstnbt.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@ApiStatus.Internal
public interface NBTInternal {

    @NotNull NBT toWorst(@NotNull Object var1);

    @NotNull NBTCompound compound();

    @NotNull NBTList list();

    @NotNull NBTEnd end();

    @NotNull NBTByteArray byteArray(byte[] array);

    @NotNull NBTIntArray intArray(int[] array);

    @NotNull NBTLongArray longArray(long[] array);

    @NotNull NBTNumeric.Byte byteNum(byte b);

    @NotNull NBTNumeric.Short shortNum(short s);

    @NotNull NBTNumeric.Int intNum(int i);

    @NotNull NBTNumeric.Long longNum(long l);

    @NotNull NBTNumeric.Float floatNum(float f);

    @NotNull NBTNumeric.Double doubleNum(double d);

    @NotNull NBTString string(@NotNull String string);

    @NotNull NBTCompound fromItemStack(@NotNull ItemStack itemStack);

    @NotNull NBTCompound fromBlock(@NotNull BlockState block);

    @NotNull NBTCompound fromEntity(@NotNull Entity entity);

    @NotNull NBTCompound fromScoreboard(@NotNull Scoreboard scoreboard);

    default @NotNull NBTCompound fromFile(@NotNull File file) throws IOException {
        return this.fromInputStream(Files.newInputStream(file.toPath()));
    }

    @NotNull NBTCompound fromInputStream(@NotNull InputStream inputStream) throws IOException;
}
