package com.alleluid.principium.client.render

import com.alleluid.principium.common.entities.EntityBullet
import net.minecraft.client.renderer.entity.Render
import net.minecraft.client.renderer.entity.RenderArrow
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
class RenderBullet(manager: RenderManager) : RenderArrow<EntityBullet>(manager) {
    override fun getEntityTexture(entity: EntityBullet) =
        ResourceLocation("textures/entity/projectiles/spectral_arrow.png")

    fun new(renderMan: RenderManager): Render<EntityBullet> {
        return renderMan.getEntityClassRenderObject<EntityBullet>(EntityBullet::class.java)
    }
}
