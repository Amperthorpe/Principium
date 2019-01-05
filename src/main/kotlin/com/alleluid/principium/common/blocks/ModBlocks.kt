package com.alleluid.principium.common.blocks

import com.alleluid.principium.Utils.mcFormat
import com.alleluid.principium.common.blocks.counter.BlockCounter
import com.alleluid.principium.common.blocks.pedestal.BlockPedestal
import com.alleluid.principium.common.blocks.smelter.BlockSmelter
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.registries.IForgeRegistry
import com.alleluid.principium.Utils.Formatting as mcf

object ModBlocks {

    object blockPrincipic : BaseBlock(Material.GLASS, "block_principic") {
        init {
            lightValue = 15
            blockHardness = 0.3f
            setHarvestLevel("any", 10)
            initOreDict("blockPrincipic")
        }
    }

    object blockEgg : BaseBlock(Material.DRAGON_EGG, "block_egg"){
        override fun isOpaqueCube(state: IBlockState): Boolean = false
        override fun isFullBlock (state: IBlockState): Boolean = false
        override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
            tooltip.add("¯\\_(ツ)_/¯".mcFormat(mcf.DARK_PURPLE))
            super.addInformation(stack, worldIn, tooltip, flagIn)
        }
    }

    val blockMachineBase = BaseBlock(Material.IRON, "block_machine_base")


    @JvmStatic
    fun register(registry: IForgeRegistry<Block>) {
        registry.registerAll(
                blockMachineBase,
                blockEgg,
                blockPrincipic,
                FramedGlowBlock,
                BlockCounter,
                BlockPedestal,
                BlockSmelter

        )

    }

    @JvmStatic
    fun registerItemBlocks(registry: IForgeRegistry<Item>) {
        registry.registerAll(
                blockMachineBase.createItemBlock(),
                blockEgg.createItemBlock(),
                blockPrincipic.createItemBlock(),
                FramedGlowBlock.createItemBlock(),
                BlockCounter.createItemBlock(),
                BlockPedestal.createItemBlock(),
                BlockSmelter.createItemBlock()
        )
    }

    @JvmStatic
    fun registerModels() {
        blockMachineBase.registerItemModel()
        blockEgg.registerItemModel()
        blockPrincipic.registerItemModel()
        FramedGlowBlock.registerItemModel()
        BlockCounter.registerItemModel()
        BlockPedestal.registerItemModel()
        BlockSmelter.registerItemModel()
    }
}