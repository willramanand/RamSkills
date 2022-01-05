package com.gmail.willramanand.RamSkills.player;

import com.gmail.willramanand.RamSkills.RamSkills;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    private final RamSkills plugin;
    private final ConcurrentHashMap<UUID, SkillPlayer> playerData;

    public PlayerManager(RamSkills plugin) {
        this.plugin = plugin;
        this.playerData = new ConcurrentHashMap<>();
        startAutoSave();
    }


    @Nullable
    public SkillPlayer getPlayerData(Player player) {
        return playerData.get(player.getUniqueId());
    }

    @Nullable
    public SkillPlayer getPlayerData(UUID id) {
        return this.playerData.get(id);
    }

    public void addPlayerData(@NotNull SkillPlayer skillPlayer) {
        this.playerData.put(skillPlayer.getUuid(), skillPlayer);
    }

    public void removePlayerData(UUID id) {
        this.playerData.remove(id);
    }

    public boolean hasPlayerData(Player player) {
        return playerData.containsKey(player.getUniqueId());
    }

    public ConcurrentHashMap<UUID, SkillPlayer> getPlayerDataMap() {
        return playerData;
    }

    public void startAutoSave() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
                    if (skillPlayer != null && !skillPlayer.isSaving()) {
                        plugin.getConfigManager().save(skillPlayer.getPlayer(), false);
                    }
                }
            }
        }.runTaskTimer(plugin, 6000L, 6000L);
    }
}
