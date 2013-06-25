/*
 * This file is part of PumpkinOnFencePlugin.
 *
 * Copyright © 2012-2013 Visual Illusions Entertainment
 *
 * PumpkinOnFencePlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * PumpkinOnFencePlugin is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with PumpkinOnFencePlugin.
 * If not, see http://www.gnu.org/licenses/gpl.html.
 */
package net.visualillusionsent.minecraft.server.mod.canary.plugin.pofp;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import net.canarymod.Canary;
import net.canarymod.plugin.Plugin;
import net.visualillusionsent.utils.ProgramStatus;
import net.visualillusionsent.utils.VersionChecker;

public class POFP extends Plugin {

    private final VersionChecker vc;
    private float version;
    private short build;
    private String buildTime;
    private ProgramStatus status;

    public POFP() {
        readManifest();
        vc = new VersionChecker(getName(), String.valueOf(version), String.valueOf(build), "http://visualillusionsent.net/minecraft/plugins/", status, false);
    }

    @Override
    public void disable() {
        Canary.hooks().unregisterPluginListeners(this);
    }

    @Override
    public boolean enable() {
        new POFPListener(this);
        new POFPCommand(this);
        return false;
    }

    final Manifest getManifest() throws Exception {
        Manifest toRet = null;
        Exception ex = null;
        JarFile jar = null;
        try {
            jar = new JarFile(getJarPath());
            toRet = jar.getManifest();
        }
        catch (Exception e) {
            ex = e;
        }
        finally {
            if (jar != null) {
                try {
                    jar.close();
                }
                catch (IOException e) {}
            }
            if (ex != null) {
                throw ex;
            }
        }
        return toRet;
    }

    final void readManifest() {
        try {
            Manifest manifest = getManifest();
            Attributes mainAttribs = manifest.getMainAttributes();
            version = Float.parseFloat(mainAttribs.getValue("Version").replace("-SNAPSHOT", ""));
            build = Short.parseShort(mainAttribs.getValue("Build"));
            buildTime = mainAttribs.getValue("Build-Time");
            try {
                status = ProgramStatus.valueOf(mainAttribs.getValue("ProgramStatus"));
            }
            catch (IllegalArgumentException iaex) {
                status = ProgramStatus.UNKNOWN;
            }
        }
        catch (Exception ex) {
            version = -1.0F;
            build = -1;
            buildTime = "19700101-0000";
        }
    }

    final void checkStatus() {
        if (status == ProgramStatus.UNKNOWN) {
            getLogman().severe(String.format("%s has declared itself as an 'UNKNOWN STATUS' build. Use is not advised and could cause damage to your system!", getName()));
        }
        else if (status == ProgramStatus.ALPHA) {
            getLogman().warning(String.format("%s has declared itself as a 'ALPHA' build. Production use is not advised!", getName()));
        }
        else if (status == ProgramStatus.BETA) {
            getLogman().warning(String.format("%s has declared itself as a 'BETA' build. Production use is not advised!", getName()));
        }
        else if (status == ProgramStatus.RELEASE_CANDIDATE) {
            getLogman().info(String.format("%s has declared itself as a 'Release Candidate' build. Expect some bugs.", getName()));
        }
    }

    final void checkVersion() {
        Boolean islatest = vc.isLatest();
        if (islatest == null) {
            getLogman().warning("VersionCheckerError: " + vc.getErrorMessage());
        }
        else if (!vc.isLatest()) {
            getLogman().warning(vc.getUpdateAvailibleMessage());
            getLogman().warning(String.format("You can view update info @ http://wiki.visualillusionsent.net/%s#ChangeLog", getName()));
        }
    }

    final float getRawVersion() {
        return version;
    }

    final short getBuildNumber() {
        return build;
    }

    final String getBuildTime() {
        return buildTime;
    }

    final VersionChecker getVersionChecker() {
        return vc;
    }
}
