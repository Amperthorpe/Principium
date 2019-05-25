package com.alleluid.principium.common.blocks

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.common.blocks.box.BlockBox
import com.alleluid.principium.common.blocks.box.TileEntityBox
import com.alleluid.principium.common.blocks.counter.BlockCounter
import com.alleluid.principium.common.blocks.counter.TileEntityCounter
import com.alleluid.principium.common.blocks.pedestal.BlockPedestal
import com.alleluid.principium.common.blocks.pedestal.TileEntityPedestal
import com.alleluid.principium.common.blocks.smelter.BlockSmelter
import com.alleluid.principium.common.blocks.smelter.TileEntitySmelter
import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.registries.IForgeRegistry

object ModBlocks {

    object BlockPrincipic : BaseBlock(Material.GLASS, "block_principic") {
        init {
            lightValue = 15
            blockHardness = 0.3f
            blockSoundType = SoundType.GLASS
            setHarvestLevel("any", 10)
        }
    }

    val BlockMachineBase = BaseBlock(Material.IRON, "block_machine_base")


    @JvmStatic
    fun register(registry: IForgeRegistry<Block>) {
        registry.registerAll(
                BlockMachineBase,
                BlockEgg,
                BlockPrincipic,
                FramedGlowBlock,
                BlockCounter,
                BlockPedestal,
                BlockSmelter,
                BlockPillow,
                BlockBox

        )

    }

    @JvmStatic
    fun registerItemBlocks(registry: IForgeRegistry<Item>) {
        registry.registerAll(
                BlockMachineBase.createItemBlock(),
                BlockEgg.createItemBlock(),
                BlockPrincipic.createItemBlock(),
                FramedGlowBlock.createItemBlock(),
                BlockCounter.createItemBlock(),
                BlockPedestal.createItemBlock(),
                BlockSmelter.createItemBlock(),
                BlockPillow.createItemBlock(),
                BlockBox.createItemBlock()
        )
    }

    @JvmStatic
    fun registerModels() {
        BlockMachineBase.registerItemModel()
        BlockEgg.registerItemModel()
        BlockPrincipic.registerItemModel()
        FramedGlowBlock.registerItemModel()
        BlockCounter.registerItemModel()
        BlockPedestal.registerItemModel()
        BlockSmelter.registerItemModel()
        BlockPillow.registerItemModel()
        BlockBox.registerItemModel()
    }

    @JvmStatic
    fun registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityCounter::class.java,
                ResourceLocation(MOD_ID, "tile_entity_counter"))

        TileEntityBox().registerTE()
        TileEntitySmelter().registerTE()
        TileEntityPedestal().registerTE()
    }

    @JvmStatic
    fun registerOreDict(){
        BlockPrincipic.initOreDict("blockPrincipic")
    }
}