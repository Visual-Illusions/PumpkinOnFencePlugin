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

import net.canarymod.Canary;
import net.canarymod.chat.Colors;
import net.canarymod.chat.MessageReceiver;
import net.canarymod.commandsys.Command;
import net.canarymod.commandsys.CommandDependencyException;
import net.visualillusionsent.utils.VersionChecker;

/**
 * Pumpkin on Fence Plugin Command Listener
 *
 * @author Jason (darkdiplomat)
 * @author Nicolai (NiccosSystem)
 */
public class POFPCommand extends VisualIllusionsCanaryPluginInformationCommand {

    POFPCommand(PumpkinOnFencePlugin pofp) throws CommandDependencyException {
        super(pofp);
        Canary.commands().registerCommands(this, pofp, false);
    }

    @Command(aliases = {"pofp"},
            description = "PumpkinOnFencePlugin Information Command",
            permissions = {""},
            toolTip = "/pofp")
    public final void infoCommand(MessageReceiver msgrec, String[] args) {
        for (String msg : about) {
            if (msg.equals("$VERSION_CHECK$")) {
                VersionChecker vc = plugin.getVersionChecker();
                Boolean isLatest = vc.isLatest();
                if (isLatest == null) {
                    msgrec.message(center(Colors.GRAY + "VersionCheckerError: " + vc.getErrorMessage()));
                } else if (!isLatest) {
                    msgrec.message(center(Colors.GRAY + vc.getUpdateAvailibleMessage()));
                } else {
                    msgrec.message(center(Colors.LIGHT_GREEN + "Latest Version Installed"));
                }
            } else {
                msgrec.message(msg);
            }
        }
    }

}
