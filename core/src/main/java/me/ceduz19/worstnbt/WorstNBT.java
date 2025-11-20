package me.ceduz19.worstnbt;

import me.ceduz19.worstnbt.internal.NBTInternal;
import me.ceduz19.worstnbt.util.NMSVer;
import me.ceduz19.worstnbt.util.Compatibility;
import me.ceduz19.worstnbt.util.ReflectionUtils;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
            NMSVer.V1_19_R1, NMSVer.V1_19_R2, NMSVer.V1_19_R3,
            NMSVer.V1_20_R1, NMSVer.V1_20_R2, NMSVer.V1_20_R3, NMSVer.V1_20_R4,
            NMSVer.V1_21_R1, NMSVer.V1_21_R2, NMSVer.V1_21_R3
    );
    private static final boolean SUPPORTED = COMPATIBILITY.isSupported();
    private static final NBTInternal INTERNAL;
    private static final Class<? extends BlockState>[] TILE_ENTITIES;

    public static @NotNull NBTByteArray byteArray(byte[] array) {
        checkCompatibility();
        return INTERNAL.byteArray(array);
    }

    public static @NotNull NBTCompound compound() {
        checkCompatibility();
        return INTERNAL.compound();
    }

    public static @NotNull NBTEnd end() {
        checkCompatibility();
        return INTERNAL.end();
    }

    public static @NotNull NBTIntArray intArray(int[] array) {
        checkCompatibility();
        return INTERNAL.intArray(array);
    }

    public static @NotNull NBTList list() {
        checkCompatibility();
        return INTERNAL.list();
    }

    public static @NotNull NBTLongArray longArray(long[] array) {
        checkCompatibility();
        if (NMSVer.SERVER.isBefore(NMSVer.V1_12_R1))
            throw new UnsupportedOperationException("Long array NBT is not implemented in this minecraft version (" +
                    NMSVer.MC_SERVER_VER + "), must be at least 1.12");

        return INTERNAL.longArray(array);
    }

    public static @NotNull NBTNumeric.Byte byteNum(byte b) {
        checkCompatibility();
        return INTERNAL.byteNum(b);
    }

    public static @NotNull NBTNumeric.Short shortNum(short s) {
        checkCompatibility();
        return INTERNAL.shortNum(s);
    }

    public static @NotNull NBTNumeric.Int intNum(int i) {
        checkCompatibility();
        return INTERNAL.intNum(i);
    }

    public static @NotNull NBTNumeric.Long longNum(long l) {
        checkCompatibility();
        return INTERNAL.longNum(l);
    }

    public static @NotNull NBTNumeric.Float floatNum(float f) {
        checkCompatibility();
        return INTERNAL.floatNum(f);
    }

    public static @NotNull NBTNumeric.Double doubleNum(double d) {
        checkCompatibility();
        return INTERNAL.doubleNum(d);
    }

    public static @NotNull NBTString string(@NotNull String string) {
        checkCompatibility();
        Objects.requireNonNull(string, "string");
        return INTERNAL.string(string);
    }

    public static @NotNull NBTCompound fromItemStack(@NotNull ItemStack itemStack) {
        checkCompatibility();
        Objects.requireNonNull(itemStack, "itemStack");
        if (itemStack.getType() == Material.AIR || itemStack.getAmount() <= 0)
            throw new IllegalArgumentException("itemStack type is air or its amount is <= 0");
        return INTERNAL.fromItemStack(itemStack);
    }

    public static @NotNull NBTCompound fromBlock(@NotNull BlockState block) {
        checkCompatibility();
        Objects.requireNonNull(block, "block");

        RuntimeException ex = isTileEntity(block);
        if (ex != null) throw ex;

        return INTERNAL.fromBlock(block);
    }

    public static @NotNull NBTCompound fromEntity(@NotNull Entity entity) {
        checkCompatibility();
        Objects.requireNonNull(entity, "entity");
        return INTERNAL.fromEntity(entity);
    }

    public static @NotNull NBTCompound fromScoreboard(@NotNull Scoreboard scoreboard) {
        checkCompatibility();
        Objects.requireNonNull(scoreboard, "scoreboard");
        return INTERNAL.fromScoreboard(scoreboard);
    }

    public static @NotNull NBTCompound fromFile(@NotNull File file) throws IOException {
        checkCompatibility();
        Objects.requireNonNull(file, "file");
        return INTERNAL.fromFile(file);
    }

    public static @NotNull NBTCompound fromInputStream(@NotNull InputStream inputStream) throws IOException {
        checkCompatibility();
        Objects.requireNonNull(inputStream, "inputStream");
        return INTERNAL.fromInputStream(inputStream);
    }

    private static void checkCompatibility() {
        if (!SUPPORTED)
            throw new UnsupportedOperationException("WorstNBT does not support this minecraft version (MC version: " +
                    NMSVer.MC_SERVER_VER + ", NMS version: " + NMSVer.SERVER + "), please use one of the following instead: "
                    + COMPATIBILITY.getSupportedMCVersions());

        if (INTERNAL == null)
            throw new IllegalStateException("Unable to create an instance of the internal class (package: " +
                    WorstNBT.class.getPackage().getName() + "." + NMSVer.SERVER + ".WorstNBTInternal), cannot find constructor?");
    }

    private static @Nullable RuntimeException isTileEntity(@NotNull BlockState blockState) {
        if (NMSVer.SERVER.isAtLeast(NMSVer.V1_14_R1))
            return blockState instanceof TileState ? null : new IllegalArgumentException("block state must be an instance of " + TileState.class.getCanonicalName());

        if (Stream.of(TILE_ENTITIES).anyMatch(c -> c.isInstance(blockState)) ||
                blockState.getType() == Material.PISTON || blockState.getType() == Material.END_PORTAL)
            return null;

        return new IllegalArgumentException("block state must be one of the following blocks: ");
    }

    static {
        if (!SUPPORTED) {
            INTERNAL = null;
        } else {
            String packageName = WorstNBT.class.getPackage().getName() + "." + NMSVer.SERVER;
            Class<?> clazz = ReflectionUtils.getClass(packageName + ".WorstNBTInternal");
            Constructor<?> constructor = clazz == null ? null : ReflectionUtils.getConstructor(clazz, true);
            INTERNAL = constructor == null ? null : (NBTInternal) ReflectionUtils.invokeConstructor(constructor, new Object[0]);
        }

        if (NMSVer.SERVER.isAtLeast(NMSVer.V1_14_R1)) {
            TILE_ENTITIES = null;
        } else {
            //noinspection deprecation
            List<Class<? extends BlockState>> classes = Arrays.asList(
                    Banner.class, Beacon.class, Bed.class, BrewingStand.class, Chest.class, CommandBlock.class,
                    DaylightDetector.class, Dispenser.class, Dropper.class, EnchantingTable.class, EnderChest.class,
                    Furnace.class, Hopper.class, Jukebox.class, CreatureSpawner.class, Sign.class, Skull.class
            );

            if (NMSVer.SERVER.isAtLeast(NMSVer.V1_9_R1)) classes.add(EndGateway.class);
            if (NMSVer.SERVER.isAtLeast(NMSVer.V1_11_R1)) classes.add(ShulkerBox.class);
            if (NMSVer.SERVER.isAtLeast(NMSVer.V1_13_R1)) classes.add(Conduit.class);

            //noinspection unchecked
            TILE_ENTITIES = (Class<? extends BlockState>[]) classes.toArray(new Class[classes.size()]);
        }
    }
}