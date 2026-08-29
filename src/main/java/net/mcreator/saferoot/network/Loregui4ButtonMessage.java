package net.mcreator.saferoot.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.SectionPos;

import net.mcreator.saferoot.procedures.Guiproclore4Procedure;
import net.mcreator.saferoot.SaferootMod;

@EventBusSubscriber
public record Loregui4ButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
	public static final Type<Loregui4ButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SaferootMod.MODID, "loregui_4_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, Loregui4ButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, Loregui4ButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new Loregui4ButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));

	@Override
	public Type<Loregui4ButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final Loregui4ButtonMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!world.getChunkSource().hasChunk(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z)))
			return;
		if (buttonID == 0) {

			Guiproclore4Procedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		SaferootMod.addNetworkMessage(Loregui4ButtonMessage.TYPE, Loregui4ButtonMessage.STREAM_CODEC, Loregui4ButtonMessage::handleData);
	}
}