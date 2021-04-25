package com.github.caverna.adalovelance.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "presences")
@SequenceGenerator(name = "pk_presence_seq", sequenceName = "presence_id_seq", allocationSize = 1)
data class Presence(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_presence_seq")
    val id: Long? = null,
    val name: String,
    val date: Date
)