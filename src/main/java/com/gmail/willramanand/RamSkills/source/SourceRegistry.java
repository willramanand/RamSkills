package com.gmail.willramanand.RamSkills.source;

import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.utils.Formatter;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class SourceRegistry {

    private final Map<Skill, Class<?>> registry;

    public SourceRegistry() {
        registry = new HashMap<>();

        try {
            for (Skill skill : Skills.values()) {
                String className = skill.getDisplayName() + "Source";
                Class<?> clazz = Class.forName("com.gmail.willramanand.RamSkills.skills." + skill.toString().toLowerCase() + "." + className);
                registry.put(skill, clazz);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void register(Skill skill, Class<? extends Source> clazz) {
        registry.put(skill, clazz);
    }

    public Source[] values(Skill skill) {
        Class<?> clazz = registry.get(skill);
        if (clazz == null) return new Source[0];
        try {
            Method method = clazz.getMethod("values");
            Object object = method.invoke(null);
            if (object instanceof Source[]) {
                return (Source[]) object;
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return new Source[0];
    }

    public Set<Source> values() {
        Set<Source> sourceSet = new HashSet<>();
        for (Class<?> sourceClass : registry.values()) {
            try {
                Method method = sourceClass.getMethod("values");
                Object object = method.invoke(null);
                if (object instanceof Source[]) {
                    sourceSet.addAll(Arrays.asList((Source[]) object));
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return sourceSet;
    }

    @Nullable
    public Source valueOf(String sourceString) {
        for (Source source : values()) {
            if (source.toString().equals(sourceString.toUpperCase())) {
                return source;
            }
        }
        return null;
    }
}
