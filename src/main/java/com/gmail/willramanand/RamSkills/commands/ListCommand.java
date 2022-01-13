package com.gmail.willramanand.RamSkills.commands;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.stats.Stat;
import com.gmail.willramanand.RamSkills.utils.ColorUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ListCommand extends SubCommand {

    private final RamSkills plugin;

    public ListCommand(RamSkills plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        if (args.length == 1) {
            player.sendMessage(ColorUtils.colorMessage("&6_______ [ &bSkills &6] _______ "));
            for (Skill skill : Skills.values()) {
                int lvl = skillPlayer.getSkillLevel(skill);
                Component component = Component.text(skill.getDisplayName(), TextColor.color(255, 170, 0));
                Component lvlComponent = Component.text(" lvl " + lvl, TextColor.color(85, 255, 85));
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
                    for (Stat stat : skill.getStats()) {
                        player.sendMessage(ColorUtils.colorMessage(stat.getPrefix() + stat.getDisplayName() + " "
                                + stat.getSymbol() + ": " + plugin.getStatRegistry().print(stat, player)));
                    }
                }
            }
        }
    }

    @Override
    public String name() {
        return Commands.LIST.getName();
    }

    @Override
    public String info() {
        return "List the skills and your levels.";
    }

    @Override
    public List<String> aliases() {
        List<String> args = new ArrayList<>();
        args.add("l");

        return args;
    }

    @Override
    public List<String> getPrimaryArguments() {
        return null;
    }

    @Override
    public List<String> getSecondaryArguments(String[] args) {
        return null;
    }
}
