package com.github.caverna.adalovelance.commands

import com.github.caverna.adalovelance.model.ChatMessage
import com.github.caverna.adalovelance.twitch.ITwitchChat
import com.github.caverna.adalovelance.twitch.OnChatMessageListener
import com.github.twitch4j.chat.TwitchChat

class Terminal:OnChatMessageListener {
    override fun onChatMessage(msg: ChatMessage, chat: ITwitchChat) {
        println("[${msg.date}] ${msg.user}: ${msg.text}")
    }
}