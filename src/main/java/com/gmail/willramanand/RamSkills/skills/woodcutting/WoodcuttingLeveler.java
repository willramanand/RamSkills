package com.gmail.willramanand.RamSkills.skills.woodcutting;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.leveler.SkillLeveler;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;
import com.gmail.willramanand.RamSkills.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

public class WoodcuttingLeveler extends SkillLeveler implements Listener {

    private final List<Material> validMats;

    public WoodcuttingLeveler(RamSkills plugin) {
        super(plugin, Skills.WOODCUTTING);
        validMats = new ArrayList<>();
        setValidMats();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChop(BlockBreakEvent event)  {
        if (!(validMats.contains(event.getBlock().getType()))) return;

        Player player = event.getPlayer();

        if (blockXpGainPlayer(player)) return;
        if (BlockUtils.isPlayerPlaced(event.getBlock())) return;

        Block block = event.getBlock();
        plugin.getLeveler().addXp(player, Skills.WOODCUTTING, getXp(player, WoodcuttingSource.valueOf(block.getType().name())));

    }



    private void setValidMats() {
        for (Source source : WoodcuttingSource.values()) {
            validMats.add(Material.matchMaterial(source.toString()));
        }
    }

}
