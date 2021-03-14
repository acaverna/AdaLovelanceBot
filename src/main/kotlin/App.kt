import com.github.philippheuer.credentialmanager.CredentialManagerBuilder
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.twitch4j.TwitchClientBuilder
import com.github.twitch4j.auth.providers.TwitchIdentityProvider
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.chat.events.channel.IRCMessageEvent
import java.util.*
import java.util.function.Consumer

class App

fun main() {


    val props = Properties()
    props.load(App::class.java.getResourceAsStream("/twitch.properties"))
    val channel = props.getProperty("BOT_TWITCH_CHANNEL")
    val token = props.getProperty("BOT_TWITCH_TOKEN")
    val clientID = props.getProperty("BOT_TWITCH_CLIENTID")
    val secretID = props.getProperty("BOT_TWITCH_SECRETID")

    val credential = OAuth2Credential("twitch", token)


    val twitchClient = TwitchClientBuilder.builder()
        .withEnableChat(true)
        .withEnableTMI(true)
        .withClientId(clientID)
        .withClientSecret(secretID)
        .withChatAccount(credential)
        .build()

    twitchClient.chat.joinChannel(channel)
    twitchClient.chat.sendMessage(channel, "A mãe ta on galacta!")

    twitchClient.chat.eventManager.onEvent(ChannelMessageEvent::class.java) { t ->
        println(t.message)
    }

}