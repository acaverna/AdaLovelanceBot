package com.github.caverna.adalovelance.commands.impl

import com.github.caverna.adalovelance.bot.IBot
import com.github.caverna.adalovelance.bot.OnChatMessageListener
import com.github.caverna.adalovelance.commands.BaseCommand
import com.github.caverna.adalovelance.model.ChatMessage
import org.slf4j.LoggerFactory

class StaticTextCommand(vararg args: String) : BaseCommand(), OnChatMessageListener {

    private val command = args[0]
    private val text = args[1]

    private val logger = LoggerFactory.getLogger(StaticTextCommand::class.java.name)
    override fun start(bot: IBot) {
        super.start(bot)

        logger.info("Iniciando o comando ${StaticTextCommand::class.java.name}...")
        bot.setOnChatMessageListener(this)
        logger.info("Comando ${StaticTextCommand::class.java.name} inicializado!")
    }

    override fun stop() {
        super.stop()

        logger.info("Parando o comando ${StaticTextCommand::class.java.name}...")
        bot.removeOnChatMessageListener(this)
        logger.info("Comando ${StaticTextCommand::class.java.name} finalizado!")
    }

    override fun onChatMessage(msg: ChatMessage) {
        when (msg.text) {
            command -> {
                bot.sendMessage(text)
            }
        }
    }

}