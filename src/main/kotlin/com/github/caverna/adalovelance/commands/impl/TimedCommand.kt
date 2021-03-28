package com.github.caverna.adalovelance.commands.impl

import com.github.caverna.adalovelance.bot.IBot
import com.github.caverna.adalovelance.commands.BaseCommand
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import kotlin.time.ExperimentalTime
import kotlin.time.minutes

class TimedCommand(bot:IBot, vararg args:String):BaseCommand(bot) {

    private val time = args[0].toInt()
    private val text = args[1]
    private var isRunning = false

    private val logger = LoggerFactory.getLogger(TimedCommand::class.java.name)

    @ExperimentalTime
    override fun start() {
        logger.info("Iniciando o comando ${TimedCommand::class.java.name}...")
        isRunning = true

        GlobalScope.launch {
            while(isRunning){
                bot.sendMessage(text)
                delay(time.minutes)
            }
        }

        logger.info("Comando ${TimedCommand::class.java.name} inicializado!")
    }

    override fun stop() {
        logger.info("Parando o comando ${StaticTextCommand::class.java.name}...")
        isRunning = false
        logger.info("Comando ${StaticTextCommand::class.java.name} finalizado!")
    }

}