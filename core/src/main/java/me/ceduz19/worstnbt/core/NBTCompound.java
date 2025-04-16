package me.ceduz19.worstnbt.core;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Set;
import java.util.UUID;

@ApiStatus.NonExtendable
public interface NBTCompound extends NBT {

    static @NotNull NBTCompound create() {
        return WorstNBT.compound();
    }

    static @NotNull NBTCompound fromItemStack(ItemStack itemStack) {
        return WorstNBT.fromItemStack(itemStack);
    }

    static @NotNull NBTCompound fromEntity(Entity entity) {
        return WorstNBT.fromEntity(entity);
    }

    static @NotNull NBTCompound fromFile(File file) throws IOException {
        return WorstNBT.fromFile(file);
    }

    static @NotNull NBTCompound fromInputStream(InputStream inputStream) throws IOException {
        return WorstNBT.fromInputStream(inputStream);
    }

    @NotNull Set<String> getAllKeys();

    boolean contains(@NotNull String var1);
    boolean contains(@NotNull String var1, @NotNull NBTType var2);
    boolean hasUUID(@NotNull String var1);

    @Nullable NBT put(@NotNull String var1, @Nullable NBT var2);
    void putByte(@NotNull String var1, byte var2);
    void putShort(@NotNull String var1, short var2);
    void putInt(@NotNull String var1, int var2);
    void putLong(@NotNull String var1, long var2);
    void putUUID(@NotNull String var1, @Nullable UUID var2);
    void putFloat(@NotNull String var1, float var2);
    void putDouble(@NotNull String var1, double var2);
    void putString(@NotNull String var1, @Nullable String var2);
    void putByteArray(@NotNull String var1, byte[] var2);
    void putIntArray(@NotNull String var1, int[] var2);
    void putLongArray(@NotNull String var1, long[] var2);
    void putBoolean(@NotNull String var1, boolean var2);

    @NotNull NBTType getType(@NotNull String var1);

    @Nullable NBT get(@NotNull String var1);
    byte getByte(@NotNull String var1);
    short getShort(@NotNull String var1);
    int getInt(@NotNull String var1);
    long getLong(@NotNull String var1);
    @NotNull UUID getUUID(@NotNull String var1);
    float getFloat(@NotNull String var1);
    double getDouble(@NotNull String var1);
    @NotNull String getString(@NotNull String var1);
    byte[] getByteArray(@NotNull String var1);
    int[] getIntArray(@NotNull String var1);
    long[] getLongArray(@NotNull String var1);
    boolean getBoolean(@NotNull String var1);
    @NotNull NBTCompound getCompound(@NotNull String var1);
    @NotNull NBTList getList(@NotNull String var1, @NotNull NBTType var2);

    @NotNull NBTCompound merge(@NotNull NBTCompound var1);

    boolean applyToItemStack(@NotNull ItemStack var1);
    boolean applyToEntity(@NotNull Entity var1);
    default boolean saveToFile(@NotNull File file, boolean compressed) {
        return this.saveToPath(file.toPath(), compressed);
    }
    boolean saveToPath(@NotNull Path var1, boolean var2);

    @Override
    default @NotNull NBTType getType() {
        return NBTType.COMPOUND;
    }

    default @Nullable UUID getUUIDOrNull(@NotNull String key) {
        return this.hasUUID(key) ? this.getUUID(key) : null;
    }

    default @Nullable String getStringOrNull(@NotNull String key) {
        return this.contains(key, NBTType.STRING) ? this.getString(key) : null;
    }

    default @Nullable NBTCompound getCompoundOrNull(@NotNull String key) {
        return this.contains(key, NBTType.COMPOUND) ? this.getCompound(key) : null;
    }

    default @Nullable NBTList getListOrNull(@NotNull String key, @NotNull NBTType type) {
        if (!this.contains(key, NBTType.LIST)) return null;

        NBTList list = (NBTList)this.get(key);
        return list != null && list.getElementType() == type ? list : null;
    }
}
