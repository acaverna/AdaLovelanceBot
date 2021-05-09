package com.github.caverna.adalovelance.controllers

import com.github.caverna.adalovelance.bot.IBot
import com.github.caverna.adalovelance.bot.OnChatMessageListener
import com.github.caverna.adalovelance.bot.impl.AdalovelanceBot
import com.github.caverna.adalovelance.commands.BaseCommand
import com.github.caverna.adalovelance.model.ChatMessage
import com.github.caverna.adalovelance.util.UpdateUI
import javafx.application.Platform
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextArea
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import net.bytebuddy.asm.Advice
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.text.DateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.swing.text.DateFormatter

class ChatController : BaseCommand(), OnChatMessageListener {

    @FXML
    private lateinit var chatTextArea: VBox

    @FXML
    private lateinit var chatScrollPane: ScrollPane

    private val CHAT_SNIPPET_FXML = "/views/chat_snippet.fxml"
    private val CHAT_FILE_NAME_SUFFIX = "chat-log.log"
    private lateinit var fileWriter: FileWriter

    @FXML
    fun initialize() {

        AdalovelanceBot.addCommand("ChatController", this)

        val date = Calendar.getInstance()
        val year = date.get(Calendar.YEAR)

        var month = (date.get(Calendar.MONTH) + 1).toString()
        if (month.length == 1) {
            month = "0$month"
        }

        val day = date.get(Calendar.DAY_OF_MONTH)

        val chatLogFile = File("$year-$month-$day-$CHAT_FILE_NAME_SUFFIX")
        if (!chatLogFile.exists()) {
            chatLogFile.createNewFile()
        }

        this.fileWriter = FileWriter(chatLogFile, true)

        chatScrollPane.vvalueProperty().bind(chatTextArea.heightProperty())

    }

    override fun start(bot: IBot) {
        super.start(bot)
        bot.setOnChatMessageListener(this)
    }

    override fun onChatMessage(msg: ChatMessage) {

        Platform.runLater {

            if (chatTextArea.children.size >= 200){
                chatTextArea.children.remove(chatTextArea.children.first())
            }

            val loader = FXMLLoader(ChatController::class.java.getResource(CHAT_SNIPPET_FXML))
            val chatSnippet = loader.load<Pane>()

            val usernameLabel = loader.namespace["chatSnippetUsername"] as Label
            usernameLabel.text = msg.user

            val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            val dateLabel = loader.namespace["chatSnippetDate"] as Label
            dateLabel.text = msg.date
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .format(dateFormat)

            val messageLabel = loader.namespace["chatSnippetMessage"] as Label
            messageLabel.text = msg.text

            chatTextArea.children.add(chatSnippet)

        }
    }
}