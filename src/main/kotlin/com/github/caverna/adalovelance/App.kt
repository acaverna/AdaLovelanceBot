package com.github.caverna.adalovelance

import com.github.caverna.adalovelance.commands.Grade
import com.github.caverna.adalovelance.commands.Terminal
import com.github.caverna.adalovelance.model.Presence
import com.github.caverna.adalovelance.persistence.PresenceRepository
import com.github.caverna.adalovelance.twitch.TwitchAPI
import com.github.caverna.adalovelance.util.HibernateUtil
import org.h2.tools.Server
import org.hibernate.cfg.Configuration
import java.util.*

class App

fun main() {

    Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start()

    val props = Properties()
    props.load(App::class.java.getResourceAsStream("/twitch.properties"))
    val channel = props.getProperty("BOT_TWITCH_CHANNEL")
    val token = props.getProperty("BOT_TWITCH_TOKEN")
    val clientId = props.getProperty("BOT_TWITCH_CLIENTID")
    val secretId = props.getProperty("BOT_TWITCH_SECRETID")

    val twitchAPI = TwitchAPI(channel, token, clientId, secretId)

    twitchAPI.setOnChatMessageListener(Terminal())
    twitchAPI.setOnChatMessageListener(Grade())


}