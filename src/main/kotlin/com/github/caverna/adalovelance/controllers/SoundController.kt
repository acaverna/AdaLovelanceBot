package com.github.caverna.adalovelance.controllers

import com.github.caverna.adalovelance.bot.impl.AdalovelanceBot
import com.github.caverna.adalovelance.commands.impl.SoundCommand
import com.github.caverna.adalovelance.model.Sound
import com.github.caverna.adalovelance.persistence.SoundCommandRepository
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.stage.FileChooser
import javafx.stage.Modality
import java.io.File

class SoundController : EventHandler<MouseEvent> {

    private val soundCommandRepository = SoundCommandRepository()

    private lateinit var soundFile: File

    @FXML
    private lateinit var soundAnchorPane: AnchorPane

    @FXML
    private lateinit var soundID: TextField

    @FXML
    private lateinit var soundCommand: TextField

    @FXML
    private lateinit var soundName: TextField

    @FXML
    private lateinit var soundDescription: TextField

    @FXML
    private lateinit var soundFileSelect: Button

    @FXML
    private lateinit var soundClear: Button

    @FXML
    private lateinit var soundSave: Button

    @FXML
    private lateinit var soundDelete: Button

    @FXML
    private lateinit var soundCommandTable: TableView<Sound>

    @FXML
    private lateinit var soundTableColumnID: TableColumn<Sound, Int>

    @FXML
    private lateinit var soundTableColumnCommand: TableColumn<Sound, String>

    @FXML
    private lateinit var soundTableColumnFilePath: TableColumn<Sound, String>

    @FXML
    private lateinit var soundTableColumnDescription: TableColumn<Sound, String>

    fun initialize() {

        soundTableColumnID.cellValueFactory = PropertyValueFactory("id")
        soundTableColumnCommand.cellValueFactory = PropertyValueFactory("command")
        soundTableColumnFilePath.cellValueFactory = PropertyValueFactory("path")
        soundTableColumnDescription.cellValueFactory = PropertyValueFactory("description")

        updateTable()

        soundFileSelect.onMouseClicked = this
        soundClear.onMouseClicked = this
        soundSave.onMouseClicked = this
        soundDelete.onMouseClicked = this

        soundCommandTable.selectionModel.selectionMode = SelectionMode.SINGLE
        soundCommandTable.selectionModel.selectedItems.addListener(ListChangeListener{
            if(it.list.isNotEmpty()){

                val soundFile = File(it.list.first().path)

                soundID.text = it.list.first().id.toString()
                soundCommand.text = it.list.first().command
                soundName.text = soundFile.name
                soundDescription.text = it.list.first().description

            }
        })

    }

    override fun handle(event: MouseEvent?) {

        when (event?.source) {

            soundFileSelect -> {

                val fileChooser = FileChooser()
                fileChooser.title = "Ada Lovelance Bot"
                fileChooser.extensionFilters.addAll(
                    FileChooser.ExtensionFilter("MP3", "*.mp3")
                )

                soundFile = fileChooser.showOpenDialog(soundAnchorPane.scene.window)

                if (soundFile != null) {
                    soundName.text = soundFile.name
                }

            }

            soundClear -> {
                clearTextFields()
                clearTableSelection()
            }

            soundSave -> {

                val id = if (soundID.text.isEmpty()) -1L else soundID.text.toLong()
                val command = soundCommand.text
                val path = soundFile.absolutePath
                val description = soundDescription.text

                if (command.isEmpty() || path.isEmpty()) {

                    val alert = createAlert(
                        Alert.AlertType.ERROR,
                        "Ada Lovelance Bot",
                        "Erro ao salvar um som",
                        "Os campos COMANDO e ARQUIVO DE AUDIO não podem ser vazios"
                    )

                    val buttonOk = ButtonType("Ok", ButtonBar.ButtonData.OK_DONE)
                    alert.buttonTypes.setAll(buttonOk)

                    alert.showAndWait()

                } else {

                    if (id == -1L) {

                        val sound = Sound(
                            command = command,
                            path = path,
                            description = description
                        )

                        soundCommandRepository.save(sound)

                        val alert = createAlert(
                            Alert.AlertType.CONFIRMATION,
                            "Ada Lovelance Bot",
                            "Salvar som",
                            "Comando de som ${command} salvo com sucesso"
                        )

                        val buttonOk = ButtonType("Ok", ButtonBar.ButtonData.OK_DONE)
                        alert.buttonTypes.setAll(buttonOk)

                        clearTextFields()
                        clearTableSelection()
                        updateTable()
                        AdalovelanceBot.loadCommandsFromDatabase()

                        alert.showAndWait()


                    } else {

                        val sound = Sound(
                            id = id,
                            command = command,
                            path = path,
                            description = description
                        )

                        soundCommandRepository.save(sound)

                        val alert = createAlert(
                            Alert.AlertType.CONFIRMATION,
                            "Ada Lovelance Bot",
                            "Atualizar som",
                            "Comando de som ${command} atualizado com sucesso"
                        )

                        val buttonOk = ButtonType("Ok", ButtonBar.ButtonData.OK_DONE)
                        alert.buttonTypes.setAll(buttonOk)

                        clearTextFields()
                        clearTableSelection()
                        updateTable()
                        AdalovelanceBot.loadCommandsFromDatabase()

                        alert.showAndWait()

                    }

                }

            }

            soundDelete -> {

                val id = if (soundID.text.isEmpty()) -1L else soundID.text.toLong()

                if(id != -1L){

                    val sound = soundCommandRepository.find(id)
                    soundCommandRepository.delete(sound)

                    val alert = createAlert(
                        Alert.AlertType.CONFIRMATION,
                        "Ada Lovelance Bot",
                        "Excluir som",
                        "Comando de som ${sound.command} excluído com sucesso"
                    )

                    val buttonOk = ButtonType("Ok", ButtonBar.ButtonData.OK_DONE)
                    alert.buttonTypes.setAll(buttonOk)

                    clearTextFields()
                    clearTableSelection()
                    updateTable()
                    AdalovelanceBot.loadCommandsFromDatabase()

                    alert.showAndWait()


                } else {

                    val alert = createAlert(
                        Alert.AlertType.ERROR,
                        "Ada Lovelance Bot",
                        "Excluir som",
                        "Deve-se selecionar um dos sons listados na tabela para realizar esta operação"
                    )

                    val buttonOk = ButtonType("Ok", ButtonBar.ButtonData.OK_DONE)
                    alert.buttonTypes.setAll(buttonOk)

                    alert.showAndWait()

                }

            }

        }

    }

    private fun updateTable() {
        val sounds = soundCommandRepository.findAll()
        soundCommandTable.items = FXCollections.observableArrayList(sounds)
    }

    private fun clearTableSelection() {
        soundCommandTable.selectionModel.clearSelection()
    }

    private fun clearTextFields() {
        soundID.text = ""
        soundCommand.text = ""
        soundName.text = ""
        soundDescription.text = ""
    }

    private fun createAlert(alertType: Alert.AlertType, title: String, header: String, content: String): Alert {
        val alert = Alert(alertType)
        alert.initModality(Modality.APPLICATION_MODAL)
        alert.initOwner(soundAnchorPane.scene.window)
        alert.title = title
        alert.headerText = header
        alert.contentText = content

        return alert
    }

}