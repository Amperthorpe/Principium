package com.alleluid.principium.common.items

import com.alleluid.principium.common.items.tools.DoublePick
import com.alleluid.principium.common.items.tools.LaserDrill
import com.alleluid.principium.common.items.tools.PrincipicSword
import com.alleluid.principium.common.items.tools.TransportRod
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.Item
import net.minecraftforge.registries.IForgeRegistry

object ModItems{

    val principitus = BaseItem("principitus").initOreDict("dustPrincipic")
    val chargedPrincipitus = BaseItem("charged_principitus")
    val ingotPrincipic = BaseItem("ingot_principic").initOreDict("ingotPrincipic")
    val longFallBoots = LongFallBootsBase(EntityEquipmentSlot.FEET, "long_fall_boots")

    @JvmStatic
    fun register(registry: IForgeRegistry<Item>){
        registry.registerAll(
                ingotPrincipic,
                principitus,
                chargedPrincipitus,
                Debugger,
                PrincipicSword,
                TransportRod,
                LaserDrill,
                DoublePick,
                longFallBoots
        )
    }

    @JvmStatic
    fun registerModels(){
        ingotPrincipic.registerItemModel()
        principitus.registerItemModel()
        chargedPrincipitus.registerItemModel()
        Debugger.registerItemModel()
        PrincipicSword.registerItemModel()
        TransportRod.registerItemModel()
        LaserDrill.registerItemModel()
        DoublePick.registerItemModel()
        longFallBoots.registerItemModel()
    }
}