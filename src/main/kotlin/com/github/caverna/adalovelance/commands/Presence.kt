package com.github.caverna.adalovelance.commands

import com.github.caverna.adalovelance.model.ChatMessage
import com.github.caverna.adalovelance.model.Presence
import com.github.caverna.adalovelance.persistence.PresenceRepository
import com.github.caverna.adalovelance.twitch.ITwitchChat
import com.github.caverna.adalovelance.twitch.OnChatMessageListener
import java.text.SimpleDateFormat
import java.util.*

class Presence : OnChatMessageListener {

    private val presenceRepository:PresenceRepository = PresenceRepository()

    override fun onChatMessage(msg: ChatMessage, chat: ITwitchChat) {

        when (msg.text) {
            "!presente" -> {
                val now = Date()
                val presence = presenceRepository.findByName(msg.user)
                if(presence != null){
                    val difference = (now.time - presence.date.time) / 1000 / 3600

                    if(difference > 4){
                        saveNewPresence(msg, chat)
                    } else {
                        notRegisterPresence(chat, msg, presence)
                    }

                } else {
                    saveNewPresence(msg, chat)
                }
            }

            "!frequência" -> {
                val frequency = presenceRepository.countFrequencyByName(msg.user)
                chat.sendMessage("/me ${msg.user} você possui ${frequency} presenças registradas neste canal!")
            }
        }

    }

    private fun notRegisterPresence(
        chat: ITwitchChat,
        msg: ChatMessage,
        presence: Presence
    ) {
        val sdf = SimpleDateFormat("dd-MM-yyy HH:mm:ss")
        chat.sendMessage("/me ${msg.user} sua presença já foi registrada as ${sdf.format(presence.date)} ")
    }

    private fun saveNewPresence(
        msg: ChatMessage,
        chat: ITwitchChat
    ) {
        val newPresence = Presence(name = msg.user, date = Date())
        presenceRepository.save(newPresence)

        chat.sendMessage("/me Sua presença foi registrada ${msg.user}")
    }

}