package com.alleluid.principium.common.blocks.smelter

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.util.chatMessage
import com.alleluid.principium.util.noTranslateMessage
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.util.ResourceLocation

class GuiSmelter(private val container: Container, private val playerInv: InventoryPlayer) : GuiContainer(container) {
    private val backgroundTexture: ResourceLocation = ResourceLocation(MOD_ID, "textures/gui/gui_energy_furnace_blank.png")

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
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize)
    }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        val name = I18n.format( "${BlockSmelter.translationKey}.name")
        fontRenderer.run {
            drawString(name, xSize / 2 - this.getStringWidth(name) / 2, 6, 0x404040)
            drawString(playerInv.displayName.unformattedText, 8, ySize - 94, 0x404040)
            if (container is ContainerSmelter) {
                drawString(container.energy.toString(), 20, ySize - 125, 0x404040)
                drawVerticalLine(145, 50, 216, 0x000000)
            }
        }
        super.drawGuiContainerForegroundLayer(mouseX, mouseY)
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        noTranslateMessage(mc.player, "Pos: $mouseX, $mouseY",false)
        super.mouseClicked(mouseX, mouseY, mouseButton)
    }

}