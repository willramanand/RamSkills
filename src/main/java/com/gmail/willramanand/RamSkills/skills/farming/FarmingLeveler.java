package com.gmail.willramanand.RamSkills.skills.farming;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.leveler.SkillLeveler;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class FarmingLeveler extends SkillLeveler implements Listener {

    private final List<Material> validMats;

    public FarmingLeveler(RamSkills plugin) {
        super(plugin, Skills.MINING);
        validMats = new ArrayList<>();
        setValidMats();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHarvest(BlockBreakEvent event) {
        if (!(validMats.contains(event.getBlock().getType()))) return;

        Player player = event.getPlayer();

        if (blockXpGainPlayer(player)) return;

        Block block = event.getBlock();
        FarmingSource source = matchSource(block.getType());

        if (source.requiresFullyGrown()) {
            Ageable age = (Ageable) block.getBlockData();
            if (age.getAge() != age.getMaximumAge()) return;
        }

        int multiBlock = 1;
        if (source.isMultiblock()) {
            multiBlock += calculateMultiblock(block.getLocation(), block);
        }

        player.sendMessage("Valid block: " + block.getType().name().toLowerCase());
        if (multiBlock > 1) {
            player.sendMessage("was multiblock of " + multiBlock);
            plugin.getLeveler().addXp(player, Skills.FARMING, multiBlock * getXp(player, FarmingSource.valueOf(block.getType().name())));
        } else {
            plugin.getLeveler().addXp(player, Skills.FARMING, getXp(player, FarmingSource.valueOf(block.getType().name())));
        }

    }

    @EventHandler(priority = EventPriority.LOW)
    public void rightClickCrop(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!(validMats.contains(event.getClickedBlock().getType()))) return;

        FarmingSource source = matchSource(event.getClickedBlock().getType());
        if(!(source.isRightClickHarvestable())) return;

        Ageable age = (Ageable) event.getClickedBlock().getBlockData();
        if(age.getAge() != age.getMaximumAge()) return;

        Player player = event.getPlayer();
        plugin.getLeveler().addXp(player, Skills.FARMING, getXp(player, FarmingSource.valueOf(event.getClickedBlock().getType().name())));
    }

    private void setValidMats() {
        for (Source source : FarmingSource.values()) {
            validMats.add(Material.matchMaterial(source.toString()));
        }
    }

    private FarmingSource matchSource(Material material) {
        for (FarmingSource source : FarmingSource.values()) {
            if (source.toString().equalsIgnoreCase(material.name()))
                return source;
        }
        return FarmingSource.valueOf(material.name());
    }

    private int calculateMultiblock(Location location, Block block) {
        boolean isBlock = true;
        int blockCount = 0;

        while (isBlock) {
            location.setY(location.getY() + 1);
            if (location.getBlock().getType() == block.getType()) {
                blockCount++;
            } else {
                isBlock = false;
            }
        }
        return blockCount;
    }
}
