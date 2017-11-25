package ua.kug.tw

import twitter4j.TwitterStream
import ua.kug.buffer.BoundedBuffer

class TwitterWall(val twitterStream: TwitterStream, val hashTags: List<String>, val size: Int = 10) {

    private val buffer = BoundedBuffer<String>(size)

    init {
        val filteredTags = hashTags.filter { it.isNotBlank() && it.length > 2 }
        if (filteredTags.isEmpty()) throw IllegalArgumentException("HashTags should not be empty or blank")
        twitterStream
                .onStatus { updateBuffer(it.text) }
                .filter(*filteredTags.toTypedArray())
    }

    fun tweets(): List<String> = buffer.values()

    internal fun updateBuffer(msg: String) {
        buffer.put(msg)
    }

}