package ua.jug.buffer

class BoundedBuffer<T>(val size: Int = 10) : Buffer<T> {

    private val values = mutableListOf<T>()

    init {
        val res = if (size <= 0) throw IllegalArgumentException("Negative size") else size
    }

    override fun put(elem: T) {
        if (values.size == size) values.removeAt(0)
        values.add(elem)
    }

    override fun elements() = values.toList().asReversed()

}