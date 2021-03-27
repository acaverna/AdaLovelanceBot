package com.github.caverna.adalovelance.twitch

import com.github.caverna.adalovelance.model.ChatMessage
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.twitch4j.TwitchClient
import com.github.twitch4j.TwitchClientBuilder
import com.github.twitch4j.chat.TwitchChat
import com.github.twitch4j.chat.events.IRCEventHandler
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TwitchAPI(
    private val channel: String,
    private val token: String,
    private val clientId: String,
    private val secretId: String
) {

    private var chatMessageListeners:MutableList<OnChatMessageListener> = mutableListOf()
    private var twitchClient: TwitchClient
    private val chat:ITwitchChat

    init {
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

        this.chat = Chat(this.twitchClient.chat, this.channel)

        this.twitchClient.chat.eventManager.onEvent(ChannelMessageEvent::class.java) { t ->
            val chatMessage = ChatMessage(t.message, t.user.name, t.messageEvent.firedAt.time)
            this.chatMessageListeners.forEach{
                GlobalScope.launch {
                    it.onChatMessage(chatMessage, chat)
                }
            }
        }

        this.twitchClient.chat.eventManager.onEvent(IRCEventHandler::class.java){

        }
    }

    fun getChat(): TwitchChat {
        return this.twitchClient.chat
    }

    fun setOnChatMessageListener(listener:OnChatMessageListener){
        this.chatMessageListeners.add(listener)
    }

    class Chat(private val chat: TwitchChat, private val channel: String): ITwitchChat{
        override fun sendMessage(text:String){
            this.chat.sendMessage(this.channel, text)
        }
    }

}