package de.joh.dmnr.common.armorupgrade;

import com.mna.api.capabilities.IPlayerMagic;
import com.mna.api.config.GeneralConfigValues;
import com.mna.api.particles.MAParticleType;
import com.mna.api.particles.ParticleInit;
import com.mna.api.sound.SFX;
import com.mna.entities.utility.MAExplosion;
import de.joh.dmnr.api.armorupgrade.OnTickArmorUpgrade;
import de.joh.dmnr.capabilities.dragonmagic.ArmorUpgradeHelper;
import de.joh.dmnr.common.init.ArmorUpgradeInit;
import de.joh.dmnr.common.util.CommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

/**
 * If the wielder shifts during a sprint jump, it lands like a metor on the ground and creates an explosion
 * This consumes a small amount of mana
 * Function and constructor are initialized versions of parent class function/constructor.
 * Configurable in the CommonConfigs.
 * @see CommonConfig
 * @see ArmorUpgradeInit
 * @author Joh0210
 */
public class MeteorJumpArmorUpgrade extends OnTickArmorUpgrade {
    private static final int reqHeight = 4;

    public MeteorJumpArmorUpgrade(@NotNull ResourceLocation registryName, RegistryObject<Item> upgradeSealItem, int upgradeCost) {
        super(registryName, 1, upgradeSealItem, true, upgradeCost);
    }

    @Override
    public void onTick(Level world, Player player, int level, IPlayerMagic magic) {
        if (level > 0 && !player.onGround() && player.getDeltaMovement().y < 0.0D && !player.isFallFlying() && player.isCrouching() && !player.getPersistentData().contains("dmnr_meteor_jumping") && player.isSprinting()) {
            int heightAboveGround = 0;
            for(BlockPos pos = player.blockPosition(); player.level().isEmptyBlock(pos) && heightAboveGround < reqHeight; ++heightAboveGround) {
                pos = pos.below();
            }

            if (heightAboveGround >= reqHeight) {
                player.getPersistentData().putBoolean("dmnr_meteor_jumping", true);
                player.level().playSeededSound(null, player.getX(), player.getY(), player.getZ(), SFX.Event.Artifact.METEOR_JUMP, SoundSource.PLAYERS, 0.25F, 0.8F, 0);
            }
        }

        if (player.getPersistentData().contains("dmnr_meteor_jumping")) {
            if (player.onGround()) {
                handlePlayerMeteorJumpImpact(player);
            }

            player.push(0.0D, -0.05D, 0.0D);
            if (player.level().isClientSide) {
                for(int i = 0; i < 25; ++i) {
                    player.level().addParticle(new MAParticleType(ParticleInit.FLAME.get()), player.getX() - 0.5D + Math.random() * 0.5D, player.getY() + Math.random(), player.getZ() - 0.5D + Math.random() * 0.5D, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    /**
     * Produces the explosion upon player impact.
     */
    private void handlePlayerMeteorJumpImpact(Player player) {
        if (player.getPersistentData().contains("dmnr_meteor_jumping")) {
            int level = ArmorUpgradeHelper.getUpgradeLevel(player, ArmorUpgradeInit.METEOR_JUMP);

            player.getPersistentData().remove("dmnr_meteor_jumping");
            player.setSprinting(false);
            if (!player.level().isClientSide) {
                MAExplosion.make(player, (ServerLevel)player.level(), player.getX(), player.getY(), player.getZ(), CommonConfig.METEOR_JUMP_IMPACT.get() * level / 2.0f, CommonConfig.METEOR_JUMP_IMPACT.get() * 3 * level, true, GeneralConfigValues.MeteorJumpEnabled && ((ServerLevel)player.level()).getServer().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.KEEP, player.damageSources().explosion(player, null));
            }
        }
    }
}
