package com.gmail.willramanand.RamSkills.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.ui.SkillsScreen;
import com.gmail.willramanand.RamSkills.ui.StatsScreen;
import com.gmail.willramanand.RamSkills.utils.ColorUtils;
import com.gmail.willramanand.RamSkills.utils.XpModifierUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("skills|skill|sk")
@Description("Base RamSkills command")
public class SkillsCommand extends BaseCommand {

    private final RamSkills plugin;

    public SkillsCommand(RamSkills plugin) {
        this.plugin = plugin;
    }

    @Default
    @Description("List all skills and levels")
    public void onSkills(Player player, String[] args) {
        SkillsScreen skillsScreen = new SkillsScreen(plugin, player);
        player.openInventory(skillsScreen.getInventory());
    }

    @CommandAlias("stats")
    @Subcommand("stats")
    @Description("Show player stats")
    public void stats(Player player) {
        StatsScreen statsScreen = new StatsScreen(plugin, player);
        player.openInventory(statsScreen.getInventory());
    }

    @Subcommand("xp give")
    @CommandPermission("skills.xp")
    @CommandCompletion("@players @skills")
    @Description("Give player xp for specific skill")
    public void giveXp(CommandSender sender, @Flags("other") Player player, Skill skill, double amount) {
        plugin.getLeveler().addXp(player, skill, amount);
        sender.sendMessage(ColorUtils.colorMessage("&d" + amount + " &eXP was given to &d" + player.getName() + " &ein skill &d" + skill.getDisplayName()));
    }

    @Subcommand("xp set")
    @CommandPermission("skills.xp")
    @CommandCompletion("@players @skills")
    @Description("Set player xp for specific skill")
    public void setXp(CommandSender sender, @Flags("other") Player player, Skill skill, double amount) {
        plugin.getLeveler().setXp(player, skill, amount);
        sender.sendMessage(ColorUtils.colorMessage("&eSet &d" + amount + " &eXP to &d" + player.getName() + " &ein skill &d" + skill.getDisplayName()));
    }

    @Subcommand("level set")
    @CommandPermission("skills.lvl")
    @CommandCompletion("@players @skills")
    @Description("Set player level for specific skill")
    public void setLvl(CommandSender sender, @Flags("other") Player player, Skill skill, int lvl) {
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        skillPlayer.setSkillLevel(skill, lvl);
        skillPlayer.setSkillXp(skill, 0);
        sender.sendMessage(ColorUtils.colorMessage("&d" + player.getName() + " &ehas had skill &d" + skill.getDisplayName() + " &eset to lvl &d" + lvl));
    }

    @Subcommand("version|v")
    @Description("Displays plugin version and information")
    public void displayVersion(CommandSender sender) {
        sender.sendMessage(ColorUtils.colorMessage("&6---- &b" + plugin.getName() + "&6----"));
        sender.sendMessage(ColorUtils.colorMessage("&dAuthor: &eWillRam"));
        sender.sendMessage(ColorUtils.colorMessage("&dVersion: &e" + plugin.getDescription().getVersion()));
        sender.sendMessage(ColorUtils.colorMessage("&e" + plugin.getDescription().getDescription()));
    }

    @Subcommand("xpevent|xevent")
    @Description("Set a global xp event")
    @CommandPermission("skills.xpevent")
    public void xpEvent(CommandSender sender, int modifier) {
        if (modifier == 1) {
            sender.sendMessage(ColorUtils.colorMessage("&cDisabled &exp event!"));
        } else if (modifier > 1) {
            sender.sendMessage(ColorUtils.colorMessage("&aEnabled &exp event with modifier of &d" + modifier));
        } else {
            return;
        }
        XpModifierUtil.setModifier(modifier);
    }

    @Subcommand("help|h")
    public void onHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }
}
