package de.joh.dragonmagicandrelics.events;

import com.mna.api.capabilities.Faction;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.joh.dragonmagicandrelics.DragonMagicAndRelics;
import de.joh.dragonmagicandrelics.item.ItemInit;
import de.joh.dragonmagicandrelics.item.items.AngelRing;
import de.joh.dragonmagicandrelics.item.items.DragonMageArmor;
import de.joh.dragonmagicandrelics.networking.ModMessages;
import de.joh.dragonmagicandrelics.networking.packet.ToggleFlightC2SPacket;
import de.joh.dragonmagicandrelics.networking.packet.ToggleNightVisionC2SPacket;
import de.joh.dragonmagicandrelics.utils.KeybindInit;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.caelus.api.RenderCapeEvent;
import top.theillusivec4.curios.api.CuriosApi;

/**
 * These event handlers take care of processing events which are on client side only.
 * Functions marked with @SubscribeEvent are called by the forge event bus handler.
 */
public class ClientEvents {
    @Mod.EventBusSubscriber(modid = DragonMagicAndRelics.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents{

        /**
         * Has the button been pressed that activates Night Vision or DM&R Flight?
         * @see ToggleNightVisionC2SPacket
         * @see ToggleFlightC2SPacket
         * @see de.joh.dragonmagicandrelics.armorupgrades.ArmorUpgradeInit
         */
        @SubscribeEvent
        public static void onKeyRegister(InputEvent.KeyInputEvent event){
            if(KeybindInit.TOGGLE_NIGHT_VISION_KEY.consumeClick()){
                ModMessages.sendToServer(new ToggleNightVisionC2SPacket());
            }
            else if(KeybindInit.TOGGLE_FLIGHT_KEY.consumeClick()){
                ModMessages.sendToServer(new ToggleFlightC2SPacket());
            }
        }

        /**
         * If the player is wearing Elytra, no cape will be rendered
         * @see de.joh.dragonmagicandrelics.item.client.armor.WingLayer
         */
        @SubscribeEvent
        public static void renderCape(RenderCapeEvent event) {
            if (event.getEntity() instanceof Player player){
                ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
                if (
                        (!chest.isEmpty() && chest.getItem() instanceof DragonMageArmor dmArmor && chest.hasTag() && chest.getTag().getBoolean(DragonMagicAndRelics.MOD_ID + "Fullset_Elytra"))
                        || CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.ANGEL_RING.get(), player).isPresent()
                        || CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.FALLEN_ANGEL_RING.get(), player).isPresent()
                ) {
                    event.setCanceled(true);
                }
            }
        }
    }
}
