package com.github.caverna.adalovelance.bot.impl

import com.github.caverna.adalovelance.App
import com.github.caverna.adalovelance.bot.IBot
import com.github.caverna.adalovelance.bot.OnChatMessageListener
import com.github.caverna.adalovelance.commands.BaseCommand
import com.github.caverna.adalovelance.commands.CommandFactory
import com.github.caverna.adalovelance.commands.CommandType
import com.github.caverna.adalovelance.model.ChatMessage
import com.github.caverna.adalovelance.persistence.StaticCommandRepository
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.twitch4j.TwitchClient
import com.github.twitch4j.TwitchClientBuilder
import com.github.twitch4j.chat.events.IRCEventHandler
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import java.util.*

object AdalovelanceBot : IBot {

    private val channel: String
    private val token: String
    private val clientId: String
    private val secretId: String

    private val commands:MutableMap<String, BaseCommand>

    private lateinit var twitchClient: TwitchClient

    private val staticCommandRepository = StaticCommandRepository()
    private val chatMessageListeners = mutableListOf<OnChatMessageListener>()
    private val logger = LoggerFactory.getLogger(AdalovelanceBot::class.java.name)

    val STREAMER_NAME:String

    init {

        val props = Properties()
        props.load(App::class.java.getResourceAsStream("/twitch.properties"))

        this.channel = props.getProperty("BOT_TWITCH_CHANNEL")
        this.token = props.getProperty("BOT_TWITCH_TOKEN")
        this.clientId = props.getProperty("BOT_TWITCH_CLIENT_ID")
        this.secretId = props.getProperty("BOT_TWITCH_SECRET_ID")
        this.STREAMER_NAME = channel

        this.commands = mutableMapOf()

    }

    fun connect() {

        val credential = OAuth2Credential("twitch", token)
        this.twitchClient = TwitchClientBuilder.builder()
            .withEnableChat(true)
            .withEnableTMI(true)
            .withClientId(clientId)
            .withClientSecret(secretId)
            .withChatAccount(credential)
            .build()

        this.twitchClient.chat.joinChannel(channel)
        this.twitchClient.chat.sendMessage(channel, "A mãe ta on galacta!")

        this.twitchClient.chat.eventManager.onEvent(ChannelMessageEvent::class.java) { t ->

            val chatMessage = ChatMessage(t.message, t.user.name, t.messageEvent.firedAt.time)

            this.chatMessageListeners.forEach {
                GlobalScope.launch {
                    it.onChatMessage(chatMessage)
                }
            }
        }

        this.twitchClient.chat.eventManager.onEvent(IRCEventHandler::class.java) {

        }

        logger.info("AdalovelanceBot está conectada a Twicth no canal $channel!")

    }

    fun addCommand(cmd:String, obj:BaseCommand){
        if(!this.commands.containsKey(cmd)){
            if(!obj.isStarted) obj.start(this)
            this.commands[cmd] = obj
        }
    }

    fun removeCommand(cmd:String){
        if(this.commands.containsKey(cmd)){
            val obj = this.commands[cmd]!!
            if(obj.isStarted) obj.stop()
            this.commands.remove(cmd)
        }
    }

    fun loadCommandsFromDatabase(){
        println("Iniciando o processo de carregamento dos comandos...")

        this.commands.forEach{
            it.value.stop()
        }

        this.commands.clear()
        this.addCommand("-1", CommandFactory.getCommand(CommandType.TERMINAL_COMMAND))
        this.loadStaticCommandsFromDatabase()
        println("Processo finalizado!")
    }

    private fun loadStaticCommandsFromDatabase(){
        staticCommandRepository.findAll().forEach {
            this.addCommand(
                it.command,
                CommandFactory.getCommand(
                    CommandType.STATIC_TEXT_COMMAND,
                    it.command,
                    it.text,
                    it.isStreamerOnly.toString()
                )
            )
        }
    }

    override fun sendMessage(msg: String) {
        this.twitchClient.chat.sendMessage(this.channel, msg)
    }

    override fun setOnChatMessageListener(listener: OnChatMessageListener) {
        this.chatMessageListeners.add(listener)
    }

    override fun removeOnChatMessageListener(listener: OnChatMessageListener) {
        this.chatMessageListeners.remove(listener)
    }

}