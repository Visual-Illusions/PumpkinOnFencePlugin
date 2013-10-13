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
package net.visualillusionsent.pofp.canary;

import net.canarymod.commandsys.CommandDependencyException;
import net.visualillusionsent.minecraft.plugin.canary.VisualIllusionsCanaryPlugin;

/**
 * Pumpkin on Fence Plugin main
 *
 * @author Jason (darkdiplomat)
 * @author Nicolai (NiccosSystem)
 */
public final class PumpkinOnFencePlugin extends VisualIllusionsCanaryPlugin {

    public PumpkinOnFencePlugin() {
        super();
    }

    @Override
    public final void disable() {
    }

    @Override
    public final boolean enable() {
        checkStatus();
        checkVersion();
        new POFPListener(this);
        try {
            new POFPCommand(this);
        } catch (CommandDependencyException e) {
            getLogman().logWarning("Failed to register PumpkinOnFencePlugin information command");
        }
        return true;
    }
}
