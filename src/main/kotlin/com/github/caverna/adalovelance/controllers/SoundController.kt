package com.github.caverna.adalovelance.controllers

import com.github.caverna.adalovelance.model.SoundCommand
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.MouseEvent

class SoundController : EventHandler<MouseEvent> {

    @FXML
    private lateinit var soundID: TextField

    @FXML
    private lateinit var soundCommand: TextField

    @FXML
    private lateinit var soundPath: TextField

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
    private lateinit var soundCommandTable: TableView<SoundCommand>

    @FXML
    private lateinit var soundTableColumnID: TableColumn<SoundCommand, Int>

    @FXML
    private lateinit var soundTableColumnCommand: TableColumn<SoundCommand, String>

    @FXML
    private lateinit var soundTableColumnFilePath: TableColumn<SoundCommand, String>

    @FXML
    private lateinit var soundTableColumnDescription: TableColumn<SoundCommand, String>

    fun initialize() {

        soundTableColumnID.cellValueFactory = PropertyValueFactory("id")
        soundTableColumnCommand.cellValueFactory = PropertyValueFactory("command")
        soundTableColumnFilePath.cellValueFactory = PropertyValueFactory("path")
        soundTableColumnDescription.cellValueFactory = PropertyValueFactory("description")


        soundFileSelect.onMouseClicked = this
        soundClear.onMouseClicked = this
        soundSave.onMouseClicked = this
        soundDelete.onMouseClicked = this

    }

    override fun handle(event: MouseEvent?) {

        when (event?.source) {

            soundFileSelect -> {

            }

            soundClear -> {

            }

            soundSave -> {

            }

            soundDelete -> {

            }

        }

    }

}