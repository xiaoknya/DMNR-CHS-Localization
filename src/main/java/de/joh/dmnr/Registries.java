package de.joh.dmnr;

import de.joh.dmnr.armorupgrades.types.ArmorUpgrade;
import de.joh.dmnr.utils.RLoc;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

/**
 * Initialization of the registers of this mod
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid = DragonMagicAndRelics.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Registries {
    /**
     * Register for all armor upgrades to be used with the Dragon Magic Containers
     */
    public static Supplier<IForgeRegistry<ArmorUpgrade>> ARMOR_UPGRADE;

    public Registries() {
    }

    @SubscribeEvent
    public static void RegisterRegistries(NewRegistryEvent event) {
        RegistryBuilder<ArmorUpgrade> armorUpgrade = new RegistryBuilder<>();
        armorUpgrade.setName(RLoc.create("armorupgrade")).setType(ArmorUpgrade.class).set(key -> ArmorUpgrade.INSTANCE).set((key, isNetwork) -> ArmorUpgrade.INSTANCE).disableSaving().allowModification();
        ARMOR_UPGRADE = event.create(armorUpgrade);
    }
}