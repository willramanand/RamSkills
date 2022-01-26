package com.gmail.willramanand.RamSkills.skills.fishing;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.leveler.SkillLeveler;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.google.common.collect.ImmutableList;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class FishingLeveler extends SkillLeveler implements Listener {

    private final List<Material> rareItems = ImmutableList.of(Material.COAL, Material.IRON_NUGGET, Material.IRON_INGOT, Material.GOLD_NUGGET, Material.GOLD_INGOT, Material.DIAMOND, Material.EMERALD, Material.LAPIS_LAZULI, Material.NETHERITE_SCRAP);
    private final List<Material> epicItems = ImmutableList.of(Material.COAL_BLOCK, Material.IRON_BLOCK, Material.GOLD_BLOCK, Material.DIAMOND_BLOCK, Material.LAPIS_BLOCK, Material.NETHERITE_INGOT, Material.NETHERITE_BLOCK);

    public FishingLeveler(RamSkills plugin) {
        super(plugin, Skills.FISHING);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFish(PlayerFishEvent event) {
        if (!(event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH))) return;
        if (!(event.getCaught() instanceof Item)) return;

        Item item = randomizedItem(event.getPlayer(), (Item) event.getCaught());

        if (item != event.getCaught()) {
            event.getHook().setHookedEntity(item);
        }

        Player player = event.getPlayer();

        if (blockXpGainPlayer(player)) return;

        if (epicItems.contains(item.getItemStack().getType())) {
            plugin.getLeveler().addXp(player, Skills.FISHING, getXp(player, FishingSource.EPIC));
        } else if (rareItems.contains(item.getItemStack().getType())) {
            plugin.getLeveler().addXp(player, Skills.FISHING, getXp(player, FishingSource.RARE));
        } else {
            plugin.getLeveler().addXp(player, Skills.FISHING, getXp(player, FishingSource.valueOf(item.getItemStack())));
        }
    }

    public Item randomizedItem(Player player, Item item) {
        double rng = new Random().nextDouble(0.0, 101.0);
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);

        double chance = 5.0 + (double) skillPlayer.getSkillLevel(Skills.FISHING) / 2.0;

        if (rng <= chance) {
            int roll = new Random().nextInt(0, 31);

            if (roll >= 0 && roll < 20) {
                item.setItemStack(new ItemStack(rareItems.get(new Random().nextInt(rareItems.size()))));
            } else {
                item.setItemStack(new ItemStack(epicItems.get(new Random().nextInt(epicItems.size()))));
            }

            return item;
        }

        return item;
    }
}
