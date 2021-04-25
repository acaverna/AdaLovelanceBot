package com.github.caverna.adalovelance.model

import javax.persistence.*

@Entity
@Table(name = "timers")
@SequenceGenerator(name = "pk_timers_seq", sequenceName = "timers_id_seq", allocationSize = 1)
data class TimerCommand(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_timers_seq")
    val id:Long?=null,
    @Column(nullable = false)
    val timer:Int,
    @Column(nullable = false)
    val text:String,
    val description:String,
)