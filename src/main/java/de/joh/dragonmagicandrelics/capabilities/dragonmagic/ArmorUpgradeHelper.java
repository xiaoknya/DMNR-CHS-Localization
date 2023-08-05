package de.joh.dragonmagicandrelics.capabilities.dragonmagic;


import com.mna.api.capabilities.IPlayerMagic;
import com.mna.capabilities.playerdata.magic.PlayerMagicProvider;
import de.joh.dragonmagicandrelics.Registries;
import de.joh.dragonmagicandrelics.armorupgrades.types.ArmorUpgrade;
import de.joh.dragonmagicandrelics.armorupgrades.types.IArmorUpgradeOnEquipped;
import de.joh.dragonmagicandrelics.armorupgrades.types.IArmorUpgradeOnTick;
import de.joh.dragonmagicandrelics.effects.EffectInit;
import de.joh.dragonmagicandrelics.item.items.DragonMageArmor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Helper functions for the Dragon Magic effects.
 * @author Joh0210
 */
public class ArmorUpgradeHelper {
    public static int getUpgradeLevel(Player player, ArmorUpgrade armorUpgrade){
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);

        AtomicInteger level = new AtomicInteger(0);

        if(!chest.isEmpty() && chest.getItem() instanceof DragonMageArmor dragonMageArmor && dragonMageArmor.isSetEquipped(player)){
            if(player.hasEffect(EffectInit.ULTIMATE_ARMOR.get())){
                return armorUpgrade.maxUpgradeLevel;
            }

            player.getCapability(PlayerDragonMagicProvider.PLAYER_DRAGON_MAGIC).ifPresent((playerCapability) -> {
                if(armorUpgrade instanceof IArmorUpgradeOnTick){
                    for(Pair<IArmorUpgradeOnTick, Integer> equippedAU : playerCapability.onTickUpgrade.values()){
                        if(armorUpgrade.equals(equippedAU.getA())){
                            level.set(Math.max(level.get(), equippedAU.getB()));
                        }
                    }
                }
                else if(armorUpgrade instanceof IArmorUpgradeOnEquipped){
                    for(Pair<IArmorUpgradeOnEquipped, Integer> equippedAU : playerCapability.onEquipUpgrade.values()){
                        if(armorUpgrade.equals(equippedAU.getA())){
                            level.set(Math.max(level.get(), equippedAU.getB()));
                        }
                    }
                }
                else {
                    for(Pair<ArmorUpgrade, Integer> equippedAU : playerCapability.onEventUpgrade.values()){
                        if(armorUpgrade.equals(equippedAU.getA())){
                            level.set(Math.max(level.get(), equippedAU.getB()));
                        }
                    }
                }
            });
        }

        player.getCapability(PlayerDragonMagicProvider.PLAYER_DRAGON_MAGIC).ifPresent((playerCapability) -> {
            if(armorUpgrade instanceof IArmorUpgradeOnTick){
                for (Pair<IArmorUpgradeOnTick, Integer> equippedAU : playerCapability.onTickPermaUpgrade.values()) {
                    if (armorUpgrade.equals(equippedAU.getA())) {
                        level.set(Math.max(level.get(), equippedAU.getB()));
                    }
                }
            }
            else if(armorUpgrade instanceof IArmorUpgradeOnEquipped){
                for (Pair<IArmorUpgradeOnEquipped, Integer> equippedAU : playerCapability.onEquipPermaUpgrade.values()) {
                    if (armorUpgrade.equals(equippedAU.getA())) {
                        level.set(Math.max(level.get(), equippedAU.getB()));
                    }
                }
            }
            else {
                for (Pair<ArmorUpgrade, Integer> equippedAU : playerCapability.onEventPermaUpgrade.values()) {
                    if (armorUpgrade.equals(equippedAU.getA())) {
                        level.set(Math.max(level.get(), equippedAU.getB()));
                    }
                }
            }
        });

        return level.get();
    }

    public static void applyOnTickUpgrade(Player player){
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        HashMap<IArmorUpgradeOnTick, Integer> toApply = new HashMap<>();
        IPlayerMagic magic = player.getCapability(PlayerMagicProvider.MAGIC).orElse(null);
        if(!chest.isEmpty() && chest.getItem() instanceof DragonMageArmor dragonMageArmor && dragonMageArmor.isSetEquipped(player)) {
            if(player.hasEffect(EffectInit.ULTIMATE_ARMOR.get())){
                for(ArmorUpgrade armorUpgrade : Registries.ARMOR_UPGRADE.get().getValues()){
                    if(armorUpgrade instanceof IArmorUpgradeOnTick && !armorUpgrade.hasStrongerAlternative()){
                        ((IArmorUpgradeOnTick)armorUpgrade).onTick(player.getLevel(), player, armorUpgrade.maxUpgradeLevel, magic);
                    }
                }
                return;
            }

            player.getCapability(PlayerDragonMagicProvider.PLAYER_DRAGON_MAGIC).ifPresent((playerCapability) -> {
                for(Pair<IArmorUpgradeOnTick, Integer> pair : playerCapability.onTickUpgrade.values()){
                    if(pair.getB() > 0){
                        toApply.put(pair.getA(),  Math.max(pair.getB(), toApply.getOrDefault(pair.getA(), 0)));
                    }
                }
            });
        }

        player.getCapability(PlayerDragonMagicProvider.PLAYER_DRAGON_MAGIC).ifPresent((playerCapability) -> {
            for(Pair<IArmorUpgradeOnTick, Integer> pair : playerCapability.onTickPermaUpgrade.values()){
                if(pair.getB() > 0){
                    toApply.put(pair.getA(),  Math.max(pair.getB(), toApply.getOrDefault(pair.getA(), 0)));
                }
            }
        });

        for(Map.Entry<IArmorUpgradeOnTick, Integer> entry : toApply.entrySet()){
            if(!entry.getKey().hasStrongerAlternative() || getUpgradeLevel(player, entry.getKey().getStrongerAlternative()) == 0){
                entry.getKey().onTick(player.getLevel(), player, entry.getValue(), magic);
            }
        }
    }

    public static void deactivateAll(Player player){
        deactivateAll(player, true);
    }

    public static void deactivateAll(Player player, boolean alsoPerma){
        player.getCapability(PlayerDragonMagicProvider.PLAYER_DRAGON_MAGIC).ifPresent((playerCapability) -> {
            for(Pair<ArmorUpgrade, Integer> pair : playerCapability.onEventUpgrade.values()){
                if(!pair.getA().hasStrongerAlternative() || getUpgradeLevel(player, pair.getA().getStrongerAlternative()) == 0){
                    pair.getA().onRemove(player);
                }
            }
            for(Pair<IArmorUpgradeOnEquipped, Integer> pair : playerCapability.onEquipUpgrade.values()){
                if(!pair.getA().hasStrongerAlternative() || getUpgradeLevel(player, pair.getA().getStrongerAlternative()) == 0){
                    pair.getA().onRemove(player);
                }
            }
            for(Pair<IArmorUpgradeOnTick, Integer> pair : playerCapability.onTickUpgrade.values()){
                if(!pair.getA().hasStrongerAlternative() || getUpgradeLevel(player, pair.getA().getStrongerAlternative()) == 0){
                    pair.getA().onRemove(player);
                }
            }
        });

        if(alsoPerma){
            deactivateAllPerma(player, false);
            activateOnEquipPerma(player);
        }
    }

    public static void deactivateAllPerma(Player player){
        deactivateAllPerma(player, true);
    }

    public static void deactivateAllPerma(Player player, boolean alsoNormal){
        player.getCapability(PlayerDragonMagicProvider.PLAYER_DRAGON_MAGIC).ifPresent((playerCapability) -> {
            for(Pair<ArmorUpgrade, Integer> pair : playerCapability.onEventPermaUpgrade.values()){
                if(!pair.getA().hasStrongerAlternative() || getUpgradeLevel(player, pair.getA().getStrongerAlternative()) == 0){
                    pair.getA().onRemove(player);
                }
            }
            for(Pair<IArmorUpgradeOnEquipped, Integer> pair : playerCapability.onEquipPermaUpgrade.values()){
                if(!pair.getA().hasStrongerAlternative() || getUpgradeLevel(player, pair.getA().getStrongerAlternative()) == 0){
                    pair.getA().onRemove(player);
                }
            }
            for(Pair<IArmorUpgradeOnTick, Integer> pair : playerCapability.onTickPermaUpgrade.values()){
                if(!pair.getA().hasStrongerAlternative() || getUpgradeLevel(player, pair.getA().getStrongerAlternative()) == 0){
                    pair.getA().onRemove(player);
                }
            }
        });

        if(alsoNormal && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof DragonMageArmor dragonMageArmor && dragonMageArmor.isSetEquipped(player)){
            deactivateAll(player, false);
            activateOnEquip(player);
        }
    }

    /**
     * Only activates when the DM Armor is worn
     */
    public static void activateOnEquipPerma(Player player){
        player.getCapability(PlayerDragonMagicProvider.PLAYER_DRAGON_MAGIC).ifPresent((playerCapability) -> {
            for (Pair<IArmorUpgradeOnEquipped, Integer> pair : playerCapability.onEquipPermaUpgrade.values()) {
                if (!pair.getA().hasStrongerAlternative() || getUpgradeLevel(player, pair.getA().getStrongerAlternative()) == 0) {
                    pair.getA().onEquip(player, getUpgradeLevel(player, pair.getA()));
                }
            }
        });
    }

    public static void activateOnEquip(Player player){
        if(player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof DragonMageArmor dma && dma.isSetEquipped(player)) {
            player.getCapability(PlayerDragonMagicProvider.PLAYER_DRAGON_MAGIC).ifPresent((playerCapability) -> {
                for (Pair<IArmorUpgradeOnEquipped, Integer> pair : playerCapability.onEquipUpgrade.values()) {
                    if (!pair.getA().hasStrongerAlternative() || getUpgradeLevel(player, pair.getA().getStrongerAlternative()) == 0) {
                        pair.getA().onEquip(player, getUpgradeLevel(player, pair.getA()));
                    }
                }
            });
        }
    }

    public static void ultimateArmorStart(Player player){
        deactivateAll(player);
        deactivateAllPerma(player);

        if(player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof DragonMageArmor dma && dma.isSetEquipped(player)){
            for(ArmorUpgrade armorUpgrade: Registries.ARMOR_UPGRADE.get().getValues()){
                if(armorUpgrade instanceof IArmorUpgradeOnEquipped){
                    ((IArmorUpgradeOnEquipped)armorUpgrade).onEquip(player, armorUpgrade.maxUpgradeLevel);
                }
            }
        }
    }

    public static void ultimateArmorFin(Player player){
        for(ArmorUpgrade armorUpgrade: Registries.ARMOR_UPGRADE.get().getValues()){
            armorUpgrade.onRemove(player);
        }
        activateOnEquipPerma(player);
        activateOnEquip(player);
    }
}
