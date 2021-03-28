package com.github.caverna.adalovelance.bot

interface IBot {

    fun setOnChatMessageListener(listener:OnChatMessageListener)

    fun removeOnChatMessageListener(listener:OnChatMessageListener)

    fun sendMessage(msg:String)

}