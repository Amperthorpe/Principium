package com.alleluid.principium.common.blocks.box

import com.alleluid.principium.ModGuiID
import com.alleluid.principium.common.blocks.BaseBlockTileEntity
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.world.World

object BlockBox : BaseBlockTileEntity<TileEntityBox>(Material.WOOD, ModGuiID.BOX, "block_box"){

    override val tileEntityClass: Class<TileEntityBox>
        get() = TileEntityBox::class.java

    override fun createTileEntity(world: World, state: IBlockState) = TileEntityBox

}