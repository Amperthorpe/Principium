package com.alleluid.principium.common.items.tools

import com.alleluid.principium.Utils
import com.alleluid.principium.Utils.setPositionAndRotationAndUpdate
import com.alleluid.principium.Utils.ifServer
import com.alleluid.principium.Utils.ifClient
import com.alleluid.principium.common.items.BaseItem
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.SoundEvents
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.*
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

object TransportRod : BaseItem("transport_rod") {
    const val teleRange = 128.0
    const val maxBlocksSearched = 5
    var didAltTeleport: Boolean = false

    init {
        // Change index below before before adding/removing infoText lines.
        loreText.add("Zzzvoop!")
        infoText.add("Right click to teleport to the block you're looking at.")
        infoText.add("Range §f§l${teleRange.toInt()}§r§7. Will search upwards §f§l$maxBlocksSearched§r§7 blocks for a space.")
        infoText.add("Right click a block to teleport through it.")
        infoText.add("Shift right click a block to set as destination. Left click entity to teleport them there.")
    }

    override fun getItemStackLimit(stack: ItemStack) = 1

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        if (!playerIn.isSneaking && !didAltTeleport) {
            val lookVec = playerIn.lookVec
                    ?: return super.onItemRightClick(worldIn, playerIn, handIn)
            val start = Vec3d(playerIn.posX, playerIn.posY + playerIn.eyeHeight, playerIn.posZ)
            val end = start.add(lookVec.x * teleRange, lookVec.y * teleRange, lookVec.z * teleRange)
            val raytrace = worldIn.rayTraceBlocks(start, end)
                    ?: return super.onItemRightClick(worldIn, playerIn, handIn)
            val pos = raytrace.blockPos
            for (i in 1..maxBlocksSearched) {
                // Check two blocks to ensure no suffocation
                val adjustedPos = BlockPos(pos.x, pos.y + i, pos.z)
                if (Utils.checkHeadspace(worldIn, adjustedPos)) {
                    worldIn.ifServer {
                        playerIn.fallDistance = 0f
                        playerIn.setPositionAndRotationAndUpdate(adjustedPos.x + 0.5, adjustedPos.y.toDouble(), adjustedPos.z + 0.5)
                    }
                    worldIn.ifClient {
//                        playerIn.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1f, 1f)
                        worldIn.playSound(playerIn, adjustedPos, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.HOSTILE, 0.3f, 1f)
                        Utils.particleGroup(worldIn, EnumParticleTypes.DRAGON_BREATH, adjustedPos.x, adjustedPos.y, adjustedPos.z, 0.5f)
                    }
                    break
                } else if (i >= maxBlocksSearched) {
                    Utils.statusMessage("§4Invalid Location")
                }
            }
        } else {
            didAltTeleport = false
        }
        return super.onItemRightClick(worldIn, playerIn, handIn)
    }

    override fun onItemUse(player: EntityPlayer, worldIn: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        didAltTeleport = true
        if (!player.isSneaking) {
            val newPos = when (facing) {
                EnumFacing.DOWN -> BlockPos(pos.x, pos.y + 1, pos.z)
                EnumFacing.UP -> BlockPos(pos.x, pos.y - 2, pos.z) // -2 to allow for head room
                EnumFacing.NORTH -> BlockPos(pos.x, pos.y, pos.z + 1)
                EnumFacing.SOUTH -> BlockPos(pos.x, pos.y, pos.z - 1)
                EnumFacing.WEST -> BlockPos(pos.x + 1, pos.y, pos.z)
                EnumFacing.EAST -> BlockPos(pos.x - 1, pos.y, pos.z)
            }
            return if (Utils.checkHeadspace(worldIn, newPos)) {
                player.setPositionAndRotationAndUpdate(newPos.x + 0.5, newPos.y.toDouble(), newPos.z + 0.5)
                worldIn.playSound(player, newPos, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.HOSTILE, 0.3f, 1f)
                EnumActionResult.SUCCESS
            } else {
                didAltTeleport = false
                EnumActionResult.FAIL
            }
        } else {
            player.getHeldItem(hand).tagCompound!!.setIntArray("storedPos", IntArray(0).plus(listOf(pos.x, pos.y + 1, pos.z)))
            worldIn.ifClient { Utils.statusMessage("Set to ${pos.x}, ${pos.y + 1}, ${pos.z}") }
        }

        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ)
    }

    override fun onUpdate(stack: ItemStack, worldIn: World, entityIn: Entity, itemSlot: Int, isSelected: Boolean) {
        if (stack.tagCompound == null) {
            stack.tagCompound = NBTTagCompound()
        }
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected)
    }

    override fun onLeftClickEntity(stack: ItemStack, player: EntityPlayer, entity: Entity): Boolean {
        if (entity is EntityPlayer && !entity.isSneaking){
            player.world.ifClient { Utils.statusMessage("Players must be sneaking to teleport.") }
            return false
        } else if (entity is EntityLivingBase) {
            val array = stack.tagCompound!!.getIntArray("storedPos") ?: return false
            entity.setPositionAndRotationAndUpdate(array[0] + 0.5, array[1].toDouble(), array[2] + 0.5)
            return true
        }
        return false
    }
}