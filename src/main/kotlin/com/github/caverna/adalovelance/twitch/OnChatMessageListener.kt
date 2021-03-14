package com.github.caverna.adalovelance.twitch

import com.github.caverna.adalovelance.model.ChatMessage
import com.github.twitch4j.chat.TwitchChat

interface OnChatMessageListener {
    fun onChatMessage(msg: ChatMessage, chat: ITwitchChat)
}