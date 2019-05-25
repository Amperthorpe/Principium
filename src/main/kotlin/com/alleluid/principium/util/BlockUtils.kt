package com.alleluid.principium.util

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.world.BlockEvent


fun canBlockBeBroken(world: World, player: EntityPlayer, pos: BlockPos): Boolean {
    val state = world.getBlockState(pos)
    if (world.isAirBlock(pos)
            || state.block.getBlockHardness(state, world, pos) < 0
            || !world.isBlockModifiable(player, pos)
    ) return false
    val event = BlockEvent.BreakEvent(world, pos, world.getBlockState(pos), player)
    MinecraftForge.EVENT_BUS.post(event)
    return !event.isCanceled
}

fun getBlockDrops(world: World, player: EntityPlayer, pos: BlockPos, fortune: Int = 0): List<ItemStack> {
    val state = world.getBlockState(pos)
    val stacks = NonNullList.create<ItemStack>()
    state.block.getDrops(stacks, world, pos, state, fortune)
    val event = BlockEvent.HarvestDropsEvent(world, pos, world.getBlockState(pos), fortune, 1f, stacks, player, false)
    MinecraftForge.EVENT_BUS.post(event)
    return event.drops
}
