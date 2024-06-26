package org.barrelmc.barrel.network.translator.bedrock;

import com.github.steveice10.mc.protocol.data.game.level.block.BlockChangeEntry;
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.level.ClientboundBlockUpdatePacket;
import org.barrelmc.barrel.network.converter.BlockConverter;
import org.barrelmc.barrel.network.translator.interfaces.BedrockPacketTranslator;
import org.barrelmc.barrel.player.Player;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;

public class UpdateBlockPacket implements BedrockPacketTranslator {

    @Override
    public void translate(BedrockPacket pk, Player player) {
        org.cloudburstmc.protocol.bedrock.packet.UpdateBlockPacket packet = (org.cloudburstmc.protocol.bedrock.packet.UpdateBlockPacket) pk;

        if (packet.getDataLayer() == 0) {
            Vector3i pos = packet.getBlockPosition();
            int blockState = BlockConverter.bedrockRuntimeToJavaStateId(packet.getDefinition().getRuntimeId());

            BlockChangeEntry blockChangeRecord = new BlockChangeEntry(pos, blockState);
            player.getJavaSession().send(new ClientboundBlockUpdatePacket(blockChangeRecord));
        }
    }
}
