package kit.kboxes.containers

/**
 * Same interface as Collection<E> but without an iterator
 */
interface Container<E> {

    /**
     * The size of the container
     */
    val size: Int

    /**
     * true if the container is empty (`size == 0`)
     */
    fun isEmpty(): Boolean = this.size < 1

    /**
     * true if element in container
     */
    operator fun contains(element: E): Boolean

    /**
     * true if all elements in container
     */
    fun containsAll(elements: Iterable<E>): Boolean {
        return elements.all { it in this }
    }

}