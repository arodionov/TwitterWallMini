package ua.jug.tw

import twitter4j.TwitterStream
import ua.jug.buffer.BoundedBuffer

class TwitterWall(stream: TwitterStream, val tags: List<String>, val size: Int = 10) {

    private val buffer = BoundedBuffer<String>(size)

    init {
        val filteredTags: List<String> = tags.filter { it.isNotBlank() }
        if (filteredTags.isEmpty()) throw IllegalArgumentException("Empty or blank tags")
        stream
                .onStatus {updateBuffer(it.text) }
                .filter(*filteredTags.toTypedArray())
    }

    fun tweets(): List<String> = buffer.elements()

    internal fun updateBuffer(msg: String) = buffer.put(msg)

}