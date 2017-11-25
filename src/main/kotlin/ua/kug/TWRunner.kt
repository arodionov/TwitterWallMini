package ua.kug

import io.javalin.Javalin
import twitter4j.TwitterStreamFactory
import ua.kug.tw.TwitterWall

fun main(args: Array<String>) {
    val app = Javalin.create().port(7000).start()
    app.get("/") { ctx -> ctx.result("Hello World") }

    val twitterWall = TwitterWall(TwitterStreamFactory().instance, listOf("#Java"))

    app.get("/tw") { ctx -> ctx.json(twitterWall.tweets()) }
}