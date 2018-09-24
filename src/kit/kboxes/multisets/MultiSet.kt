package kit.kboxes.multisets

import kit.kboxes.containers.Container

interface MultiSet<E> : Iterable<Count<E>>, Container<E> {

    // properties

    /** Every element - appearing as many times as it has counts */
    val elements: List<E>

    /** Every unique element */
    val support: Set<E>

    /** The number of (non-unique) elements i.e. the sum of every count */
    val totalSize: Int
        get() = this.iterator().asSequence().map { it.count }.sum()

    /** The total number of *unique* elements */
    override val size
        get() = this.support.size

    // methods

    /**
     *  The first n highest counts in the MultiSet in descending order.
     *  If `n == null`, every element is given.
     */
    fun mostCommon(n: Int? = null): List<Count<E>>

    /**
     * The multiset union -- where each element from this and other retains the *maximum* count
     */
    fun union(other: MultiSet<E>): MultiSet<E>

    /**
     * The multiset intersection -- where each element from this and other retains the *minimum* count
     */
    fun intersection(other: MultiSet<E>): MultiSet<E>

    // comparisons

    /**
     * Equivalent of the Set subset operator (<=)
     * Is true if every unique element has a lower or equal count in `this` than `other`
     */
    infix fun isIncludedIn(other: MultiSet<E>): Boolean = this.all { it.count <= other[it.element] }

    /**
     * Equivalent of the Set subset operator in reverse (>=)
     * Is true if every unique element has a greater or equal count in `this` than `other`
     */
    infix fun includes(other: MultiSet<E>): Boolean = other.all { it.count <= this[it.element] }

    /**
     * Equivalent of the Set trueSubset operator (<)
     * Is true if every unique element has a lower count in `this` than `other`
     */
    infix fun isFullyIncludedIn(other: MultiSet<E>): Boolean = this != other && this isIncludedIn other

    /**
     * Equivalent of the Set trueSubset operator in reverse (>)
     * Is true if every unique element has a greater count in `this` than `other`
     */
    infix fun fullyIncludes(other: MultiSet<E>): Boolean = this != other && this includes other

    // copies and to
    // toList and toSet inherited from Iterable


    fun toMultiSet(): MultiSet<E> = Counter(this)  // no immutable implementations yet

    fun toMutableMultiSet(): MutableMultiSet<E> = Counter(this)

    fun toMap(): Map<E, Int> = this.iterator().asSequence().map { (k, v) -> Pair(k, v) }.toMap()

    // operators

    /**
     * Get the count for this element
     */
    operator fun get(element: E): Int

    /**
     * The multiset sum -- where each element has a count equal to the sum of counts from `this` and `other`
     */
    operator fun plus(other: MultiSet<E>): MultiSet<E>

    /**
     * The multiset difference -- where each element has a count equal to `this` count minus the `other` count
     * (with a minimum value of 0)
     */
    operator fun minus(other: MultiSet<E>): MultiSet<E>

    override operator fun contains(element: E): Boolean = element in this.support

}
