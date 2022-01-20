package com.gmail.willramanand.RamSkills;

import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.PaperCommandManager;
import com.gmail.willramanand.RamSkills.commands.SkillsCommand;
import com.gmail.willramanand.RamSkills.leveler.Leveler;
import com.gmail.willramanand.RamSkills.listeners.DamageListener;
import com.gmail.willramanand.RamSkills.listeners.FortuneListener;
import com.gmail.willramanand.RamSkills.listeners.PlayerListener;
import com.gmail.willramanand.RamSkills.mana.ManaAbility;
import com.gmail.willramanand.RamSkills.mana.ManaManager;
import com.gmail.willramanand.RamSkills.player.PlayerConfiguration;
import com.gmail.willramanand.RamSkills.player.PlayerManager;
import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.SkillRegistry;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.skills.agility.AgilityLeveler;
import com.gmail.willramanand.RamSkills.skills.alchemy.AlchemyLeveler;
import com.gmail.willramanand.RamSkills.skills.combat.CombatLeveler;
import com.gmail.willramanand.RamSkills.skills.cooking.CookingLeveler;
import com.gmail.willramanand.RamSkills.skills.defense.DefenseLeveler;
import com.gmail.willramanand.RamSkills.skills.enchanting.EnchantingLeveler;
import com.gmail.willramanand.RamSkills.skills.excavation.ExcavationLeveler;
import com.gmail.willramanand.RamSkills.skills.farming.FarmingLeveler;
import com.gmail.willramanand.RamSkills.skills.fishing.FishingLeveler;
import com.gmail.willramanand.RamSkills.skills.mining.MiningLeveler;
import com.gmail.willramanand.RamSkills.skills.sorcery.SorceryLeveler;
import com.gmail.willramanand.RamSkills.skills.woodcutting.WoodcuttingLeveler;
import com.gmail.willramanand.RamSkills.source.SourceManager;
import com.gmail.willramanand.RamSkills.source.SourceRegistry;
import com.gmail.willramanand.RamSkills.stats.StatManager;
import com.gmail.willramanand.RamSkills.stats.StatRegistry;
import com.gmail.willramanand.RamSkills.ui.ActionBar;
import com.gmail.willramanand.RamSkills.ui.SkillBossBar;
import com.gmail.willramanand.RamSkills.utils.ColorUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RamSkills extends JavaPlugin {

    private static RamSkills i;

    private static Logger log = Logger.getLogger("Minecraft");

    private static Economy econ = null;

    private PlayerConfiguration playerConfiguration;
    private PlayerManager playerManager;
    private PlayerListener playerListener;
    private PaperCommandManager commandManager;

    private SourceRegistry sourceRegistry;
    private SourceManager sourceManager;

    private SkillRegistry skillRegistry;

    private StatRegistry statRegistry;
    private StatManager statManager;

    private Leveler leveler;
    private SorceryLeveler sorceryLeveler;
    private MiningLeveler miningLeveler;
    private ExcavationLeveler excavationLeveler;
    private WoodcuttingLeveler woodcuttingLeveler;

    private SkillBossBar bossBar;
    private ActionBar actionBar;

    private ManaManager manaManager;
    private ManaAbility manaAbility;

    @Override
    public void onEnable() {
        i = this;

        long startTime = System.currentTimeMillis();
        log.info(ColorUtils.colorMessage("[" + this.getName() + "] &6===&b ENABLE START &6==="));

        if (isVaultActive()) {
            log.info(ColorUtils.colorMessage("[" + this.getName() + "] &2Enabling Vault integration."));
            setupEconomy();
        }

        playerConfiguration = new PlayerConfiguration(this);
        playerManager = new PlayerManager(this);
        playerListener = new PlayerListener(this);
        sourceRegistry = new SourceRegistry();
        sourceManager = new SourceManager(this);
        skillRegistry = new SkillRegistry();
        statRegistry = new StatRegistry(this);
        statManager = new StatManager(this);
        leveler = new Leveler(this);
        bossBar = new SkillBossBar(this);
        actionBar = new ActionBar(this);
        manaManager = new ManaManager(this);
        manaAbility = new ManaAbility(this);
        sorceryLeveler = new SorceryLeveler(this);
        miningLeveler = new MiningLeveler(this);
        excavationLeveler = new ExcavationLeveler(this);
        woodcuttingLeveler = new WoodcuttingLeveler(this);

        // Config
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        // Listeners
        registerEvents();

        // Commands
        registerCommands();

        // Load Modules
        sourceManager.loadSources();
        statManager.loadStats();

        bossBar.load();
        actionBar.startUpdateActionBar();

        manaManager.regenMana();

        leveler.loadLevelReqs();

        startTime = System.currentTimeMillis() - startTime;
        log.info(ColorUtils.colorMessage("[" + this.getName() + "] &6=== &bENABLE &2COMPLETE &6(&eTook &d" + startTime +"ms&6) ==="));
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerConfiguration.save(player, true);
        }

        actionBar.resetActionBars();
        log.info("Disabled");
    }

    public static RamSkills getInstance() { return i; }

    public static Logger logger() { return log; }

    private void registerCommands() {
        commandManager = new PaperCommandManager(this);

        commandManager.enableUnstableAPI("help");

        for (Skill skill : Skills.values()) {
            skillRegistry.register(skill.getDisplayName(), skill);
        }

        commandManager.getCommandContexts().registerContext(Skill.class, c -> {
            String input = c.popFirstArg();
            Skill skill = skillRegistry.getSkill(input);
            if (skill != null) {
                return skill;
            } else {
                throw new InvalidCommandArgument("Skill " + input + " not found!");
            }
        });

        commandManager.getCommandCompletions().registerAsyncCompletion("skills", context -> {
           List<String> values = new ArrayList<>();
           for (Skill skill : Skills.values()) {
               values.add(skill.toString().toLowerCase());
           }
           return values;
        });

        commandManager.registerCommand(new SkillsCommand(this));
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(playerListener, this);
        pm.registerEvents(new DamageListener(this), this);
        pm.registerEvents(bossBar, this);
        pm.registerEvents(new FortuneListener(this), this);

        // Levelers
        pm.registerEvents(new CombatLeveler(this), this);
        pm.registerEvents(miningLeveler, this);
        pm.registerEvents(excavationLeveler, this);
        pm.registerEvents(new CookingLeveler(this), this);
        pm.registerEvents(new EnchantingLeveler(this), this);
        pm.registerEvents(woodcuttingLeveler, this);
        pm.registerEvents(new DefenseLeveler(this), this);
        pm.registerEvents(new FarmingLeveler(this), this);
        pm.registerEvents(new AgilityLeveler(this), this);
        pm.registerEvents(new FishingLeveler(this), this);
        pm.registerEvents(new AlchemyLeveler(this), this);

        pm.registerEvents(manaAbility, this);

    }

    public boolean isVaultActive() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            log.warning("Vault is not installed, disabling Vault integration!");
            return false;
        }
        return true;
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public Economy getEcon() { return econ; }

    public PlayerConfiguration getPlayerConfig() {
        return playerConfiguration;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public PaperCommandManager getCommandManager() { return commandManager; }

    public SourceRegistry getSourceRegistry() { return sourceRegistry; }

    public SourceManager getSourceManager() { return sourceManager; }

    public StatRegistry getStatRegistry() { return statRegistry; }

    public StatManager getStatManager() { return statManager; }

    public Leveler getLeveler() { return leveler; }

    public SorceryLeveler getSorceryLeveler() { return sorceryLeveler; }

    public ManaAbility getManaAbility() { return manaAbility; }

    public MiningLeveler getMiningLeveler() { return miningLeveler; }

    public ExcavationLeveler getExcavationLeveler() { return excavationLeveler; }

    public WoodcuttingLeveler getWoodcuttingLeveler() { return woodcuttingLeveler; }

    public SkillBossBar getBossBar() { return bossBar; }

    public ActionBar getActionBar() { return actionBar; }

}
