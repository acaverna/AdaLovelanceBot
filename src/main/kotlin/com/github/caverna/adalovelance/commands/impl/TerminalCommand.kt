package com.github.caverna.adalovelance.commands.impl

import com.github.caverna.adalovelance.bot.IBot
import com.github.caverna.adalovelance.bot.OnChatMessageListener
import com.github.caverna.adalovelance.commands.BaseCommand
import com.github.caverna.adalovelance.model.ChatMessage
import org.slf4j.LoggerFactory

class TerminalCommand(bot: IBot) : BaseCommand(bot), OnChatMessageListener {

    private val logger = LoggerFactory.getLogger(TerminalCommand::class.java.name)

    override fun start() {
        logger.info("Iniciando o comando...")
        bot.setOnChatMessageListener(this)
    }

    override fun stop() {
        logger.info("Parando o comando...")
        bot.removeOnChatMessageListener(this)
    }

    override fun onChatMessage(msg: ChatMessage) {
        println("[${msg.date}] ${msg.user}: ${msg.text}")
    }
}