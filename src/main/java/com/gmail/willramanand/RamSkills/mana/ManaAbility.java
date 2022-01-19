package com.gmail.willramanand.RamSkills.mana;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.skills.excavation.ExcavationSource;
import com.gmail.willramanand.RamSkills.utils.ItemUtils;
import com.gmail.willramanand.RamSkills.utils.VBlockFace;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.*;

public class ManaAbility implements Listener {

    private final RamSkills plugin;
    private final List<Block> blockBuffer;

    public ManaAbility(RamSkills plugin) {
        this.plugin = plugin;
        this.blockBuffer = new ArrayList<>();
    }


    @EventHandler
    public void readyAbility(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && (event.getAction() != Action.RIGHT_CLICK_BLOCK)) return;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !(event.getPlayer().isSneaking())) return;
        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        if (!(validWeapon(player.getInventory().getItemInMainHand()))) return;

        if (player.getMetadata("readied").get(0).asBoolean()) {
            plugin.getActionBar().sendAbilityActionBar(player, "Weapon unreadied!");
            player.setMetadata("readied", new FixedMetadataValue(plugin, false));
        } else {
            plugin.getActionBar().sendAbilityActionBar(player, "Weapon readied!");
            player.setMetadata("readied", new FixedMetadataValue(plugin, true));
        }
    }

    @EventHandler
    public void cancelReady(PlayerItemHeldEvent event) {
        if (event.getPlayer().hasMetadata("readied"))
            if (event.getPlayer().getMetadata("readied").get(0).asBoolean()) {
                event.getPlayer().setMetadata("readied", new FixedMetadataValue(plugin, false));
                plugin.getActionBar().sendAbilityActionBar(event.getPlayer(), "Weapon unreadied!");
            }
    }

    @EventHandler
    public void bowAbility(PlayerInteractEvent event) {
        int manaCost = 15;
        Player player = event.getPlayer();

        if (!(ItemUtils.isBow(player.getInventory().getItemInMainHand()))) return;
        if (!(event.getAction().isLeftClick())) return;
        if (player.isSneaking()) return;

        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        if (skillPlayer.getSkillLevel(Skills.COMBAT) < 25) return;
        if (skillPlayer.getSkillLevel(Skills.COMBAT) >= 50) manaCost /= 2;
        if (checkMana(skillPlayer, manaCost)) {
            skillPlayer.removeMana(manaCost);
        } else {
            plugin.getActionBar().sendAbilityActionBar(player, "&4Not enough mana!");
            return;
        }

        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1.0f, 1.0f);

        ItemStack bow = player.getInventory().getItemInMainHand();

        Arrow arrow = player.launchProjectile(Arrow.class);
        arrow.setShooter(player);

        arrow.setDamage(calculatePowerDmg(bow, arrow.getDamage()));
        arrow.setFireTicks(checkBowFire(bow));
        arrow.setKnockbackStrength(bow.getItemMeta().getEnchantLevel(Enchantment.ARROW_KNOCKBACK));
        arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);

        consumeDurability(bow);
        plugin.getActionBar().sendAbilityActionBar(player, "Quickshot activated!");
    }

    @EventHandler
    public void hoeAbility(PlayerInteractEvent event) {
        boolean isSuccessful = false;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!(ItemUtils.isHoe(event.getPlayer().getInventory().getItemInMainHand()))) return;

        Player player = event.getPlayer();
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        double manaCost = 1.0;

        // Ability size 5x5
        int startBlock = -2;
        int endBlock = 3;

        if (skillPlayer.getSkillLevel(Skills.FARMING) < 25) {
            return;
        } else if (skillPlayer.getSkillLevel(Skills.FARMING) >= 50) {
            // Ability size 9x9
            startBlock = -4;
            endBlock = 5;

            // Reduced mana cost
            manaCost /= 2;
        }

        Block block;
        for (int x = startBlock; x < endBlock; x++) {
            for (int z = startBlock; z < endBlock; z++) {
                block = event.getClickedBlock().getLocation().add(x, 0, z).getBlock();
                if (validCrop(block.getType())) {

                    if (!(checkMana(skillPlayer, manaCost))) {
                        plugin.getActionBar().sendAbilityActionBar(player, "&4Not enough mana!");
                        return;
                    }

                    Ageable age = (Ageable) block.getBlockData();
                    if (age.getAge() != age.getMaximumAge()) {
                        block.applyBoneMeal(BlockFace.UP);
                        consumeDurability(player.getInventory().getItemInMainHand());
                        skillPlayer.removeMana(manaCost);
                        isSuccessful = true;
                    }
                }
            }
        }
        if (isSuccessful) plugin.getActionBar().sendAbilityActionBar(player, "Demeter's Touch activated!");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void veinMine(BlockBreakEvent event) {
        if (!(ItemUtils.isPick(event.getPlayer().getInventory().getItemInMainHand()))) return;
        if (!(validVein(event.getBlock().getType()))) return;
        if (!(event.getPlayer().getMetadata("readied").get(0).asBoolean())) return;

        Player player = event.getPlayer();
        ItemStack pick = player.getInventory().getItemInMainHand();
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);

        int maxVeinSize = 10;
        int manaCost = 10;

        if (skillPlayer.getSkillLevel(Skills.MINING) < 25) {
            return;
        } else if (skillPlayer.getSkillLevel(Skills.MINING) >= 50) {
            // Vein mine 20 blocks
            maxVeinSize *= 2;

            // Reduced mana cost
            manaCost /= 2;
        }

        Set<Block> veinBlocks = new HashSet<>();
        veinBlocks.add(event.getBlock());

        while (veinBlocks.size() < maxVeinSize) {
            Iterator<Block> trackedBlocks = veinBlocks.iterator();
            while (trackedBlocks.hasNext() && veinBlocks.size() + this.blockBuffer.size() <= maxVeinSize) {
                Block current = trackedBlocks.next();
                for (VBlockFace face : VBlockFace.values()) {
                    if (veinBlocks.size() + this.blockBuffer.size() >= maxVeinSize) {
                        break;
                    }

                    Block nextBlock = face.getConnectedBlock(current);
                    if (veinBlocks.contains(nextBlock) || nextBlock.getType() != event.getBlock().getType()) {
                        continue;
                    }

                    this.blockBuffer.add(nextBlock);
                }
            }

            if (blockBuffer.isEmpty()) {
                break;
            }

            veinBlocks.addAll(blockBuffer);
            blockBuffer.clear();
        }

        manaCost *= veinBlocks.size();
        if (!(checkMana(skillPlayer, manaCost))) {
            plugin.getActionBar().sendAbilityActionBar(player, "&4Not enough mana!");
            return;
        }
        skillPlayer.removeMana(manaCost);

        plugin.getMiningLeveler().level(player, event.getBlock(), veinBlocks.size());

        for (Block block : veinBlocks) {
            block.breakNaturally(pick, true);
            consumeDurability(pick);
        }
        plugin.getActionBar().sendAbilityActionBar(player, "Vein Mine activated!");
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void shovelAbility(BlockBreakEvent event) {
        boolean isSuccessful = false;
        if (!(ItemUtils.isShovel(event.getPlayer().getInventory().getItemInMainHand()))) return;
        if (!(event.getPlayer().getMetadata("readied").get(0).asBoolean())) return;

        Player player = event.getPlayer();
        ItemStack shovel = player.getInventory().getItemInMainHand();
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        double manaCost = 2.5;

        // Ability size 5x5
        int startBlock = -2;
        int endBlock = 3;

        if (skillPlayer.getSkillLevel(Skills.EXCAVATION) < 25) {
            return;
        } else if (skillPlayer.getSkillLevel(Skills.EXCAVATION) >= 50) {
            // Ability size 9x9
            startBlock = -4;
            endBlock = 5;

            // Reduced mana cost
            manaCost /= 2;
        }

        int blocksBroken = 0;
        Block block = event.getBlock();

        if (block.getType() == Material.AIR || block.getType() == Material.CAVE_AIR) return;

        Material originalType = event.getBlock().getType();

        for (int x = startBlock; x < endBlock; x++) {
            for (int z = startBlock; z < endBlock; z++) {
                block = event.getBlock().getLocation().add(x, 0, z).getBlock();
                if (originalType == block.getType() && validDig(block.getType())) {
                    if (!(checkMana(skillPlayer, manaCost))) {
                        plugin.getActionBar().sendAbilityActionBar(player, "&4Not enough mana!");
                        return;
                    }
                    block.breakNaturally(shovel, true);
                    consumeDurability(shovel);
                    skillPlayer.removeMana(manaCost);
                    isSuccessful = true;
                    blocksBroken++;
                }
            }
        }

        if (blocksBroken == 0) return;
        plugin.getExcavationLeveler().level(player, originalType, blocksBroken);
        if (isSuccessful) plugin.getActionBar().sendAbilityActionBar(player, "Excavator activated!");
    }

    public void consumeDurability(ItemStack item) {
        double breakChance = 1;
        if (item.getItemMeta().hasEnchant(Enchantment.DURABILITY)) {
            int enchantLvl = item.getItemMeta().getEnchantLevel(Enchantment.DURABILITY);
            breakChance /= (enchantLvl + 1);
        }

        double randDouble = new Random().nextDouble(0.01, 1.01);
        if (randDouble < breakChance) {
            Damageable damage = (Damageable) item.getItemMeta();
            damage.setDamage(damage.getDamage() + 1);
            item.setItemMeta(damage);
        }
    }

    public int checkBowFire(ItemStack bow) {
        if (!(bow.getItemMeta().hasEnchant(Enchantment.ARROW_FIRE))) return 0;
        return 100;
    }

    public double calculatePowerDmg(ItemStack bow, double damage) {
        if (!(bow.getItemMeta().hasEnchant(Enchantment.ARROW_DAMAGE))) return damage;
        int enchantLvl = bow.getItemMeta().getEnchantLevel(Enchantment.ARROW_DAMAGE);

        double newDamage = damage + ((damage * 0.5) * (enchantLvl + 1));

        return newDamage;
    }

    public boolean checkMana(SkillPlayer skillPlayer, double manaCost) {
        double currentMana = skillPlayer.getMana();
        if (currentMana < manaCost) return false;
        return true;
    }

    public boolean validWeapon(ItemStack item) {
        if (ItemUtils.isShovel(item) || ItemUtils.isPick(item) || ItemUtils.isAxe(item) || ItemUtils.isSword(item))
            return true;
        return false;
    }

    public boolean validCrop(Material type) {
        switch (type) {
            case WHEAT, CARROTS, POTATOES, BEETROOTS, BAMBOO, MELON_STEM, PUMPKIN_STEM -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public boolean validVein(Material type) {
        switch (type) {
            case COAL_ORE, DEEPSLATE_COAL_ORE, COPPER_ORE, DEEPSLATE_COPPER_ORE, IRON_ORE, DEEPSLATE_IRON_ORE,
                    REDSTONE_ORE, DEEPSLATE_REDSTONE_ORE, LAPIS_ORE, DEEPSLATE_LAPIS_ORE, GOLD_ORE, DEEPSLATE_GOLD_ORE,
                    DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE, EMERALD_ORE, DEEPSLATE_EMERALD_ORE, NETHER_QUARTZ_ORE,
                    NETHER_GOLD_ORE, ANCIENT_DEBRIS -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public boolean validDig(Material type) {
        for (ExcavationSource source : ExcavationSource.values()) {
            if (type.name().equalsIgnoreCase(source.toString())) {
                return true;
            }
        }
        return false;
    }
}
