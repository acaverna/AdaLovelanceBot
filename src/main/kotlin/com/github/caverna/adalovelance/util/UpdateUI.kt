package com.github.caverna.adalovelance.util

import com.github.caverna.adalovelance.App
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.layout.BorderPane

object UpdateUI {

    private val CHAT_FXML_PATH = "/views/chat.fxml"
    private val COMMANDS_FXML_PATH = "/views/commands.fxml"
    private val TIMERS_FXML_PATH = "/views/timers.fxml"
    private val SETTINGS_FXML_PATH = "/views/settings.fxml"
    private val MODULES_FXML_PATH = "/views/modules.fxml"

    private val scenesLoader = mutableMapOf<SCENE, Parent>()

    enum class SCENE {
        CHAT,
        COMMAND,
        TIMERS,
        SETTINGS,
        MODULES,
    }

    fun update(borderPane: BorderPane, scene: SCENE) {

        val root: Parent = when (scene) {
            SCENE.CHAT -> {
                if (!scenesLoader.containsKey(scene)) {
                    scenesLoader[scene] = FXMLLoader.load(UpdateUI::class.java.getResource(CHAT_FXML_PATH)) as Parent
                }
                scenesLoader[scene]!!
            }

            SCENE.COMMAND -> {
                if (!scenesLoader.containsKey(scene)) {
                    scenesLoader[scene] =
                        FXMLLoader.load(UpdateUI::class.java.getResource(COMMANDS_FXML_PATH)) as Parent
                }
                scenesLoader[scene]!!
            }
            SCENE.TIMERS -> {
                if (!scenesLoader.containsKey(scene)) {
                    scenesLoader[scene] =
                        FXMLLoader.load(UpdateUI::class.java.getResource(TIMERS_FXML_PATH)) as Parent
                }
                scenesLoader[scene]!!
            }

            SCENE.SETTINGS -> {
                if (!scenesLoader.containsKey(scene)) {
                    scenesLoader[scene] =
                        FXMLLoader.load(UpdateUI::class.java.getResource(SETTINGS_FXML_PATH)) as Parent
                }
                scenesLoader[scene]!!
            }

            SCENE.MODULES -> {
                if (!scenesLoader.containsKey(scene)) {
                    scenesLoader[scene] = FXMLLoader.load(UpdateUI::class.java.getResource(MODULES_FXML_PATH)) as Parent
                }
                scenesLoader[scene]!!
            }

            else -> {
                if (!scenesLoader.containsKey(scene)) {
                    scenesLoader[scene] = FXMLLoader.load(UpdateUI::class.java.getResource(CHAT_FXML_PATH)) as Parent
                }
                scenesLoader[scene]!!
            }
        }

        borderPane.center = root

    }

}