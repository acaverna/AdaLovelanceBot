package com.github.caverna.adalovelance.commands

import com.github.caverna.adalovelance.model.ChatMessage
import com.github.caverna.adalovelance.twitch.ITwitchChat
import com.github.caverna.adalovelance.twitch.OnChatMessageListener
import kotlin.random.Random

class Grade:OnChatMessageListener {
    override fun onChatMessage(msg: ChatMessage, chat: ITwitchChat) {
        if(msg.text == "!nota"){
            val grade = Random.nextInt(11)
            val resp = "/me ${msg.user} você merece nota $grade!"
            if(grade >= 7){
                chat.sendMessage("$resp Parabens! SeemsGood SeemsGood SeemsGood")
            } else {
                chat.sendMessage("$resp Estude mais! NotLikeThis NotLikeThis NotLikeThis")
            }

        }
    }
}