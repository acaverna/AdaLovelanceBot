package com.github.caverna.adalovelance.commands

import com.github.caverna.adalovelance.bot.IBot

abstract class BaseCommand(val bot: IBot) {

    abstract fun start()

    abstract fun stop()

}