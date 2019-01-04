package com.alleluid.principium

import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
import net.minecraft.world.World

object Utils {
    fun statusMessage(msg: String, actionBar: Boolean = true) {
        Minecraft.getMinecraft().player.sendStatusMessage(TextComponentString(msg), actionBar)
    }

    fun chatMessage(msg: String) = statusMessage(msg, false)

    fun TODO(reason: String) {
        chatMessage("TODO: $reason")
    }


    fun checkHeadspace(world: World, pos: BlockPos): Boolean {
        return world.isAirBlock(pos) && world.isAirBlock(BlockPos(pos.x, pos.y + 1, pos.z))
    }

    fun Entity.setPositionAndRotationAndUpdate(x: Double, y: Double, z: Double, yaw: Float, pitch: Float) {
        this.setPositionAndUpdate(x, y, z)
        this.setPositionAndRotation(x, y, z, yaw, pitch)
    }

    fun Entity.setPositionAndRotationAndUpdate(x: Double, y: Double, z: Double) {
        this.setPositionAndRotationAndUpdate(x, y, z, this.rotationYaw, this.rotationPitch)
    }

    object Formatting{
        // Reference: https://minecraft.gamepedia.com/Formatting_codes
        // Colors
        const val BLACK = "§0"
        const val DARK_BLUE = "§1"
        const val DARK_GREEN = "§2"
        const val DARK_AQUA = "§3"
        const val DARK_RED = "§4"
        const val DARK_PURPLE = "§5"
        const val GOLD = "§6"
        const val GREY = "§7"
        const val DARK_GREY = "§8"
        const val BLUE = "§9"
        const val GREEN = "§a"
        const val AQUA = "§b"
        const val RED = "§c"
        const val LIGHT_PURPLE = "§d"
        const val YELLOW = "§e"
        const val WHITE = "§f"

        // Styles
        const val OBFUSCATED = "§k"
        const val BOLD = "§l"
        const val STRIKETHROUGH = "§m"
        const val UNDERLINE = "§n"
        const val ITALIC = "§o"
        const val RESET = "§r"

        const val LORE = DARK_PURPLE + ITALIC
    }

    fun String.mcFormat(format: String): String{
        return format + this + Formatting.RESET
    }
}
