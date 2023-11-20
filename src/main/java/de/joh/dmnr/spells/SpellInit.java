package de.joh.dmnr.spells;


import com.mna.api.spells.parts.Shape;
import com.mna.api.spells.parts.SpellEffect;
import de.joh.dmnr.DragonMagicAndRelics;
import de.joh.dmnr.spells.components.*;
import de.joh.dmnr.spells.shapes.ShapeAtMark;
import de.joh.dmnr.spells.shapes.ShapeCurse;
import de.joh.dmnr.spells.shapes.ShapeTrueTouch;
import de.joh.dmnr.utils.RLoc;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Register all spell-components and shapes. Call via the event bus.
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid = DragonMagicAndRelics.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpellInit {

    public static Shape ATMARK;
    public static Shape TRUE_TOUCH;
    public static Shape CURSE;

    public static SpellEffect ALTERNATIVERECALL;
    public static SpellEffect FORCE_DAMAGE;
    public static SpellEffect SUNRISE;
    public static SpellEffect MOONRISE;
    public static SpellEffect ULTIMATEARMOR;
    public static SpellEffect BANISH_RAIN;
    public static SpellEffect CONJURE_STORM;
    public static SpellEffect CONJURE_WATER;
    public static SpellEffect CONJURE_LAVA;
    public static SpellEffect SATURATE;
    public static SpellEffect MARK;

    @SubscribeEvent
    public static void registerShapes(final RegistryEvent.Register<Shape> event) {
        event.getRegistry().register(SpellInit.ATMARK);
        event.getRegistry().register(SpellInit.TRUE_TOUCH);
        event.getRegistry().register(SpellInit.CURSE);
    }

    @SubscribeEvent
    public static void registerComponents(final RegistryEvent.Register<SpellEffect> event) {
        event.getRegistry().register(SpellInit.SUNRISE);
        event.getRegistry().register(SpellInit.MOONRISE);
        event.getRegistry().register(SpellInit.ULTIMATEARMOR);
        event.getRegistry().register(SpellInit.BANISH_RAIN);
        event.getRegistry().register(SpellInit.CONJURE_STORM);
        event.getRegistry().register(SpellInit.CONJURE_WATER);
        event.getRegistry().register(SpellInit.CONJURE_LAVA);
        event.getRegistry().register(SpellInit.SATURATE);
        event.getRegistry().register(SpellInit.MARK);
        event.getRegistry().register(SpellInit.ALTERNATIVERECALL);
        event.getRegistry().register(SpellInit.FORCE_DAMAGE);
    }

    static {
        SpellInit.ATMARK = new ShapeAtMark(RLoc.create("shapes/atmark"), RLoc.create("textures/spell/shape/atmark.png"));
        SpellInit.TRUE_TOUCH = new ShapeTrueTouch(RLoc.create("shapes/true_touch"), RLoc.create("textures/spell/shape/true_touch.png"));
        SpellInit.CURSE = new ShapeCurse(RLoc.create("shapes/curse"), RLoc.create("textures/spell/shape/curse.png"));

        SpellInit.SUNRISE = new ComponentSunrise(RLoc.create("components/sunrise"), RLoc.create("textures/spell/component/sunrise.png"));
        SpellInit.MOONRISE = new ComponentMoonrise(RLoc.create("components/moonrise"), RLoc.create("textures/spell/component/moonrise.png"));
        SpellInit.ULTIMATEARMOR = new ComponentUltimateArmor(RLoc.create("components/ultimatearmor"), RLoc.create("textures/spell/component/ultimatearmor.png"));
        SpellInit.BANISH_RAIN = new ComponentBanishRain(RLoc.create("components/banishrain"), RLoc.create("textures/spell/component/banishrain.png"));
        SpellInit.CONJURE_STORM = new ComponentConjureStorm(RLoc.create("components/conjurestorm"), RLoc.create("textures/spell/component/conjurestorm.png"));
        SpellInit.CONJURE_WATER = new ComponentConjureWater(RLoc.create("components/conjurewater"), RLoc.create("textures/spell/component/conjurewater.png"));
        SpellInit.CONJURE_LAVA = new ComponentConjureLava(RLoc.create("components/conjurelava"), RLoc.create("textures/spell/component/conjurelava.png"));
        SpellInit.SATURATE = new ComponentSaturate(RLoc.create("components/saturate"), RLoc.create("textures/spell/component/saturate.png"));
        SpellInit.MARK = new ComponentMark(RLoc.create("components/mark"), RLoc.create("textures/spell/component/mark.png"));
        SpellInit.FORCE_DAMAGE = new ComponentForceDamage(RLoc.create("components/forcedamage"), RLoc.create("textures/spell/component/forcedamage.png"));
        SpellInit.ALTERNATIVERECALL = new ComponentAlternativeRecall(RLoc.create("components/alternativerecall"), RLoc.create("textures/spell/component/alternativerecall.png"));
    }
}