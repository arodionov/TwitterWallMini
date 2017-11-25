package ua.kug.buffer

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.matchers.shouldThrow
import org.junit.Test

class BoundedBufferTest {

    @Test
    fun newBuffer() {
        BoundedBuffer<Any>()
    }

    @Test
    fun sizeByDefault() {
        val boundedBuffer: BoundedBuffer<Any> = BoundedBuffer()
        boundedBuffer.size shouldBe 10
    }

    @Test
    fun sizeIsSpecified() {
        val boundedBuffer: BoundedBuffer<Any> = BoundedBuffer(3)
        boundedBuffer.size shouldBe 3
    }

    @Test
    fun exceptionWhenSizeIsNotPositive() {
        shouldThrow<IllegalArgumentException> {
            BoundedBuffer<Any>(0)
        }.message shouldBe "Negative value"
    }

    @Test
    fun valuesIsEmpty() {
        val boundedBuffer = BoundedBuffer<Any>()
        val values: List<Any>? = boundedBuffer.values()
        values shouldBe emptyList<Any>()
    }

    @Test
    fun putNull() {
        val boundedBuffer = BoundedBuffer<String>()
        //boundedBuffer.put(null)
    }

    @Test
    fun putValue() {
        val boundedBuffer = BoundedBuffer<String>()
        boundedBuffer.put("abc")
    }

    @Test
    fun putValuesAndCheck() {
        val boundedBuffer = BoundedBuffer<String>()
        with(boundedBuffer) {
            put("a")
            put("b")
            put("c")
        }
        boundedBuffer.values() shouldEqual listOf("c", "b", "a")
    }

    @Test
    fun putValuesShouldDiscardTheOldOne() {
        val boundedBuffer = BoundedBuffer<String>(2)
        with(boundedBuffer) {
            put("a")
            put("b")
            put("c")
        }
        boundedBuffer.values() shouldEqual listOf("c", "b")
    }
}