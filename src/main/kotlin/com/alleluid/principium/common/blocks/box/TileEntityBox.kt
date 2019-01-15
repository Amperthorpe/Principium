package com.alleluid.principium.common.blocks.box

import com.alleluid.principium.common.blocks.BaseInventoryTileEntity

object TileEntityBox : BaseInventoryTileEntity("tile_entity_box", 54) {
    init {
        inventory
    }
//    override val inventory = object : ItemStackHandler(54) {
//        override fun onContentsChanged(slot: Int) {
//            super.onContentsChanged(slot)
//            markDirty()
//        }
//    }


}