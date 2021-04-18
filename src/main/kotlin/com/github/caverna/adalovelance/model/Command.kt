package com.github.caverna.adalovelance.model

import javax.persistence.*

@Entity
@Table(name="commands")
data class Command (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Long? = null,
    @Column(nullable = false)
    val command:String,
    @Column(nullable = false)
    val text:String,
    val description:String,
)