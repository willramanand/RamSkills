package com.gmail.willramanand.RamSkills.skills.excavation;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.leveler.SkillLeveler;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.utils.BlockUtils;
import com.gmail.willramanand.RamSkills.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

public class ExcavationLeveler extends SkillLeveler implements Listener {

    private final List<Material> validMats;

    public ExcavationLeveler(RamSkills plugin) {
        super(plugin, Skills.EXCAVATION);
        validMats = new ArrayList<>();
        setValidMats();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onShovel(BlockBreakEvent event)  {
        if (!(validMats.contains(event.getBlock().getType()))) return;

        Player player = event.getPlayer();

        if (blockXpGainPlayer(player)) return;
        if (BlockUtils.isPlayerPlaced(event.getBlock())) return;

        Block block = event.getBlock();
        player.sendMessage("Valid block: " + block.getType().name().toLowerCase());
        plugin.getLeveler().addXp(player, Skills.EXCAVATION, getXp(player, ExcavationSource.valueOf(block.getType().name())));

    }



    private void setValidMats() {
        for (ExcavationSource source : ExcavationSource.values()) {
            validMats.add(Material.matchMaterial(source.toString()));
        }
    }

}
