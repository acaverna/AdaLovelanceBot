package com.github.caverna.adalovelance.controllers

import com.github.caverna.adalovelance.bot.impl.AdalovelanceBot
import com.github.caverna.adalovelance.model.Timer
import com.github.caverna.adalovelance.persistence.TimerCommandRepository
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.stage.Modality

class TimerController : EventHandler<MouseEvent> {

    private val timerRepository = TimerCommandRepository()

    @FXML
    private lateinit var timerAnchorPane: AnchorPane

    @FXML
    private lateinit var timerID: TextField

    @FXML
    private lateinit var timerQuantity: TextField

    @FXML
    private lateinit var timerText: TextField

    @FXML
    private lateinit var timerDescription: TextField

    @FXML
    private lateinit var timerClear: Button

    @FXML
    private lateinit var timerSave: Button

    @FXML
    private lateinit var timerDelete: Button

    @FXML
    private lateinit var timerCommandTable: TableView<Timer>

    @FXML
    private lateinit var timerTableColumnID: TableColumn<Timer, Int>

    @FXML
    private lateinit var timerTableColumnTimer: TableColumn<Timer, Int>

    @FXML
    private lateinit var timerTableColumnText: TableColumn<Timer, String>

    @FXML
    private lateinit var timerTableColumnDescription: TableColumn<Timer, String>

    fun initialize() {

        timerTableColumnID.cellValueFactory = PropertyValueFactory("id")
        timerTableColumnTimer.cellValueFactory = PropertyValueFactory("timer")
        timerTableColumnText.cellValueFactory = PropertyValueFactory("text")
        timerTableColumnDescription.cellValueFactory = PropertyValueFactory("description")

        updateTable()

        timerClear.onMouseClicked = this
        timerSave.onMouseClicked = this
        timerDelete.onMouseClicked = this

        timerCommandTable.selectionModel.selectionMode = SelectionMode.SINGLE
        timerCommandTable.selectionModel.selectedItems.addListener(ListChangeListener{
            if(it.list.isNotEmpty()){
                timerID.text = it.list.first().id.toString()
                timerQuantity.text = it.list.first().timer.toString()
                timerText.text = it.list.first().text
                timerDescription.text = it.list.first().description
            }
        })

    }

    override fun handle(event: MouseEvent?) {

        when (event?.source) {

            timerClear -> {
                clearTextFields()
                clearTableSelection()
            }

            timerSave -> {

                val id = if (timerID.text.isEmpty()) -1L else timerID.text.toLong()
                val timer = timerQuantity.text
                val text = timerText.text
                val description = timerDescription.text

                if (timer.isEmpty() || text.isEmpty()) {

                    val alert = createAlert(
                        Alert.AlertType.ERROR,
                        "Ada Lovelance Bot",
                        "Erro ao salvar um temporizador",
                        "Os campos TEMPO e TEXTO não podem ser vazios"
                    )

                    val buttonOk = ButtonType("Ok", ButtonBar.ButtonData.OK_DONE)
                    alert.buttonTypes.setAll(buttonOk)

                    alert.showAndWait()

                } else {

                    val timerQuantityValue: Int

                    try {
                        timerQuantityValue = timer.toInt()
                    } catch (ex: NumberFormatException) {
                        val alert = createAlert(
                            Alert.AlertType.ERROR,
                            "Ada Lovelance Bot",
                            "Erro ao salvar um temporizador",
                            "O campo TEMPO só aceita números"
                        )

                        val buttonOk = ButtonType("Ok", ButtonBar.ButtonData.OK_DONE)
                        alert.buttonTypes.setAll(buttonOk)

                        alert.showAndWait()
                        return

                    }

                    if (id == -1L) {

                        val timerCommand = Timer(
                            timer = timerQuantityValue,
                            text = text,
                            description = description
                        )

                        timerRepository.save(timerCommand)

                        val alert = createAlert(
                            Alert.AlertType.CONFIRMATION,
                            "Ada Lovelance Bot",
                            "Salvar novo temporizador",
                            "Temporizador salvo com sucesso!"
                        )

                        val buttonOk = ButtonType("Ok", ButtonBar.ButtonData.OK_DONE)
                        alert.buttonTypes.setAll(buttonOk)

                        clearTextFields()
                        clearTableSelection()
                        updateTable()
                        AdalovelanceBot.loadCommandsFromDatabase()

                        alert.showAndWait()

                    } else {

                        val timerCommand = Timer(
                            id = id,
                            timer = timerQuantityValue,
                            text = text,
                            description = description
                        )

                        timerRepository.save(timerCommand)

                        val alert = createAlert(
                            Alert.AlertType.CONFIRMATION,
                            "Ada Lovelance Bot",
                            "Salvar novo temporizador",
                            "Temporizador atualizado com sucesso!"
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

            timerDelete -> {
                val id = if (timerID.text.isEmpty()) -1L else timerID.text.toLong()

                if(id != -1L){

                    val timer = timerRepository.find(id)
                    timerRepository.delete(timer)

                    val alert = createAlert(
                        Alert.AlertType.CONFIRMATION,
                        "Ada Lovelance Bot",
                        "Excluir temporizador",
                        "Temporizador excluído com sucesso!"
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
                        Alert.AlertType.CONFIRMATION,
                        "Ada Lovelance Bot",
                        "Excluir temporizador",
                        "Deve-se selecionar um dos temporizadores listados na tabela para realizar esta operação"
                    )

                    val buttonOk = ButtonType("Ok", ButtonBar.ButtonData.OK_DONE)
                    alert.buttonTypes.setAll(buttonOk)

                    alert.showAndWait()

                }

            }

        }

    }

    private fun updateTable() {
        val timers = timerRepository.findAll()
        timerCommandTable.items = FXCollections.observableArrayList(timers)
    }

    private fun clearTextFields() {
        timerID.text = ""
        timerQuantity.text = ""
        timerText.text = ""
        timerDescription.text = ""
    }

    private fun clearTableSelection() {
        timerCommandTable.selectionModel.clearSelection()
    }

    private fun createAlert(alertType: Alert.AlertType, title: String, header: String, content: String): Alert {
        val alert = Alert(alertType)
        alert.initModality(Modality.APPLICATION_MODAL)
        alert.initOwner(timerAnchorPane.scene.window)
        alert.title = title
        alert.headerText = header
        alert.contentText = content

        return alert
    }


}