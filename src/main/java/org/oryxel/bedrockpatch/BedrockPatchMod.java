package org.oryxel.bedrockpatch;

import com.viaversion.viaversion.api.Via;
import de.florianmichael.viafabricplus.event.PostViaVersionLoadCallback;
import net.fabricmc.api.ModInitializer;
import net.raphimc.viabedrock.protocol.BedrockProtocol;
import org.oryxel.bedrockpatch.packets.ItemPackets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BedrockPatchMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("bedrockpatch");

    @Override
    public void onInitialize() {
        PostViaVersionLoadCallback.EVENT.register(() -> {
            BedrockProtocol protocol = Via.getManager().getProtocolManager().getProtocol(BedrockProtocol.class);

            if (protocol != null) {
                LOGGER.info("Setting up translation...");

                ItemPackets.register(protocol);
            }
        });
    }
}
