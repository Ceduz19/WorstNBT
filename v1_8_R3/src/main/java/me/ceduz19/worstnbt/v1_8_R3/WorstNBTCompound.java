package me.ceduz19.worstnbt.v1_8_R3;

import me.ceduz19.worstnbt.core.*;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagIntArray;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

class WorstNBTCompound implements NBTCompound {

    private final NBTTagCompound handle;

    WorstNBTCompound() {
        this(new NBTTagCompound());
    }

    WorstNBTCompound(NBTTagCompound handle) {
        this.handle = handle;
    }

    @Override
    public @NotNull Set<String> getAllKeys() {
        return this.handle.c();
    }

    @Override
    public boolean contains(@NotNull String key) {
        return this.handle.hasKey(key);
    }

    @Override
    public boolean contains(@NotNull String key, @NotNull NBTType type) {
        return this.handle.hasKeyOfType(key, type.asId());
    }

    @Override
    public boolean hasUUID(@NotNull String key) {
        if (this.contains(key + "Most", NBTType.ANY_NUMERIC) && this.contains(key + "Least", NBTType.ANY_NUMERIC))
            return true;

        NBTBase nbt = this.handle.get(key);
        return nbt instanceof NBTTagIntArray && ((NBTTagIntArray) nbt).c().length == 4;
    }

    @Override
    public @Nullable NBT put(@NotNull String key, @Nullable NBT value) {
        NBTBase old = this.handle.get(key);

        if (value == null) this.handle.remove(key);
        else this.handle.set(key, (NBTBase)value.getHandle());

        return old == null ? null : WorstNBT.getInternal().toWorst(old);
    }

    @Override
    public void putByte(@NotNull String key, byte value) {
        this.handle.setByte(key, value);
    }

    @Override
    public void putShort(@NotNull String key, short value) {
        this.handle.setShort(key, value);
    }

    @Override
    public void putInt(@NotNull String key, int value) {
        this.handle.setInt(key, value);
    }

    @Override
    public void putLong(@NotNull String key, long value) {
        this.handle.setLong(key, value);
    }

    @Override
    public void putUUID(@NotNull String key, @Nullable UUID value) {
        if (this.handle.hasKeyOfType(key + "Most", NBTType.ANY_NUMERIC.asId()) && this.handle.hasKeyOfType(key + "Least", NBTType.ANY_NUMERIC.asId())) {
            this.handle.remove(key + "Most");
            this.handle.remove(key + "Least");
        }

        if (value == null) {
            if (this.handle.hasKeyOfType(key, NBTType.INT_ARRAY.asId())) this.handle.c().remove(key);
            return;
        }

        long most = value.getMostSignificantBits();
        long least = value.getLeastSignificantBits();
        this.handle.set(key, new NBTTagIntArray(new int[]{(int)(most >> 32), (int)most, (int)(least >> 32), (int)least}));
    }

    @Override
    public void putFloat(@NotNull String key, float value) {
        this.handle.setFloat(key, value);
    }

    @Override
    public void putDouble(@NotNull String key, double value) {
        this.handle.setDouble(key, value);
    }

    @Override
    public void putString(@NotNull String key, @Nullable String value) {
        if (value != null) this.handle.setString(key, value);
        else if (this.getType(key) == NBTType.STRING) this.handle.remove(key);
    }

    @Override
    public void putByteArray(@NotNull String key, byte[] value) {
        this.handle.setByteArray(key, value);
    }

    @Override
    public void putIntArray(@NotNull String key, int[] value) {
        this.handle.setIntArray(key, value);
    }

    @Override
    public void putLongArray(@NotNull String key, long[] value) {
    }

    @Override
    public void putBoolean(@NotNull String key, boolean value) {
        this.handle.setBoolean(key, value);
    }

    @Override
    public @NotNull NBTType getType(@NotNull String key) {
        return Objects.requireNonNull(NBTType.byId(this.handle.b(key)));
    }

    @Override
    public @Nullable NBT get(@NotNull String key) {
        NBTBase nbt = this.handle.get(key);
        return nbt == null ? null : WorstNBT.getInternal().toWorst(nbt);
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
        if (this.handle.hasKeyOfType(key, NBTType.INT_ARRAY.asId())) {
            int[] array = this.handle.getIntArray(key);
            if (array.length != 4)
                throw new IllegalStateException("Expected UUID array to be of length 4, but found " + array.length);

            return new UUID((long)array[0] << 32 | (long)array[1] & Long.MAX_VALUE, (long)array[2] << 32 | (long)array[3] & Long.MAX_VALUE);
        }

        if (!this.handle.hasKeyOfType(key + "Most", NBTType.ANY_NUMERIC.asId()) || !this.handle.hasKeyOfType(key + "Least", NBTType.ANY_NUMERIC.asId()))
            throw new IllegalStateException(key + "Most and " + key + "Least keys must be of any numeric type in order to get an UUID");

        return new UUID(this.handle.getLong(key + "Most"), this.handle.getLong(key + "Least"));
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
        return new long[0];
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
    @NotNull
    public NBTCompound merge(@NotNull NBTCompound other) {
        this.handle.a((NBTTagCompound)other.getHandle());
        return this;
    }

    @Override
    public boolean applyToItemStack(@NotNull ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack nms = net.minecraft.server.v1_8_R3.ItemStack.createStack(this.handle);
        if (nms == null) return false;

        itemStack.setItemMeta(CraftItemStack.asCraftMirror(nms).getItemMeta());
        return true;
    }

    @Override
    public boolean applyToEntity(@NotNull Entity entity) {
        net.minecraft.server.v1_8_R3.Entity nms = ((CraftEntity) entity).getHandle();
        try {
            nms.f(this.handle);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean saveToPath(@NotNull Path path, boolean compressed) {
        try {
            if (compressed) NBTCompressedStreamTools.a(this.handle, Files.newOutputStream(path));
            else NBTCompressedStreamTools.a(this.handle, (OutputStream) new DataOutputStream(Files.newOutputStream(path)));

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
        return obj == this || handle.equals(obj instanceof WorstNBTCompound ? ((WorstNBTCompound) obj).handle : obj);
    }

    public String toString() {
        return this.handle.toString();
    }
}
