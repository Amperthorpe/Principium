package com.alleluid.principium.client.gui

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.MachineSyncMessage
import com.alleluid.principium.PacketHandler
import com.alleluid.principium.common.blocks.smelter.BlockSmelter
import com.alleluid.principium.common.blocks.smelter.ContainerSmelter
import com.alleluid.principium.common.blocks.smelter.TileEntitySmelter
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.util.ResourceLocation

class GuiSmelter(val container: Container, private val playerInv: InventoryPlayer) : GuiContainer(container) {
    private val BG_TEXTURE: ResourceLocation = ResourceLocation(MOD_ID, "textures/gui/gui_energy_furnace_blank.png")
    private var energy = 0
    init {
        if (container is ContainerSmelter)
            energy = container.smelter.energy
    }

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
        fontRenderer.run {
            drawString(name, xSize / 2 - this.getStringWidth(name) / 2, 6, 0x404040)
            drawString(playerInv.displayName.unformattedText, 8, ySize - 94, 0x404040)
            if (container is ContainerSmelter) {
                val smelter: TileEntitySmelter = container.smelter
                drawString(energy.toString(), 20, ySize - 125, 0x404040)
                drawString(smelter.timer.toString(), 20, ySize - 135, 0x404040)
            }
        }
        super.drawGuiContainerForegroundLayer(mouseX, mouseY)
    }

}