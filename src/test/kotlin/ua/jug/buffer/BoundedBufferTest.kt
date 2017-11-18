package ua.jug.buffer

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.matchers.shouldThrow
import io.kotlintest.specs.FunSpec

class BoundedBufferTest : FunSpec() {

    init {

        test("Create default buffer") {
            BoundedBuffer<Any>()
        }


        test("Default buffer size should be 10") {
            val boundedBuffer = BoundedBuffer<Any>()
            boundedBuffer.size shouldEqual 10
        }

        test("Create buffer with special size") {
            val size = 5
            val boundedBuffer = BoundedBuffer<Any>(size)
            boundedBuffer.size shouldEqual size
        }

        test("Size should be positive") {
            val exception = shouldThrow<IllegalArgumentException> {
                BoundedBuffer<Any>(-5)
            }
            exception.message shouldBe "Negative size"
        }


        test("put element") {
            val boundedBuffer = BoundedBuffer<String>()
            boundedBuffer.put("a")
        }

        test("get elemets") {
            val boundedBuffer = BoundedBuffer<String>()
            boundedBuffer.put("a")
            boundedBuffer.put("b")

            boundedBuffer.elements() shouldBe listOf("b", "a")
        }

        test("push out old element") {
            val boundedBuffer = BoundedBuffer<String>(2)
            boundedBuffer.put("a")
            boundedBuffer.put("b")
            boundedBuffer.put("c")

            boundedBuffer.elements() shouldBe listOf("c", "b")
        }

    }

}