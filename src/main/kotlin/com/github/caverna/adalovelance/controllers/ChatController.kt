package com.github.caverna.adalovelance.controllers

import com.github.caverna.adalovelance.bot.IBot
import com.github.caverna.adalovelance.bot.OnChatMessageListener
import com.github.caverna.adalovelance.bot.impl.AdalovelanceBot
import com.github.caverna.adalovelance.commands.BaseCommand
import com.github.caverna.adalovelance.model.ChatMessage
import javafx.fxml.FXML
import javafx.scene.control.TextArea
import javafx.scene.text.Text

class ChatController : BaseCommand(), OnChatMessageListener {

    @FXML
    private lateinit var chatTextArea: TextArea

    private val chat: StringBuffer = StringBuffer()

    @FXML
    fun initialize() {
        AdalovelanceBot.addCommand(this)
    }

    override fun start(bot: IBot) {
        super.start(bot)
        bot.setOnChatMessageListener(this)
    }

    override fun stop() {
        super.stop()
    }

    override fun onChatMessage(msg: ChatMessage) {
        chat.append("[${msg.date}] ${msg.user}: ${msg.text}\n")
        chatTextArea.text = chat.toString()
        chatTextArea.scrollTop = Double.MAX_VALUE
    }

}