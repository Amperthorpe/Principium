package com.alleluid.principium.client.gui

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.common.blocks.smelter.BlockSmelter
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.util.ResourceLocation

class GuiSmelter(container: Container, private val playerInv: InventoryPlayer) : GuiContainer(container) {
    private val BG_TEXTURE: ResourceLocation = ResourceLocation(MOD_ID, "textures/gui/gui_energy_furnace_blank.png")

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        renderHoveredToolTip(mouseX, mouseY)
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        GlStateManager.color(1f, 1f, 1f, 1f)
        mc.textureManager.bindTexture(BG_TEXTURE)
        val x = (width - xSize) / 2
        val y = (height - ySize) / 2
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize)
    }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        val name = I18n.format( "${BlockSmelter.translationKey}.name")
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040)
        fontRenderer.drawString(playerInv.displayName.unformattedText, 8, ySize - 94, 0x404040)
        super.drawGuiContainerForegroundLayer(mouseX, mouseY)
    }

}