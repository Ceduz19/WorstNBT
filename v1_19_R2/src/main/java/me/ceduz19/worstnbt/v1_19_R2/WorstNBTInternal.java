package me.ceduz19.worstnbt.v1_19_R2;

import me.ceduz19.worstnbt.*;
import me.ceduz19.worstnbt.internal.NBTInternal;
import net.minecraft.nbt.*;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R2.scoreboard.CraftScoreboard;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

class WorstNBTInternal implements NBTInternal {

    private static WorstNBTInternal INSTANCE;

    static WorstNBTInternal get() {
        if (INSTANCE == null) throw new IllegalStateException(WorstNBTInternal.class.getCanonicalName() + " not initialized");
        return INSTANCE;
    }

    {
        INSTANCE = this;
    }

    @Override
    public @NotNull NBT toWorst(@NotNull Object nms) {
        if (!(nms instanceof Tag))
            throw new IllegalArgumentException("Unable to convert to worst NBT: " + nms.getClass().getCanonicalName() +
                    " is not an instance of " + Tag.class.getCanonicalName());

        if (nms instanceof ByteArrayTag tag) return new WorstNBTCollection.ByteArray(tag);
        if (nms instanceof CompoundTag tag) return new WorstNBTCompound(tag);
        if (nms instanceof EndTag) return WorstNBTEnd.INSTANCE;
        if (nms instanceof IntArrayTag tag) return new WorstNBTCollection.IntArray(tag);
        if (nms instanceof ListTag tag) return new WorstNBTList(tag);
        if (nms instanceof LongArrayTag tag) new WorstNBTCollection.LongArray(tag);
        if (nms instanceof ByteTag tag) return new WorstNBTNumeric.Byte(tag);
        if (nms instanceof ShortTag tag) return new WorstNBTNumeric.Short(tag);
        if (nms instanceof IntTag tag) return new WorstNBTNumeric.Int(tag);
        if (nms instanceof LongTag tag) return new WorstNBTNumeric.Long(tag);
        if (nms instanceof FloatTag tag) return new WorstNBTNumeric.Float(tag);
        if (nms instanceof DoubleTag tag) return new WorstNBTNumeric.Double(tag);
        if (nms instanceof StringTag tag) return new WorstNBTString(tag);

        throw new IllegalStateException("Unable to convert to worst NBT: " + nms.getClass().getCanonicalName() +
                    " is an unknown " + Tag.class.getCanonicalName() + " implementation");
    }

    @Override
    public @NotNull NBTByteArray byteArray(byte[] array) {
        return new WorstNBTCollection.ByteArray(array);
    }

    @Override
    public @NotNull NBTCompound compound() {
        return new WorstNBTCompound();
    }

    @Override
    public @NotNull NBTEnd end() {
        return WorstNBTEnd.INSTANCE;
    }

    @Override
    public @NotNull NBTIntArray intArray(int[] array) {
        return new WorstNBTCollection.IntArray(array);
    }

    @Override
    public @NotNull NBTList list() {
        return new WorstNBTList();
    }

    @Override
    public @NotNull NBTLongArray longArray(long[] array) {
        return new WorstNBTCollection.LongArray(array);
    }

    @Override
    public @NotNull NBTNumeric.Byte byteNum(byte b) {
        return new WorstNBTNumeric.Byte(b);
    }

    @Override
    public @NotNull NBTNumeric.Short shortNum(short s) {
        return new WorstNBTNumeric.Short(s);
    }

    @Override
    public @NotNull NBTNumeric.Int intNum(int i) {
        return new WorstNBTNumeric.Int(i);
    }

    @Override
    public @NotNull NBTNumeric.Long longNum(long l) {
        return new WorstNBTNumeric.Long(l);
    }

    @Override
    public @NotNull NBTNumeric.Float floatNum(float f) {
        return new WorstNBTNumeric.Float(f);
    }

    @Override
    public @NotNull NBTNumeric.Double doubleNum(double d) {
        return new WorstNBTNumeric.Double(d);
    }

    @Override
    public @NotNull NBTString string(@NotNull String string) {
        return new WorstNBTString(string);
    }


    @Override
    public @NotNull NBTCompound fromItemStack(@NotNull org.bukkit.inventory.ItemStack itemStack) {
        ItemStack nms = CraftItemStack.asNMSCopy(itemStack);
        return new WorstNBTCompound(nms.save(new CompoundTag()));
    }

    @Override
    public @NotNull NBTCompound fromEntity(@NotNull Entity entity) {
        net.minecraft.world.entity.Entity nms = ((CraftEntity) entity).getHandle();
        CompoundTag tag = new CompoundTag();

        String id = nms.getEncodeId();
        if (id != null) tag.putString("id", id);

        nms.saveWithoutId(tag);
        return new WorstNBTCompound(tag);
    }

    @Override
    public @NotNull NBTCompound fromScoreboard(@NotNull Scoreboard scoreboard) {
        ServerScoreboard nms = (ServerScoreboard) ((CraftScoreboard) scoreboard).getHandle();
        CompoundTag nbt = new CompoundTag();

        nms.createData().save(nbt);
        return new WorstNBTCompound(nbt);
    }

    @Override
    public @NotNull NBTCompound fromInputStream(@NotNull InputStream inputStream) throws IOException {
        CompoundTag tag;
        try {
            tag = NbtIo.readCompressed(inputStream);
        } catch (IOException e) {
            tag = NbtIo.read(new DataInputStream(inputStream));
        }
        return new WorstNBTCompound(tag);
    }
}
