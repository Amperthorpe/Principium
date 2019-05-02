package com.alleluid.principium.proxy

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.PrincipiumMod
import net.minecraft.entity.Entity
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.registry.EntityRegistry


open class CommonProxy{
    open fun registerItemRenderer(item: Item, meta: Int, id: String){}

    open fun registerEntity(name: String, entity: Class<Entity>, id: Int, range: Int, color1: Int, color2 : Int){
        EntityRegistry.registerModEntity(ResourceLocation("$MOD_ID:$name"), entity, name, id, PrincipiumMod, range, 1, true, color1, color2)
    }

}