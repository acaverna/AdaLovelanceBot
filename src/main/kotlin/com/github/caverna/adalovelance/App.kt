package com.github.caverna.adalovelance

import com.github.caverna.adalovelance.bot.impl.AdalovelanceBot
import com.github.caverna.adalovelance.commands.CommandFactory
import com.github.caverna.adalovelance.commands.CommandType
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.stage.StageStyle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.h2.tools.Server
import java.util.*


class App : Application() {

    override fun start(primaryStage: Stage) {

        val root:Parent = FXMLLoader.load(App::class.java.getResource("/views/main.fxml"))

        val scene = Scene(root, 1280.0, 720.0)
        primaryStage.scene = scene
        primaryStage.isResizable = false
        primaryStage.show()

        GlobalScope.launch {
            startBot()
        }

    }

    private fun startBot(){
        // Criação do servidor H2
        Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start()

        AdalovelanceBot.connect()
        
        val commands = listOf(
            CommandFactory.getCommand(CommandType.TERMINAL_COMMAND, AdalovelanceBot),
            CommandFactory.getCommand(CommandType.PRESENCE_COMMAND, AdalovelanceBot),
            CommandFactory.getCommand(CommandType.RANDOM_GRADE_COMMAND, AdalovelanceBot),
            CommandFactory.getCommand(CommandType.STATIC_TEXT_COMMAND, AdalovelanceBot, "!morganna", "Morgio amor! morgioAmor morgioAmor morgioAmor"),
            CommandFactory.getCommand(CommandType.TIMED_COMMAND, AdalovelanceBot, "30", "/me A mãe ta on galerê!"),
            CommandFactory.getCommand(CommandType.TEST_COMMAND, AdalovelanceBot)
        )

        commands.forEach {
            it.start()
        }

    }
}


fun main() {

    Application.launch(App::class.java)

}