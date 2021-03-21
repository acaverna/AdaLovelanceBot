package com.github.caverna.adalovelance.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "presences")
data class Presence(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    val name: String,
    val date: Date
)