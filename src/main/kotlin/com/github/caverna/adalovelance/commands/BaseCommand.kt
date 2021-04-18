package com.github.caverna.adalovelance.commands

import com.github.caverna.adalovelance.bot.IBot

abstract class BaseCommand(vararg args:String) {

    lateinit var bot:IBot
    var isStarted = false

    open fun start(bot: IBot){
        if (isStarted) throw Exception("Comando já inicializado!")
        this.bot = bot
        this.isStarted = true
    }

    open fun stop() {
        if (!isStarted) throw Exception("Comando não inicializado")
        this.isStarted = false
    }

}