package com.github.caverna.adalovelance.commands.impl

import com.github.caverna.adalovelance.bot.IBot
import com.github.caverna.adalovelance.bot.OnChatMessageListener
import com.github.caverna.adalovelance.commands.BaseCommand
import com.github.caverna.adalovelance.model.ChatMessage
import org.slf4j.LoggerFactory

class TerminalCommand() : BaseCommand(), OnChatMessageListener {

    private val logger = LoggerFactory.getLogger(TerminalCommand::class.java.name)

    override fun start(bot: IBot) {
        super.start(bot)

        logger.info("Iniciando o comando ${TerminalCommand::class.java.name}...")
        bot.setOnChatMessageListener(this)
        logger.info("Comando ${TerminalCommand::class.java.name} inicializado!")
    }

    override fun stop() {
        super.stop()

        logger.info("Parando o comando ${TerminalCommand::class.java.name}...")
        bot.removeOnChatMessageListener(this)
        logger.info("Comando ${TerminalCommand::class.java.name} finalizado!")
    }

    override fun onChatMessage(msg: ChatMessage) {
        println("[${msg.date}] ${msg.user}: ${msg.text}")
    }
}