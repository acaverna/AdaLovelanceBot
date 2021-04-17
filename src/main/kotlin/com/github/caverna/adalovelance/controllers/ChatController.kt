package com.github.caverna.adalovelance.controllers

import com.github.caverna.adalovelance.bot.IBot
import com.github.caverna.adalovelance.bot.OnChatMessageListener
import com.github.caverna.adalovelance.bot.impl.AdalovelanceBot
import com.github.caverna.adalovelance.commands.BaseCommand
import com.github.caverna.adalovelance.model.ChatMessage
import javafx.fxml.FXML
import javafx.scene.control.TextArea
import javafx.scene.text.Text
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.util.*

class ChatController : BaseCommand(), OnChatMessageListener {

    @FXML
    private lateinit var chatTextArea: TextArea

    private val stringBuffer = StringBuffer()
    private val CHAT_FILE_NAME_SUFFIX = "chat-log.log"
    private lateinit var fileWriter: FileWriter

    @FXML
    fun initialize() {
        AdalovelanceBot.addCommand(this)

        val date = Calendar.getInstance()
        val year = date.get(Calendar.YEAR)

        var month = (date.get(Calendar.MONTH)+1).toString()
        if(month.length == 1){
            month = "0$month"
        }

        val day = date.get(Calendar.DAY_OF_MONTH)

        val chatLogFile = File("$year-$month-$day-$CHAT_FILE_NAME_SUFFIX")
        if (!chatLogFile.exists()) {
            chatLogFile.createNewFile()
        } else {
            chatLogFile.readLines().forEach{
                stringBuffer.append("$it\n")
            }

            chatTextArea.text = stringBuffer.toString()
        }

        this.fileWriter = FileWriter(chatLogFile, true)

    }

    override fun start(bot: IBot) {
        super.start(bot)
        bot.setOnChatMessageListener(this)
    }

    override fun stop() {
        super.stop()
    }

    override fun onChatMessage(msg: ChatMessage) {

        val msg = "[${msg.date}] ${msg.user}: ${msg.text}\n"

        this.fileWriter.append(msg).flush()
        this.stringBuffer.append(msg)

        chatTextArea.text = stringBuffer.toString()
        chatTextArea.scrollTop = Double.MAX_VALUE

    }

}