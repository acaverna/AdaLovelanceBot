package com.github.caverna.adalovelance.commands

import com.github.caverna.adalovelance.model.ChatMessage
import com.github.caverna.adalovelance.twitch.ITwitchChat
import com.github.caverna.adalovelance.twitch.OnChatMessageListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*
import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.minutes

@ExperimentalTime
class ShowStreamer: OnChatMessageListener {

    private val BASE_URL = "https://raw.githubusercontent.com/acaverna/streamers-amigues-da-caverna/master/"

    private var chat: ITwitchChat? = null
    private var streamers = mutableListOf<String>()
    private val props = Properties()

    init {

        props.load(ShowStreamer::class.java.getResourceAsStream("/twitch.properties"))

        val set = mutableSetOf<String>()

        val liveCodersBr = getStreamerList("$BASE_URL/livecodersbr.md")
        set.addAll(liveCodersBr)

        val girls = getStreamerList("$BASE_URL/mulheres.md")
        set.addAll(girls)

        streamers.addAll(set)

        GlobalScope.launch {
            while (true){
                val index = Random.nextInt(streamers.size + 1)
                chat?.sendMessage("!sh-so ${streamers[index]}")
                delay(30.minutes)
            }
        }

    }

    override fun onChatMessage(msg: ChatMessage, chat: ITwitchChat) {
        if(this.chat == null){
            this.chat = chat
        }
    }

    private fun getStreamerList(url:String):List<String>{

        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .build()

        val list = mutableListOf<String>()

        val response = client.newCall(request).execute()
        response.body!!.byteStream().bufferedReader().lines()
            .filter {
                !it.startsWith("#") && it != ""
            }.forEach {
                if(!it.contains("desativ")){
                    val streamer = it.substring(1, it.indexOf("]"))
                    list.add(streamer)
                }
            }

        return list
    }

}