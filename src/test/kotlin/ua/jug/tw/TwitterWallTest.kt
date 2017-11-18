package ua.jug.tw

import com.nhaarman.mockito_kotlin.*
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldThrow

import io.kotlintest.specs.FunSpec
import twitter4j.TwitterStream


class TwitterWallTest : FunSpec() {

    init {

        val twitterStream: TwitterStream = mock{
            on { onStatus(any()) } doReturn it
        }

        test("Init TW with tags") {
            TwitterWall(twitterStream, listOf("#JUG", "#ITweekend"))
        }

        test("Init fail with empty tags") {
            shouldThrow<IllegalArgumentException> {
                TwitterWall(twitterStream, listOf())
            }.message shouldBe "Empty or blank tags"
        }

        test("Init fail with blank tags") {
            shouldThrow<IllegalArgumentException> {
                TwitterWall(twitterStream, listOf("", "  "))
            }.message shouldBe "Empty or blank tags"
        }

        test("tweets") {
            val twitterWall = TwitterWall(twitterStream, listOf("#JUG", "#ITweekend"))
            twitterWall.tweets()
        }

        test("size TW") {
            val twitterWall = TwitterWall(twitterStream, listOf("#a"), 10)
            twitterWall.size shouldBe 10
        }

        test("empty tweets") {
            val twitterWall = TwitterWall(stream = twitterStream, tags = listOf("#JUG", "#ITweekend"), size = 10)
            twitterWall.tweets() shouldBe listOf<String>()
        }

        test("empty tweets") {
            val twitterStream: TwitterStream = mock {
                on { onStatus(any()) } doReturn it
            }
            val twitterWall = TwitterWall(stream = twitterStream, tags = listOf("#JUG", "#ITweekend"), size = 10)
            verify(twitterStream).onStatus(any())
        }

        test("filter is called") {
            val twitterStream: TwitterStream = mock {
                on { onStatus(any()) } doReturn it
            }
            val tags = listOf("#JUG", "#ITweekend")
            val twitterWall = TwitterWall(stream = twitterStream, tags = tags, size = 10)
            verify(twitterStream).filter(anyVararg<String>())
        }


        test("filter on tags is called") {
            val twitterStream: TwitterStream = mock {
                on { onStatus(any()) } doReturn it
            }
            val tags = listOf("#JUG", "#ITweekend")
            val twitterWall = TwitterWall(stream = twitterStream, tags = tags, size = 10)
            verify(twitterStream).filter(*tags.toTypedArray())
        }

        test("empty tweets") {
            val twitterStream: TwitterStream = mock {
                on { onStatus(any()) } doReturn it
            }
            val twitterWall = TwitterWall(stream = twitterStream, tags = listOf("#JUG", "#ITweekend"), size = 2)

            with(twitterWall) {
                updateBuffer("msg1")
                updateBuffer("msg2")
                updateBuffer("msg3")
            }
            twitterWall.tweets() shouldBe listOf("msg3", "msg2")
        }
    }
}