package com.github.caverna.adalovelance.controllers

import com.github.caverna.adalovelance.App
import com.github.caverna.adalovelance.bot.impl.AdalovelanceBot
import com.github.caverna.adalovelance.util.UpdateUI
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.stage.Stage
import kotlin.system.exitProcess

class MainController : EventHandler<MouseEvent> {

    private val EXIT_SUCCESS = 0



    @FXML
    private lateinit var mainMenuChat: Button

    @FXML
    private lateinit var mainMenuCommands: Button

    @FXML
    private lateinit var mainMenuTimers: Button

    @FXML
    private lateinit var mainMenuSettings: Button

    @FXML
    private lateinit var mainMenuModules: Button

    @FXML
    private lateinit var mainMenuExit: Button

    @FXML
    private lateinit var mainLayoutContainer: BorderPane

    @FXML
    fun initialize() {

        mainMenuChat.onMouseClicked = this
        mainMenuCommands.onMouseClicked = this
        mainMenuTimers.onMouseClicked = this
        mainMenuSettings.onMouseClicked = this
        mainMenuModules.onMouseClicked = this
        mainMenuExit.onMouseClicked = this

        UpdateUI.update(mainLayoutContainer, UpdateUI.SCENE.CHAT)

    }

    override fun handle(event: MouseEvent?) {

        when(event?.source){

            mainMenuChat -> {
                UpdateUI.update(mainLayoutContainer, UpdateUI.SCENE.CHAT)
            }

            mainMenuCommands -> {
                UpdateUI.update(mainLayoutContainer, UpdateUI.SCENE.COMMAND)
            }

            mainMenuTimers ->{
                UpdateUI.update(mainLayoutContainer, UpdateUI.SCENE.TIMERS)
            }

            mainMenuSettings ->{
                UpdateUI.update(mainLayoutContainer, UpdateUI.SCENE.SETTINGS)
            }

            mainMenuModules ->{
                UpdateUI.update(mainLayoutContainer, UpdateUI.SCENE.MODULES)
            }

            mainMenuExit -> {
                closeApplication(event)
            }
        }

    }

    private fun closeApplication(event: MouseEvent?) {
        val alert = Alert(Alert.AlertType.WARNING)
        alert.title = "Ada Lovelance Bot"
        alert.headerText = "Fechar a aplicação"
        alert.contentText = "Você realmente deseja fechar a Ada Lovelance Bot?"

        val buttonExit = ButtonType("Sair")
        val buttonCancel = ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE)
        alert.buttonTypes.setAll(buttonExit, buttonCancel)

        val result = alert.showAndWait()
        if (result.get() == buttonExit) {
            ((event?.source as Button).scene.window as Stage).close()
            exitProcess(EXIT_SUCCESS)
        }
    }

}