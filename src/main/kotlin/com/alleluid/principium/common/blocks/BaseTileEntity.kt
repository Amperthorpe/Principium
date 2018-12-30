package com.alleluid.principium.common.blocks

import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

abstract class BaseTileEntity<TE : TileEntity>(material: Material, val name_: String) : BaseBlock(material, name_){
    init {

    }

    abstract val tileEntityClass: Class<TE>

    override fun hasTileEntity(state: IBlockState): Boolean = true

    abstract override fun createTileEntity(world: World, state: IBlockState): TE?

    fun getTileEntity(world: IBlockAccess, pos: BlockPos): TE{
        return world.getTileEntity(pos) as TE
    }
}