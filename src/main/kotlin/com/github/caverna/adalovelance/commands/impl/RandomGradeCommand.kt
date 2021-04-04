package com.github.caverna.adalovelance.commands.impl

import com.github.caverna.adalovelance.bot.IBot
import com.github.caverna.adalovelance.bot.OnChatMessageListener
import com.github.caverna.adalovelance.commands.BaseCommand
import com.github.caverna.adalovelance.model.ChatMessage
import org.slf4j.LoggerFactory
import kotlin.random.Random

class RandomGradeCommand():BaseCommand(), OnChatMessageListener {

    private val NOTA_CMD = "!nota"

    private val logger = LoggerFactory.getLogger(RandomGradeCommand::class.java.name)

    override fun start(bot:IBot) {
        super.start(bot)

        logger.info("Iniciando o comando ${RandomGradeCommand::class.java.name}...")
        bot.setOnChatMessageListener(this)
        logger.info("Comando ${RandomGradeCommand::class.java.name} inicializado!")
    }

    override fun stop() {
        super.stop()

        logger.info("Parando o comando ${RandomGradeCommand::class.java.name}...")
        bot.removeOnChatMessageListener(this)
        logger.info("Comando ${RandomGradeCommand::class.java.name} finalizado!")
    }

    override fun onChatMessage(msg: ChatMessage) {

        when(msg.text){
            NOTA_CMD -> {
                logger.info("Recebido um comando $NOTA_CMD de ${msg.user} em ${msg.date}")
                generateRandomGrade(msg)
            }
        }

    }

    private fun generateRandomGrade(msg: ChatMessage) {
        val grade = Random.nextInt(11)
        val resp = "/me ${msg.user} você merece nota $grade!"
        if (grade >= 7) {
            bot.sendMessage("$resp Parabens! GlitchCat GlitchCat GlitchCat")
        } else {
            bot.sendMessage("$resp Estude mais! NotLikeThis NotLikeThis NotLikeThis")
        }
    }
}