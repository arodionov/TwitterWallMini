package ua.jug

import io.javalin.Javalin
import twitter4j.TwitterStreamFactory
import ua.jug.tw.TwitterWall

fun main(args: Array<String>) {
    println("Hello, World")

    val app = Javalin.start(7000)
    app.get("/") { ctx -> ctx.result("Hello World") }

    val tags = listOf("#Java", "#JUGUA", "#JavaDayUA2017")

    val twitterWall = TwitterWall(TwitterStreamFactory().instance,
            tags
    )

    app.get("/tw") { ctx -> ctx.json(twitterWall.tweets()) }

}