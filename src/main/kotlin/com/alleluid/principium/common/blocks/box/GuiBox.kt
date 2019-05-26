package com.alleluid.principium.common.blocks.box

import com.alleluid.principium.common.blocks.box.BlockBox
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.util.ResourceLocation

class GuiBox(container: Container, private val playerInv: InventoryPlayer) : GuiContainer(container){
    private val backgroundTexture = ResourceLocation("textures/gui/container/generic_54.png")

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        renderHoveredToolTip(mouseX, mouseY)
    }

    private val inventoryRows = 6
    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        this.mc.textureManager.bindTexture(backgroundTexture)
        val x = (this.width - this.xSize) / 2
        val y = ((this.height - this.ySize) / 2) - 18
        drawTexturedModalRect(x, y, 0, 0, this.xSize, this.inventoryRows * 18 + 17)
        drawTexturedModalRect(x, y + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96)
    }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        val name = I18n.format( "${BlockBox.translationKey}.name")
        fontRenderer.drawString(playerInv.displayName.unformattedText, 8, this.ySize - 96 + 2 + 37, 4210752)
        fontRenderer.drawString(name, 8, 6 - 18, 4210752)

        super.drawGuiContainerForegroundLayer(mouseX, mouseY)
    }


}