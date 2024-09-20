package org.oryxel.bedrockpatch.mixin.viabedrock;

import net.minecraft.client.MinecraftClient;
import net.raphimc.viabedrock.api.model.entity.ClientPlayerEntity;
import net.raphimc.viabedrock.api.util.MathUtil;
import net.raphimc.viabedrock.protocol.data.enums.bedrock.PlayerAuthInputPacket_InputData;
import net.raphimc.viabedrock.protocol.model.Position3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.EnumSet;
import java.util.Set;

@Mixin(value = ClientPlayerEntity.class, remap = false)
public class ClientPlayerBedrockMixin {

    @Redirect(method = "sendPlayerAuthInputPacketToServer", at = @At(value = "INVOKE", target = "Lnet/raphimc/viabedrock/api/util/MathUtil;calculatePressedDirectionKeys(Lnet/raphimc/viabedrock/protocol/model/Position3f;F)Ljava/util/Set;"))
    public Set<PlayerAuthInputPacket_InputData> injectKeyPressedDirections(Position3f positionDelta, float yaw) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) {
            return MathUtil.calculatePressedDirectionKeys(positionDelta, yaw);
        }

        final Set<PlayerAuthInputPacket_InputData> pressedKeys = EnumSet.noneOf(PlayerAuthInputPacket_InputData.class);
        if (client.player.input.movementSideways >= 0.65F) {
            pressedKeys.add(PlayerAuthInputPacket_InputData.Left);
        } else if (client.player.input.movementSideways <= -0.65F) {
            pressedKeys.add(PlayerAuthInputPacket_InputData.Right);
        }
        if (client.player.input.movementForward >= 0.65F) {
            pressedKeys.add(PlayerAuthInputPacket_InputData.Up);
        } else if (client.player.input.movementForward <= -0.65F) {
            pressedKeys.add(PlayerAuthInputPacket_InputData.Down);
        }

        return pressedKeys;
    }

}
