package com.gmail.willramanand.RamSkills.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.mana.Ability;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.stats.Stat;
import com.gmail.willramanand.RamSkills.utils.ColorUtils;
import com.gmail.willramanand.RamSkills.utils.XpModifierUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
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
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        if (args.length == 0) {
            player.sendMessage(ColorUtils.colorMessage("&6_______ [ &bSkills &6] _______ "));
            for (Skill skill : Skills.values()) {
                int lvl = skillPlayer.getSkillLevel(skill);
                Component component = Component.text(skill.getDisplayName(), TextColor.color(255, 170, 0));
                Component lvlComponent = Component.text(": lvl " + lvl, TextColor.color(85, 255, 85));
                component = component.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/skills l " + skill.getDisplayName().toLowerCase()));
                component = component.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("View Skill", TextColor.color(170, 0, 170))));
                player.sendMessage(component.append(lvlComponent) );
            }
        } else if (args.length == 2) {
            for (Skill skill : Skills.values()) {
                if (args[1].equalsIgnoreCase(skill.getDisplayName())) {
                    player.sendMessage(ColorUtils.colorMessage("&6______________ [ &b" + skill.getDisplayName() + " &6] ______________"));
                    player.sendMessage(ColorUtils.colorMessage("&dDescription: &e" + skill.getDescription()));
                    player.sendMessage(ColorUtils.colorMessage("&dLevel: &a" + skillPlayer.getSkillLevel(skill)));
                    if (skill.getAbility() != null) {
                        player.sendMessage("");
                        for (Ability ability : skill.getAbility()) {
                            if (skillPlayer.getSkillLevel(skill) < ability.getUnlock()) {
                                player.sendMessage(ColorUtils.colorMessage("&3" + ability.getDisplayName() + "&8: &cUNLOCKS AT LVL 25"));
                            } else if (skillPlayer.getSkillLevel(skill) >= ability.getUnlock() && skillPlayer.getSkillLevel(skill) < ability.getUpgrade()) {
                                player.sendMessage(ColorUtils.colorMessage("&3" + ability.getDisplayName() + "&8: &aUNLOCKED &8| &dUPGRADES AT LVL 50"));
                            } else if (skillPlayer.getSkillLevel(skill) >= ability.getUpgrade()) {
                                player.sendMessage(ColorUtils.colorMessage("&3" + ability.getDisplayName() + "&8: &dUPGRADED"));
                            }
                        }
                    }
                    player.sendMessage("");
                    for (Stat stat : skill.getStats()) {
                        player.sendMessage(ColorUtils.colorMessage(stat.getPrefix() + stat.getDisplayName() + " "
                                + stat.getSymbol() + ": " + plugin.getStatRegistry().print(stat, player)));
                    }
                }
            }
        }
    }

    @Subcommand("stats")
    @Description("Show player stats")
    public void stats(Player player) {
        player.sendMessage(ColorUtils.colorMessage("&6_____________ [ &bStats &6] _____________"));
        for (Stat stat : Stat.values()) {
            String point = plugin.getStatRegistry().print(stat, player);
            player.sendMessage(ColorUtils.colorMessage(stat.getPrefix() + stat.getDisplayName() + " " + stat.getSymbol() + ": " + point));
        }
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
