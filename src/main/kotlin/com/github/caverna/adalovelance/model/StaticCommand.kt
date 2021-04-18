package com.github.caverna.adalovelance.model

import javax.persistence.*

@Entity
@Table(name="static_commands")
data class StaticCommand (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Long? = null,
    @Column(nullable = false)
    val command:String,
    @Column(nullable = false)
    val text:String,
    val description:String,
    @Column(name="is_streamer_only")
    val isStreamerOnly:Boolean = false
)