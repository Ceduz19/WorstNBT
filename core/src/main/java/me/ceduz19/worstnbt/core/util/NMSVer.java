package me.ceduz19.worstnbt.core.util;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public enum NMSVer {
    UNKNOWN,
    V1_8_R3 ("1.8.8"),
    V1_9_R1 ("1.9", "1.9.2"),
    V1_9_R2 ("1.9.4"),
    V1_10_R1("1.10", "1.10.2"),
    V1_11_R1("1.11", "1.11.1", "1.11.2"),
    V1_12_R1("1.12", "1.12.1", "1.12.2"),
    V1_13_R1("1.13"),
    V1_13_R2("1.13.1", "1.13.2"),
    V1_14_R1("1.14", "1.14.1", "1.14.2", "1.14.3", "1.14.4"),
    V1_15_R1("1.15", "1.15.1", "1.15.2"),
    V1_16_R1("1.16.1"),
    V1_16_R2("1.16.2", "1.16.3", "1.16.4"),
    V1_16_R3("1.16.5"),
    V1_17_R1("1.17", "1.17.1"),
    V1_18_R1("1.18", "1.18.1"),
    V1_18_R2("1.18.2"),
    V1_19_R1("1.19", "1.19.1", "1.19.2"),
    V1_19_R2("1.19.3"),
    V1_19_R3("1.19.4"),
    V1_20_R1("1.20", "1.20.1"),
    V1_20_R2("1.20.2"),
    V1_20_R3("1.20.3", "1.20.4"),
    V1_20_R4("1.20.6"),
    V1_21_R1("1.21", "1.21.1"),
    V1_21_R2("1.21.2", "1.21.3"),
    V1_21_R3("1.21.4");

    public static final NMSVer SERVER;
    public static final String MC_SERVER_VER;
    @NotNull
    private final String[] mcVer;

    NMSVer(String... ver) {
        this.mcVer = ver;
    }

    public boolean isBefore(@NotNull NMSVer ver) {
        return this.ordinal() < ver.ordinal();
    }

    public boolean isAfter(@NotNull NMSVer ver) {
        return this.ordinal() > ver.ordinal();
    }

    private boolean isOfMCVersion() {
        if (MC_SERVER_VER == null) return false;

        for (String mc : this.mcVer)
            if (MC_SERVER_VER.equals(mc)) return true;

        return false;
    }

    public String toString() {
        return "v" + this.name().substring(1);
    }

    static {
        String[] splitted = Bukkit.getBukkitVersion().split("-");
        if (splitted.length == 0) {
            SERVER = UNKNOWN;
            MC_SERVER_VER = "UNKNOWN";
        } else {
            MC_SERVER_VER = splitted[0];
            NMSVer found = null;
            for (NMSVer v : NMSVer.values()) {
                if (!v.isOfMCVersion()) continue;
                found = v;
                break;
            }
            SERVER = found == null ? UNKNOWN : found;
        }
    }
}