package me.ceduz19.worstnbt.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public final class Compatibility {

    private final NMSVer[] versions;

    private Compatibility(NMSVer... versions) {
        if (versions == null) throw new NullPointerException("versions");
        this.versions = versions;
    }

    public boolean isSupported() {
        return isSupported(NMSVer.SERVER);
    }

    public boolean isSupported(@Nullable NMSVer version) {
        for (NMSVer ver : versions)
            if (version == ver) return true;

        return false;
    }

    public @NotNull String getSupportedMCVersions() {
        StringBuilder sb = new StringBuilder();
        String mcVers, curr;

        for (int i = 0; i < versions.length; i++) {
            mcVers = Arrays.toString(versions[i].getMCVersions());
            curr = mcVers.substring(1, mcVers.length()-1);
            if (curr.isEmpty()) continue;

            sb.append(curr);
            if (i+1 < versions.length) sb.append(", ");
        }

        String s = sb.toString();
        if (s.endsWith(", ")) s = s.substring(0, s.length()-2);

        return s;
    }

    @Override
    public String toString() {
        return Arrays.toString(versions);
    }

    public static @NotNull Compatibility of(@NotNull NMSVer version, @NotNull NMSVer @NotNull... otherVersions) {
        NMSVer[] vers = new NMSVer[otherVersions.length+1];

        vers[0] = version;
        for (int i = 0; i < otherVersions.length && i+1 < vers.length; i++)
            vers[i+1] = otherVersions[i];

        return new Compatibility(vers);
    }

    public static @NotNull Compatibility except(@NotNull NMSVer version, @NotNull NMSVer @NotNull... otherVersions) {
        NMSVer[] values = NMSVer.values();
        NMSVer[] vers = new NMSVer[values.length - otherVersions.length -1];

        for (int i = 0, j = 0; i < values.length && j < vers.length; i++)
            if (values[i] != version && !contains(otherVersions, values[i]))
                vers[j++] = values[i];

        return new Compatibility(vers);
    }

    private static boolean contains(NMSVer[] array, NMSVer element) {
        for (NMSVer ver : array)
            if (ver == element) return true;

        return false;
    }

    public static @NotNull Compatibility range(@NotNull NMSVer from, @NotNull NMSVer to) {
        if (from.ordinal() > to.ordinal()) {
            NMSVer temp = from;
            from = to;
            to = temp;
        }

        NMSVer[] vers = new NMSVer[to.ordinal() - from.ordinal() +1];
        System.arraycopy(NMSVer.values(), from.ordinal(), vers, 0, vers.length);

        return new Compatibility(vers);
    }
}
