package net.mcreator.saferoot;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;

import net.mcreator.saferoot.world.inventory.RootGolemGuiMenu;
import net.mcreator.saferoot.entity.ROOTEntity;

@EventBusSubscriber
public record RootGolemNetwork(int mode, int ore, int quantity) implements CustomPacketPayload {
	public static final Type<RootGolemNetwork> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SaferootMod.MODID, "root_golem_mode"));

	public static final StreamCodec<RegistryFriendlyByteBuf, RootGolemNetwork> STREAM_CODEC = StreamCodec.of(
			(RegistryFriendlyByteBuf buffer, RootGolemNetwork message) -> {
				buffer.writeVarInt(message.mode);
				buffer.writeVarInt(message.ore);
				buffer.writeVarInt(message.quantity);
			}, (RegistryFriendlyByteBuf buffer) -> new RootGolemNetwork(buffer.readVarInt(), buffer.readVarInt(), buffer.readVarInt()));

	@Override
	public Type<RootGolemNetwork> type() {
		return TYPE;
	}

	public static void handleData(final RootGolemNetwork message, final IPayloadContext context) {
		if (context.flow() != PacketFlow.SERVERBOUND)
			return;
		context.enqueueWork(() -> {
			if (!(context.player().containerMenu instanceof RootGolemGuiMenu menu))
				return;
			ROOTEntity golem = menu.getGolem();
			if (golem == null || !golem.isAlive())
				return;
			if (message.mode < 0 || message.mode >= ROOTEntity.MODE_COUNT)
				return;
			if (!menu.hasTool(message.mode))
				return;
			golem.startMission(context.player(), message.mode, message.ore, message.quantity);
		});
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		SaferootMod.addNetworkMessage(RootGolemNetwork.TYPE, RootGolemNetwork.STREAM_CODEC, RootGolemNetwork::handleData);
	}
}
