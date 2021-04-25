package com.github.caverna.adalovelance.commands.impl

import com.github.caverna.adalovelance.bot.IBot
import com.github.caverna.adalovelance.bot.OnChatMessageListener
import com.github.caverna.adalovelance.commands.BaseCommand
import com.github.caverna.adalovelance.model.ChatMessage
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import org.slf4j.LoggerFactory
import java.io.File

class SoundCommand(vararg args:String):BaseCommand(), OnChatMessageListener {

    private val command = args[0]
    private val path = args[1]

    private val logger = LoggerFactory.getLogger(SoundCommand::class.java.name)
    private lateinit var player:MediaPlayer


    override fun start(bot: IBot) {
        super.start(bot)

        logger.info("Iniciando o comando ${command}...")
        bot.setOnChatMessageListener(this)
        player = MediaPlayer(Media(File(path).toURI().toString()))
        logger.info("Comando ${command} inicializado!")

    }

    override fun stop() {
        super.stop()

        logger.info("Parando o comando ${command}...")
        bot.removeOnChatMessageListener(this)
        logger.info("Comando ${command} finalizado!")

    }

    override fun onChatMessage(msg: ChatMessage) {

        when(msg.text) {
            command -> {
                player.play()
                player.setOnEndOfMedia {
                    player.stop()
                }
            }
        }

    }

}