package me.ceduz19.worstnbt.v1_11_R1;

import me.ceduz19.worstnbt.*;
import me.ceduz19.worstnbt.internal.NBTInternal;
import net.minecraft.server.v1_11_R1.*;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_11_R1.scoreboard.CraftScoreboard;
import org.bukkit.inventory.ItemStack;
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
        if (!(nms instanceof NBTBase))
            throw new IllegalArgumentException("Unable to convert to worst NBT: " + nms.getClass().getCanonicalName() +
                    " is not an instance of " + NBTBase.class.getCanonicalName());

        if (nms instanceof NBTTagByteArray) return new WorstNBTByteArray((NBTTagByteArray)nms);
        if (nms instanceof NBTTagCompound) return new WorstNBTCompound((NBTTagCompound)nms);
        if (nms instanceof NBTTagEnd) return WorstNBTEnd.INSTANCE;
        if (nms instanceof NBTTagIntArray) return new WorstNBTIntArray((NBTTagIntArray)nms);
        if (nms instanceof NBTTagList) return new WorstNBTList((NBTTagList)nms);
        if (nms instanceof NBTTagByte) return new WorstNBTNumeric.Byte((NBTTagByte)nms);
        if (nms instanceof NBTTagShort) return new WorstNBTNumeric.Short((NBTTagShort)nms);
        if (nms instanceof NBTTagInt) return new WorstNBTNumeric.Int((NBTTagInt)nms);
        if (nms instanceof NBTTagLong) return new WorstNBTNumeric.Long((NBTTagLong)nms);
        if (nms instanceof NBTTagFloat) return new WorstNBTNumeric.Float((NBTTagFloat)nms);
        if (nms instanceof NBTTagDouble) return new WorstNBTNumeric.Double((NBTTagDouble)nms);
        if (nms instanceof NBTTagString) return new WorstNBTString((NBTTagString)nms);

        throw new IllegalStateException("Unable to convert to worst NBT: " + nms.getClass().getCanonicalName() +
                " is an unknown " + NBTBase.class.getCanonicalName() + " implementation");
    }

    @Override
    public @NotNull NBTByteArray byteArray(byte[] array) {
        return new WorstNBTByteArray(array);
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
        return new WorstNBTIntArray(array);
    }

    @Override
    public @NotNull NBTList list() {
        return new WorstNBTList();
    }

    @Override
    public @NotNull NBTLongArray longArray(long[] array) {
        throw new UnsupportedOperationException();
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
    public @NotNull NBTCompound fromItemStack(@NotNull ItemStack itemStack) {
        net.minecraft.server.v1_11_R1.ItemStack nms = CraftItemStack.asNMSCopy(itemStack);
        return new WorstNBTCompound(nms.save(new NBTTagCompound()));
    }

    @Override
    public @NotNull NBTCompound fromBlock(@NotNull BlockState block) {
        TileEntity tileEntity = ((CraftWorld) block.getWorld()).getTileEntityAt(block.getX(), block.getY(), block.getZ());
        if (tileEntity == null) throw new IllegalStateException(block.getType() + " is not a tile entity");

        NBTTagCompound compound = new NBTTagCompound();
        tileEntity.save(compound);

        return new WorstNBTCompound(compound);
    }

    @Override
    public @NotNull NBTCompound fromEntity(@NotNull org.bukkit.entity.Entity entity) {
        Entity nms = ((CraftEntity) entity).getHandle();
        NBTTagCompound nbt = new NBTTagCompound();

        MinecraftKey id = EntityTypes.a(nms);
        if (id != null) nbt.setString("id", id.toString());

        nms.e(nbt);
        return new WorstNBTCompound(nbt);
    }

    @Override
    public @NotNull NBTCompound fromScoreboard(@NotNull Scoreboard scoreboard) {
        ScoreboardServer nms = ((ScoreboardServer) ((CraftScoreboard) scoreboard).getHandle());

        NBTTagCompound nbt = new NBTTagCompound();
        PersistentScoreboard data = new PersistentScoreboard();

        data.a(nms);
        return new WorstNBTCompound(data.b(nbt));
    }

    @Override
    public @NotNull NBTCompound fromInputStream(@NotNull InputStream inputStream) throws IOException {
        NBTTagCompound compound;
        try {
            compound = NBTCompressedStreamTools.a(inputStream);
        } catch (IOException e) {
            compound = NBTCompressedStreamTools.a(new DataInputStream(inputStream));
        }
        return new WorstNBTCompound(compound);
    }
}
