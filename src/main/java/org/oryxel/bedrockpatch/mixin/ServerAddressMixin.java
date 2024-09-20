package org.oryxel.bedrockpatch.mixin;

import com.google.common.net.HostAndPort;
import de.florianmichael.viafabricplus.protocoltranslator.ProtocolTranslator;
import net.minecraft.client.network.ServerAddress;
import net.raphimc.viabedrock.api.BedrockProtocolVersion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerAddress.class)
public class ServerAddressMixin {

    @Redirect(method = "parse", at = @At(value = "INVOKE", target = "Lcom/google/common/net/HostAndPort;withDefaultPort(I)Lcom/google/common/net/HostAndPort;"))
    private static HostAndPort redirectPortOrDefault(HostAndPort instance, int defaultPort) {
        if (ProtocolTranslator.getTargetVersion() == BedrockProtocolVersion.bedrockLatest) {
            return instance.withDefaultPort(19132);
        }

        return instance.withDefaultPort(25565);
    }
    
}
