package com.github.caverna.adalovelance.controllers

import javafx.fxml.FXML
import javafx.scene.control.Label

class ChatSnippetController {

    @FXML
    lateinit var chatSnippetUsername: Label

    @FXML
    lateinit var chatSnippetDate: Label

    @FXML
    lateinit var chatSnippetMessage: Label

    @FXML
    fun initialize() {

    }

}