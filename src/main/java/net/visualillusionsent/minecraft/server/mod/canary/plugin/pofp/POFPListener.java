package net.visualillusionsent.minecraft.server.mod.canary.plugin.pofp;

import net.canarymod.api.inventory.Item;
import net.canarymod.api.inventory.ItemType;
import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.hook.player.BlockRightClickHook;
import net.canarymod.plugin.PluginListener;


public class POFPListener implements PluginListener {
    
    public void onBlockRightClick(BlockRightClickHook hook) {
        Block blockClicked = hook.getBlockClicked();
        
        if (blockClicked.getType() == BlockType.Fence || blockClicked.getType() == BlockType.NetherBrickFence) {            
            Item itemInHand = hook.getPlayer().getItemInHand();    
            
            if (itemInHand.getType() == ItemType.Pumpkin || itemInHand.getType() == ItemType.JackOLantern) {
                Block pof = hook.getPlayer().getWorld().getBlockAt(blockClicked.getX(), blockClicked.getY() + 1, blockClicked.getZ());
                pof.setType(BlockType.fromId(itemInHand.getId()));
                
                int facing = (int) Math.floor(hook.getPlayer().getRotation());                
                if ((facing >= 135 && facing <= 180) || (facing <= 135 && facing >= -180)) {//Player is facing North
                    pof.setData((short) 1);
                }
                else if (facing >= -135 && facing <= -45) { //Player is facing East
                    pof.setData((short) 2);                    
                }
                else if (facing >= -45 && facing <= 45 ) { //Player is facing South
                    pof.setData((short) 3);
                }
                else if (facing >= 45 && facing <= 135) { //Player is facing West
                    pof.setData((short) 0);
                }
                
                hook.getPlayer().getInventory().decreaseItemStackSize(itemInHand.getId(), 1);
            }
        }
    }
    
}
