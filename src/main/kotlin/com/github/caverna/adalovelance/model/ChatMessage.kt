package com.github.caverna.adalovelance.model

import java.time.Instant

data class ChatMessage(
    val text: String,
    val user: String,
    val date: Instant
)