import java.util.*

class App

fun main() {


    val props = Properties()
    props.load(App::class.java.getResourceAsStream("/twitch-sample.properties"))
    val channel = props.getProperty("BOT_TWITCH_USERNAME")
    println(channel)

}