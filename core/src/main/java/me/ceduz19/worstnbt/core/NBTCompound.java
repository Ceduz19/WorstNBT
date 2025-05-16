package me.ceduz19.worstnbt.core;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;
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

    static @NotNull NBTCompound fromScoreboard(Scoreboard scoreboard) {
        return WorstNBT.fromScoreboard(scoreboard);
    }

    static @NotNull NBTCompound fromFile(File file) throws IOException {
        return WorstNBT.fromFile(file);
    }

    static @NotNull NBTCompound fromInputStream(InputStream inputStream) throws IOException {
        return WorstNBT.fromInputStream(inputStream);
    }

    @NotNull Set<String> getAllKeys();

    boolean contains(@NotNull String key);
    boolean contains(@NotNull String key, @NotNull NBTType type);
    boolean hasUUID(@NotNull String key);

    @Nullable NBT put(@NotNull String key, @Nullable NBT nbt);
    void putByte(@NotNull String key, byte b);
    void putShort(@NotNull String key, short s);
    void putInt(@NotNull String key, int i);
    void putLong(@NotNull String key, long l);
    void putUUID(@NotNull String key, @Nullable UUID uuid);
    void putFloat(@NotNull String key, float f);
    void putDouble(@NotNull String key, double d);
    void putString(@NotNull String key, @Nullable String string);
    void putByteArray(@NotNull String key, byte[] array);
    void putIntArray(@NotNull String key, int[] array);
    void putLongArray(@NotNull String key, long[] array);
    void putBoolean(@NotNull String key, boolean bool);

    @NotNull NBTType getType(@NotNull String key);

    @Nullable NBT get(@NotNull String key);
    byte getByte(@NotNull String key);
    short getShort(@NotNull String key);
    int getInt(@NotNull String key);
    long getLong(@NotNull String key);
    @NotNull UUID getUUID(@NotNull String key);
    float getFloat(@NotNull String key);
    double getDouble(@NotNull String key);
    @NotNull String getString(@NotNull String key);
    byte[] getByteArray(@NotNull String key);
    int[] getIntArray(@NotNull String key);
    long[] getLongArray(@NotNull String key);
    boolean getBoolean(@NotNull String key);
    @NotNull NBTCompound getCompound(@NotNull String key);
    @NotNull NBTList getList(@NotNull String key, @NotNull NBTType elementType);

    @NotNull NBTCompound merge(@NotNull NBTCompound var1);

    // APPLY METHODS

    boolean applyToItemStack(@NotNull ItemStack itemStack);

    boolean applyToEntity(@NotNull Entity entity);

    boolean applyToScoreboard(@NotNull Scoreboard scoreboard);

    default boolean saveToFile(@NotNull File file, boolean compressed) {
        return this.saveToPath(file.toPath(), compressed);
    }

    boolean saveToPath(@NotNull Path path, boolean compressed);

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

    default @Nullable NBTList getListOrNull(@NotNull String key, @NotNull NBTType elementType) {
        if (!this.contains(key, NBTType.LIST)) return null;

        NBTList list = (NBTList) this.get(key);
        return list != null && (elementType == NBTType.END || list.isEmpty() || list.getElementType() == elementType) ? list : null;
    }

    default @NotNull NBTCompound getOrCreateCompound(@NotNull String key) {
        if (contains(key, NBTType.COMPOUND)) return getCompound(key);

        NBTCompound compound = create();
        put(key, compound);
        return compound;
    }

    default @NotNull NBTList getOrCreateList(@NotNull String key, @NotNull NBTType elementType) {
        NBTList list;

        if (contains(key, NBTType.LIST)) {
            list = Objects.requireNonNull((NBTList) get(key));

            // if we want a list of a specific type; in case the contained one is not empty (then is of a type)
            // and is not of the desired type, create a new one
            if (elementType != NBTType.END && !list.isEmpty() && elementType != list.getElementType()) {
                list = NBTList.create();
                put(key, list);
            }

        } else {
            list = NBTList.create();
            put(key, list);
        }

        return list;
    }
}
