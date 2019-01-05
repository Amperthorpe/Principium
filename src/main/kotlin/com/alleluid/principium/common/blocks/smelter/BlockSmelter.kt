package com.alleluid.principium.common.blocks.smelter

import com.alleluid.principium.common.blocks.BaseBlockTileEntity
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.world.World

object BlockSmelter : BaseBlockTileEntity<TileEntitySmelter>(Material.ROCK, "block_smelter") {
    init {
        blockHardness = 3f
    }

    override val tileEntityClass: Class<TileEntitySmelter>
        get() = TileEntitySmelter::class.java

    override fun createTileEntity(world: World, state: IBlockState) = TileEntitySmelter()
}