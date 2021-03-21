package com.github.caverna.adalovelance.model

import java.util.*

data class ChatMessage(
    val text: String,
    val user: String,
    val date: Date
)