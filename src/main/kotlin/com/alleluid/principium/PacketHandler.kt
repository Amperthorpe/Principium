package com.alleluid.principium

import com.alleluid.principium.common.blocks.smelter.ContainerSmelter
import com.alleluid.principium.common.items.tools.LaserDrill
import com.alleluid.principium.common.items.weapons.BaseWeapon
import com.alleluid.principium.common.misc.SmelterFields
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.EnumHand
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.relauncher.Side

object PacketHandler {
    @JvmStatic
    val INSTANCE = SimpleNetworkWrapper(MOD_ID)
    @JvmStatic
    var uid = 0

    @JvmStatic
    fun registerMessages() {
        INSTANCE.registerMessage(ShootMessage.ShootMessageHandler::class.java, ShootMessage::class.java, uid++, Side.SERVER)
        INSTANCE.registerMessage(SmelterSyncMessage.SmelterSyncMesssageHandler::class.java, SmelterSyncMessage::class.java, uid++, Side.CLIENT)
        INSTANCE.registerMessage(MineBlockMessage.MineBlockMesssageHandler::class.java, MineBlockMessage::class.java, uid++, Side.SERVER)
    }
}

class ShootMessage(var toSend: Float = 0f) : IMessage {

    override fun toBytes(buf: ByteBuf) {
        buf.writeFloat(toSend)
    }

    override fun fromBytes(buf: ByteBuf) {
        toSend = buf.readFloat()
    }

    class ShootMessageHandler : IMessageHandler<ShootMessage, IMessage> {
        override fun onMessage(message: ShootMessage?, ctx: MessageContext?): IMessage? {
            val serverPlayer: EntityPlayerMP = ctx!!.serverHandler.player
            val num = message?.toSend ?: 0f
            serverPlayer.serverWorld.addScheduledTask {
                val heldItem = serverPlayer.getHeldItem(EnumHand.MAIN_HAND).item
                if (num > 0f && heldItem is BaseWeapon) {
                    heldItem.onWeaponFire(serverPlayer.serverWorld, serverPlayer, EnumHand.MAIN_HAND, num)
                }
            }
            return null
        }
    }
}

class MineBlockMessage : IMessage {
    override fun toBytes(buf: ByteBuf?) {}
    override fun fromBytes(buf: ByteBuf?) {}

    class MineBlockMesssageHandler : IMessageHandler<MineBlockMessage, IMessage> {
        override fun onMessage(message: MineBlockMessage?, ctx: MessageContext?): IMessage? {
            val playerMP = ctx!!.serverHandler.player
            val heldItem  = playerMP.heldItemMainhand.item
            if (heldItem is LaserDrill){
                heldItem.onPrecisionMine(playerMP.serverWorld, playerMP)
            }
            return null
        }
    }
}

class SmelterSyncMessage(var fields: SmelterFields = SmelterFields()) : IMessage {
    override fun toBytes(buf: ByteBuf) {
        buf.writeInt(fields.potential)
        buf.writeInt(fields.capacity)
        buf.writeInt(fields.progress)
    }

    override fun fromBytes(buf: ByteBuf) {
        val potential = buf.readInt()
        val capacity = buf.readInt()
        val progress = buf.readInt()

        fields = SmelterFields(potential, capacity, progress)
    }

    class SmelterSyncMesssageHandler : IMessageHandler<SmelterSyncMessage, IMessage> {
        override fun onMessage(message: SmelterSyncMessage?, ctx: MessageContext?): IMessage? {
            if (message != null){
                Minecraft.getMinecraft().addScheduledTask {
                    val container = Minecraft.getMinecraft().player.openContainer
                    (container as ContainerSmelter).updateFields(message.fields)
                }
            }
            return null
        }
    }
}


