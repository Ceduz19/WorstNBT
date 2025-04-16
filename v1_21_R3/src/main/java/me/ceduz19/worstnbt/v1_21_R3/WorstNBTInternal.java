package me.ceduz19.worstnbt.v1_21_R3;

import me.ceduz19.worstnbt.core.*;
import me.ceduz19.worstnbt.core.internal.NBTInternal;
import net.minecraft.nbt.*;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

class WorstNBTInternal implements NBTInternal {

    @Override
    public @NotNull NBT toWorst(@NotNull Object nms) {
        if (!(nms instanceof Tag))
            throw new IllegalArgumentException("Unable to convert to worst NBT: " + nms.getClass().getCanonicalName() +
                    " is not an instance of " + Tag.class.getCanonicalName());

        return switch (nms) {
            case ByteArrayTag tag -> new WorstNBTCollection.ByteArray(tag);
            case CompoundTag tag -> new WorstNBTCompound(tag);
            case EndTag ignore -> WorstNBTEnd.INSTANCE;
            case IntArrayTag tag -> new WorstNBTCollection.IntArray(tag);
            case ListTag tag -> new WorstNBTList(tag);
            case LongArrayTag tag -> new WorstNBTCollection.LongArray(tag);
            case ByteTag tag -> new WorstNBTNumeric.Byte(tag);
            case ShortTag tag -> new WorstNBTNumeric.Short(tag);
            case IntTag tag -> new WorstNBTNumeric.Int(tag);
            case LongTag tag -> new WorstNBTNumeric.Long(tag);
            case FloatTag tag -> new WorstNBTNumeric.Float(tag);
            case DoubleTag tag -> new WorstNBTNumeric.Double(tag);
            case StringTag tag -> new WorstNBTString(tag);
            default -> throw new IllegalStateException("Unable to convert to worst NBT: " + nms.getClass().getCanonicalName() + " is an unknown Tag implementation");
        };
    }

    @Override
    public @NotNull NBTByteArray byteArray(byte[] a2) {
        return new WorstNBTCollection.ByteArray(a2);
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
    public @NotNull NBTIntArray intArray(int[] a2) {
        return new WorstNBTCollection.IntArray(a2);
    }

    @Override
    public @NotNull NBTList list() {
        return new WorstNBTList();
    }

    @Override
    public @NotNull NBTLongArray longArray(long[] a2) {
        return new WorstNBTCollection.LongArray(a2);
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
    public @NotNull NBTString string(@NotNull String s) {
        return new WorstNBTString(s);
    }


    @Override
    public @NotNull NBTCompound fromItemStack(@NotNull org.bukkit.inventory.ItemStack itemStack) {
        CraftServer craftServer = (CraftServer) Bukkit.getServer();
        ItemStack nms = CraftItemStack.asNMSCopy(itemStack);
        return new WorstNBTCompound((CompoundTag) nms.save(craftServer.getServer().registries().compositeAccess()));
    }

    @Override
    public @NotNull NBTCompound fromEntity(@NotNull Entity entity) {
        net.minecraft.world.entity.Entity nms = ((CraftEntity) entity).getHandle();
        CompoundTag tag = new CompoundTag();

        String id = nms.getEncodeId();
        if (id != null) tag.putString("id", id);

        nms.saveWithoutId(tag, true, false, false);
        return new WorstNBTCompound(tag);
    }

    @Override
    public @NotNull NBTCompound fromInputStream(@NotNull InputStream inputStream) throws IOException {
        CompoundTag tag;
        try {
            tag = NbtIo.readCompressed(inputStream, NbtAccounter.unlimitedHeap());
        } catch (IOException e) {
            tag = NbtIo.read(new DataInputStream(inputStream));
        }
        return new WorstNBTCompound(tag);
    }
}
