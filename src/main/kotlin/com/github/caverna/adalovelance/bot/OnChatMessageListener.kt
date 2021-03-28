package com.github.caverna.adalovelance.bot

import com.github.caverna.adalovelance.model.ChatMessage

interface OnChatMessageListener {
    fun onChatMessage(msg: ChatMessage)
}