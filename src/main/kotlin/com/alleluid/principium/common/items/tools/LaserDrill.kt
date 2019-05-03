package com.alleluid.principium.common.items.tools

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.PrincipiumMod
import com.alleluid.principium.Utils
import net.minecraft.block.state.IBlockState
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemPickaxe
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import java.util.*
import com.alleluid.principium.Utils.Formatting as umf
import com.alleluid.principium.Utils.ifClient
import net.minecraft.entity.player.EntityPlayerMP
import java.util.jar.Attributes

object LaserDrill : ItemPickaxe(PrincipiumMod.principicToolMaterial) {
    private const val breakCooldown = 6
    private const val altReach = 6.0

    init {
        creativeTab = PrincipiumMod.creativeTab
        registryName = ResourceLocation(MOD_ID, "laser_drill")
        translationKey = "$MOD_ID.laser_drill"
    }

    fun registerItemModel() = PrincipiumMod.proxy.registerItemRenderer(this, 0, "laser_drill")

    override fun getDestroySpeed(stack: ItemStack, state: IBlockState): Float = 1600f
    override fun canItemEditBlocks(): Boolean = true // It can edit any block, it's a laser drill.
    override fun canHarvestBlock(state: IBlockState, stack: ItemStack): Boolean = true

    fun onPrecisionMine(worldIn: World, playerIn: EntityPlayerMP){
        val dist = playerIn.getEntityAttribute(EntityPlayer.REACH_DISTANCE).attributeValue

        val lookVec = playerIn.lookVec
        val start = Vec3d(playerIn.posX, playerIn.posY + playerIn.eyeHeight, playerIn.posZ)
        val end = start.add(lookVec.x * dist, lookVec.y * dist, lookVec.z * dist)
        val raytrace = worldIn.rayTraceBlocks(start, end) ?: return
        val pos = raytrace.blockPos
        worldIn.destroyBlock(pos, true)
    }

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        tooltip.add("${umf.LORE}This drill is the drill that will pierce the heavens!")
        tooltip.add("Left click to burst mine, right click is single block Silk Touch.")
        tooltip.add("Shift right click will switch to Precise Mode.")
    }

    override fun onUpdate(stack: ItemStack, worldIn: World, entityIn: Entity, itemSlot: Int, isSelected: Boolean) {
        // Ensures this item always has an NBT tag.
        if (stack.tagCompound == null) {
            stack.tagCompound = NBTTagCompound().apply {
                setBoolean("preciseMode", false)
            }
        }
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected)
    }

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        val stack = playerIn.getHeldItem(handIn)
        if (!playerIn.isSneaking) {
            if (!worldIn.isRemote && stack.hasTagCompound()) {
                var didBreak = false
                if (stack.tagCompound!!.getLong("timeWhenUsable") < worldIn.totalWorldTime) {
                    val lookVec = playerIn.lookVec
                            ?: return super.onItemRightClick(worldIn, playerIn, handIn)
                    val start = Vec3d(playerIn.posX, playerIn.posY + playerIn.eyeHeight, playerIn.posZ)
                    val end = start.add(lookVec.x * altReach, lookVec.y * altReach, lookVec.z * altReach)
                    val raytrace = worldIn.rayTraceBlocks(start, end)
                            ?: return super.onItemRightClick(worldIn, playerIn, handIn)
                    val blockPos = raytrace.blockPos
                    val state = worldIn.getBlockState(blockPos)

                    // Prevent breaking if block is air, is unbreakable, or has a TileEntity. TODO: make it work with TEs
                    if (worldIn.isAirBlock(blockPos) || state.getBlockHardness(worldIn, blockPos) < 0 || state.block.hasTileEntity(state)) {
                        return super.onItemRightClick(worldIn, playerIn, handIn)
                    }
                    didBreak = true
                    val itemToCollect: ItemStack = if (state.block.canSilkHarvest(worldIn, blockPos, state, playerIn)) {
                        val item = Item.getItemFromBlock(state.block)
                        val i = if (item.hasSubtypes) {
                            state.block.getMetaFromState(state)
                        } else {
                            0
                        }
                        ItemStack(item, 1, i)
                    } else {
                        ItemStack(
                                state.block.getItemDropped(state, Random(), 0),
                                state.block.quantityDropped(Random())
                        )
                    }
                    val itemAttempt = playerIn.addItemStackToInventory(itemToCollect)
                    if (!itemAttempt) playerIn.entityDropItem(itemToCollect, 0f)?.setNoPickupDelay()
                    worldIn.destroyBlock(blockPos, false)
                }
                if (didBreak) {
                    stack.tagCompound!!.setLong("timeWhenUsable",
                            worldIn.totalWorldTime + breakCooldown)

                }
            }
        } else {
            // Precise Mode Toggle
            val preciseMode = stack.tagCompound!!.getBoolean("preciseMode")
            stack.tagCompound!!.setBoolean("preciseMode", !preciseMode)
            worldIn.ifClient { Utils.statusMessage("preciseMode: ${!preciseMode}") }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn)
    }
}