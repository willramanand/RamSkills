package com.gmail.willramanand.RamSkills.ui;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.utils.Formatter;
import com.gmail.willramanand.RamSkills.utils.TextUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;

public class ActionBar {

    private final RamSkills plugin;

    private final HashMap<Player, Integer> currentAction = new HashMap<>();
    private final HashSet<Player> isPaused = new HashSet<>();
    private final HashMap<Player, Integer> timer = new HashMap<>();

    public ActionBar(RamSkills plugin) {
        this.plugin = plugin;
    }

    public void startUpdateActionBar() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!currentAction.containsKey(player)) {
                    currentAction.put(player, 0);
                }
                if (!isPaused.contains(player)) {
                    SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
                    if (skillPlayer != null) {
                        sendActionBar(player, TextUtil.replace("&c{hp}/{max_hp}❤                &b{mana}/{max_mana}۞"
                                , "{hp}", getHp(player)
                                , "{max_hp}", getMaxHp(player)
                                , "{mana}", getMana(skillPlayer)
                                , "{max_mana}", getMaxMana(skillPlayer)));
                    }
                }
            }
        }, 0L, 2L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Integer time = timer.get(player);
                    if (time != null) {
                        if (time != 0) {
                            timer.put(player, time - 1);
                        }
                    } else {
                        timer.put(player, 0);
                    }
                }
        }, 0L, 2L);
    }

    public void sendAbilityActionBar(Player player, String message) {
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        if (skillPlayer == null) return;
        sendActionBar(player, TextUtil.replace("&c{hp}/{max_hp}❤    &6{message}   &b{mana}/{max_mana}۞",
                "{hp}", getHp(player),
                "{max_hp}", getMaxHp(player),
                "{mana}", getMana(skillPlayer),
                "{max_mana}", getMaxMana(skillPlayer),
                "{message}", message));
        setPaused(player, 40);
    }

    private String getHp(Player player) {
        return String.valueOf(Math.round(player.getHealth()));
    }

    private String getMaxHp(Player player) {
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (attribute != null) {
            return Formatter.decimalFormat(attribute.getValue(), 1);
        }
        return "";
    }

    private String getMana(SkillPlayer skillPlayer) {
        return Formatter.decimalFormat(skillPlayer.getMana(), 1);
    }

    private String getMaxMana(SkillPlayer skillPlayer) {
        return Formatter.decimalFormat(skillPlayer.getMaxMana(), 1);
    }

    private void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public void setPaused(Player player, int ticks) {
        isPaused.add(player);
        Integer action = currentAction.get(player);
        if (action != null) {
            currentAction.put(player, action + 1);
        } else {
            currentAction.put(player, 0);
        }
        int thisAction = this.currentAction.get(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                Integer actionBarCurrentAction = currentAction.get(player);
                if (actionBarCurrentAction != null) {
                    if (thisAction == actionBarCurrentAction) {
                        isPaused.remove(player);
                    }
                }
            }
        }.runTaskLater(plugin, ticks);
    }

    public void resetActionBar(Player player) {
        currentAction.remove(player);
        isPaused.remove(player);
        timer.clear();
    }

    public void resetActionBars() {
        currentAction.clear();
        isPaused.clear();
        timer.clear();
    }

}
