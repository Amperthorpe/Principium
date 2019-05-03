package com.alleluid.principium

import com.alleluid.principium.common.blocks.smelter.TileEntitySmelter
import com.alleluid.principium.common.items.weapons.BaseWeapon
import com.alleluid.principium.common.items.weapons.WeaponPistol
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraftforge.energy.IEnergyStorage
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
        INSTANCE.registerMessage(MachineSyncMessage.MachineSyncMesssageHandler::class.java, MachineSyncMessage::class.java, uid++, Side.CLIENT)
    }
}

class ShootMessage(var toSend: Float = 0f) : IMessage {

    override fun toBytes(buf: ByteBuf?) {
        buf?.writeFloat(toSend)
    }

    override fun fromBytes(buf: ByteBuf?) {
        toSend = buf?.readFloat() ?: 0f
    }

    class ShootMessageHandler : IMessageHandler<ShootMessage, IMessage> {
        override fun onMessage(message: ShootMessage?, ctx: MessageContext?): IMessage? {
            val serverPlayer: EntityPlayerMP = ctx!!.serverHandler.player
            val num = message?.toSend ?: 0f
            serverPlayer.serverWorld.addScheduledTask {
                val heldItem = serverPlayer.getHeldItem(EnumHand.MAIN_HAND).item
                if (num > 0f && heldItem is BaseWeapon){
                    heldItem.onWeaponFire(serverPlayer.serverWorld, serverPlayer, EnumHand.MAIN_HAND, num)
                }
            }
            return null
        }
    }
}

class MachineSyncMessage(var energy: Int, var pos: BlockPos) : IMessage {
    override fun toBytes(buf: ByteBuf?) {
        buf?.writeInt(energy)
        buf?.writeInt(pos.x)
        buf?.writeInt(pos.y)
        buf?.writeInt(pos.z)
    }

    override fun fromBytes(buf: ByteBuf?) {
        energy = buf?.readInt() ?: -1
        val x = buf?.readInt() ?: 0
        val y = buf?.readInt() ?: 0
        val z = buf?.readInt() ?: 0

        pos = BlockPos(x, y, z)
    }

    class MachineSyncMesssageHandler : IMessageHandler<MachineSyncMessage, IMessage> {
        override fun onMessage(message: MachineSyncMessage?, ctx: MessageContext?): IMessage? {
            val player = Minecraft.getMinecraft().player
            val energy = message?.energy ?: -2
            val pos = message?.pos ?: BlockPos.ORIGIN
            Minecraft.getMinecraft().addScheduledTask {
                val TE = player.world.getTileEntity(pos)
                if (TE is IEnergyStorage && TE is TileEntitySmelter){
                    TE.energy = energy
                }
            }
            return null
        }
    }

}