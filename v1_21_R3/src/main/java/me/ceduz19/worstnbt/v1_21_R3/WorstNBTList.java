package me.ceduz19.worstnbt.v1_21_R3;

import me.ceduz19.worstnbt.NBT;
import me.ceduz19.worstnbt.NBTCompound;
import me.ceduz19.worstnbt.NBTList;
import me.ceduz19.worstnbt.NBTType;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

class WorstNBTList extends WorstNBTCollection<NBT, ListTag, Tag> implements NBTList {

    WorstNBTList() {
        this(new ListTag());
    }

    WorstNBTList(ListTag handle) {
        super(handle);
    }

    @Override
    public NBT get(int index) {
        return WorstNBTInternal.get().toWorst(this.handle.get(index));
    }

    @Override
    public @NotNull NBTCompound getCompound(int index) {
        return new WorstNBTCompound(this.handle.getCompound(index));
    }

    @Override
    public @NotNull NBTList getList(int index) {
        return new WorstNBTList(this.handle.getList(index));
    }

    @Override
    public short getShort(int index) {
        return this.handle.getShort(index);
    }

    @Override
    public int getInt(int index) {
        return this.handle.getInt(index);
    }

    @Override
    public int[] getIntArray(int index) {
        return this.handle.getIntArray(index);
    }

    @Override
    public long[] getLongArray(int index) {
        return this.handle.getLongArray(index);
    }

    @Override
    public double getDouble(int index) {
        return this.handle.getDouble(index);
    }

    @Override
    public float getFloat(int index) {
        return this.handle.getFloat(index);
    }

    @Override
    public @NotNull String getString(int index) {
        return this.handle.getString(index);
    }

    @Override
    public @NotNull NBTType getElementType() {
        return Objects.requireNonNull(NBTType.byId(this.handle.getElementType()));
    }
}
