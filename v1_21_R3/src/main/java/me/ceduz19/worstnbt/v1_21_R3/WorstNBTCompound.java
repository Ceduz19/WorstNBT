package me.ceduz19.worstnbt.v1_21_R3;

import me.ceduz19.worstnbt.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

class WorstNBTCompound implements NBTCompound {

    private final CompoundTag handle;

    WorstNBTCompound() {
        this(new CompoundTag());
    }

    public WorstNBTCompound(CompoundTag handle) {
        this.handle = handle;
    }

    @Override
    public @NotNull Set<String> getAllKeys() {
        return this.handle.getAllKeys();
    }

    @Override
    public boolean contains(@NotNull String key) {
        return this.handle.contains(key);
    }

    @Override
    public boolean contains(@NotNull String key, @NotNull NBTType type) {
        return this.handle.contains(key, type.asId());
    }

    @Override
    public boolean hasUUID(@NotNull String key) {
        return this.handle.hasUUID(key);
    }

    @Override
    public @Nullable NBT put(@NotNull String key, @Nullable NBT value) {
        Tag old;
        if (value == null) {
            old = this.handle.get(key);
            this.handle.remove(key);
        } else {
            old = this.handle.put(key, (Tag) value.getHandle());
        }
        return old == null ? null : WorstNBT.getInternal().toWorst(old);
    }

    @Override
    public void putByte(@NotNull String key, byte value) {
        this.handle.putByte(key, value);
    }

    @Override
    public void putShort(@NotNull String key, short value) {
        this.handle.putShort(key, value);
    }

    @Override
    public void putInt(@NotNull String key, int value) {
        this.handle.putInt(key, value);
    }

    @Override
    public void putLong(@NotNull String key, long value) {
        this.handle.putLong(key, value);
    }

    @Override
    public void putUUID(@NotNull String key, @Nullable UUID value) {
        if (value != null) this.handle.putUUID(key, value);
        else if (this.hasUUID(key)) this.handle.remove(key);
    }

    @Override
    public void putFloat(@NotNull String key, float value) {
        this.handle.putFloat(key, value);
    }

    @Override
    public void putDouble(@NotNull String key, double value) {
        this.handle.putDouble(key, value);
    }

    @Override
    public void putString(@NotNull String key, @Nullable String value) {
        if (value != null) this.handle.putString(key, value);
        else if (this.getType(key) == NBTType.STRING) this.handle.remove(key);
    }

    @Override
    public void putByteArray(@NotNull String key, byte[] value) {
        this.handle.putByteArray(key, value);
    }

    @Override
    public void putIntArray(@NotNull String key, int[] value) {
        this.handle.putIntArray(key, value);
    }

    @Override
    public void putLongArray(@NotNull String key, long[] value) {
        this.handle.putLongArray(key, value);
    }

    @Override
    public void putBoolean(@NotNull String key, boolean value) {
        this.handle.putBoolean(key, value);
    }

    @Override
    public @NotNull NBTType getType(@NotNull String key) {
        return Objects.requireNonNull(NBTType.byId(this.handle.getTagType(key)));
    }

    @Override
    public @Nullable NBT get(@NotNull String key) {
        Tag tag = this.handle.get(key);
        return tag == null ? null : WorstNBT.getInternal().toWorst(tag);
    }

    @Override
    public byte getByte(@NotNull String key) {
        return this.handle.getByte(key);
    }

    @Override
    public short getShort(@NotNull String key) {
        return this.handle.getShort(key);
    }

    @Override
    public int getInt(@NotNull String key) {
        return this.handle.getInt(key);
    }

    @Override
    public long getLong(@NotNull String key) {
        return this.handle.getLong(key);
    }

    @Override
    public @NotNull UUID getUUID(@NotNull String key) {
        return this.handle.getUUID(key);
    }

    @Override
    public float getFloat(@NotNull String key) {
        return this.handle.getFloat(key);
    }

    @Override
    public double getDouble(@NotNull String key) {
        return this.handle.getDouble(key);
    }

    @Override
    public @NotNull String getString(@NotNull String key) {
        return this.handle.getString(key);
    }

    @Override
    public byte[] getByteArray(@NotNull String key) {
        return this.handle.getByteArray(key);
    }

    @Override
    public int[] getIntArray(@NotNull String key) {
        return this.handle.getIntArray(key);
    }

    @Override
    public long[] getLongArray(@NotNull String key) {
        return this.handle.getLongArray(key);
    }

    @Override
    public boolean getBoolean(@NotNull String key) {
        return this.handle.getBoolean(key);
    }

    @Override
    public @NotNull NBTCompound getCompound(@NotNull String key) {
        return new WorstNBTCompound(this.handle.getCompound(key));
    }

    @Override
    public @NotNull NBTList getList(@NotNull String key, @NotNull NBTType type) {
        return new WorstNBTList(this.handle.getList(key, type.asId()));
    }

    @Override
    public @NotNull NBTCompound merge(@NotNull NBTCompound other) {
        this.handle.merge(((WorstNBTCompound) other).handle);
        return this;
    }

    @Override
    public boolean applyToItemStack(@NotNull org.bukkit.inventory.ItemStack itemStack) {
        Optional<ItemStack> opt = net.minecraft.world.item.ItemStack.parse(MinecraftServer.getServer().registries().compositeAccess(), this.handle);
        if (opt.isEmpty()) return false;

        org.bukkit.inventory.ItemStack newItemStack = opt.get().asBukkitMirror();
        //noinspection deprecation
        itemStack.setType(newItemStack.getType());
        itemStack.setAmount(newItemStack.getAmount());
        itemStack.setItemMeta(newItemStack.getItemMeta());
        return true;
    }

    @Override
    public boolean applyToEntity(@NotNull Entity entity) {
        net.minecraft.world.entity.Entity nms = ((CraftEntity) entity).getHandle();
        try {
            nms.load(this.handle);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean saveToPath(@NotNull Path path, boolean compressed) {
        try {
            if (compressed) NbtIo.writeCompressed(this.handle, path);
            else NbtIo.write(this.handle, path);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isEmpty() {
        return this.handle.isEmpty();
    }

    @Override
    public @NotNull Object getHandle() {
        return this.handle;
    }

    public int hashCode() {
        return this.handle.hashCode();
    }

    public boolean equals(Object obj) {
        return obj == this || this.handle.equals(obj instanceof WorstNBTCompound w ? w.handle : obj);
    }

    public String toString() {
        return this.handle.toString();
    }
}
