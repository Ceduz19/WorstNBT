package me.ceduz19.worstnbt.core;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface NBTNumeric extends NBT {

    static @NotNull Byte create(byte b) {
        return WorstNBT.byteNum(b);
    }

    static @NotNull Short create(short s) {
        return WorstNBT.shortNum(s);
    }

    static @NotNull Int create(int i) {
        return WorstNBT.intNum(i);
    }

    static @NotNull Long create(long l) {
        return WorstNBT.longNum(l);
    }

    static @NotNull Float create(float f) {
        return WorstNBT.floatNum(f);
    }

    static @NotNull Double create(double d) {
        return WorstNBT.doubleNum(d);
    }

    long getAsLong();
    int getAsInt();
    short getAsShort();
    byte getAsByte();
    double getAsDouble();
    float getAsFloat();
    @NotNull Number getAsNumber();

    @ApiStatus.NonExtendable
    interface Double extends NBTNumeric {
        static @NotNull Double create(double d) {
            return WorstNBT.doubleNum(d);
        }

        @Override
        default @NotNull NBTType getType() {
            return NBTType.DOUBLE;
        }
    }

    @ApiStatus.NonExtendable
    interface Float extends NBTNumeric {
        static @NotNull Float create(float f) {
            return WorstNBT.floatNum(f);
        }

        @Override
        default @NotNull NBTType getType() {
            return NBTType.FLOAT;
        }
    }

    @ApiStatus.NonExtendable
    interface Long extends NBTNumeric {
        static @NotNull Long create(long l) {
            return WorstNBT.longNum(l);
        }

        @Override
        default @NotNull NBTType getType() {
            return NBTType.LONG;
        }
    }

    @ApiStatus.NonExtendable
    interface Int extends NBTNumeric {
        static @NotNull Int create(int i) {
            return WorstNBT.intNum(i);
        }

        @Override
        default @NotNull NBTType getType() {
            return NBTType.INT;
        }
    }

    @ApiStatus.NonExtendable
    interface Short extends NBTNumeric {
        static @NotNull Short create(short s) {
            return WorstNBT.shortNum(s);
        }

        @Override
        default @NotNull NBTType getType() {
            return NBTType.SHORT;
        }
    }

    @ApiStatus.NonExtendable
    interface Byte extends NBTNumeric {
        static @NotNull Byte create(byte b) {
            return WorstNBT.byteNum(b);
        }

        @Override
        default @NotNull NBTType getType() {
            return NBTType.BYTE;
        }
    }
}
