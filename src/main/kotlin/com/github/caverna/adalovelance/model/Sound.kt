package com.github.caverna.adalovelance.model

import javax.persistence.*

@Entity
@Table(name="sound_commands")
@SequenceGenerator(name = "pk_sound_commands_seq", sequenceName = "sound_commands_id_seq", allocationSize = 1)
data class Sound(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sound_commands_seq")
    val id:Long?=null,
    @Column(nullable = false)
    val command:String,
    @Column(nullable = false)
    val path:String,
    val description:String
)