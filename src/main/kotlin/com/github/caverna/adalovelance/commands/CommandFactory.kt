package com.github.caverna.adalovelance.commands

import com.github.caverna.adalovelance.bot.IBot
import com.github.caverna.adalovelance.commands.impl.*

object CommandFactory {

    fun getCommand(cmd: CommandType, vararg args:String): BaseCommand {
        return when (cmd) {
            CommandType.TERMINAL_COMMAND -> TerminalCommand()
            CommandType.PRESENCE_COMMAND -> PresenceCommand()
            CommandType.RANDOM_GRADE_COMMAND -> RandomGradeCommand()
            CommandType.STATIC_TEXT_COMMAND -> StaticTextCommand(*args)
            CommandType.TIMED_COMMAND -> TimedCommand(*args)
            CommandType.TEST_COMMAND -> TestCommand()
            CommandType.SOUND_COMMAND -> SoundCommand(*args)
        }
    }
}