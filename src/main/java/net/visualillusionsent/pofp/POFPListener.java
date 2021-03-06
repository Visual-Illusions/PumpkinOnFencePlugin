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

import net.canarymod.Canary;
import net.canarymod.api.GameMode;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.inventory.Item;
import net.canarymod.api.inventory.ItemType;
import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.hook.HookHandler;
import net.canarymod.hook.player.BlockRightClickHook;
import net.canarymod.plugin.PluginListener;
import net.canarymod.plugin.Priority;

/**
 * Pumpkin on Fence Plugin Listener
 *
 * @author Jason (darkdiplomat)
 * @author Nicolai (NiccosSystem)
 */
public final class POFPListener implements PluginListener {

    POFPListener(PumpkinOnFencePlugin pofp) {
        Canary.hooks().registerListener(this, pofp);
    }

    @HookHandler(priority = Priority.LOW) // Since there are not perms checks or protections checks, run low priority
    public final void onBlockRightClick(BlockRightClickHook hook) {
        Block blockClicked = hook.getBlockClicked();
        if (blockClicked.getType() == BlockType.Fence || blockClicked.getType() == BlockType.NetherBrickFence) {
            Item itemInHand = hook.getPlayer().getItemHeld();

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
                    decreaseStack(hook.getPlayer());
                    hook.setCanceled();
                }
            }
        }
    }

    private int forceOverflow(int face) {
        if (face < -180) {
            face = face + 360;
        }
        else if (face > 180) {
            face = face - 360;
        }
        return face;
    }

    private void decreaseStack(Player player) {
        if (player.getMode() != GameMode.CREATIVE) {
            Item held = player.getItemHeld();
            held.setAmount(held.getAmount() - 1);
            if (held.getAmount() <= 0) {
                player.getInventory().setSlot(held.getSlot(), null);
            }
        }
    }
}
