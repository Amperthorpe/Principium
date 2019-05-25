package com.alleluid.principium.common.blocks

import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.entity.Entity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object BlockPillow : BaseBlock(Material.CARPET, "block_pillow"){
    init {
        blockHardness = 1f
        loreText.addNamedKey("lore1") //"IT'S SO FLUFFY"
        infoText.addNamedKey("info1") //"Negates fall damage when landed on."
        blockSoundType = SoundType.CLOTH
    }

    override fun onFallenUpon(worldIn: World, pos: BlockPos, entityIn: Entity, fallDistance: Float) {
        entityIn.fallDistance = 0f
    }
}