package com.gmail.willramanand.RamSkills.listeners;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.stats.Stat;
import com.gmail.willramanand.RamSkills.utils.BlockUtils;
import com.gmail.willramanand.RamSkills.utils.ItemUtils;
import com.google.common.collect.ImmutableList;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class FortuneListener implements Listener {

    private final RamSkills plugin;

    public FortuneListener(RamSkills plugin) {
        this.plugin = plugin;
    }

    private final ImmutableList<Material> nonOreMats = ImmutableList.of(Material.ANCIENT_DEBRIS,Material.OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG,
            Material.ACACIA_LOG, Material.DARK_OAK_LOG);

    private ImmutableList<Material> oreMats = ImmutableList.of(Material.COAL_ORE, Material.IRON_ORE, Material.NETHER_QUARTZ_ORE, Material.REDSTONE_ORE,
            Material.GOLD_ORE, Material.LAPIS_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.NETHER_GOLD_ORE, Material.COPPER_ORE, Material.DEEPSLATE_COAL_ORE,
            Material.DEEPSLATE_COPPER_ORE, Material.DEEPSLATE_IRON_ORE, Material.DEEPSLATE_GOLD_ORE, Material.DEEPSLATE_REDSTONE_ORE, Material.DEEPSLATE_EMERALD_ORE,
            Material.DEEPSLATE_LAPIS_ORE, Material.DEEPSLATE_DIAMOND_ORE);

    @EventHandler(priority = EventPriority.MONITOR)
    public void fortune(BlockBreakEvent event) {
        Material dropMat = Material.AIR;
        if (oreMats.contains(event.getBlock().getType())) {
            dropMat = convertOre(event.getBlock().getType(), event);
        } else if (!(nonOreMats.contains(event.getBlock().getType()))) {
            return;
        }

        if (BlockUtils.isPlayerPlaced(event.getBlock())) return;

        int fortuneMult = fortuneCalc(event.getPlayer());
        int dropAmount = 0;

        for (ItemStack item : event.getBlock().getDrops()) {
            dropAmount += item.getAmount();
        }

        ItemStack newItem = new ItemStack(dropMat);
        newItem.setAmount(dropAmount * fortuneMult);
        if (newItem.getType() == Material.AIR || newItem.getAmount() == 0) return;
        event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation(), newItem);
    }

    private int fortuneCalc(Player player) {
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);

        int fortuneMult = 0;
        String[] decimalSplit = String.valueOf(skillPlayer.getStatPoint(Stat.FORTUNE) / 100).split("\\.");

        double fullLevel = Double.parseDouble(decimalSplit[0]);
        double randDouble = new Random().nextDouble(0.01, 1);
        double remainder = Double.parseDouble("0." + decimalSplit[1]);

        // Get full 100s
        fortuneMult += fullLevel;

        // < 100% Fortune
        if (remainder >= randDouble) {
            fortuneMult++;

        }
        return fortuneMult;
    }

    private Material convertOre(Material inputOre, BlockBreakEvent event) {
        if (ItemUtils.hasSilkTouch(event.getPlayer().getInventory().getItemInMainHand())) {
            return inputOre;
        }
        return switch (inputOre) {
            case COAL_ORE, DEEPSLATE_COAL_ORE -> Material.COAL;
            case COPPER_ORE, DEEPSLATE_COPPER_ORE -> Material.RAW_COPPER;
            case IRON_ORE, DEEPSLATE_IRON_ORE -> Material.RAW_IRON;
            case REDSTONE_ORE, DEEPSLATE_REDSTONE_ORE -> Material.REDSTONE;
            case LAPIS_ORE, DEEPSLATE_LAPIS_ORE -> Material.LAPIS_LAZULI;
            case GOLD_ORE, DEEPSLATE_GOLD_ORE -> Material.RAW_GOLD;
            case DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE -> Material.DIAMOND;
            case EMERALD_ORE, DEEPSLATE_EMERALD_ORE -> Material.EMERALD;
            case NETHER_QUARTZ_ORE -> Material.QUARTZ;
            case NETHER_GOLD_ORE -> Material.GOLD_NUGGET;
            default -> inputOre;
        };
    }
}
