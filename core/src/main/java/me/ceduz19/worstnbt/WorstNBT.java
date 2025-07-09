package me.ceduz19.worstnbt;

import me.ceduz19.worstnbt.internal.NBTInternal;
import me.ceduz19.worstnbt.util.NMSVer;
import me.ceduz19.worstnbt.util.Compatibility;
import me.ceduz19.worstnbt.util.ReflectionUtils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Objects;

public final class WorstNBT {

    private WorstNBT() {
        throw new IllegalStateException("cannot instantiate utility class");
    }

    private static final Compatibility COMPATIBILITY = Compatibility.of(
            NMSVer.V1_8_R3,
            NMSVer.V1_9_R1, NMSVer.V1_9_R2,
            NMSVer.V1_10_R1,
            NMSVer.V1_11_R1,
            NMSVer.V1_12_R1,
            NMSVer.V1_13_R1,
            NMSVer.V1_20_R1, NMSVer.V1_20_R2, NMSVer.V1_20_R3, NMSVer.V1_20_R4,
            NMSVer.V1_21_R1, NMSVer.V1_21_R2, NMSVer.V1_21_R3
    );
    private static final boolean SUPPORTED = COMPATIBILITY.isSupported();
    private static final String PACKAGE_NAME = "me.ceduz19.worstnbt." + NMSVer.SERVER;
    private static final NBTInternal INTERNAL;

    @NotNull
    public static NBTByteArray byteArray(byte[] a) {
        checkCompatibility();
        return INTERNAL.byteArray(a);
    }

    @NotNull
    public static NBTCompound compound() {
        checkCompatibility();
        return INTERNAL.compound();
    }

    @NotNull
    public static NBTEnd end() {
        checkCompatibility();
        return INTERNAL.end();
    }

    @NotNull
    public static NBTIntArray intArray(int[] a) {
        checkCompatibility();
        return INTERNAL.intArray(a);
    }

    @NotNull
    public static NBTList list() {
        checkCompatibility();
        return INTERNAL.list();
    }

    @NotNull
    public static NBTLongArray longArray(long[] a) {
        checkCompatibility();
        if (NMSVer.SERVER.isBefore(NMSVer.V1_12_R1))
            throw new UnsupportedOperationException("Long array NBT is not implemented in this minecraft version (" +
                    NMSVer.MC_SERVER_VER + "), must be at least 1.12");

        return INTERNAL.longArray(a);
    }

    @NotNull
    public static NBTNumeric.Byte byteNum(byte b) {
        checkCompatibility();
        return INTERNAL.byteNum(b);
    }

    @NotNull
    public static NBTNumeric.Short shortNum(short s) {
        checkCompatibility();
        return INTERNAL.shortNum(s);
    }

    @NotNull
    public static NBTNumeric.Int intNum(int i) {
        checkCompatibility();
        return INTERNAL.intNum(i);
    }

    @NotNull
    public static NBTNumeric.Long longNum(long l) {
        checkCompatibility();
        return INTERNAL.longNum(l);
    }

    @NotNull
    public static NBTNumeric.Float floatNum(float f) {
        checkCompatibility();
        return INTERNAL.floatNum(f);
    }

    @NotNull
    public static NBTNumeric.Double doubleNum(double d) {
        checkCompatibility();
        return INTERNAL.doubleNum(d);
    }

    @NotNull
    public static NBTString string(@NotNull String string) {
        checkCompatibility();
        Objects.requireNonNull(string, "string");
        return INTERNAL.string(string);
    }

    @NotNull
    public static NBTCompound fromItemStack(@NotNull ItemStack itemStack) {
        checkCompatibility();
        Objects.requireNonNull(itemStack, "itemStack");
        if (itemStack.getType() == Material.AIR || itemStack.getAmount() <= 0)
            throw new IllegalArgumentException("itemStack is air or amount is <= 0");
        return INTERNAL.fromItemStack(itemStack);
    }

    @NotNull
    public static NBTCompound fromEntity(@NotNull Entity entity) {
        checkCompatibility();
        Objects.requireNonNull(entity, "entity");
        return INTERNAL.fromEntity(entity);
    }

    public static NBTCompound fromScoreboard(@NotNull Scoreboard scoreboard) {
        checkCompatibility();
        Objects.requireNonNull(scoreboard, "scoreboard");
        return INTERNAL.fromScoreboard(scoreboard);
    }

    @NotNull
    public static NBTCompound fromFile(@NotNull File file) throws IOException {
        checkCompatibility();
        Objects.requireNonNull(file, "file");
        return INTERNAL.fromFile(file);
    }

    @NotNull
    public static NBTCompound fromInputStream(@NotNull InputStream inputStream) throws IOException {
        checkCompatibility();
        Objects.requireNonNull(inputStream, "inputStream");
        return INTERNAL.fromInputStream(inputStream);
    }

    private static void checkCompatibility() {
        if (!SUPPORTED)
            throw new IllegalStateException("WorstNBT does not support this minecraft version (MC version: " + NMSVer.MC_SERVER_VER +
                    ", NMS version: " + NMSVer.SERVER + "), please use one of the following instead: " + COMPATIBILITY.getSupportedMCVersions());
    }

    static {
        if (NMSVer.SERVER == NMSVer.UNKNOWN) {
            INTERNAL = null;
        } else {
            Class<?> clazz = ReflectionUtils.getClass(PACKAGE_NAME + ".WorstNBTInternal");
            Constructor<?> constructor = clazz == null ? null : ReflectionUtils.getConstructor(clazz, true);
            INTERNAL = constructor == null ? null : (NBTInternal) ReflectionUtils.invokeConstructor(constructor, new Object[0]);
        }
    }
}