package com.github.caverna.adalovelance

import com.github.caverna.adalovelance.commands.Grade
import com.github.caverna.adalovelance.commands.Presence
import com.github.caverna.adalovelance.commands.Terminal
import com.github.caverna.adalovelance.twitch.TwitchAPI
import org.h2.tools.Server
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

    TwitchAPI(channel, token, clientId, secretId).apply{
        setOnChatMessageListener(Terminal())
        setOnChatMessageListener(Grade())
        setOnChatMessageListener(Presence())
    }


}