package com.gmail.willramanand.RamSkills.ui;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.utils.BigNumber;
import com.gmail.willramanand.RamSkills.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class SkillBossBar implements Listener {

    private final Map<Player, Map<Skill, BossBar>> bossBars;
    private final Map<Player, Map<Skill, Integer>> currentActions;
    private final Map<Player, Map<Skill, Integer>> checkCurrentActions;
    private long stayTime = 60L;
    private Map<Skill, BarColor> colors;
    private BarStyle barStyle = BarStyle.SEGMENTED_6;
    private final RamSkills plugin;

    public SkillBossBar(RamSkills plugin) {
        this.bossBars = new HashMap<>();
        this.currentActions = new HashMap<>();
        this.plugin = plugin;
        this.checkCurrentActions = new HashMap<>();
    }

    public void load() {
        colors = new HashMap<>();

        for (Skill skill : Skills.values()) {
            colors.put(skill, skill.getBarColor());
        }
        for (Map.Entry<Player, Map<Skill, BossBar>> entry : bossBars.entrySet()) {
            Map<Skill, BossBar> bossBars = entry.getValue();
            for (Map.Entry<Skill, BossBar> bossBarEntry : bossBars.entrySet()) {
                bossBarEntry.getValue().setVisible(false);
                bossBarEntry.getValue().removeAll();
            }
        }
        bossBars.clear();
    }

    public void sendBossBar(Player player, Skill skill, double currentXp, double levelXp, int level, boolean maxed) {
        BarColor color = getColor(skill);
        BarStyle style = getStyle(skill);
        BossBar bossBar;

        if (!bossBars.containsKey(player)) bossBars.put(player, new HashMap<>());
        bossBar = bossBars.get(player).get(skill);
        // If player does not have a boss bar in that skill
        if (bossBar == null) {
            if (!maxed) {
                bossBar = Bukkit.createBossBar(TextUtil.replace("&6{skill} {level} &7({current_xp}/{level_xp} XP)",
                        "{skill}", skill.getDisplayName(),
                        "{level}", String.valueOf(level),
                        "{current_xp}", String.valueOf((int) currentXp),
                        "{level_xp}", BigNumber.withSuffix((long) levelXp)), color, style);
            } else {
                bossBar = Bukkit.createBossBar(TextUtil.replace("&6{skill} {level} &7(MAXED)",
                        "{skill}", skill.getDisplayName(),
                        "{level}", String.valueOf(level)), color, style);
            }
            double progress = currentXp / levelXp;
            if (progress <= 1 && progress >= 0) {
                bossBar.setProgress(currentXp / levelXp);
            } else {
                bossBar.setProgress(1.0);
            }
            bossBar.addPlayer(player);
            // Add to maps
            bossBars.get(player).put(skill, bossBar);
        }
        // Use existing one
        else {
            if (!maxed) {
                bossBar.setTitle(TextUtil.replace("&6{skill} {level} &7({current_xp}/{level_xp} XP)",
                        "{skill}", skill.getDisplayName(),
                        "{level}", String.valueOf(level),
                        "{current_xp}", String.valueOf((int) currentXp),
                        "{level_xp}", BigNumber.withSuffix((long) levelXp)));
            } else {
                bossBar.setTitle(TextUtil.replace("&6{skill} {level} &7(MAXED)",
                        "{level}", String.valueOf(level),
                        "{skill}", skill.getDisplayName()));
            }
            double progress = currentXp / levelXp;
            if (progress <= 1 && progress >= 0) {
                bossBar.setProgress(currentXp / levelXp);
            } else {
                bossBar.setProgress(1.0);
            }
            bossBar.setVisible(true);
        }
        if (!currentActions.containsKey(player)) currentActions.put(player, new HashMap<>());
        Integer currentAction = currentActions.get(player).get(skill);
        if (currentAction != null) {
            currentActions.get(player).put(skill, currentAction + 1);
        } else {
            currentActions.get(player).put(skill, 0);
        }
        scheduleHide(player, skill, bossBar);
    }

    public void incrementAction(Player player, Skill skill) {
        if (!checkCurrentActions.containsKey(player)) checkCurrentActions.put(player, new HashMap<>());
        Integer currentAction = checkCurrentActions.get(player).get(skill);
        if (currentAction != null) {
            checkCurrentActions.get(player).put(skill, currentAction + 1);
        } else {
            checkCurrentActions.get(player).put(skill, 0);
        }
    }

    private void scheduleHide(Player player, Skill skill, BossBar bossBar) {
        Map<Skill, Integer> multiCurrentActions = currentActions.get(player);
        if (multiCurrentActions == null) return;
        final int currentAction = multiCurrentActions.get(skill);
        new BukkitRunnable() {
            @Override
            public void run() {
                Map<Skill, Integer> multiCurrentActions = currentActions.get(player);
                if (multiCurrentActions == null) return;
                if (currentAction == multiCurrentActions.get(skill)) {
                    bossBar.setVisible(false);
                    checkCurrentActions.remove(player);
                }
            }
        }.runTaskLater(plugin, stayTime);
    }


    private BarColor getColor(Skill skill) {
        BarColor color = colors.get(skill);
        if (color == null) color = BarColor.GREEN;
        return color;
    }

    private BarStyle getStyle(Skill skill) {
        return barStyle;
    }

    public int getCurrentAction(Player player, Skill skill) {
        Map<Skill, Integer> multiCurrentActions = checkCurrentActions.get(player);
        if (multiCurrentActions != null) {
            return multiCurrentActions.get(skill);
        }
        return -1;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        bossBars.remove(player);
        currentActions.remove(player);
        checkCurrentActions.remove(player);
    }
}
