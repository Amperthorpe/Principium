package com.alleluid.principium.common.items.tools

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.PrincipiumMod
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

object LaserDrill : ItemPickaxe(PrincipiumMod.principicToolMaterial) {
    private const val breakCooldown = 8
    private const val altReach = 10.0

    init {
        creativeTab = PrincipiumMod.creativeTab
        registryName = ResourceLocation(MOD_ID, "laser_drill")
        unlocalizedName = "$MOD_ID.laser_drill"
    }

    fun registerItemModel() = PrincipiumMod.proxy.registerItemRenderer(this, 0, "laser_drill")

    override fun getDestroySpeed(stack: ItemStack, state: IBlockState): Float = 1600f
    override fun canItemEditBlocks(): Boolean = true
    override fun canHarvestBlock(state: IBlockState, stack: ItemStack): Boolean = true

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        tooltip.add("Instant Pickaxe + Laser Deletion Gun")
        tooltip.add("Left click to burst mine, right click is single block Silk Touch.")
    }

    override fun onUpdate(stack: ItemStack, worldIn: World, entityIn: Entity, itemSlot: Int, isSelected: Boolean) {
        if (stack.tagCompound == null) {
            stack.tagCompound = NBTTagCompound()
        }
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected)
    }

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        if (!worldIn.isRemote) {
            val stack = playerIn.getHeldItem(handIn)
            if (stack.hasTagCompound()) {
                var didBreak = false
                if (stack.tagCompound!!.getLong("timeWhenUsable") < worldIn.totalWorldTime) {
                    val lookVec = playerIn.lookVec
                            ?: return super.onItemRightClick(worldIn, playerIn, handIn)
                    val start = Vec3d(playerIn.posX, playerIn.posY + playerIn.eyeHeight, playerIn.posZ)
                    val end = start.addVector(lookVec.x * altReach, lookVec.y * altReach, lookVec.z * altReach)
                    val raytrace = worldIn.rayTraceBlocks(start, end)
                            ?: return super.onItemRightClick(worldIn, playerIn, handIn)
                    val blockPos = raytrace.blockPos
                    val state = worldIn.getBlockState(blockPos)

                    if (playerIn.isSneaking) {
                        //Maybe make this do something?
                    } else {
                        if (worldIn.isAirBlock(blockPos) || state.getBlockHardness(worldIn, blockPos) < 0) return super.onItemRightClick(worldIn, playerIn, handIn)
                        didBreak = true
                        val itemToCollect: ItemStack = if (state.block.canSilkHarvest(worldIn, blockPos, state, playerIn)) {
                            ItemStack(Item.getItemFromBlock(state.block), 1)
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
                }
                if (didBreak) {
                    stack.tagCompound!!.setLong("timeWhenUsable",
                            worldIn.totalWorldTime + breakCooldown)
                }
            }


        }
        return super.onItemRightClick(worldIn, playerIn, handIn)
    }
}