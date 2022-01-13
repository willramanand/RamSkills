package com.gmail.willramanand.RamSkills.skills.mining;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.leveler.SkillLeveler;
import com.gmail.willramanand.RamSkills.skills.Skills;
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

public class MiningLeveler extends SkillLeveler implements Listener {

    private final List<Material> validMats;

    public MiningLeveler(RamSkills plugin) {
        super(plugin, Skills.MINING);
        validMats = new ArrayList<>();
        setValidMats();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMine(BlockBreakEvent event)  {
        if (!(validMats.contains(event.getBlock().getType()))) return;

        Player player = event.getPlayer();

        if (blockXpGainPlayer(player)) return;
        if (BlockUtils.isPlayerPlaced(event.getBlock())) return;

        Block block = event.getBlock();
        plugin.getLeveler().addXp(player, Skills.MINING, getXp(player, MiningSource.valueOf(block.getType().name())));

    }



    private void setValidMats() {
        for (MiningSource source : MiningSource.values()) {
            validMats.add(Material.matchMaterial(source.toString()));
        }
    }

}
