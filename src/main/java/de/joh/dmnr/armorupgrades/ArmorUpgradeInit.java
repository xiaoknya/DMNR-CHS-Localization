package de.joh.dmnr.armorupgrades;

import de.joh.dmnr.DragonMagicAndRelics;
import de.joh.dmnr.armorupgrades.init.*;
import de.joh.dmnr.armorupgrades.types.ArmorUpgrade;
import de.joh.dmnr.events.CommonEventHandler;
import de.joh.dmnr.item.items.dragonmagearmor.DragonMageArmor;
import de.joh.dmnr.utils.RLoc;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * An initialization of all upgrades for the Dragon Mage Armor.
 * Each new upgrade must also be entered getAllUpgrades or in one of the arrays!
 * Each new upgrade must also be listed in the configs for the initial upgrades!
 * @see DragonMageArmor
 * @see de.joh.dmnr.config.CommonConfigs
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid = DragonMagicAndRelics.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ArmorUpgradeInit {
    public static ArmorUpgrade FLY;
    public static ArmorUpgrade SATURATION;
    public static ArmorUpgrade MOVEMENT_SPEED;
    public static ArmorUpgrade WATER_BREATHING;
    public static ArmorUpgrade METEOR_JUMP;
    public static ArmorUpgrade DOLPHINS_GRACE;
    public static ArmorUpgrade REGENERATION;
    public static ArmorUpgrade MANA_BOOST;
    public static ArmorUpgrade MANA_REGEN;
    public static ArmorUpgrade HEALTH_BOOST;
    public static ArmorUpgrade REACH_DISTANCE;
    /**
     * This upgrade reduces the damage the wearer receives, regardless of the source. Currently 20% reduction per level.
     * @see de.joh.dmnr.events.DamageEventHandler
     */
    public static ArmorUpgrade DAMAGE_RESISTANCE;
    /**
     * This upgrade increases the damage dealt by the wearer of the Dragon Mage Armor. Both spells and normal weapons.
     * @see de.joh.dmnr.events.DamageEventHandler
     */
    public static ArmorUpgrade DAMAGE_BOOST;
    public static ArmorUpgrade MINOR_FIRE_RESISTANCE;
    public static ArmorUpgrade MAJOR_FIRE_RESISTANCE;
    /**
     * This upgrade protects you from fall- and kinetic damage.
     * Increasing the maximum level has no effect without further adjustments.
     * @see de.joh.dmnr.events.DamageEventHandler
     */
    public static ArmorUpgrade KINETIC_RESISTANCE;

    /**
     * This upgrade protects you from explosion damage.
     * Increasing the maximum level has no effect without further adjustments.
     * @see de.joh.dmnr.events.DamageEventHandler
     */
    public static ArmorUpgrade EXPLOSION_RESISTANCE;

    /**
     * This upgrade reflects projectiles when they try to hit you.
     * With each upgrade, a projectile can be reflected more before it needs to recharge.
     * @see de.joh.dmnr.events.DamageEventHandler
     */
    public static ArmorUpgrade PROJECTILE_REFLECTION;

    /**
     * This upgrade lets you enter Mist Form when you are about to die.
     * Increasing the maximum level has no effect without further adjustments.
     * @see de.joh.dmnr.events.DamageEventHandler
     */
    public static ArmorUpgrade MIST_FORM;

    public static ArmorUpgrade JUMP;

    /**
     * This upgrade allows the wearer to use Elytra Flight. Level 2 gives a permanent boost, but needs mana.
     * Increasing the maximum level has no effect without further adjustments.
     * @see DragonMageArmor
     */
    public static ArmorUpgrade ELYTRA;
    public static ArmorUpgrade ANGEL_FLIGHT;

    public static ArmorUpgrade NIGHT_VISION;

    public static ArmorUpgrade BURNING_FRENZY;
    public static ArmorUpgrade MAJOR_MANA_BOOST;

    /**
     * Increases castet Spells Attributes:
     * <br> - MAGNITUDE +0.5
     * <br> - DAMAGE    +3
     * <br> - DURATION: +30%
     * @see CommonEventHandler
     */
    public static ArmorUpgrade SORCERERS_PRIDE;
    
    @SubscribeEvent
    public static void registerArmorUpgrades(final RegistryEvent.Register<ArmorUpgrade> event) {
        event.getRegistry().register(ArmorUpgradeInit.FLY);
        event.getRegistry().register(ArmorUpgradeInit.SATURATION);
        event.getRegistry().register(ArmorUpgradeInit.MOVEMENT_SPEED);
        event.getRegistry().register(ArmorUpgradeInit.WATER_BREATHING);

        event.getRegistry().register(ArmorUpgradeInit.METEOR_JUMP);
        event.getRegistry().register(ArmorUpgradeInit.DOLPHINS_GRACE);
        event.getRegistry().register(ArmorUpgradeInit.REGENERATION);
        event.getRegistry().register(ArmorUpgradeInit.MANA_BOOST);
        event.getRegistry().register(ArmorUpgradeInit.MAJOR_MANA_BOOST);

        event.getRegistry().register(ArmorUpgradeInit.MANA_REGEN);
        event.getRegistry().register(ArmorUpgradeInit.HEALTH_BOOST);
        event.getRegistry().register(ArmorUpgradeInit.DAMAGE_RESISTANCE);
        event.getRegistry().register(ArmorUpgradeInit.DAMAGE_BOOST);
        event.getRegistry().register(ArmorUpgradeInit.REACH_DISTANCE);

        event.getRegistry().register(ArmorUpgradeInit.MINOR_FIRE_RESISTANCE);
        event.getRegistry().register(ArmorUpgradeInit.MAJOR_FIRE_RESISTANCE);
        event.getRegistry().register(ArmorUpgradeInit.KINETIC_RESISTANCE);
        event.getRegistry().register(ArmorUpgradeInit.EXPLOSION_RESISTANCE);
        event.getRegistry().register(ArmorUpgradeInit.PROJECTILE_REFLECTION);

        event.getRegistry().register(ArmorUpgradeInit.MIST_FORM);
        event.getRegistry().register(ArmorUpgradeInit.JUMP);
        event.getRegistry().register(ArmorUpgradeInit.ELYTRA);
        event.getRegistry().register(ArmorUpgradeInit.ANGEL_FLIGHT);
        event.getRegistry().register(ArmorUpgradeInit.NIGHT_VISION);

        event.getRegistry().register(ArmorUpgradeInit.BURNING_FRENZY);
        event.getRegistry().register(ArmorUpgradeInit.SORCERERS_PRIDE);
    }

    //todo: revise upgrade costs and recipes
    static {
        ArmorUpgradeInit.REACH_DISTANCE = new ArmorUpgradeReachDistance(RLoc.create("armorupgrade/reach_distance"), 8);
        ArmorUpgradeInit.FLY = new ArmorUpgradeFly(RLoc.create("armorupgrade/fly"), 7);
        ArmorUpgradeInit.SATURATION = new ArmorUpgradeSaturation(RLoc.create("armorupgrade/saturation"), 8);
        ArmorUpgradeInit.MOVEMENT_SPEED = new ArmorUpgradeSpeed(RLoc.create("armorupgrade/movement_speed"), 4);
        ArmorUpgradeInit.WATER_BREATHING = new ArmorUpgradeWaterbreathing(RLoc.create("armorupgrade/water_breathing"), 2, 2);
        ArmorUpgradeInit.JUMP = new ArmorUpgradeJump(RLoc.create("armorupgrade/jump"), 2);
        ArmorUpgradeInit.METEOR_JUMP = new ArmorUpgradeMeteorJump(RLoc.create("armorupgrade/meteor_jump"), 3);
        ArmorUpgradeInit.DOLPHINS_GRACE = new ArmorUpgradeSwimSpeed(RLoc.create("armorupgrade/dolphins_grace"), 2, 2);
        ArmorUpgradeInit.REGENERATION = new ArmorUpgradeRegeneration(RLoc.create("armorupgrade/regeneration"), 1, 10);
        ArmorUpgradeInit.MANA_BOOST = new ArmorUpgradeManaBoost(RLoc.create("armorupgrade/mana_boost"), false, 4);
        ArmorUpgradeInit.MAJOR_MANA_BOOST = new ArmorUpgradeManaBoost(RLoc.create("armorupgrade/major_mana_boost"), true, 5);
        ArmorUpgradeInit.MANA_REGEN = new ArmorUpgradeManaRegeneration(RLoc.create("armorupgrade/mana_regen"), 3, 5);
        ArmorUpgradeInit.HEALTH_BOOST = new ArmorUpgradeHealthBoost(RLoc.create("armorupgrade/health_boost"), 2);
        ArmorUpgradeInit.DAMAGE_RESISTANCE = new ArmorUpgrade(RLoc.create("armorupgrade/damage_resistance"), 3, true, 8);
        ArmorUpgradeInit.DAMAGE_BOOST = new ArmorUpgrade(RLoc.create("armorupgrade/damage_boost"), 4, true, 7);
        ArmorUpgradeInit.MINOR_FIRE_RESISTANCE = new ArmorUpgradeFireResistance(RLoc.create("armorupgrade/minor_fire_resistance"), true, 4);
        ArmorUpgradeInit.MAJOR_FIRE_RESISTANCE = new ArmorUpgradeFireResistance(RLoc.create("armorupgrade/major_fire_resistance"), false, 2);
        ArmorUpgradeInit.KINETIC_RESISTANCE = new ArmorUpgrade(RLoc.create("armorupgrade/kinetic_resistance"), 1, false, 4);
        ArmorUpgradeInit.EXPLOSION_RESISTANCE = new ArmorUpgrade(RLoc.create("armorupgrade/explosion_resistance"), 1, false, 4);
        ArmorUpgradeInit.PROJECTILE_REFLECTION = new ArmorUpgrade(RLoc.create("armorupgrade/projectile_reflection"), 3, true, 2);
        ArmorUpgradeInit.MIST_FORM = new ArmorUpgrade(RLoc.create("armorupgrade/mist_form"), 1, false, 8);
        ArmorUpgradeInit.ELYTRA = new ArmorUpgradeElytra(RLoc.create("armorupgrade/elytra"), 1, true, 5);
        ArmorUpgradeInit.ANGEL_FLIGHT = new ArmorUpgradeElytra(RLoc.create("armorupgrade/angel_flight"), 2, false, 5);
        ArmorUpgradeInit.NIGHT_VISION = new ArmorUpgradeNightVision(RLoc.create("armorupgrade/night_vision"), 2);
        ArmorUpgradeInit.BURNING_FRENZY = new ArmorUpgradeBurningFrenzy(RLoc.create("armorupgrade/burning_frenzy"), 6);
        ArmorUpgradeInit.SORCERERS_PRIDE = new ArmorUpgrade(RLoc.create("armorupgrade/sorcerers_pride"), 3, true, 5);
    }
}