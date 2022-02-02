package com.gmail.willramanand.RamSkills.api;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.stats.Stat;
import org.bukkit.entity.Player;

public class RamSkillsAPI {

    private static RamSkills plugin;

    public static void setPlugin(RamSkills plugin) {
        RamSkillsAPI.plugin = plugin;
    }

    /**
     * Get the RamSkills plugin instance
     * @return the instance of RamSkills
     */
    public static RamSkills getPlugin() {
        if (plugin == null) {
            throw new IllegalStateException("The RamSkillsAPI is not loaded yet");
        }
        return plugin;
    }

    /**
     * Adds Skill XP to a player for a certain skill
     * @param player The player to add xp to
     * @param skill The skill to add xp to
     * @param amount The amount to add
     */
    public static void addXp(Player player, Skill skill, double amount) {
        plugin.getLeveler().addXp(player, skill, amount);
    }

    /**
     * Adds Skill XP to a player for a certain skill excludes modifier multipliers
     * @param player The player to add xp to
     * @param skill The skill to add xp to
     * @param amount The amount to add
     */
    public static void addUnmodifiedXp(Player player, Skill skill, double amount) {
        plugin.getLeveler().addUnmodifiedXp(player, skill, amount);
    }

    /**
     * Gets the skill level of a player
     * @param player The player to get from
     * @param skill The skill level to get
     * @return the skill level of a player, or 1 if player does not have a skills profile
     */
    public static int getSkillLevel(Player player, Skill skill) {
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        if (skillPlayer != null) {
            return skillPlayer.getSkillLevel(skill);
        }
        else {
            return 1;
        }
    }

    /**
     * Gets the stat level of a player
     * @param player The player to get from
     * @param stat The stat to get
     * @return The stat level
     */
    public static double getStatPoints(Player player, Stat stat) {
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        if (skillPlayer != null) {
            return skillPlayer.getStatPoint(stat);
        }
        else {
            return 0;
        }
    }

}
