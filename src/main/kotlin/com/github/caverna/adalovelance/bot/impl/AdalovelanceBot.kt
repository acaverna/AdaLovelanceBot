package com.github.caverna.adalovelance.bot.impl

import com.github.caverna.adalovelance.bot.IBot
import com.github.caverna.adalovelance.bot.OnChatMessageListener
import com.github.caverna.adalovelance.model.ChatMessage
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.twitch4j.TwitchClient
import com.github.twitch4j.TwitchClientBuilder
import com.github.twitch4j.chat.events.IRCEventHandler
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory

class AdalovelanceBot private constructor(
    private val channel: String?,
    private val token: String?,
    private val clientId: String?,
    private val secretId: String?
) : IBot {

    private lateinit var twitchClient: TwitchClient
    private val chatMessageListeners = mutableListOf<OnChatMessageListener>()
    private val logger = LoggerFactory.getLogger(AdalovelanceBot::class.java.name)

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

    override fun sendMessage(msg: String) {
        this.twitchClient.chat.sendMessage(this.channel, msg)
    }

    override fun setOnChatMessageListener(listener: OnChatMessageListener) {
        this.chatMessageListeners.add(listener)
    }

    override fun removeOnChatMessageListener(listener: OnChatMessageListener) {
        this.chatMessageListeners.remove(listener)
    }

    data class Builder(
        var channel: String? = null,
        var token: String? = null,
        var clientId: String? = null,
        var secretId: String? = null
    ) {
        fun setChannel(channel: String) = apply { this.channel = channel }
        fun setToken(token: String) = apply { this.token = token }
        fun setClientId(clientId: String) = apply { this.clientId = clientId }
        fun setSecretId(secretId: String) = apply { this.secretId = secretId }
        fun build() = AdalovelanceBot(channel, token, clientId, secretId)
    }

}