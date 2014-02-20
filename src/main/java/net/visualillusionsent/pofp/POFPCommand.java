/*
 * This file is part of PumpkinOnFencePlugin.
 *
 * Copyright © 2012-2014 Visual Illusions Entertainment
 *
 * PumpkinOnFencePlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see http://www.gnu.org/licenses/gpl.html.
 */
package net.visualillusionsent.pofp;

import net.canarymod.chat.MessageReceiver;
import net.canarymod.commandsys.Command;
import net.canarymod.commandsys.CommandDependencyException;
import net.visualillusionsent.minecraft.plugin.canary.VisualIllusionsCanaryPluginInformationCommand;

/**
 * Pumpkin on Fence Plugin Command Listener
 *
 * @author Jason (darkdiplomat)
 * @author Nicolai (NiccosSystem)
 */
public class POFPCommand extends VisualIllusionsCanaryPluginInformationCommand {

    POFPCommand(PumpkinOnFencePlugin pofp) throws CommandDependencyException {
        super(pofp);
        pofp.registerCommands(this, false);
    }

    @Command(aliases = { "pofp" },
            description = "PumpkinOnFencePlugin Information Command",
            permissions = { "" },
            toolTip = "/pofp")
    public final void infoCommand(MessageReceiver receiver, String[] args) {
        this.sendInformation(receiver);
    }

}
