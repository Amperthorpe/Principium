package com.alleluid.principium.proxy

import net.minecraft.item.Item


open class CommonProxy{
    open fun registerItemRenderer(item: Item, meta: Int, id: String){}
}