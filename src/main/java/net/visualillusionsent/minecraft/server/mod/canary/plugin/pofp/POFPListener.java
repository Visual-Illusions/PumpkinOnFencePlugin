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

import net.canarymod.Canary;
import net.canarymod.api.inventory.Item;
import net.canarymod.api.inventory.ItemType;
import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.hook.HookHandler;
import net.canarymod.hook.player.BlockRightClickHook;
import net.canarymod.plugin.PluginListener;

public class POFPListener implements PluginListener {

    POFPListener(POFP pofp) {
        Canary.hooks().registerListener(this, pofp);
    }

    @HookHandler
    public void onBlockRightClick(BlockRightClickHook hook) {
        Block blockClicked = hook.getBlockClicked();

        if (blockClicked.getType() == BlockType.Fence || blockClicked.getType() == BlockType.NetherBrickFence) {
            Item itemInHand = hook.getPlayer().getItemInHand();

            if (itemInHand.getType() == ItemType.Pumpkin || itemInHand.getType() == ItemType.JackOLantern) {
                Block pof = hook.getPlayer().getWorld().getBlockAt(blockClicked.getX(), blockClicked.getY() + 1, blockClicked.getZ());
                if (pof.isAir()) {
                    pof.setType(BlockType.fromId(itemInHand.getId()));
                    pof.update();

                    int facing = forceOverflow((int) Math.floor(hook.getPlayer().getRotation()));
                    //0x0: Facing south | 0x1: Facing west | 0x2: Facing north | 0x3: Facing east | 0x4: No face
                    if (facing >= 135 && facing <= 180 || facing <= -135 && facing >= -180) {//Player is facing North
                        pof.setData((short) 0);
                    }
                    else if (facing >= -135 && facing <= -45) { //Player is facing East
                        pof.setData((short) 1);
                    }
                    else if (facing >= -45 && facing <= 45) { //Player is facing South
                        pof.setData((short) 2);
                    }
                    else if (facing >= 45 && facing <= 135) { //Player is facing West
                        pof.setData((short) 3);
                    }
                    pof.update();

                    //hook.getPlayer().getInventory().decreaseItemStackSize(itemInHand.getId(), 1);
                    hook.setCanceled();
                }
            }
        }
    }

    private final int forceOverflow(int face) {
        if (face < -180) {
            face = -180 - face;
        }
        else if (face > 180) {
            face = 180 - (180 - face);
        }
        return face;
    }
}
