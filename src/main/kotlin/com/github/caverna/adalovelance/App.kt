package com.github.caverna.adalovelance

import com.github.caverna.adalovelance.bot.impl.AdalovelanceBot
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import kotlin.system.exitProcess


class App : Application() {

    private val EXIT_SUCCESS = 0

    override fun start(primaryStage: Stage) {

        val root: Parent = FXMLLoader.load(App::class.java.getResource("/views/main.fxml"))

        val scene = Scene(root, 1280.0, 720.0)
        primaryStage.scene = scene
        primaryStage.isResizable = false
        primaryStage.title = "Ada Lovelance Bot"
        primaryStage.icons.add(Image(App::class.java.getResourceAsStream("/views/img/ada.png")));

        primaryStage.setOnCloseRequest {
            primaryStage.close()
            exitProcess(EXIT_SUCCESS)
        }

        primaryStage.show()

    }
}

fun main() {

    AdalovelanceBot.connect()

    AdalovelanceBot.loadCommandsFromDatabase()

    //AdalovelanceBot.addCommand("2", CommandFactory.getCommand(CommandType.PRESENCE_COMMAND))
    //AdalovelanceBot.addCommand("3", CommandFactory.getCommand(CommandType.RANDOM_GRADE_COMMAND))

    //AdalovelanceBot.addCommand("4", CommandFactory.getCommand(CommandType.TIMED_COMMAND, "30", "/me A mãe ta on galerê!"))
    //AdalovelanceBot.addCommand("5", CommandFactory.getCommand(CommandType.TEST_COMMAND))

    Application.launch(App::class.java)

}