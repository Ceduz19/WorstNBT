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

public final class WorstNBT {

    private static final Compatibility COMPATIBILITY = Compatibility.of(NMSVer.V1_8_R3, NMSVer.V1_10_R1, NMSVer.V1_11_R1, NMSVer.V1_12_R1, NMSVer.V1_21_R3);
    private static final boolean SUPPORTED = COMPATIBILITY.isSupported();
    private static final String PACKAGE_NAME = "me.ceduz19.worstnbt." + NMSVer.SERVER;
    private static final NBTInternal INTERNAL;

    @NotNull
    public static NBTByteArray byteArray(byte[] a) {
        checkVersion();
        return INTERNAL.byteArray(a);
    }

    @NotNull
    public static NBTCompound compound() {
        checkVersion();
        return INTERNAL.compound();
    }

    @NotNull
    public static NBTEnd end() {
        checkVersion();
        return INTERNAL.end();
    }

    @NotNull
    public static NBTIntArray intArray(int[] a) {
        checkVersion();
        return INTERNAL.intArray(a);
    }

    @NotNull
    public static NBTList list() {
        checkVersion();
        return INTERNAL.list();
    }

    @NotNull
    public static NBTLongArray longArray(long[] a) {
        checkVersion();
        if (NMSVer.SERVER.isBefore(NMSVer.V1_12_R1))
            throw new UnsupportedOperationException("Long array NBT is not implemented in this minecraft version (" +
                    NMSVer.MC_SERVER_VER + "), must be at least 1.12");

        return INTERNAL.longArray(a);
    }

    @NotNull
    public static NBTNumeric.Byte byteNum(byte b) {
        checkVersion();
        return INTERNAL.byteNum(b);
    }

    @NotNull
    public static NBTNumeric.Short shortNum(short s) {
        checkVersion();
        return INTERNAL.shortNum(s);
    }

    @NotNull
    public static NBTNumeric.Int intNum(int i) {
        checkVersion();
        return INTERNAL.intNum(i);
    }

    @NotNull
    public static NBTNumeric.Long longNum(long l) {
        checkVersion();
        return INTERNAL.longNum(l);
    }

    @NotNull
    public static NBTNumeric.Float floatNum(float f) {
        checkVersion();
        return INTERNAL.floatNum(f);
    }

    @NotNull
    public static NBTNumeric.Double doubleNum(double d) {
        checkVersion();
        return INTERNAL.doubleNum(d);
    }

    @NotNull
    public static NBTString string(String s) {
        checkVersion();
        if (s == null) throw new NullPointerException("string");
        return INTERNAL.string(s);
    }

    @NotNull
    public static NBTCompound fromItemStack(ItemStack itemStack) {
        checkVersion();
        if (itemStack == null) throw new NullPointerException("itemStack");
        if (itemStack.getType() == Material.AIR || itemStack.getAmount() <= 0)
            throw new IllegalArgumentException("itemStack is air or amount is less or equal to 0");
        return INTERNAL.fromItemStack(itemStack);
    }

    @NotNull
    public static NBTCompound fromEntity(Entity entity) {
        checkVersion();
        if (entity == null) throw new NullPointerException("entity");
        return INTERNAL.fromEntity(entity);
    }

    public static NBTCompound fromScoreboard(Scoreboard scoreboard) {
        checkVersion();
        if (scoreboard == null) throw new NullPointerException("scoreboard");
        return INTERNAL.fromScoreboard(scoreboard);
    }

    @NotNull
    public static NBTCompound fromFile(File file) throws IOException {
        checkVersion();
        if (file == null) throw new NullPointerException("file");
        return INTERNAL.fromFile(file);
    }

    @NotNull
    public static NBTCompound fromInputStream(InputStream inputStream) throws IOException {
        checkVersion();
        if (inputStream == null) throw new NullPointerException("inputStream");
        return INTERNAL.fromInputStream(inputStream);
    }

    private static void checkVersion() {
        if (!SUPPORTED)
            throw new IllegalStateException("WorstNBT does not support this minecraft version (Minecraft version: " + NMSVer.MC_SERVER_VER + ")" +
                    ", please use one of the following instead: " + COMPATIBILITY.getSupportedMCVersions());
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