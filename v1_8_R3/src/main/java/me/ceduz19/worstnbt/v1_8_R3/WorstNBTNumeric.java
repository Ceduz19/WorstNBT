package me.ceduz19.worstnbt.v1_8_R3;

import me.ceduz19.worstnbt.core.NBTNumeric;
import net.minecraft.server.v1_8_R3.*;
import org.jetbrains.annotations.NotNull;

abstract class WorstNBTNumeric<T extends NBTBase.NBTNumber> implements NBTNumeric {

    private final T handle;

    protected WorstNBTNumeric(T handle) {
        this.handle = handle;
    }

    @Override
    public long getAsLong() {
        return this.handle.c();
    }

    @Override
    public int getAsInt() {
        return this.handle.d();
    }

    @Override
    public short getAsShort() {
        return this.handle.e();
    }

    @Override
    public byte getAsByte() {
        return this.handle.f();
    }

    @Override
    public double getAsDouble() {
        return this.handle.g();
    }

    @Override
    public float getAsFloat() {
        return this.handle.h();
    }

    @Override
    public @NotNull Number getAsNumber() {
        if (this.handle instanceof NBTTagByte) return this.getAsByte();
        if (this.handle instanceof NBTTagShort) return this.getAsShort();
        if (this.handle instanceof NBTTagInt) return this.getAsInt();
        if (this.handle instanceof NBTTagLong) return this.getAsLong();
        if (this.handle instanceof NBTTagFloat) return this.getAsFloat();
        if (this.handle instanceof NBTTagDouble) return this.getAsDouble();
        throw new IllegalStateException("unknown NBTNumber implementation: " + this.getClass().getCanonicalName());
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
    }

    static class Float extends WorstNBTNumeric<NBTTagFloat> implements NBTNumeric.Float {
        Float(float f) {
            this(new NBTTagFloat(f));
        }

        Float(NBTTagFloat handle) {
            super(handle);
        }
    }

    static class Long extends WorstNBTNumeric<NBTTagLong> implements NBTNumeric.Long {
        Long(long l) {
            this(new NBTTagLong(l));
        }

        Long(NBTTagLong handle) {
            super(handle);
        }
    }

    static class Int extends WorstNBTNumeric<NBTTagInt> implements NBTNumeric.Int {
        Int(int i) {
            this(new NBTTagInt(i));
        }

        Int(NBTTagInt handle) {
            super(handle);
        }
    }

    static class Short extends WorstNBTNumeric<NBTTagShort> implements NBTNumeric.Short {
        Short(short s) {
            this(new NBTTagShort(s));
        }

        Short(NBTTagShort handle) {
            super(handle);
        }
    }

    static class Byte extends WorstNBTNumeric<NBTTagByte> implements NBTNumeric.Byte {
        Byte(byte b) {
            this(new NBTTagByte(b));
        }

        Byte(NBTTagByte handle) {
            super(handle);
        }
    }
}
