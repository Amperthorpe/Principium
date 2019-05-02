package com.alleluid.principium.common.items

import com.alleluid.principium.Utils
import com.alleluid.principium.Utils.ifClient
import com.alleluid.principium.common.blocks.smelter.TileEntitySmelter
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object Debugger : BaseItem("debugger"){
    init {
        loreText.add("Pay no attention to the item behind the curtain")
    }

    override fun onItemUse(player: EntityPlayer, worldIn: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        worldIn.ifClient {
            val te = worldIn.getTileEntity(pos) ?: return@ifClient Unit
            println(te.tileData.toString())
            Unit
        }

        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ)
    }

}