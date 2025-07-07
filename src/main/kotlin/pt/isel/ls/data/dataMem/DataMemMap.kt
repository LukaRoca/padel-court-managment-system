package pt.isel.ls.data.dataMem

import java.util.concurrent.atomic.AtomicInteger

/**
 * Organizes the memory in a HashMap
 */
class DataMemMap<T> {
    val map =
        object : HashMap<Int, T>() {
            override fun put(
                key: Int,
                value: T,
            ): T? {
                nextId.incrementAndGet()
                return super.put(key, value)
            }
        }

    fun clear() {
        map.clear()
    }
    var nextId = AtomicInteger(1)
        private set
}