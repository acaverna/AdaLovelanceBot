package com.github.caverna.adalovelance.commands

import com.github.caverna.adalovelance.bot.IBot
import com.github.caverna.adalovelance.commands.impl.PresenceCommand
import com.github.caverna.adalovelance.commands.impl.TerminalCommand
import com.github.caverna.adalovelance.commands.impl.TestCommand

object CommandFactory {

    fun getCommand(cmd: CommandType, bot: IBot): BaseCommand {
        return when (cmd) {
            CommandType.TERMINAL_COMMAND -> TerminalCommand(bot)
            CommandType.PRESENCE_COMMAND -> PresenceCommand(bot)
            CommandType.TEST_COMMAND -> TestCommand(bot)
        }
    }
}