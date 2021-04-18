package com.github.caverna.adalovelance.controllers

import com.github.caverna.adalovelance.bot.impl.AdalovelanceBot
import com.github.caverna.adalovelance.model.StaticCommand
import com.github.caverna.adalovelance.persistence.StaticCommandRepository
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.CheckBoxTableCell
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.stage.Modality
import javafx.util.Callback

class CommandController : EventHandler<MouseEvent> {

    private val commandRepository = StaticCommandRepository()

    @FXML
    private lateinit var cmdAnchorPane: AnchorPane

    @FXML
    private lateinit var cmdID: TextField

    @FXML
    private lateinit var cmdCommand: TextField

    @FXML
    private lateinit var cmdText: TextField

    @FXML
    private lateinit var cmdDescription: TextField

    @FXML
    private lateinit var cmdIsStreamerOnly: CheckBox

    @FXML
    private lateinit var cmdClear: Button

    @FXML
    private lateinit var cmdSave: Button

    @FXML
    private lateinit var cmdDelete: Button

    @FXML
    private lateinit var cmdCommandTable: TableView<StaticCommand>

    @FXML
    private lateinit var cmdTableColumnID: TableColumn<StaticCommand, Int>

    @FXML
    private lateinit var cmdTableColumnCommand: TableColumn<StaticCommand, String>

    @FXML
    private lateinit var cmdTableColumnText: TableColumn<StaticCommand, String>

    @FXML
    private lateinit var cmdTableColumnDescription: TableColumn<StaticCommand, String>

    @FXML
    private lateinit var cmdTableColumnStreamerOnly: TableColumn<StaticCommand, Boolean>

    @FXML
    fun initialize() {

        cmdTableColumnID.cellValueFactory = PropertyValueFactory("id")
        cmdTableColumnCommand.cellValueFactory = PropertyValueFactory("command")
        cmdTableColumnText.cellValueFactory = PropertyValueFactory("text")
        cmdTableColumnDescription.cellValueFactory = PropertyValueFactory("description")
        cmdTableColumnStreamerOnly.setCellValueFactory { features -> SimpleBooleanProperty(features!!.value.isStreamerOnly) }

        updateTable()

        cmdClear.onMouseClicked = this
        cmdSave.onMouseClicked = this
        cmdDelete.onMouseClicked = this

        cmdCommandTable.selectionModel.selectionMode = SelectionMode.SINGLE
        cmdCommandTable.selectionModel.selectedItems.addListener(ListChangeListener {
            if (it.list.isNotEmpty()) {
                cmdID.text = it.list.first().id.toString()
                cmdCommand.text = it.list.first().command
                cmdText.text = it.list.first().text
                cmdDescription.text = it.list.first().description
                cmdIsStreamerOnly.isSelected = it.list.first().isStreamerOnly
            }
        })
    }


    private fun updateTable() {
        val commands = commandRepository.findAll()
        cmdCommandTable.items = FXCollections.observableArrayList(commands)
    }

    override fun handle(event: MouseEvent?) {
        when (event?.source) {

            cmdClear -> {
                clearTextFields()
                clearTableSelection()
            }

            cmdSave -> {
                val id = if (cmdID.text.isEmpty()) -1L else cmdID.text.toLong()
                val command = if (cmdCommand.text.startsWith("!")) cmdCommand.text else "!${cmdCommand.text}"
                val text = cmdText.text
                val description = cmdDescription.text
                val isStreamerOnly = cmdIsStreamerOnly.isSelected

                if (command.isEmpty() || text.isEmpty()) {

                    val alert = createAlert(
                        Alert.AlertType.ERROR,
                        "Ada Lovelance Bot",
                        "Erro ao salvar um comando",
                        "Os campos COMANDO e TEXTO não podem ser vazios"
                    )

                    val buttonOk = ButtonType("Ok", ButtonBar.ButtonData.OK_DONE)
                    alert.buttonTypes.setAll(buttonOk)

                    alert.showAndWait()

                } else {

                    if (id == -1L) {
                        val cmd = StaticCommand(
                            command = command,
                            text = text,
                            description = description,
                            isStreamerOnly = isStreamerOnly
                        )

                        commandRepository.save(cmd)

                        clearTextFields()

                        updateTable()

                        val alert = createAlert(
                            Alert.AlertType.CONFIRMATION,
                            "Ada Lovelance Bot",
                            "Salvar novo comando",
                            "O comando ${command} foi inserio com sucesso!",
                        )

                        val buttonOk = ButtonType("Ok", ButtonBar.ButtonData.OK_DONE)
                        alert.buttonTypes.setAll(buttonOk)

                        AdalovelanceBot.loadCommandsFromDatabase()

                        alert.showAndWait()

                    } else {
                        val cmd = StaticCommand(
                            id = id,
                            command = command,
                            text = text,
                            description = description,
                            isStreamerOnly = isStreamerOnly
                        )
                        commandRepository.save(cmd)

                        val alert = createAlert(
                            Alert.AlertType.CONFIRMATION,
                            "Ada Lovelance Bot",
                            "Salvar novo comando",
                            "O comando ${command} foi atualizado com sucesso!"
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

            cmdDelete -> {
                val id = if (cmdID.text.isEmpty()) -1L else cmdID.text.toLong()

                if (id != -1L) {

                    val command = commandRepository.find(id)
                    commandRepository.delete(command)

                    val alert = createAlert(
                        Alert.AlertType.CONFIRMATION,
                        "Ada Lovelance Bot",
                        "Excluir comando",
                        "O comando ${command.command} foi excluído com sucesso!"
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
                        "Excluir comando",
                        "Deve-se selecionar um dos comandos listados na tabela para realizar esta operação"
                    )

                    val buttonOk = ButtonType("Ok", ButtonBar.ButtonData.OK_DONE)
                    alert.buttonTypes.setAll(buttonOk)

                    alert.showAndWait()
                }

            }
        }
    }

    private fun clearTableSelection() {
        cmdCommandTable.selectionModel.clearSelection()
    }

    private fun createAlert(alertType: Alert.AlertType, title: String, header: String, content: String): Alert {
        val alert = Alert(alertType)
        alert.initModality(Modality.APPLICATION_MODAL)
        alert.initOwner(cmdAnchorPane.scene.window)
        alert.title = title
        alert.headerText = header
        alert.contentText = content

        return alert
    }

    private fun clearTextFields() {
        cmdID.text = ""
        cmdCommand.text = ""
        cmdText.text = ""
        cmdDescription.text = ""
        cmdIsStreamerOnly.isSelected = false
    }

}