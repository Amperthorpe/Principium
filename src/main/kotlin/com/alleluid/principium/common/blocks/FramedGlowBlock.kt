package com.alleluid.principium.common.blocks

import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState

object FramedGlowBlock : BaseBlock(Material.GLASS, "framed_glow"){
    init {
        lightValue = 15
        blockHardness = 0.3f
        setHarvestLevel("any", 3)
    }

//    override fun isOpaqueCube(state: IBlockState): Boolean = false
    override fun isFullCube(state: IBlockState): Boolean = false
}