package com.alleluid.principium

import com.alleluid.principium.client.PrincipiumTab
import com.alleluid.principium.common.blocks.ModBlocks
import com.alleluid.principium.common.commands.myCommand
import com.alleluid.principium.common.entities.EntityBullet
import com.alleluid.principium.common.entities.ModEntities
import com.alleluid.principium.common.items.ModItems
import com.alleluid.principium.common.items.registerSmeltingRecipes
import com.alleluid.principium.proxy.CommonProxy
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.common.util.EnumHelper
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.event.FMLServerStartingEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.NetworkRegistry

const val MOD_ID = "principium"
const val MOD_NAME = "Principium"
const val VERSION = "1.0.0"

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter",
        useMetadata = true, dependencies = "required-after:forgelin;")
object PrincipiumMod {

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, ModGuiHandler())
        ModEntities.registerEntities()
        RenderingRegistry.registerEntityRenderingHandler(EntityBullet::class.java) {
            it.getEntityClassRenderObject<EntityBullet>(EntityBullet::class.java)
        }
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        ModItems.registerOreDict()
        ModBlocks.registerOreDict()
        registerSmeltingRecipes()
        ModBlocks.registerTileEntities()
        PacketHandler.registerMessages()
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent){

    }

    @SidedProxy(serverSide = "com.alleluid.principium.proxy.CommonProxy",
            clientSide = "com.alleluid.principium.proxy.ClientProxy")
    var proxy = CommonProxy()

    @JvmStatic
    var creativeTab = PrincipiumTab

    @JvmStatic
    val principicToolMaterial =  EnumHelper.addToolMaterial(
            "PRINCIPIC", 10, -1, 20f, 10f, 1)!!
}


@Mod.EventBusSubscriber(modid = MOD_ID)
object RegistryHandler {
    @JvmStatic
    @SubscribeEvent
    fun onItemRegister(event: RegistryEvent.Register<Item>) {
        ModItems.registerItems(event.registry)
        ModBlocks.registerItemBlocks(event.registry)
    }

    @JvmStatic
    @SubscribeEvent
    fun onModelRegister(event: ModelRegistryEvent){
        ModItems.registerModels()
        ModBlocks.registerModels()
    }

    @JvmStatic
    @SubscribeEvent
    fun onBlockRegister(event: RegistryEvent.Register<Block>) {
        ModBlocks.register(event.registry)
    }

    @JvmStatic
    @Mod.EventHandler
    fun onOtherRegister(event: FMLServerStartingEvent) {
        event.registerServerCommand(myCommand)
    }


}

