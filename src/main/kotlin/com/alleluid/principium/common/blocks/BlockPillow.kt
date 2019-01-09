package com.alleluid.principium.common.blocks

import net.minecraft.block.material.Material
import net.minecraft.entity.Entity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import com.alleluid.principium.Utils.Formatting as mcf

object BlockPillow : BaseBlock(Material.CLOTH, "block_pillow"){
    init {
        blockHardness = 3f
        loreText.add("IT'S SO FLUFFY")
        infoText.add("Negates fall damage when landed on.")
    }

    override fun onFallenUpon(worldIn: World, pos: BlockPos, entityIn: Entity, fallDistance: Float) {
        entityIn.fallDistance = 0f
    }
}