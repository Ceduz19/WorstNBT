package me.ceduz19.worstnbt.v1_13_R1;

import me.ceduz19.worstnbt.NBT;
import me.ceduz19.worstnbt.NBTCompound;
import me.ceduz19.worstnbt.NBTList;
import me.ceduz19.worstnbt.NBTType;
import net.minecraft.server.v1_13_R1.*;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_13_R1.scoreboard.CraftScoreboard;
import org.bukkit.craftbukkit.v1_13_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataOutput;
import java.io.DataOutputStream;
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
        return this.handle.getKeys();
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
        return this.handle.b(key);
    }

    @Override
    public @Nullable NBT put(@NotNull String key, @Nullable NBT nbt) {
        NBTBase old = this.handle.get(key);

        if (nbt == null) this.handle.remove(key);
        else this.handle.set(key, (NBTBase) nbt.getHandle());

        return old == null ? null : WorstNBTInternal.get().toWorst(old);
    }

    @Override
    public void putByte(@NotNull String key, byte b) {
        this.handle.setByte(key, b);
    }

    @Override
    public void putShort(@NotNull String key, short s) {
        this.handle.setShort(key, s);
    }

    @Override
    public void putInt(@NotNull String key, int i) {
        this.handle.setInt(key, i);
    }

    @Override
    public void putLong(@NotNull String key, long l) {
        this.handle.setLong(key, l);
    }

    @Override
    public void putUUID(@NotNull String key, @Nullable UUID uuid) {
        if (uuid != null) {
            this.handle.a(key, uuid);
            return;
        }

        if (this.handle.hasKeyOfType(key + "Most", NBTType.ANY_NUMERIC.asId()) && this.handle.hasKeyOfType(key + "Least", NBTType.ANY_NUMERIC.asId())) {
            this.handle.remove(key + "Most");
            this.handle.remove(key + "Least");
        }
    }

    @Override
    public void putFloat(@NotNull String key, float f) {
        this.handle.setFloat(key, f);
    }

    @Override
    public void putDouble(@NotNull String key, double d) {
        this.handle.setDouble(key, d);
    }

    @Override
    public void putString(@NotNull String key, @Nullable String string) {
        if (string != null) this.handle.setString(key, string);
        else if (this.getType(key) == NBTType.STRING) this.handle.remove(key);
    }

    @Override
    public void putByteArray(@NotNull String key, byte[] array) {
        this.handle.setByteArray(key, array);
    }

    @Override
    public void putIntArray(@NotNull String key, int[] array) {
        this.handle.setIntArray(key, array);
    }

    @Override
    public void putLongArray(@NotNull String key, long[] array) {
        this.handle.a(key, array);
    }

    @Override
    public void putBoolean(@NotNull String key, boolean bool) {
        this.handle.setBoolean(key, bool);
    }

    @Override
    public @NotNull NBTType getType(@NotNull String key) {
        return Objects.requireNonNull(NBTType.byId(this.handle.d(key)));
    }

    @Override
    public @Nullable NBT get(@NotNull String key) {
        NBTBase nbt = this.handle.get(key);
        return nbt == null ? null : WorstNBTInternal.get().toWorst(nbt);
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
        return Objects.requireNonNull(this.handle.a(key));
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
        NBT nbt = get(key);
        return nbt instanceof WorstNBTLongArray ? ((WorstNBTLongArray) nbt).getAsLongArray() : new long[0];
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
    public @NotNull NBTList getList(@NotNull String key, @NotNull NBTType elementType) {
        return new WorstNBTList(this.handle.getList(key, elementType.asId()));
    }

    @Override
    @NotNull
    public NBTCompound merge(@NotNull NBTCompound other) {
        this.handle.a((NBTTagCompound)other.getHandle());
        return this;
    }

    @Override
    public boolean applyToItemStack(@NotNull ItemStack itemStack) {
        net.minecraft.server.v1_13_R1.ItemStack nms = net.minecraft.server.v1_13_R1.ItemStack.a(handle);

        itemStack.setType(CraftMagicNumbers.getMaterial(nms.getItem()));
        itemStack.setAmount(nms.getCount());
        itemStack.setItemMeta(CraftItemStack.asCraftMirror(nms).getItemMeta());
        return true;
    }

    @Override
    public boolean applyToEntity(@NotNull Entity entity) {
        net.minecraft.server.v1_13_R1.Entity nms = ((CraftEntity) entity).getHandle();
        try {
            nms.f(this.handle);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean applyToScoreboard(@NotNull Scoreboard scoreboard) {
        ScoreboardServer nms = ((ScoreboardServer) ((CraftScoreboard) scoreboard).getHandle());
        PersistentScoreboard data = new PersistentScoreboard();

        data.a(nms);
        data.a(this.handle);
        return true;
    }

    @Override
    public boolean saveToPath(@NotNull Path path, boolean compressed) {
        try {
            if (compressed) NBTCompressedStreamTools.a(this.handle, Files.newOutputStream(path));
            else NBTCompressedStreamTools.a(this.handle, (DataOutput) new DataOutputStream(Files.newOutputStream(path)));

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
