package com.alleluid.principium.common.blocks

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentBase
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.registries.IForgeRegistry
import com.alleluid.principium.Utils
import com.alleluid.principium.common.blocks.counter.BlockCounter
import com.alleluid.principium.common.blocks.pedestal.BlockPedestal
import java.util.*

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
                BlockPedestal

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
                BlockPedestal.createItemBlock()
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
    }
}