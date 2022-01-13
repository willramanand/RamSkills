package com.gmail.willramanand.RamSkills.skills.alchemy;

// Copyright (c) 2020 Archy.

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.leveler.Leveler;
import com.gmail.willramanand.RamSkills.leveler.SkillLeveler;
import com.gmail.willramanand.RamSkills.skills.Skills;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;

public class AlchemyLeveler extends SkillLeveler implements Listener {

    public AlchemyLeveler(RamSkills plugin) {
        super(plugin, Skills.ALCHEMY);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBrew(BrewEvent event) {
        if (!(event.getBlock().hasMetadata("skills_brewing_owner"))) return;
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(event.getBlock().getMetadata("skills_brewing_owner").get(0).asString()));
        if (!(offlinePlayer.isOnline())) return;
        if (event.getContents().getIngredient() == null) return;
        Player player = offlinePlayer.getPlayer();
        if (player == null) return;
        if (blockXpGainPlayer(player)) return;
        int amountBrewed = event.getResults().size();
        addAlchemyXp(player, event.getContents().getIngredient().getType(), amountBrewed);
    }

    private void addAlchemyXp(Player player, Material mat, int amountBrewed) {
        Leveler leveler = plugin.getLeveler();
        if (mat.equals(Material.REDSTONE)) {
            leveler.addXp(player, Skills.ALCHEMY, amountBrewed * getXp(player, AlchemySource.EXTENDED));
        } else if (mat.equals(Material.GLOWSTONE_DUST)) {
            leveler.addXp(player, Skills.ALCHEMY, amountBrewed * getXp(player, AlchemySource.UPGRADED));
        } else if (mat.equals(Material.NETHER_WART)) {
            leveler.addXp(player, Skills.ALCHEMY, amountBrewed * getXp(player, AlchemySource.AWKWARD));
        } else if (mat.equals(Material.GUNPOWDER)) {
            leveler.addXp(player, Skills.ALCHEMY, amountBrewed * getXp(player, AlchemySource.SPLASH));
        } else if (mat.equals(Material.DRAGON_BREATH)) {
            leveler.addXp(player, Skills.ALCHEMY, amountBrewed * getXp(player, AlchemySource.LINGERING));
        } else {
            leveler.addXp(player, Skills.ALCHEMY, amountBrewed * getXp(player, AlchemySource.REGULAR));
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType().equals(Material.BREWING_STAND)) {
            event.getBlock().setMetadata("skills_brewing_owner", new FixedMetadataValue(plugin, event.getPlayer().getUniqueId()));
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.BREWING_STAND)) {
            if (event.getBlock().hasMetadata("skills_brewing_owner")) {
                event.getBlock().removeMetadata("skills_brewing_owner", plugin);
            }
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getInventory().getType().equals(InventoryType.BREWING)) {
            if (event.getInventory().getHolder() != null) {
                if (event.getInventory().getLocation() != null) {
                    Block block = event.getInventory().getLocation().getBlock();
                    if (!block.hasMetadata("skills_brewing_owner")) {
                        block.setMetadata("skills_brewing_owner", new FixedMetadataValue(plugin, event.getPlayer().getUniqueId()));
                    }
                }
            }
        }
    }

}
