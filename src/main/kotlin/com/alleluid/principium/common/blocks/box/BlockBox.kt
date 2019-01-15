package com.alleluid.principium.common.blocks.box

import com.alleluid.principium.ModGuiID
import com.alleluid.principium.Utils.Formatting
import com.alleluid.principium.common.blocks.BaseBlockTileEntity
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.item.EntityItem
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler

object BlockBox : BaseBlockTileEntity<TileEntityBox>(Material.WOOD, ModGuiID.BOX, "block_box"){
    init {
        blockHardness = 3f
    }

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        tooltip.add("${Formatting.LORE}Two chests in one full size block")
        super.addInformation(stack, worldIn, tooltip, flagIn)
    }

    override fun isToolEffective(type: String, state: IBlockState): Boolean {
        return when (type){
            "pickaxe" -> true
            "axe" -> true
            else -> false
        }
    }

    override fun isOpaqueCube(state: IBlockState): Boolean = false

    override val tileEntityClass: Class<TileEntityBox>
        get() = TileEntityBox::class.java

    override fun createTileEntity(world: World, state: IBlockState) = TileEntityBox()

    override fun breakBlock(worldIn: World, pos: BlockPos, state: IBlockState) {
        val tile: TileEntityBox = getTileEntity(worldIn, pos)
        val itemHandler: IItemHandler? = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)
        if (itemHandler != null){
            for (slot in 0 until itemHandler.slots) {
                val stack = itemHandler.getStackInSlot(slot)
                if (!stack.isEmpty) {
                    val item = EntityItem(worldIn, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), stack)
                    worldIn.spawnEntity(item)
                }
            }
        }
        super.breakBlock(worldIn, pos, state)
    }


}