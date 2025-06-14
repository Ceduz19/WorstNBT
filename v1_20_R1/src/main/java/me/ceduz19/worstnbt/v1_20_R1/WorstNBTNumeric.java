package me.ceduz19.worstnbt.v1_20_R1;

import me.ceduz19.worstnbt.NBTNumeric;
import net.minecraft.nbt.*;
import org.jetbrains.annotations.NotNull;

abstract class WorstNBTNumeric<T extends NumericTag> implements NBTNumeric {

    private final T handle;

    protected WorstNBTNumeric(T handle) {
        this.handle = handle;
    }

    @Override
    public long getAsLong() {
        return this.handle.getAsLong();
    }

    @Override
    public int getAsInt() {
        return this.handle.getAsInt();
    }

    @Override
    public short getAsShort() {
        return this.handle.getAsShort();
    }

    @Override
    public byte getAsByte() {
        return this.handle.getAsByte();
    }

    @Override
    public double getAsDouble() {
        return this.handle.getAsDouble();
    }

    @Override
    public float getAsFloat() {
        return this.handle.getAsFloat();
    }

    @Override
    public @NotNull Number getAsNumber() {
        return this.handle.getAsNumber();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public @NotNull Object getHandle() {
        return this.handle;
    }

    public int hashCode() {
        return this.handle.hashCode();
    }

    public boolean equals(Object obj) {
        return obj == this || this.handle.equals(obj instanceof WorstNBTNumeric<?> w ? w.handle : obj);
    }

    public String toString() {
        return this.handle.toString();
    }

    static class Double extends WorstNBTNumeric<DoubleTag> implements NBTNumeric.Double {
        Double(double d) {
            this(DoubleTag.valueOf(d));
        }

        Double(DoubleTag handle) {
            super(handle);
        }
    }

    static class Float extends WorstNBTNumeric<FloatTag> implements NBTNumeric.Float {
        Float(float f) {
            this(FloatTag.valueOf(f));
        }

        Float(FloatTag handle) {
            super(handle);
        }
    }

    static class Long extends WorstNBTNumeric<LongTag> implements NBTNumeric.Long {
        Long(long l) {
            this(LongTag.valueOf(l));
        }

        Long(LongTag handle) {
            super(handle);
        }
    }

    static class Int extends WorstNBTNumeric<IntTag> implements NBTNumeric.Int {
        Int(int i) {
            this(IntTag.valueOf(i));
        }

        Int(IntTag handle) {
            super(handle);
        }
    }

    static class Short extends WorstNBTNumeric<ShortTag> implements NBTNumeric.Short {
        Short(short s) {
            this(ShortTag.valueOf(s));
        }

        Short(ShortTag handle) {
            super(handle);
        }
    }

    static class Byte extends WorstNBTNumeric<ByteTag> implements NBTNumeric.Byte {
        Byte(byte b) {
            this(ByteTag.valueOf(b));
        }

        Byte(ByteTag handle) {
            super(handle);
        }
    }
}
