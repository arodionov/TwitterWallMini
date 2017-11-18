package ua.jug.buffer

interface Buffer<T> {
    fun elements(): List<T>
    fun put(elem: T)
}