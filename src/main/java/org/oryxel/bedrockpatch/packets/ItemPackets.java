package org.oryxel.bedrockpatch.packets;

import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPackets1_20_5;
import net.raphimc.viabedrock.api.model.container.player.InventoryContainer;
import net.raphimc.viabedrock.protocol.BedrockProtocol;
import net.raphimc.viabedrock.protocol.ServerboundBedrockPackets;
import net.raphimc.viabedrock.protocol.data.enums.bedrock.ComplexInventoryTransaction_Type;
import net.raphimc.viabedrock.protocol.model.Position3f;
import net.raphimc.viabedrock.protocol.rewriter.ItemRewriter;
import net.raphimc.viabedrock.protocol.storage.EntityTracker;
import net.raphimc.viabedrock.protocol.storage.InventoryTracker;
import net.raphimc.viabedrock.protocol.types.BedrockTypes;

public class ItemPackets {

    public static void register(final BedrockProtocol protocol) {
        protocol.registerServerbound(ServerboundPackets1_20_5.USE_ITEM, ServerboundBedrockPackets.INVENTORY_TRANSACTION, wrapper -> {
            final EntityTracker entityTracker = wrapper.user().get(EntityTracker.class);
            final InventoryContainer inventoryContainer = wrapper.user().get(InventoryTracker.class).getInventoryContainer();

            // Not really needed, just put it here so it there.
            final int hand = wrapper.read(Types.VAR_INT); // hand
            final int sequence = wrapper.read(Types.VAR_INT); // sequence
            final float yaw = wrapper.read(Types.FLOAT), pitch = wrapper.read(Types.FLOAT);

            wrapper.write(BedrockTypes.VAR_INT, 0); // legacy request id
            wrapper.write(BedrockTypes.UNSIGNED_VAR_INT, ComplexInventoryTransaction_Type.ItemUseTransaction.getValue()); // transaction type
            wrapper.write(BedrockTypes.UNSIGNED_VAR_INT, 0); // actions count

            wrapper.write(BedrockTypes.UNSIGNED_VAR_INT, 1); // action type
            wrapper.write(BedrockTypes.UNSIGNED_VAR_INT, 0); // trigger type

            // block position
            wrapper.write(BedrockTypes.VAR_INT, 0);
            wrapper.write(BedrockTypes.UNSIGNED_VAR_INT, 0);
            wrapper.write(BedrockTypes.VAR_INT, 0);

            wrapper.write(BedrockTypes.VAR_INT, 255);

            wrapper.write(BedrockTypes.VAR_INT, (int) inventoryContainer.getSelectedHotbarSlot()); // hotbar slot
            wrapper.write(wrapper.user().get(ItemRewriter.class).itemType(), inventoryContainer.getSelectedHotbarItem()); // hand item
            wrapper.write(BedrockTypes.POSITION_3F, entityTracker.getClientPlayer().position()); // player position
            wrapper.write(BedrockTypes.POSITION_3F, Position3f.ZERO); // click position

            wrapper.write(BedrockTypes.UNSIGNED_VAR_INT, 0); // block runtime id
            wrapper.write(BedrockTypes.UNSIGNED_VAR_INT, 0); // predicted result.
        }, true);
    }

}
