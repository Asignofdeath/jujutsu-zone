package com.absolutedeliriousdarkness;

import com.absolutedeliriousdarkness.combat.CombatMode;
import com.absolutedeliriousdarkness.combat.CombatNetworking;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.chat.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JuJutsuZone implements ModInitializer {
	public static final String MOD_ID = "jujutsu-zone";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> CombatMode.remove(handler.player));
		ServerPlayNetworking.registerGlobalReceiver(CombatNetworking.TOGGLE_COMBAT_MODE_PACKET_ID,
			(server, player, handler, buf, responseSender) -> server.execute(() -> {
				boolean enabled = CombatMode.toggle(player);
				String translationKey = enabled
					? "message.jujutsu-zone.combat_mode.enabled"
					: "message.jujutsu-zone.combat_mode.disabled";
				player.displayClientMessage(Component.translatable(translationKey), true);
			}));

		LOGGER.info("Hello Fabric world!");
	}
}