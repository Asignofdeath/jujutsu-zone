package com.absolutedeliriousdarkness;

import com.absolutedeliriousdarkness.combat.CombatNetworking;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class JuJutsuZoneClient implements ClientModInitializer {
	private static final KeyMapping TOGGLE_COMBAT_MODE_KEY = KeyBindingHelper.registerKeyBinding(
		new KeyMapping(
			"key.jujutsu-zone.toggle_combat_mode",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_B,
			"category.jujutsu-zone.combat"
		)
	);

	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (TOGGLE_COMBAT_MODE_KEY.consumeClick()) {
				if (client.player == null) {
					continue;
				}
				ClientPlayNetworking.send(CombatNetworking.TOGGLE_COMBAT_MODE_PACKET_ID, PacketByteBufs.empty());
			}
		});
	}
}
