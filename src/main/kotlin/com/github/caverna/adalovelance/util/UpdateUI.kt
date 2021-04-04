package com.github.caverna.adalovelance.util

import com.github.caverna.adalovelance.App
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.layout.BorderPane

object UpdateUI {

    fun update(borderPane: BorderPane, path:String){
        val root: Parent = FXMLLoader.load(App::class.java.getResource(path))
        borderPane.center = root
    }
}