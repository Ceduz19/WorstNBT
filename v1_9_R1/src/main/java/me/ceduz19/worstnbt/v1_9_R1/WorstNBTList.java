package me.ceduz19.worstnbt.v1_9_R1;

import me.ceduz19.worstnbt.NBT;
import me.ceduz19.worstnbt.NBTCompound;
import me.ceduz19.worstnbt.NBTList;
import me.ceduz19.worstnbt.NBTType;
import me.ceduz19.worstnbt.util.ReflectionUtils;
import net.minecraft.server.v1_9_R1.NBTBase;
import net.minecraft.server.v1_9_R1.NBTTagList;
import net.minecraft.server.v1_9_R1.NBTTagShort;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.List;
import java.util.Objects;

class WorstNBTList extends AbstractList<NBT> implements NBTList {

    private static final Field FIELD_LIST = ReflectionUtils.getField(NBTTagList.class, "list", true);
    private final NBTTagList handle;

    WorstNBTList() {
        this(new NBTTagList());
    }

    WorstNBTList(NBTTagList handle) {
        this.handle = handle;
    }

    @Override
    public int size() {
        return this.handle.size();
    }

    @Override
    public NBT get(int index) {
        return WorstNBTInternal.get().toWorst(this.handle.h(index));
    }

    @Override
    public NBT set(int index, NBT element) {
        NBT old = this.get(index);
        this.setNBT(index, element);
        return old;
    }

    @Override
    public boolean add(NBT nbt) {
        NBTType type = nbt.getType();
        if (type == NBTType.END) return false;

        NBTType elementType = this.getElementType();
        if (elementType != NBTType.END && nbt.getType() != elementType) return false;

        this.handle.add((NBTBase) nbt.getHandle());
        return true;
    }

    @Override
    public void add(int index, NBT element) {
        this.addNBT(index, element);
    }

    @Override
    public NBT remove(int index) {
        return WorstNBTInternal.get().toWorst(this.handle.remove(index));
    }

    @Override
    public @NotNull NBTCompound getCompound(int index) {
        return new WorstNBTCompound(this.handle.get(index));
    }

    @Override
    public @NotNull NBTList getList(int index) {
        NBTBase nbt = this.handle.h(index);
        return nbt instanceof NBTTagList ? new WorstNBTList((NBTTagList) nbt) : new WorstNBTList();
    }

    @Override
    public short getShort(int index) {
        NBTBase nbt = this.handle.h(index);
        return nbt instanceof NBTTagShort ? ((NBTTagShort) nbt).e() : 0;
    }

    @Override
    public int getInt(int index) {
        return this.handle.c(index);
    }

    @Override
    public int[] getIntArray(int index) {
        return this.handle.d(index);
    }

    @Override
    public long[] getLongArray(int index) {
        return new long[0];
    }

    @Override
    public double getDouble(int index) {
        return this.handle.e(index);
    }

    @Override
    public float getFloat(int index) {
        return this.handle.f(index);
    }

    @Override
    public @NotNull String getString(int index) {
        return this.handle.getString(index);
    }

    @Override
    public boolean setNBT(int index, @NotNull NBT value) {
        this.handle.a(index, (NBTBase) value.getHandle());
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addNBT(int index, @NotNull NBT value) {
        if (FIELD_LIST == null) return false;

        List<NBTBase> list = (List<NBTBase>) ReflectionUtils.readField(FIELD_LIST, this.handle);
        if (list == null) return false;

        list.add(index, (NBTBase) value.getHandle());
        return true;
    }

    @Override
    public @NotNull NBTType getElementType() {
        return Objects.requireNonNull(NBTType.byId((byte) this.handle.d()));
    }

    @Override
    public @NotNull Object getHandle() {
        return this.handle;
    }

    @Override
    public int hashCode() {
        return this.handle.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o == this || handle.equals(o instanceof WorstNBTList ? ((WorstNBTList) o).handle : o);
    }

    @Override
    public String toString() {
        return this.handle.toString();
    }
}
