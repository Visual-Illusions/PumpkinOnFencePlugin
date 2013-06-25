package net.visualillusionsent.minecraft.server.mod.canary.plugin.pofp;

import net.canarymod.chat.MessageReceiver;
import net.canarymod.commandsys.Command;
import net.canarymod.commandsys.CommandListener;


public class POFPCommand implements CommandListener {
    private POFP pofp;
    
    public POFPCommand(POFP pofp) {
        this.pofp = pofp;
    }
    
    @Command(aliases = { "pofp" }, 
        description = "Displays POFP's version info.", 
        permissions = { "" }, 
        toolTip = "/pofp")
    public void onCommand(MessageReceiver caller, String[] args) {
        // Darkdiplomat, implement your magic here
    }
    
}
