package kit.kboxes.containers

/**
 * Same as MutableCollection but without an iterator (and retainAll since assumes an iterator)
 */
interface MutableContainer<E> : Container<E> {

    /**
     * Adds the specified element to the collection.
     *
     * Returns true on success
     */
    fun add(element: E): Boolean

    /**
     * Adds all of the elements in the specified collection to this collection.
     *
     * Returns true on any success
     */
    fun addAll(elements: Iterable<E>) = elements.map(this::add).any { it }

    /**
     * Remove all elements from container
     */
    fun clear()

    /**
     * Remove a *single* instance of element from container -- returns true if element found, otherwise false.
     */
    fun remove(element: E): Boolean

    /**
     * Removes all of this container's elements that are also contained in the specified collection.
     */
    fun removeAll(elements: Iterable<E>): Boolean = elements.map(this::remove).any { it }
}