package com.gmail.willramanand.RamSkills.leveler;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.events.SkillLevelUpEvent;
import com.gmail.willramanand.RamSkills.events.XpGainEvent;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.utils.Formatter;
import com.gmail.willramanand.RamSkills.utils.TextUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class Leveler {
    private final RamSkills plugin;
    private final XpReqs xpReqs;

    public Leveler(RamSkills plugin) {
        this.plugin = plugin;
        this.xpReqs = new XpReqs(plugin);
    }

    public void loadLevelReqs() {
        xpReqs.loadXpRequirements();
    }

//    public double getMultiplier(Player player, Skill skill) {
//        double multiplier = 1.0;
//        if (skill != null && !OptionL.getBoolean(Option.valueOf(skill + "_CHECK_MULTIPLIER_PERMISSIONS"))) { // Disable check option
//            return multiplier;
//        }
//        // Add permission multipliers
//        for (PermissionAttachmentInfo info : player.getEffectivePermissions()) {
//            String permission = info.getPermission().toLowerCase(Locale.ROOT);
//            if (permission.startsWith("aureliumskills.multiplier.")) {
//                permission = TextUtil.replace(permission, "aureliumskills.multiplier.", "");
//                if (pattern.matcher(permission).matches()) { // Parse all skills multiplier
//                    multiplier += Double.parseDouble(permission) / 100;
//                } else if (skill != null) { // Skill specific multiplier
//                    String skillName = skill.toString().toLowerCase(Locale.ROOT);
//                    if (permission.startsWith(skillName)) {
//                        permission = TextUtil.replace(permission, skillName + ".", "");
//                        if (pattern.matcher(permission).matches()) {
//                            multiplier += Double.parseDouble(permission) / 100;
//                        }
//                    }
//                }
//            }
//        }
//        // Add multiplier modifiers
//        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player);
//        if (playerData != null) {
//            multiplier += playerData.getTotalMultiplier(skill) / 100;
//        }
//        return multiplier;
//    }
//
//    public double getMultiplier(Player player) {
//        return getMultiplier(player, null);
//    }


    //Method for adding xp with a defined amount
    public void addXp(Player player, Skill skill, double amount) {
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        //Checks if player has a skill profile for safety
        if (skillPlayer == null) return;
        //Checks if xp amount is not zero
        if (amount == 0) return;
        //Gets xp amount
        double xpAmount = amount;
        //* getMultiplier(player, skill);
        //Calls event
        XpGainEvent event = new XpGainEvent(player, skill, xpAmount);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            //Adds xp
            double modifier = skillPlayer.getXpModifier(skill);
            skillPlayer.addSkillXp(skill, modifier * event.getAmount());
            player.sendMessage("Modifier: " + modifier);
            player.sendMessage("XP earned: " + modifier * event.getAmount());
            //Check if player leveled up
            checkLevelUp(player, skill);
            //Sends action bar message
            //plugin.getActionBar().sendXpActionBar(player, skill, event.getAmount());
            // Sends boss bar if enabled
            sendBossBar(player, skill, skillPlayer);


        }
    }

    //Method for setting xp with a defined amount
    public void setXp(Player player, Skill skill, double amount) {
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        //Checks if player has a skill profile for safety
        if (skillPlayer == null) return;
        double originalAmount = skillPlayer.getSkillXp(skill);
        //Sets Xp
        skillPlayer.setSkillXp(skill, amount);
        //Check if player leveled up
        checkLevelUp(player, skill);
        //Sends action bar message
        //plugin.getActionBar().sendXpActionBar(player, skill, amount - originalAmount);
        // Sends boss bar if enabled
        sendBossBar(player, skill, skillPlayer);
    }

    private void sendBossBar(Player player, Skill skill, SkillPlayer skillPlayer) {
        // Check whether boss bar should update
        plugin.getBossBar().incrementAction(player, skill);
        int currentAction = plugin.getBossBar().getCurrentAction(player, skill);
        if (currentAction != -1) {
            int level = skillPlayer.getSkillLevel(skill);
            boolean notMaxed = xpReqs.getListSize(skill) > skillPlayer.getSkillLevel(skill) - 1 && level < xpReqs.getMaxLevel();
            if (notMaxed) {
                plugin.getBossBar().sendBossBar(player, skill, skillPlayer.getSkillXp(skill), xpReqs.getXpRequired(skill, level + 1), level, false);
            } else {
                plugin.getBossBar().sendBossBar(player, skill, 1, 1, level, true);
            }
        }
    }

    public void checkLevelUp(Player player, Skill skill) {
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        if (skillPlayer == null) return;
        int currentLevel = skillPlayer.getSkillLevel(skill);
        double currentXp = skillPlayer.getSkillXp(skill);
        if (currentLevel < xpReqs.getMaxLevel()) { //Check max level options
            if (xpReqs.getListSize(skill) > currentLevel - 1) {
                if (currentXp >= xpReqs.getXpRequired(skill, currentLevel + 1)) {
                    levelUpSkill(skillPlayer, skill);
                }
            }
        }
    }

    private void levelUpSkill(SkillPlayer skillPlayer, Skill skill) {
        Player player = skillPlayer.getPlayer();

        double currentXp = skillPlayer.getSkillXp(skill);
        int level = skillPlayer.getSkillLevel(skill) + 1;

        skillPlayer.setSkillXp(skill, currentXp - xpReqs.getXpRequired(skill, level));
        skillPlayer.setSkillLevel(skill, level);
        // Adds money rewards if enabled
        if (plugin.isVaultActive()) {
            Economy economy = plugin.getEcon();
            double base = 10;
            double multiplier = 5;
            economy.depositPlayer(player, base + (multiplier * level));
        }
        // Reload items and armor to check for newly met requirements
        //plugin.getModifierManager().reloadPlayer(player);
        // Calls event
        SkillLevelUpEvent event = new SkillLevelUpEvent(player, skill, level);
        Bukkit.getPluginManager().callEvent(event);
        // Sends messages
        sendTitle(player, skill, level);
        playSound(player);
        player.sendMessage(getLevelUpMessage(player, skillPlayer, skill, level));
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> checkLevelUp(player, skill), 20L);
    }

    private void sendTitle(Player player, Skill skill, int level) {
        player.sendTitle(TextUtil.replace("&a{skill} Level Up", "{skill}", skill.getDisplayName()),
                TextUtil.replace("&6{old} ➜ {new}"
                        , "{old}", String.valueOf(level - 1)
                        , "{new}", String.valueOf(level))
                , 5, 100, 5);
    }

    private void playSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1f, 0.5f);
    }

    private String getLevelUpMessage(Player player, SkillPlayer skillPlayer, Skill skill, int level) {
        return "You have leveled up to " + skillPlayer.getSkillLevel(skill) + " for " + skill.getDisplayName();
    }

    public XpReqs getXpRequirements() {
        return xpReqs;
    }

}
