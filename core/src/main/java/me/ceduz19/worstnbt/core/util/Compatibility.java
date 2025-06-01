package me.ceduz19.worstnbt.core.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
            if (ver != null && version == ver) return true;

        return false;
    }

    public static @NotNull Compatibility of(@NotNull NMSVer version, @NotNull NMSVer @NotNull... otherVersions) {
        NMSVer[] vers = new NMSVer[otherVersions.length+1];

        vers[0] = version;
        for (int i = 0; i < otherVersions.length && i+1 < vers.length; i++)
            vers[i+1] = otherVersions[i];

        return new Compatibility(vers);
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
