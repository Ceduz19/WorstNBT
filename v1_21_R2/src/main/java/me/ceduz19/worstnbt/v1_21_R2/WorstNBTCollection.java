package me.ceduz19.worstnbt.v1_21_R2;

import me.ceduz19.worstnbt.core.*;
import net.minecraft.nbt.*;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractList;

abstract class WorstNBTCollection<API_E extends NBT, MC extends CollectionTag<MC_E>, MC_E extends Tag> extends AbstractList<API_E> implements NBTCollection<API_E> {

    protected final MC handle;

    protected WorstNBTCollection(MC handle) {
        this.handle = handle;
    }

    @Override
    public int size() {
        return this.handle.size();
    }

    @Override
    public boolean setNBT(int index, @NotNull NBT value) {
        return this.handle.setTag(index, (Tag) value.getHandle());
    }

    @Override
    public boolean addNBT(int index, @NotNull NBT value) {
        return this.handle.addTag(index, (Tag) value.getHandle());
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
        return o == this || this.handle.equals(o instanceof WorstNBTCollection<?,?,?> w ? w.handle : o);
    }

    @Override
    public String toString() {
        return this.handle.toString();
    }

    static class LongArray extends WorstNBTCollection<NBTNumeric.Long, LongArrayTag, LongTag> implements NBTLongArray {
        LongArray(long[] a) {
            this(new LongArrayTag(a));
        }

        LongArray(LongArrayTag handle) {
            super(handle);
        }

        @Override
        public NBTNumeric.Long get(int index) {
            return new WorstNBTNumeric.Long(this.handle.get(index));
        }

        @Override
        public long[] getAsLongArray() {
            return this.handle.getAsLongArray();
        }
    }

    static class IntArray extends WorstNBTCollection<NBTNumeric.Int, IntArrayTag, IntTag> implements NBTIntArray {
        IntArray(int[] a) {
            this(new IntArrayTag(a));
        }

        IntArray(IntArrayTag handle) {
            super(handle);
        }

        @Override
        public NBTNumeric.Int get(int index) {
            return new WorstNBTNumeric.Int(this.handle.get(index));
        }

        @Override
        public int[] getAsIntArray() {
            return this.handle.getAsIntArray();
        }
    }

    static class ByteArray extends WorstNBTCollection<NBTNumeric.Byte, ByteArrayTag, ByteTag> implements NBTByteArray {
        ByteArray(byte[] a) {
            this(new ByteArrayTag(a));
        }

        ByteArray(ByteArrayTag handle) {
            super(handle);
        }

        @Override
        public NBTNumeric.Byte get(int index) {
            return new WorstNBTNumeric.Byte(this.handle.get(index));
        }

        @Override
        public byte[] getAsByteArray() {
            return this.handle.getAsByteArray();
        }
    }
}
