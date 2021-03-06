package com.gmail.willramanand.RamSkills.skills;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SkillRegistry {

    public final Map<String, Skill> skills;

    public SkillRegistry() {
        this.skills = new HashMap<>();
    }

    public void register(String key, Skill skill) {
        this.skills.put(key.toLowerCase(), skill);
    }

    public Collection<Skill> getSkills() {
        return skills.values();
    }

    public Skill getSkill(String key) {
        return this.skills.get(key.toLowerCase());
    }
}
