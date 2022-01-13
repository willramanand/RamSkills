package com.gmail.willramanand.RamSkills.stats;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.stats.stat.AbstractStat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class StatRegistry {

    private final Map<Stat, AbstractStat> registry;
    private final RamSkills plugin;

    public StatRegistry(RamSkills plugin) {
        this.plugin = plugin;
        registry = new HashMap<>();

        try {
            for (Stat stat : Stat.values()) {
                String className = stat.getClazzName();
                Class<?> clazz = Class.forName("com.gmail.willramanand.RamSkills.stats.stat." + className);
                Constructor[] constructor = clazz.getConstructors();
                AbstractStat object = (AbstractStat) constructor[0].newInstance(plugin);
                registry.put(stat, object);
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setBase(Stat stat, double amount) {
        try {
            registry.get(stat).setBase(amount);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void setPointsPerLvl(Stat stat, double amount) {
        try {
            registry.get(stat).setPointsPerLevel(amount);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void setAssociatedSkills(Stat stat, String[] skills) {
        try {
            registry.get(stat).setAssociatedSkills(skills);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void allocate(Player player) {
        for (Stat stat : Stat.values()) {
            try {
                registry.get(stat).allocate(player);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    public String print(Stat stat, Player player) {
        try {
            return registry.get(stat).print(player);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return "Error in stat registry!";
    }
}
