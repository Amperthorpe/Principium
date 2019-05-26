package com.alleluid.principium.common.items

import com.alleluid.principium.common.items.basic.Substructs
import com.alleluid.principium.common.items.tools.DoublePick
import com.alleluid.principium.common.items.tools.LaserDrill
import com.alleluid.principium.common.items.tools.TransportRod
import com.alleluid.principium.common.items.weapons.PrincipicSword
import com.alleluid.principium.common.items.weapons.WeaponPistol
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.Item
import net.minecraftforge.registries.IForgeRegistry

object ModItems{

    val principitus = BaseItem("principitus")
    val chargedPrincipitus = BaseItem("charged_principitus")
    val ingotPrincipic = BaseItem("ingot_principic")
    val longFallBoots = LongFallBootsBase(EntityEquipmentSlot.FEET, "long_fall_boots")

    @JvmStatic
    fun registerItems(registry: IForgeRegistry<Item>){
        // Register individuals
        registry.registerAll(
                ingotPrincipic,
                principitus,
                chargedPrincipitus,
                Debugger,
                PrincipicSword,
                TransportRod,
                LaserDrill,
                DoublePick,
                longFallBoots,
                WeaponPistol
        )
        // Register variants TODO: figure out metadata maybe? It's not long for this world...
        Substructs.variants.forEach { registry.register(it) }
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
        WeaponPistol.registerItemModel()

        Substructs.variants.forEach { it.registerItemModel() }
    }

    @JvmStatic
    fun registerOreDict(){
        principitus.initOreDict("dustPrincipic")
        ingotPrincipic.initOreDict("ingotPrincipic")
    }
}