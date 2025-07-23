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
            throw new IllegalArgumentException("itemStack type is air or its amount is <= 0");
        return INTERNAL.fromItemStack(itemStack);
    }

    private static final Class<? extends BlockState>[] TILE_ENTITIES;

    private static @Nullable RuntimeException isTileEntity(@NotNull BlockState blockState) {
        if (NMSVer.SERVER.isAtLeast(NMSVer.V1_14_R1))
            return blockState instanceof TileState ? null : new IllegalArgumentException("block state must be an instance of " + TileState.class.getCanonicalName());

        if (Stream.of(TILE_ENTITIES).anyMatch(c -> c.isInstance(blockState)) ||
                blockState.getType() == Material.PISTON || blockState.getType() == Material.END_PORTAL)
            return null;

        return new IllegalArgumentException("block state must be one of the following blocks: ");
    }

    @NotNull
    public static NBTCompound fromBlock(@NotNull BlockState block) {
        checkCompatibility();
        Objects.requireNonNull(block, "block");

        RuntimeException ex = isTileEntity(block);
        if (ex != null) throw ex;

        return INTERNAL.fromBlock(block);
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
            throw new UnsupportedOperationException("WorstNBT does not support this minecraft version (MC version: " +
                    NMSVer.MC_SERVER_VER + ", NMS version: " + NMSVer.SERVER + "), please use one of the following instead: "
                    + COMPATIBILITY.getSupportedMCVersions());

        if (INTERNAL == null)
            throw new IllegalStateException("Unable to create an instance of the internal class (package: " +
                    WorstNBT.class.getPackage().getName() + "." + NMSVer.SERVER + ".WorstNBTInternal), cannot find constructor?");
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
            //TODO check in which version classes are implemented (avoid no class found exception)
            TILE_ENTITIES = new Class[] {
                    Banner.class, Beacon.class, Bed.class, BrewingStand.class, Chest.class, CommandBlock.class,
                    Conduit.class, DaylightDetector.class, Dispenser.class, Dropper.class, EnchantingTable.class,
                    EnderChest.class, EndGateway.class, Furnace.class, Hopper.class, Jukebox.class, CreatureSpawner.class,
                    ShulkerBox.class, Sign.class, Skull.class
            };
        }
    }
}