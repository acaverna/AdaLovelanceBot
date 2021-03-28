package com.github.caverna.adalovelance

import com.github.caverna.adalovelance.bot.impl.AdalovelanceBot
import com.github.caverna.adalovelance.commands.CommandFactory
import com.github.caverna.adalovelance.commands.CommandType
import com.github.caverna.adalovelance.commands.impl.TerminalCommand
import org.h2.tools.Server
import java.util.*
import kotlin.time.ExperimentalTime

class App

@ExperimentalTime
fun main() {

    // Criação do servidor H2
    Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start()

    val props = Properties()
    props.load(App::class.java.getResourceAsStream("/twitch.properties"))
    val channel = props.getProperty("BOT_TWITCH_CHANNEL")
    val token = props.getProperty("BOT_TWITCH_TOKEN")
    val clientId = props.getProperty("BOT_TWITCH_CLIENTID")
    val secretId = props.getProperty("BOT_TWITCH_SECRETID")

    val bot = AdalovelanceBot.Builder()
        .setChannel(channel)
        .setToken(token)
        .setClientId(clientId)
        .setSecretId(secretId)
        .build()

    bot.connect()

    val commands = listOf(
        CommandFactory.getCommand(CommandType.TERMINAL_COMMAND, bot),
        CommandFactory.getCommand(CommandType.PRESENCE_COMMAND, bot),
        CommandFactory.getCommand(CommandType.TEST_COMMAND, bot)
    )

    commands.forEach {
        it.start()
    }

}