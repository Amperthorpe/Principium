package com.alleluid.principium.common.items.tools

import com.alleluid.principium.BlockUtils
import com.alleluid.principium.MOD_ID
import com.alleluid.principium.PrincipiumMod
import com.alleluid.principium.GeneralUtils
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
import com.alleluid.principium.GeneralUtils.Formatting as umf
import com.alleluid.principium.GeneralUtils.ifClient
import jdk.nashorn.internal.ir.Block
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.Enchantments
import net.minecraft.nbt.NBTTagInt
import net.minecraft.nbt.NBTTagString
import net.minecraft.world.WorldServer
import net.minecraftforge.common.util.Constants
import net.minecraftforge.common.util.FakePlayer
import net.minecraftforge.common.util.FakePlayerFactory

object LaserDrill : ItemPickaxe(PrincipiumMod.principicToolMaterial) {
    private const val directDrops = true

    init {
        creativeTab = PrincipiumMod.creativeTab
        registryName = ResourceLocation(MOD_ID, "laser_drill")
        translationKey = "$MOD_ID.laser_drill"
    }

    fun registerItemModel() = PrincipiumMod.proxy.registerItemRenderer(this, 0, "laser_drill")

    override fun getDestroySpeed(stack: ItemStack, state: IBlockState): Float = 1600f
    override fun canItemEditBlocks(): Boolean = true // It can edit any block, it's a laser drill.
    override fun canHarvestBlock(state: IBlockState, stack: ItemStack): Boolean = true

    fun onPrecisionMine(worldIn: WorldServer, playerIn: EntityPlayerMP) {
        val dist = playerIn.getEntityAttribute(EntityPlayer.REACH_DISTANCE).attributeValue
        val lookVec = playerIn.lookVec
        val start = Vec3d(playerIn.posX, playerIn.posY + playerIn.eyeHeight, playerIn.posZ)
        val end = start.add(lookVec.x * dist, lookVec.y * dist, lookVec.z * dist)
        val raytrace = worldIn.rayTraceBlocks(start, end) ?: return
        val pos = raytrace.blockPos

        val compound = playerIn.heldItemMainhand.tagCompound
        val tagList = compound?.getTagList("ench", Constants.NBT.TAG_COMPOUND)
        var isSilky = false
        var fortuneLvl = 0

        if (tagList != null) {
            for (enchCompound in tagList) {
                val id = (enchCompound as NBTTagCompound).getShort("id")
                val lvl = enchCompound.getShort("lvl")
                when (Enchantment.getEnchantmentByID(id.toInt())) {
                    Enchantments.SILK_TOUCH -> {
                        isSilky = true
                    }
                    Enchantments.FORTUNE -> {
                        fortuneLvl = lvl.toInt()
                    }
                }
            }
        }
        val dropsList: MutableList<ItemStack> = mutableListOf()
        if (BlockUtils.canBlockBeBroken(worldIn, playerIn, pos)) {
            val block = worldIn.getBlockState(pos).block
            if (isSilky && block.canSilkHarvest(worldIn, pos, worldIn.getBlockState(pos), playerIn)) {
                dropsList.add(block.getPickBlock(worldIn.getBlockState(pos), raytrace, worldIn, pos, playerIn))
            } else {
                dropsList.addAll(BlockUtils.getBlockDrops(worldIn, playerIn, pos, fortuneLvl))
            }

            for (item in dropsList) {
                //TODO make it less direct when off
                if (!directDrops && !playerIn.addItemStackToInventory(item))
                    playerIn.entityDropItem(item, 0f)?.setNoPickupDelay()
            }

            worldIn.destroyBlock(pos, false)
        }
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
        if (!playerIn.isSneaking){
            // Weapon TODO: Implement weapon
        } else {
            // Precise Mode Toggle
            val preciseMode = stack.tagCompound!!.getBoolean("preciseMode")
            stack.tagCompound!!.setBoolean("preciseMode", !preciseMode)
            worldIn.ifClient { GeneralUtils.statusMessage("preciseMode: ${!preciseMode}") }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn)
    }
}