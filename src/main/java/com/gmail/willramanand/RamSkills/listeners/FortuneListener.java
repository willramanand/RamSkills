package com.gmail.willramanand.RamSkills.listeners;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.events.AbilityFortuneEvent;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.stats.Stat;
import com.gmail.willramanand.RamSkills.utils.BlockUtils;
import com.gmail.willramanand.RamSkills.utils.ItemUtils;
import com.google.common.collect.ImmutableList;
import org.bukkit.Material;
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

    private final ImmutableList<Material> nonOreMats = ImmutableList.of(Material.ANCIENT_DEBRIS, Material.OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG,
            Material.ACACIA_LOG, Material.DARK_OAK_LOG, Material.CRIMSON_STEM, Material.WARPED_STEM, Material.STRIPPED_OAK_LOG, Material.STRIPPED_SPRUCE_LOG, Material.STRIPPED_BIRCH_LOG,
            Material.STRIPPED_ACACIA_LOG, Material.STRIPPED_DARK_OAK_LOG, Material.STRIPPED_CRIMSON_STEM, Material.STRIPPED_WARPED_STEM, Material.MANGROVE_LOG, Material.STRIPPED_MANGROVE_LOG,
            Material.MUDDY_MANGROVE_ROOTS, Material.MANGROVE_ROOTS);

    private final ImmutableList<Material> oreMats = ImmutableList.of(Material.COAL_ORE, Material.IRON_ORE, Material.NETHER_QUARTZ_ORE, Material.REDSTONE_ORE,
            Material.GOLD_ORE, Material.LAPIS_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.NETHER_GOLD_ORE, Material.COPPER_ORE, Material.DEEPSLATE_COAL_ORE,
            Material.DEEPSLATE_COPPER_ORE, Material.DEEPSLATE_IRON_ORE, Material.DEEPSLATE_GOLD_ORE, Material.DEEPSLATE_REDSTONE_ORE, Material.DEEPSLATE_EMERALD_ORE,
            Material.DEEPSLATE_LAPIS_ORE, Material.DEEPSLATE_DIAMOND_ORE);

    @EventHandler(priority = EventPriority.NORMAL)
    public void fortune(BlockBreakEvent event) {
        Material dropMat = event.getBlock().getType();
        if (oreMats.contains(dropMat)) {
            dropMat = convertOre(event.getBlock().getType(), event.getPlayer());
        } else if (!(nonOreMats.contains(event.getBlock().getType()))) {
            return;
        }

        if (BlockUtils.isPlayerPlaced(event.getBlock())) return;

        int fortuneAdd = fortuneCalc(event.getPlayer());

        ItemStack newItem = new ItemStack(dropMat);
        newItem.setAmount(fortuneAdd);
        if (dropMat == Material.AIR || newItem.getAmount() == 0) return;
        event.getPlayer().getWorld().dropItem(event.getBlock().getLocation(), newItem);
    }

    @EventHandler
    public void fortuneAbility(AbilityFortuneEvent event) {
        Material dropMat = event.getType();
        if (oreMats.contains(dropMat)) {
            dropMat = convertOre(event.getType(), event.getPlayer());
        } else if (!(nonOreMats.contains(event.getType()))) {
            return;
        }

        int fortuneMult = fortuneCalc(event.getPlayer());

        ItemStack newItem = new ItemStack(dropMat);
        newItem.setAmount((event.getAmount() - 1)* fortuneMult);
        if (dropMat == Material.AIR || newItem.getAmount() == 0) return;
        event.getPlayer().getWorld().dropItem(event.getBlockLocation(), newItem);
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

    private Material convertOre(Material inputOre, Player player) {
        if (player.getInventory().getItemInMainHand() != null && ItemUtils.hasSilkTouch(player.getInventory().getItemInMainHand())) {
            return inputOre;
        }
        switch (inputOre) {
            case COAL_ORE:
            case DEEPSLATE_COAL_ORE:
                return Material.COAL;
            case COPPER_ORE:
            case DEEPSLATE_COPPER_ORE:
                return Material.RAW_COPPER;
            case IRON_ORE:
            case DEEPSLATE_IRON_ORE:
                return Material.RAW_IRON;
            case REDSTONE_ORE:
            case DEEPSLATE_REDSTONE_ORE:
                return Material.REDSTONE;
            case LAPIS_ORE:
            case DEEPSLATE_LAPIS_ORE:
                return Material.LAPIS_LAZULI;
            case GOLD_ORE:
            case DEEPSLATE_GOLD_ORE:
                return Material.RAW_GOLD;
            case DIAMOND_ORE:
            case DEEPSLATE_DIAMOND_ORE:
                return Material.DIAMOND;
            case EMERALD_ORE:
            case DEEPSLATE_EMERALD_ORE:
                return Material.EMERALD;
            case NETHER_QUARTZ_ORE:
                return Material.QUARTZ;
            case NETHER_GOLD_ORE:
                return Material.GOLD_NUGGET;
        }
        return inputOre;
    }
}
