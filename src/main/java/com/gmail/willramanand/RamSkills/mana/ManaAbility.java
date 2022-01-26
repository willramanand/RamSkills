package com.gmail.willramanand.RamSkills.mana;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.events.AbilityFortuneEvent;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.skills.excavation.ExcavationSource;
import com.gmail.willramanand.RamSkills.skills.woodcutting.WoodcuttingSource;
import com.gmail.willramanand.RamSkills.utils.ItemUtils;
import com.gmail.willramanand.RamSkills.utils.VBlockFace;
import org.bukkit.Bukkit;
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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ManaAbility implements Listener {

    private final RamSkills plugin;
    private final List<Block> veinBuffer;
    private final List<Block> treeBuffer;
    private final Set<Player> bowPaused;

    public ManaAbility(RamSkills plugin) {
        this.plugin = plugin;
        this.veinBuffer = new ArrayList<>();
        this.treeBuffer = new ArrayList<>();
        this.bowPaused = new HashSet<>();
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
        if (event.getPlayer().hasMetadata("readied")) if (event.getPlayer().getMetadata("readied").get(0).asBoolean()) {
            event.getPlayer().setMetadata("readied", new FixedMetadataValue(plugin, false));
            plugin.getActionBar().sendAbilityActionBar(event.getPlayer(), "Weapon unreadied!");
        }
    }

    @EventHandler
    public void bowAbility(PlayerInteractEvent event) {
        double manaCost = Ability.QUICKSHOT.getManaCost();
        Player player = event.getPlayer();

        if (!(ItemUtils.isBow(player.getInventory().getItemInMainHand()))) return;
        if (!(event.getAction().isLeftClick())) return;
        if (player.isSneaking()) return;
        if (bowPaused.contains(player)) {
            plugin.getActionBar().sendAbilityActionBar(player, "&4On Cooldown!");
            return;
        }

        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        if (skillPlayer.getSkillLevel(Skills.COMBAT) < Ability.QUICKSHOT.getUnlock()) return;
        if (skillPlayer.getSkillLevel(Skills.COMBAT) >= Ability.QUICKSHOT.getUpgrade()) manaCost /= 2;
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

        consumeDurability(player, bow);
        plugin.getActionBar().sendAbilityActionBar(player, "Quickshot activated!");
        setBowPaused(player, 10);
    }

    @EventHandler
    public void hoeAbility(PlayerInteractEvent event) {
        boolean isSuccessful = false;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!(ItemUtils.isHoe(event.getPlayer().getInventory().getItemInMainHand()))) return;

        Player player = event.getPlayer();
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        double manaCost = Ability.DEMETERS_TOUCH.getManaCost();

        // Ability size 5x5
        int startBlock = -2;
        int endBlock = 3;

        if (skillPlayer.getSkillLevel(Skills.FARMING) < Ability.DEMETERS_TOUCH.getUnlock()) {
            return;
        } else if (skillPlayer.getSkillLevel(Skills.FARMING) >= Ability.DEMETERS_TOUCH.getUpgrade()) {
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
                        consumeDurability(player, player.getInventory().getItemInMainHand());
                        skillPlayer.removeMana(manaCost);
                        isSuccessful = true;
                    }
                }
            }
        }
        if (isSuccessful) plugin.getActionBar().sendAbilityActionBar(player, "Demeter's Touch activated!");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void pickAbility(BlockBreakEvent event) {
        if (!(ItemUtils.isPick(event.getPlayer().getInventory().getItemInMainHand()))) return;
        if (!(validVein(event.getBlock().getType()))) return;
        if (!(event.getPlayer().getMetadata("readied").get(0).asBoolean())) return;

        Player player = event.getPlayer();
        ItemStack pick = player.getInventory().getItemInMainHand();
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);

        Material type = event.getBlock().getType();

        int maxVeinSize = 10;
        double manaCost = Ability.VEIN_MINER.getManaCost();

        if (skillPlayer.getSkillLevel(Skills.MINING) < Ability.VEIN_MINER.getUnlock()) {
            return;
        } else if (skillPlayer.getSkillLevel(Skills.MINING) >= Ability.VEIN_MINER.getUpgrade()) {
            // Vein mine 20 blocks
            maxVeinSize *= 2;

            // Reduced mana cost
            manaCost /= 2;
        }

        Set<Block> veinBlocks = new HashSet<>();
        veinBlocks.add(event.getBlock());

        while (veinBlocks.size() < maxVeinSize) {
            Iterator<Block> trackedBlocks = veinBlocks.iterator();
            while (trackedBlocks.hasNext() && veinBlocks.size() + this.veinBuffer.size() <= maxVeinSize) {
                Block current = trackedBlocks.next();
                for (VBlockFace face : VBlockFace.values()) {
                    if (veinBlocks.size() + this.veinBuffer.size() >= maxVeinSize) {
                        break;
                    }

                    Block nextBlock = face.getConnectedBlock(current);
                    if (veinBlocks.contains(nextBlock) || nextBlock.getType() != event.getBlock().getType()) {
                        continue;
                    }

                    this.veinBuffer.add(nextBlock);
                }
            }

            if (veinBuffer.isEmpty()) {
                break;
            }

            veinBlocks.addAll(veinBuffer);
            veinBuffer.clear();
        }

        manaCost *= veinBlocks.size();
        if (!(checkMana(skillPlayer, manaCost))) {
            plugin.getActionBar().sendAbilityActionBar(player, "&4Not enough mana!");
            return;
        }
        skillPlayer.removeMana(manaCost);

        plugin.getMiningLeveler().level(player, type, veinBlocks.size());

        for (Block block : veinBlocks) {
            block.breakNaturally(pick, true);
            consumeDurability(player, pick);
        }
        Bukkit.getPluginManager().callEvent(new AbilityFortuneEvent(player, type, event.getBlock().getLocation(), veinBlocks.size()));
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
        double manaCost = Ability.EXCAVATOR.getManaCost();

        // Ability size 5x5
        int startBlock = -2;
        int endBlock = 3;

        if (skillPlayer.getSkillLevel(Skills.EXCAVATION) < Ability.EXCAVATOR.getUnlock()) {
            return;
        } else if (skillPlayer.getSkillLevel(Skills.EXCAVATION) >= Ability.EXCAVATOR.getUpgrade()) {
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
                    consumeDurability(player, shovel);
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

    @EventHandler(priority = EventPriority.MONITOR)
    public void axeAbility(BlockBreakEvent event) {
        if (!(ItemUtils.isAxe(event.getPlayer().getInventory().getItemInMainHand()))) return;
        if (!(validChop(event.getBlock().getType()))) return;
        if (!(event.getPlayer().getMetadata("readied").get(0).asBoolean())) return;

        Player player = event.getPlayer();
        ItemStack axe = player.getInventory().getItemInMainHand();
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        Material type = event.getBlock().getType();

        int maxTreeSize = 40;
        double manaCost = Ability.TREECAPTITOR.getManaCost();

        if (skillPlayer.getSkillLevel(Skills.WOODCUTTING) < Ability.TREECAPTITOR.getUnlock()) {
            return;
        } else if (skillPlayer.getSkillLevel(Skills.WOODCUTTING) >= Ability.TREECAPTITOR.getUpgrade()) {
            // Vein mine 20 blocks
            maxTreeSize *= 2;

            // Reduced mana cost
            manaCost /= 2;
        }

        Set<Block> treeBlocks = new HashSet<>();
        treeBlocks.add(event.getBlock());

        while (treeBlocks.size() < maxTreeSize) {
            Iterator<Block> trackedBlocks = treeBlocks.iterator();
            while (trackedBlocks.hasNext() && treeBlocks.size() + this.treeBuffer.size() <= maxTreeSize) {
                Block current = trackedBlocks.next();
                for (VBlockFace face : VBlockFace.values()) {
                    if (treeBlocks.size() + this.treeBuffer.size() >= maxTreeSize) {
                        break;
                    }

                    Block nextBlock = face.getConnectedBlock(current);
                    if (treeBlocks.contains(nextBlock) || nextBlock.getType() != event.getBlock().getType()) {
                        continue;
                    }

                    this.treeBuffer.add(nextBlock);
                }
            }

            if (treeBuffer.isEmpty()) {
                break;
            }

            treeBlocks.addAll(treeBuffer);
            treeBuffer.clear();
        }

        manaCost *= treeBlocks.size();
        if (!(checkMana(skillPlayer, manaCost))) {
            plugin.getActionBar().sendAbilityActionBar(player, "&4Not enough mana!");
            return;
        }
        skillPlayer.removeMana(manaCost);

        for (Block block : treeBlocks) {
            block.breakNaturally(axe, true);
            consumeDurability(player, axe);
        }

        plugin.getWoodcuttingLeveler().level(player, type, treeBlocks.size());
        Bukkit.getPluginManager().callEvent(new AbilityFortuneEvent(player, type, event.getBlock().getLocation(), treeBlocks.size()));
        plugin.getActionBar().sendAbilityActionBar(player, "Treecaptitor activated!");
    }

    public void consumeDurability(Player player, ItemStack item) {
        double breakChance = 1;
        if (item.getItemMeta() != null && item.getItemMeta().hasEnchant(Enchantment.DURABILITY)) {
            int enchantLvl = item.getItemMeta().getEnchantLevel(Enchantment.DURABILITY);
            breakChance /= (enchantLvl + 1);
        }

        if (item.getItemMeta() == null) return;

        Damageable damage = (Damageable) item.getItemMeta();
        double randDouble = new Random().nextDouble(0.01, 1.01);
        if (randDouble < breakChance) {
            damage.setDamage(damage.getDamage() + 1);
        }

        item.setItemMeta(damage);

        if (damage.getDamage() > item.getType().getMaxDurability()) {
            player.getInventory().remove(item);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
        }
    }

    public void setBowPaused(Player player, int ticks) {
        bowPaused.add(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                bowPaused.remove(player);
                }
        }.runTaskLater(plugin, ticks);
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
            case WHEAT:
            case CARROTS:
            case POTATOES:
            case BEETROOTS:
            case BAMBOO:
            case MELON_STEM:
            case PUMPKIN_STEM:
            case SWEET_BERRY_BUSH:
                return true;
            default:
                return false;
        }
    }

    public boolean validVein(Material type) {
        switch (type) {
            case COAL_ORE:
            case COPPER_ORE:
            case DEEPSLATE_COPPER_ORE:
            case IRON_ORE:
            case DEEPSLATE_IRON_ORE:
            case REDSTONE_ORE:
            case DEEPSLATE_REDSTONE_ORE:
            case LAPIS_ORE:
            case DEEPSLATE_LAPIS_ORE:
            case GOLD_ORE:
            case DEEPSLATE_GOLD_ORE:
            case DIAMOND_ORE:
            case DEEPSLATE_DIAMOND_ORE:
            case EMERALD_ORE:
            case DEEPSLATE_EMERALD_ORE:
            case NETHER_QUARTZ_ORE:
            case NETHER_GOLD_ORE:
            case ANCIENT_DEBRIS:
            case DEEPSLATE_COAL_ORE:
                return true;
            default:
                return false;
        }
    }

    public boolean validChop(Material type) {
        for (WoodcuttingSource source : WoodcuttingSource.values()) {
            if (type.name().equalsIgnoreCase(source.toString())) {
                return true;
            }
        }
        return false;
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
