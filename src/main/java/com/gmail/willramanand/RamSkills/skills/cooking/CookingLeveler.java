package com.gmail.willramanand.RamSkills.skills.cooking;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.leveler.SkillLeveler;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.ArrayList;
import java.util.List;

public class CookingLeveler extends SkillLeveler implements Listener {

    private final List<Material> validItems;

    public CookingLeveler(RamSkills plugin) {
        super(plugin, Skills.COOKING);
        this.validItems = new ArrayList<>();
        setValidMats();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void cookFood(FurnaceExtractEvent event) {
        if (event.getItemType() == null) return;
        if (!(validItems.contains(event.getItemType()))) return;

        int numCooked = event.getItemAmount();

        Player player = event.getPlayer();
        if (blockXpGainPlayer(player)) return;

        plugin.getLeveler().addXp(player, Skills.COOKING, numCooked * getXp(player, CookingSource.valueOf(event.getItemType().name())));
    }


    // Better implementation needed player only gets credit for one item if shift-clicked
    // Most likely inventory click event will be needed.
    @EventHandler(priority = EventPriority.NORMAL)
    public void craftFood(CraftItemEvent event) {
        if (event.getResult() == null) return;
        if (!(validItems.contains(event.getInventory().getResult().getType()))) return;

        Player player = (Player) event.getWhoClicked();
        if (blockXpGainPlayer(player)) return;

        plugin.getLeveler().addXp(player, Skills.COOKING, getXp(player, CookingSource.valueOf(event.getInventory().getResult().getType().name())));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void eatItemEvent(PlayerItemConsumeEvent event) {
        if (event.getItem() == null) return;
        if (!(validItems.contains(event.getItem().getType()))) return;

        Player player = event.getPlayer();
        if (blockXpGainPlayer(player)) return;

        plugin.getLeveler().addXp(player, Skills.COOKING, getXp(player, CookingSource.valueOf(event.getItem().getType().name())));
    }

    private void setValidMats() {
        for (Source source : CookingSource.values()) {
            validItems.add(Material.matchMaterial(source.toString()));
        }
    }
}
