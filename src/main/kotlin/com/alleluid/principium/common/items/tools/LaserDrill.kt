package com.alleluid.principium.common.items.tools

import com.alleluid.principium.*
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import com.alleluid.principium.GeneralUtils.Formatting as umf
import com.alleluid.principium.GeneralUtils.ifClient
import com.alleluid.principium.GeneralUtils.replaceModifer
import com.alleluid.principium.GeneralUtils.spawnParticleVec
import com.alleluid.principium.common.items.BaseItem
import com.alleluid.principium.util.BlockUtils
import com.google.common.collect.Multimap
import net.minecraft.block.BlockShulkerBox
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnumEnchantmentType
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.Enchantments
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.tileentity.TileEntityShulkerBox
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.WorldServer
import net.minecraftforge.common.util.Constants

object LaserDrill : BaseItem("laser_drill") {
    private const val directDrops = true
    private const val weaponTraceDist = 100.0

    init {
        loreText.add("This drill is the drill that will pierce the heavens!")
        infoText.add("Left click to burst mine, right click is a laser blast.")
        infoText.add("Shift right click will switch to Precise Mode.")
        infoText.add("")
    }

    override fun getAttributeModifiers(slot: EntityEquipmentSlot, stack: ItemStack): Multimap<String, AttributeModifier> {
        val map = super.getAttributeModifiers(slot, stack)
        if (slot == EntityEquipmentSlot.MAINHAND) {
            map.put(SharedMonsterAttributes.ATTACK_DAMAGE.name,
                    AttributeModifier(ATTACK_DAMAGE_MODIFIER,
                            SharedMonsterAttributes.ATTACK_DAMAGE.name,
                            13.0, 0
                    )
            )
            map.put(SharedMonsterAttributes.ATTACK_SPEED.name,
                    AttributeModifier(ATTACK_SPEED_MODIFIER,
                            SharedMonsterAttributes.ATTACK_SPEED.name,
                            -3.0, 0
                    )
            )
        }
        return map
    }

    override fun getDestroySpeed(stack: ItemStack, state: IBlockState): Float = 1600f
    override fun canItemEditBlocks(): Boolean = true // It can edit any block, it's a laser drill.
    override fun canHarvestBlock(state: IBlockState, stack: ItemStack): Boolean = true
    override fun canApplyAtEnchantingTable(stack: ItemStack, enchantment: Enchantment): Boolean {
        return enchantment.type == EnumEnchantmentType.WEAPON || enchantment.type == EnumEnchantmentType.DIGGER
                || super.canApplyAtEnchantingTable(stack, enchantment)
    }

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
            val state = worldIn.getBlockState(pos)
            val block = state.block

            // Special handling for shulker boxes; add to drops then ensure no dropping as item
            if (block is BlockShulkerBox) {
                dropsList.add(block.getItem(worldIn, pos, state))
                val shulker = worldIn.getTileEntity(pos) as TileEntityShulkerBox
                shulker.isDestroyedByCreativePlayer = true
                shulker.clear()

            } else if (isSilky && block.canSilkHarvest(worldIn, pos, worldIn.getBlockState(pos), playerIn)) {
                dropsList.add(block.getPickBlock(worldIn.getBlockState(pos), raytrace, worldIn, pos, playerIn))
            } else {
                dropsList.addAll(BlockUtils.getBlockDrops(worldIn, playerIn, pos, fortuneLvl))
            }

            for (item in dropsList) {
                //TODO make it less direct when off
                if (!playerIn.addItemStackToInventory(item) && !directDrops)
                    playerIn.entityDropItem(item, 0f)?.setNoPickupDelay()
            }

            worldIn.destroyBlock(pos, false)
        }
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
            // Weapon
            val look = playerIn.lookVec
            val eyePos = playerIn.getPositionEyes(1f)
            val endPoint = eyePos.add(look.x * weaponTraceDist, look.y * weaponTraceDist, look.z * weaponTraceDist)
            // Credit to Gigaherz for this method
            val entTrace = JavaUtils.getEntityIntercept(worldIn, playerIn, eyePos, look, endPoint, null)

            val blockTrace = worldIn.rayTraceBlocks(eyePos, entTrace?.hitVec ?: endPoint)
            val effectsEndPoint = if (entTrace != null) {
                if (blockTrace == null || blockTrace.typeOfHit == RayTraceResult.Type.MISS
                        || !worldIn.getBlockState(blockTrace.blockPos).isFullCube) {
                    playerIn.attackTargetEntityWithCurrentItem(entTrace.entityHit)
                    entTrace.hitVec
                } else {
                    blockTrace.hitVec
                }

            } else {
                blockTrace?.hitVec
            }

            if (worldIn.isRemote && effectsEndPoint != null) {
                if (entTrace != null && entTrace.typeOfHit == RayTraceResult.Type.ENTITY && (blockTrace == null || blockTrace.typeOfHit == RayTraceResult.Type.MISS)) {
                    for (i in 0..50) {
                        worldIn.spawnParticle(EnumParticleTypes.FLAME,
                                entTrace.entityHit.posX,
                                entTrace.entityHit.posY + entTrace.entityHit.eyeHeight,
                                entTrace.entityHit.posZ,
                                worldIn.rand.nextGaussian() * 0.1,
                                worldIn.rand.nextGaussian() * 0.1,
                                worldIn.rand.nextGaussian() * 0.1
                        )
                    }
                }
                for (i in 1..(eyePos.distanceTo(effectsEndPoint) * 5).toInt()) {
//                    val lookEye = look.add(eyePos.subtract(0.0, 1.0, 0.0))
                    val lookEye = look
                    worldIn.spawnParticle(EnumParticleTypes.END_ROD,
                            true,
                            lookEye.x + (i / 5 * look.x),
                            lookEye.y + (i / 5 * look.y),
                            lookEye.z + (i / 5 * look.z),
                            worldIn.rand.nextGaussian() * (0.1 * look.x),
                            worldIn.rand.nextGaussian() * (0.1 * look.y),
                            worldIn.rand.nextGaussian() * (0.1 * look.z)
                    )
                }
            }

        } else {
            // Precise Mode Toggle
            val preciseMode = stack.tagCompound!!.getBoolean("preciseMode")
            stack.tagCompound!!.setBoolean("preciseMode", !preciseMode)
            worldIn.ifClient { GeneralUtils.statusMessage("preciseMode: ${!preciseMode}") }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn)
    }
}