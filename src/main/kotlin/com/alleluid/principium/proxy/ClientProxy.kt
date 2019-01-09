package com.alleluid.principium.proxy

import com.alleluid.principium.MOD_ID
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.client.model.ModelLoader


open class ClientProxy : CommonProxy() {
    override fun registerItemRenderer(item: Item, meta: Int, id: String) {
        ModelLoader.setCustomModelResourceLocation(
                item, meta, ModelResourceLocation("$MOD_ID:$id", "inventory")
        )
    }
}