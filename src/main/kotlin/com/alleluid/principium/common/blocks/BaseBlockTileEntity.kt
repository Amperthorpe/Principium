package com.alleluid.principium.common.blocks

import com.alleluid.principium.ModGuiID
import com.alleluid.principium.PrincipiumMod
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

abstract class BaseBlockTileEntity<TE : TileEntity>(material: Material, private val guiID: ModGuiID?, name_: String) : BaseBlock(material, name_) {
    init {

    }

    abstract val tileEntityClass: Class<TE>

    override fun hasTileEntity(state: IBlockState): Boolean = true

    abstract override fun createTileEntity(world: World, state: IBlockState): TE?

    fun getTileEntity(world: IBlockAccess, pos: BlockPos): TE {
        return world.getTileEntity(pos) as TE
    }

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if (guiID != null) {
            if (!worldIn.isRemote) {
                if (!playerIn.isSneaking) {
                    playerIn.openGui(PrincipiumMod.instance, guiID.ordinal, worldIn, pos.x, pos.y, pos.z)
                } else {
                }
            }
            return true
        } else {
            return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ)
        }
    }
}