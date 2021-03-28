package com.github.caverna.adalovelance.commands

import com.github.caverna.adalovelance.bot.IBot
import com.github.caverna.adalovelance.commands.impl.*

object CommandFactory {

    fun getCommand(cmd: CommandType, bot: IBot, vararg args:String): BaseCommand {
        return when (cmd) {
            CommandType.TERMINAL_COMMAND -> TerminalCommand(bot)
            CommandType.PRESENCE_COMMAND -> PresenceCommand(bot)
            CommandType.RANDOM_GRADE_COMMAND -> RandomGradeCommand(bot)
            CommandType.STATIC_TEXT_COMMAND -> StaticTextCommand(bot, *args)
            CommandType.TIMED_COMMAND -> TimedCommand(bot, *args)
            CommandType.TEST_COMMAND -> TestCommand(bot)
        }
    }
}