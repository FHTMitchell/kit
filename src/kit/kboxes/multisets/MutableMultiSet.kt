package kit.kboxes.multisets

import kit.kboxes.containers.MutableContainer

interface MutableMultiSet<E> : MultiSet<E>, MutableContainer<E> {

    /**
     * Add the element `times` times.
     * Equivalent to:
     *     for (_ in 1..times) { this.add(element) }
     *
     * Returns false if the element doesn't exist in the MultiSet upon completion
     * (possible if `times <= 0`)
     */
    fun add(element: E, times: Int): Boolean

    /**
     * Like plus but done in-place
     */
    operator fun plusAssign(other: MultiSet<E>)

    /**
     * Like minus but done in place
     */
    operator fun minusAssign(other: MultiSet<E>)

    // Maybe add unionInPlace, intersectionInPlace at a later date?

}