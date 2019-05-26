package com.alleluid.principium.common.commands

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.server.MinecraftServer
import net.minecraft.util.math.BlockPos

val myCommand  = object : CommandBase(){
    override fun getName(): String = "ac"

    override fun getRequiredPermissionLevel(): Int = 2

    override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
        val entityPlayer = getCommandSenderAsPlayer(sender)
        entityPlayer.heal(-10f)
        notifyCommandListener(sender, this, "commands.kill.successful")
    }

    override fun getUsage(sender: ICommandSender): String = "commands.principium.usage"

    override fun getTabCompletions(server: MinecraftServer, sender: ICommandSender, args: Array<String>, targetPos: BlockPos?): MutableList<String> {
        return if (args.size == 1) getListOfStringsMatchingLastWord(args, *server.onlinePlayerNames) else mutableListOf()
    }

}
