package de.joh.dragonmagicandrelics.armorupgrades.init;

import de.joh.dragonmagicandrelics.armorupgrades.types.IArmorUpgradeOnEquipped;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

/**
 * Increases the range in which the user can interact
 * @author Joh0210
 */
public class ArmorUpgradeReachDistance extends IArmorUpgradeOnEquipped {
    private static final AttributeModifier reachBoost1 = new AttributeModifier("mma_armor_reach_boost_1", 1, AttributeModifier.Operation.ADDITION);
    private static final AttributeModifier reachBoost2 = new AttributeModifier("mma_armor_reach_boost_2", 1, AttributeModifier.Operation.ADDITION);
    private static final AttributeModifier reachBoost3 = new AttributeModifier("mma_armor_reach_boost_3", 1, AttributeModifier.Operation.ADDITION);

    private static final AttributeModifier attackRangBoost1 = new AttributeModifier("mma_armor_attack_range_boost_1", 1, AttributeModifier.Operation.ADDITION);
    private static final AttributeModifier attackRangBoost2 = new AttributeModifier("mma_armor_attack_range_boost_2", 1, AttributeModifier.Operation.ADDITION);
    private static final AttributeModifier attackRangBoost3 = new AttributeModifier("mma_armor_attack_range_boost_3", 1, AttributeModifier.Operation.ADDITION);


    public ArmorUpgradeReachDistance(@NotNull ResourceLocation registryName, int maxUpgradeLevel) {
        super(registryName, maxUpgradeLevel, false);
    }

    @Override
    public void onEquip(Player player, int level) {
        AttributeInstance reach = player.getAttribute(ForgeMod.REACH_DISTANCE.get());

        if (!reach.hasModifier(reachBoost1) && level >= 1) {
            reach.addTransientModifier(reachBoost1);

            if (!reach.hasModifier(reachBoost2) && level >= 2) {
                reach.addTransientModifier(reachBoost2);

                if (!reach.hasModifier(reachBoost3) && level >= 3) {
                    reach.addTransientModifier(reachBoost3);
                }
            }
        }

        AttributeInstance attackRange = player.getAttribute(ForgeMod.ATTACK_RANGE.get());

        if (!attackRange.hasModifier(attackRangBoost1) && level >= 1) {
            attackRange.addTransientModifier(attackRangBoost1);

            if (!attackRange.hasModifier(attackRangBoost2) && level >= 2) {
                attackRange.addTransientModifier(attackRangBoost2);

                if (!attackRange.hasModifier(attackRangBoost3) && level >= 3) {
                    attackRange.addTransientModifier(attackRangBoost3);
                }
            }
        }
    }

    public void onRemove(Player player) {
        AttributeInstance reach = player.getAttribute(ForgeMod.REACH_DISTANCE.get());
        AttributeInstance attackRange = player.getAttribute(ForgeMod.ATTACK_RANGE.get());

        reach.removeModifier(reachBoost1);
        reach.removeModifier(reachBoost2);
        reach.removeModifier(reachBoost3);
        attackRange.removeModifier(attackRangBoost1);
        attackRange.removeModifier(attackRangBoost2);
        attackRange.removeModifier(attackRangBoost3);
    }
}