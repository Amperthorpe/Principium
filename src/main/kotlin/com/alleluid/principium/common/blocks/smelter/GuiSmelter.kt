package com.alleluid.principium.common.blocks.smelter

import com.alleluid.principium.MOD_ID
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.util.ResourceLocation

class GuiSmelter(container: Container, private val playerInv: InventoryPlayer) : GuiContainer(container) {
    private val backgroundTexture: ResourceLocation = ResourceLocation(MOD_ID, "textures/gui/gui_energy_furnace.png")
    private val containerSmelter = container as ContainerSmelter

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawVerticalLine(345, 50, 216, 0x000000)
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        drawVerticalLine(245, 50, 216, 0x404040)
        renderHoveredToolTip(mouseX, mouseY)
        drawVerticalLine(385, 50, 216, 0xffffff)
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        GlStateManager.color(1f, 1f, 1f, 1f)
        mc.textureManager.bindTexture(backgroundTexture)
        val x = (width - xSize) / 2
        val y = (height - ySize) / 2
        // Draw main blank gui
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize)
        val tileFields = containerSmelter.smelterTile.getFields()
        // Draw energy bar
        val barScale = tileFields.potential.toDouble() / tileFields.capacity
        drawTexturedModalRect(x + 46, y + 23, 176, 0, 12, (44 * barScale).toInt())
        val inRegion = isPointInRegion(x + 46, y + 23, 12, 44, mouseX, mouseY)
        if (inRegion){
            drawHoveringText("Test", mouseX, mouseY)
        }

    }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        val tileFields = containerSmelter.smelterTile.getFields()
        val name = I18n.format( "${BlockSmelter.translationKey}.name")
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040)
        fontRenderer.drawString(playerInv.displayName.unformattedText, 8, ySize - 94, 0x404040)
        fontRenderer.drawString(tileFields.potential.toString(), 20, ySize - 125, 0x404040)
        super.drawGuiContainerForegroundLayer(mouseX, mouseY)
    }

    // For...some reason these coords aren't the same scale as setting draw positions.
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        noTranslateMessage(mc.player, "Pos: $mouseX, $mouseY",false)
        super.mouseClicked(mouseX, mouseY, mouseButton)
    }

}