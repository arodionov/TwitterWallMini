package ua.kug.tw

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldThrow
import io.kotlintest.specs.FunSpec
import twitter4j.TwitterStream

class TwitterWallTest : FunSpec() {

    init {

        val twitterStream: TwitterStream = mock {
            on { onStatus(any()) } doReturn it
        }

        test("create TW") {
            TwitterWall(twitterStream, listOf("#KKUG"))
        }

        test("TW with hashTags") {
            val hashtags = listOf("#KKUG")
            TwitterWall(twitterStream, hashtags)
        }

        test("hashTags should not be empty") {
            shouldThrow<IllegalArgumentException> {
                TwitterWall(twitterStream, listOf())
            }.message shouldBe "HashTags should not be empty or blank"
        }

        test("hashTags should not be blank") {
            shouldThrow<IllegalArgumentException> {
                TwitterWall(twitterStream, listOf(" ", "   "))
            }.message shouldBe "HashTags should not be empty or blank"
        }

        test("TW size should be 10 by default") {
            val twitterWall = TwitterWall(twitterStream, listOf("#KKUG"))
            twitterWall.size shouldBe 10
        }

        test("TW size") {
            val twitterWall = TwitterWall(twitterStream, listOf("#KKUG"), size = 5)
            twitterWall.size shouldBe 5
        }

        test("tweets should be empty") {
            val twitterWall = TwitterWall(twitterStream, listOf("#KKUG"))
            twitterWall.tweets() shouldBe emptyList<String>()
        }

        test("TwitterStream mocking") {
            TwitterWall(twitterStream = twitterStream, hashTags = listOf("#KKUG"), size = 5)
        }

        test("TwitterStream subscribe on #hashTags") {
            val ts: TwitterStream = mock {
                on { onStatus(any()) } doReturn it
            }
            val hashTags = listOf("#KKUG", "#ITWeekend")
            TwitterWall(ts, hashTags)

            verify(ts).filter(*hashTags.toTypedArray())
        }

        test("TwitterStream onStatus is called") {
            val ts: TwitterStream = mock {
                on { onStatus(any()) } doReturn it
            }
            val hashTags = listOf("#KKUG", "#ITWeekend")
            TwitterWall(ts, hashTags)

            verify(ts).onStatus(any())
        }

//        test("TwitterStream onStatus is with update buffer method") {
//            val ts: TwitterStream = mock{
//                on{onStatus(any())} doReturn it
//            }
//            val hashTags = listOf("#KKUG", "#ITWeekend")
//            val twitterWall = TwitterWall(ts, hashTags)
//
//            verify(ts).onStatus {twitterWall.updateBuffer(any())}
//        }

        test("updateBuffer and tweets are working correctly") {
            val twitterWall = TwitterWall(twitterStream, listOf("#abc"), 2)
            with(twitterWall) {
                updateBuffer("msg1")
                updateBuffer("msg2")
                updateBuffer("msg3")
            }

            twitterWall.tweets() shouldBe listOf("msg3", "msg2")
        }
    }
}