package com.absolutedeliriousdarkness.combat;

import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 每名玩家是否在「战斗模式」下。逻辑以服务端为准；客户端若要显示或按键，
 * 需通过后续同步（例如自定义网络包）与服务端保持一致。
 */
public final class CombatMode {
	private static final Map<UUID, Boolean> ENABLED = new ConcurrentHashMap<>();

	private CombatMode() {}

	public static boolean isEnabled(ServerPlayer player) {
		return ENABLED.getOrDefault(player.getUUID(), false);
	}

	public static void setEnabled(ServerPlayer player, boolean enabled) {
		if (enabled) {
			ENABLED.put(player.getUUID(), true);
		} else {
			ENABLED.remove(player.getUUID());
		}
	}

	/** @return 切换后的状态 */
	public static boolean toggle(ServerPlayer player) {
		boolean next = !isEnabled(player);
		setEnabled(player, next);
		return next;
	}

	public static void remove(ServerPlayer player) {
		ENABLED.remove(player.getUUID());
	}
}
