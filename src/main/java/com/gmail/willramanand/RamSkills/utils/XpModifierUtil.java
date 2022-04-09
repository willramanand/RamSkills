package com.gmail.willramanand.RamSkills.utils;

import com.gmail.willramanand.RamSkills.RamSkills;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

public class XpModifierUtil {

    private static int globalXpModifier = 1;
    private static boolean xpModifierActive = false;

    public static void load() {
        ConfigurationSection section = RamSkills.getInstance().getConfig().getConfigurationSection("xp_event");
        int modifier = section.getInt("modifier");
        boolean active = section.getBoolean("active");

        if (modifier < 1) {
            Bukkit.getServer().getConsoleSender().sendMessage(Txt.parse("&cInvalid modifier in config. Setting to defaults."));
            section.set("modifier", 1);
            section.set("active", false);
            RamSkills.getInstance().saveConfig();
        } else if (modifier == 1 && xpModifierActive) {
            Bukkit.getServer().getConsoleSender().sendMessage(Txt.parse("&cXP modifier active even though modifier is 1. Setting to false."));
            section.set("active", false);
            RamSkills.getInstance().saveConfig();
        } else {
            globalXpModifier = modifier;
            xpModifierActive = active;
            RamSkills.getInstance().getLogger().info(Txt.parse("&eGlobal XP modifier set to: &d" + modifier));
        }
    }

    public static void save() {
        ConfigurationSection section = RamSkills.getInstance().getConfig().getConfigurationSection("xp_event");
        section.set("modifier", globalXpModifier);
        section.set("active", xpModifierActive);
        RamSkills.getInstance().saveConfig();
    }

    public static void setModifier(int modifier) {
        globalXpModifier = modifier;
        xpModifierActive = modifier > 1;
    }

    public static int getModifier() { return globalXpModifier; }

    public static boolean getActive() {
        return xpModifierActive;
    }
}
