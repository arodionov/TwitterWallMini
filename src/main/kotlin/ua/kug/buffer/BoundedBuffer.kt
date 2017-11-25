package ua.kug.buffer

class BoundedBuffer<T>(override val size: Int = 10) : Buffer<T> {

    private val buffer = mutableListOf<T>()

    init {
        if (size <= 0) throw IllegalArgumentException("Negative value")
    }

    override fun values(): List<T> = buffer.toList().asReversed()

    override fun put(value: T) {
        if (buffer.size == size) buffer.removeAt(0)
        buffer.add(value)
    }

}