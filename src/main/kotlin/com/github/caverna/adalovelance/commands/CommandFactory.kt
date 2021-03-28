package com.github.caverna.adalovelance.commands

import com.github.caverna.adalovelance.bot.IBot
import com.github.caverna.adalovelance.commands.impl.TerminalCommand

object CommandFactory {

    fun getCommand(cmd: CommandType, bot: IBot): BaseCommand {

        return when (cmd) {

            CommandType.TERMINAL_COMMAND -> return TerminalCommand(bot)

        }
    }
}