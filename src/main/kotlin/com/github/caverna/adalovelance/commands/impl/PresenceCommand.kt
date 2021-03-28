package com.github.caverna.adalovelance.commands.impl

import com.github.caverna.adalovelance.bot.IBot
import com.github.caverna.adalovelance.bot.OnChatMessageListener
import com.github.caverna.adalovelance.commands.BaseCommand
import com.github.caverna.adalovelance.model.ChatMessage
import com.github.caverna.adalovelance.model.Presence
import com.github.caverna.adalovelance.persistence.PresenceRepository
import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat
import java.util.*

class PresenceCommand(bot: IBot) : BaseCommand(bot), OnChatMessageListener {

    private val PRESENTE_CMD = "!presente"
    private val FREQUENCIA_CMD = "!frequencia"
    private val FREQUÊNCIA_CMD = "!frequência"

    private val logger = LoggerFactory.getLogger(PresenceCommand::class.java.name)

    private val presenceRepository = PresenceRepository()

    override fun start() {
        logger.info("Iniciando o comando ${PresenceCommand::class.java.name}...")
        bot.setOnChatMessageListener(this)
        logger.info("Comando ${PresenceCommand::class.java.name} inicializado!")
    }

    override fun stop() {
        logger.info("Parando o comando ${PresenceCommand::class.java.name}...")
        bot.removeOnChatMessageListener(this)
        logger.info("Comando ${PresenceCommand::class.java.name} finalizado!")
    }

    override fun onChatMessage(msg: ChatMessage) {

        when (msg.text) {
            PRESENTE_CMD -> {
                logger.info("Recebido um comando $PRESENTE_CMD de ${msg.user} em ${msg.date}")
                savePresence(msg)
            }
            FREQUENCIA_CMD, FREQUÊNCIA_CMD -> {
                logger.info("Recebido um comando $FREQUÊNCIA_CMD de ${msg.user} em ${msg.date}")
                showFrequency(msg)
            }
        }

    }

    private fun showFrequency(msg: ChatMessage) {
        val frequency = presenceRepository.countFrequencyByName(msg.user)
        bot.sendMessage("/me ${msg.user} você possui ${frequency} presenças registradas neste canal!")
    }

    private fun savePresence(msg: ChatMessage) {
        val now = Date()
        val presence = presenceRepository.findByName(msg.user)
        if (presence != null) {
            val difference = (now.time - presence.date.time) / 1000 / 3600

            if (difference > 4) {
                saveNewPresence(msg)
            } else {
                notRegisterPresence(msg, presence)
            }

        } else {
            saveNewPresence(msg)
        }
    }

    private fun notRegisterPresence(msg: ChatMessage, presence: Presence) {
        val sdf = SimpleDateFormat("dd-MM-yyy HH:mm:ss")
        bot.sendMessage("/me ${msg.user} sua presença já foi registrada as ${sdf.format(presence.date)} ")
    }

    private fun saveNewPresence(msg: ChatMessage) {
        val newPresence = Presence(name = msg.user, date = Date())
        presenceRepository.save(newPresence)

        bot.sendMessage("/me Sua presença foi registrada ${msg.user}")
    }
}