package net.visualillusionsent.minecraft.server.mod.canary.plugin.pofp;

import net.canarymod.Canary;
import net.canarymod.commandsys.CommandDependencyException;
import net.canarymod.plugin.Plugin;

public class POFP extends Plugin {

	@Override
	public void disable() {
		Canary.hooks().unregisterPluginListeners(this);
	}

	@Override
	public boolean enable() {
	    Canary.hooks().registerListener(new POFPListener(), this);
	    
	    try {
            Canary.commands().registerCommands(new POFPCommand(this), this, false);
        }
        catch (CommandDependencyException e) {
            
        }
		return false;
	}

}
