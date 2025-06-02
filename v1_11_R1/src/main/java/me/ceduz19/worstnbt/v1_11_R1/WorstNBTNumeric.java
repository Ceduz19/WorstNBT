package me.ceduz19.worstnbt.v1_11_R1;

import me.ceduz19.worstnbt.NBTNumeric;
import net.minecraft.server.v1_11_R1.*;
import org.jetbrains.annotations.NotNull;

abstract class WorstNBTNumeric<T extends NBTBase> implements NBTNumeric {

    protected final T handle;

    private WorstNBTNumeric(T handle) {
        this.handle = handle;
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
        return obj == this || handle.equals(obj instanceof WorstNBTNumeric<?> ? ((WorstNBTNumeric<?>) obj).handle : obj);
    }

    public String toString() {
        return this.handle.toString();
    }

    static class Double extends WorstNBTNumeric<NBTTagDouble> implements NBTNumeric.Double {
        Double(double d) {
            this(new NBTTagDouble(d));
        }

        Double(NBTTagDouble handle) {
            super(handle);
        }

        @Override
        public long getAsLong() {
            return handle.d();
        }

        @Override
        public int getAsInt() {
            return handle.e();
        }

        @Override
        public short getAsShort() {
            return handle.f();
        }

        @Override
        public byte getAsByte() {
            return handle.g();
        }

        @Override
        public double getAsDouble() {
            return handle.asDouble();
        }

        @Override
        public float getAsFloat() {
            return handle.i();
        }

        @Override
        public @NotNull Number getAsNumber() {
            return handle.asDouble();
        }
    }

    static class Float extends WorstNBTNumeric<NBTTagFloat> implements NBTNumeric.Float {
        Float(float f) {
            this(new NBTTagFloat(f));
        }

        Float(NBTTagFloat handle) {
            super(handle);
        }

        @Override
        public long getAsLong() {
            return handle.d();
        }

        @Override
        public int getAsInt() {
            return handle.e();
        }

        @Override
        public short getAsShort() {
            return handle.f();
        }

        @Override
        public byte getAsByte() {
            return handle.g();
        }

        @Override
        public double getAsDouble() {
            return handle.asDouble();
        }

        @Override
        public float getAsFloat() {
            return handle.i();
        }

        @Override
        public @NotNull Number getAsNumber() {
            return handle.i();
        }
    }

    static class Long extends WorstNBTNumeric<NBTTagLong> implements NBTNumeric.Long {
        Long(long l) {
            this(new NBTTagLong(l));
        }

        Long(NBTTagLong handle) {
            super(handle);
        }

        @Override
        public long getAsLong() {
            return handle.d();
        }

        @Override
        public int getAsInt() {
            return handle.e();
        }

        @Override
        public short getAsShort() {
            return handle.f();
        }

        @Override
        public byte getAsByte() {
            return handle.g();
        }

        @Override
        public double getAsDouble() {
            return handle.asDouble();
        }

        @Override
        public float getAsFloat() {
            return handle.i();
        }

        @Override
        public @NotNull Number getAsNumber() {
            return handle.d();
        }
    }

    static class Int extends WorstNBTNumeric<NBTTagInt> implements NBTNumeric.Int {
        Int(int i) {
            this(new NBTTagInt(i));
        }

        Int(NBTTagInt handle) {
            super(handle);
        }

        @Override
        public long getAsLong() {
            return handle.d();
        }

        @Override
        public int getAsInt() {
            return handle.e();
        }

        @Override
        public short getAsShort() {
            return handle.f();
        }

        @Override
        public byte getAsByte() {
            return handle.g();
        }

        @Override
        public double getAsDouble() {
            return handle.asDouble();
        }

        @Override
        public float getAsFloat() {
            return handle.i();
        }

        @Override
        public @NotNull Number getAsNumber() {
            return handle.e();
        }
    }

    static class Short extends WorstNBTNumeric<NBTTagShort> implements NBTNumeric.Short {
        Short(short s) {
            this(new NBTTagShort(s));
        }

        Short(NBTTagShort handle) {
            super(handle);
        }

        @Override
        public long getAsLong() {
            return handle.d();
        }

        @Override
        public int getAsInt() {
            return handle.e();
        }

        @Override
        public short getAsShort() {
            return handle.f();
        }

        @Override
        public byte getAsByte() {
            return handle.g();
        }

        @Override
        public double getAsDouble() {
            return handle.asDouble();
        }

        @Override
        public float getAsFloat() {
            return handle.i();
        }

        @Override
        public @NotNull Number getAsNumber() {
            return handle.f();
        }
    }

    static class Byte extends WorstNBTNumeric<NBTTagByte> implements NBTNumeric.Byte {
        Byte(byte b) {
            this(new NBTTagByte(b));
        }

        Byte(NBTTagByte handle) {
            super(handle);
        }

        @Override
        public long getAsLong() {
            return handle.d();
        }

        @Override
        public int getAsInt() {
            return handle.e();
        }

        @Override
        public short getAsShort() {
            return handle.f();
        }

        @Override
        public byte getAsByte() {
            return handle.g();
        }

        @Override
        public double getAsDouble() {
            return handle.asDouble();
        }

        @Override
        public float getAsFloat() {
            return handle.i();
        }

        @Override
        public @NotNull Number getAsNumber() {
            return handle.g();
        }
    }
}
