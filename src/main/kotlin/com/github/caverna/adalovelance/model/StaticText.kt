package com.github.caverna.adalovelance.model

import javax.persistence.*

@Entity
@Table(name="static_commands")
@SequenceGenerator(name = "pk_static_commands_seq", sequenceName = "static_commands_id_seq", allocationSize = 1)
data class StaticText (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_static_commands_seq")
    val id:Long? = null,
    @Column(nullable = false)
    val command:String,
    @Column(nullable = false)
    val text:String,
    val description:String,
    @Column(name="is_streamer_only")
    val isStreamerOnly:Boolean = false
)