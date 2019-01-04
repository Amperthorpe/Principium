package com.alleluid.principium.common.items.tools

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.PrincipiumMod
import net.minecraft.block.state.IBlockState
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemPickaxe
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object DoublePick : ItemPickaxe(ToolMaterial.DIAMOND) {
    const val name = "double_pick"

    init {
        translationKey = "$MOD_ID.$name"
        registryName = ResourceLocation(MOD_ID, name)
        creativeTab = PrincipiumMod.creativeTab
    }

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        tooltip.add("Always mines the two blocks in front of you.")
        tooltip.add("Sneak to mine normally.")
        tooltip.add("Also a bit of a multitool.")
    }

    override fun getDestroySpeed(stack: ItemStack, state: IBlockState): Float {
        return super.getDestroySpeed(stack, state) + 3f
    }

    fun registerItemModel() {
        PrincipiumMod.proxy.registerItemRenderer(this, 0, name)
    }

    override fun onBlockStartBreak(itemstack: ItemStack, pos: BlockPos, player: EntityPlayer): Boolean {
        if (!player.isSneaking) {
            val secondPos = if (player.position.y < pos.y) pos.down() else pos.up()
            val state = player.world.getBlockState(secondPos)
            if (state.getBlockHardness(player.world, pos) >= 0 && player.canHarvestBlock(state))
                player.world.destroyBlock(secondPos, true)
        }
        return super.onBlockStartBreak(itemstack, pos, player)
    }

}