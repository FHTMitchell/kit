@file:Suppress("MemberVisibilityCanBePrivate")

package kit.kboxes.multisets

import kit.kiter.repeat
import java.util.*
import kotlin.math.max
import kotlin.math.min

open class Counter<T>() : MutableMultiSet<T> {

    // constructors

    constructor(elements: Iterable<T>) : this() {
        this.addAll(elements)
    }

    constructor(pairs: Collection<Pair<T, Int>>) : this() {
        this.mapping.putAll(pairs)
    }

    constructor(multiSet: MultiSet<T>) : this(multiSet.elements)

    // statics

    companion object {
        fun <T> of(vararg elements: T): Counter<T> {
            return Counter(elements.toList())
        }

        fun <T> ofCounts(vararg pairs: Pair<T, Int>): Counter<T> {
            return Counter(pairs.toList())
        }
    }

    // properties

    protected val mapping: MutableMap<T, Int> = HashMap()

    override val size: Int
        get() = this.mapping.size

    override val elements: List<T>
        get() = this.asSequence().flatMap { (k, v) -> k.repeat(v) }.toList() // only Sequence has flatMap

    override val support: Set<T>
        get() = this.mapping.keys.toSet()  // copy


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is MultiSet<*>) {
            if (other is Counter<*>) {
                return this.mapping == other.mapping
            }
            // fallback
            return this.toMap() == other.toMap()
        }
        return false
    }

    override fun toString() = "Counter${this.mapping}"

    override fun add(element: T): Boolean {
        // this[element] += 1 fails (bug)
        this.add(element, 1)
        return true
    }

    override fun add(element: T, times: Int): Boolean {
        val sum = this[element] + times
        return if (sum > 0) {
            this.mapping[element] = sum
            true
        } else {
            this.mapping.remove(element)
            false
        }

    }

    override fun clear() = this.mapping.clear()

    /**
     * Removes one count of element and returns `true` (if `this.get(element) > 0`)
     * Otherwise returns `false`
     */
    override fun remove(element: T): Boolean {
        if (this.mapping[element] == null)
            return false
        this.add(element, -1)
        return true
    }

    override fun iterator(): Iterator<Count<T>> {
        return this.mapping.asSequence().map { (k, v) -> Count(k, v) }.iterator()
    }

    override fun union(other: MultiSet<T>): Counter<T> {
        val new = Counter<T>()
        this.support.union(other.support).forEach {
            new.add(it, max(this[it], other[it]))
        }
        return new
    }

    override fun intersection(other: MultiSet<T>): Counter<T> {
        val new = Counter<T>()
        this.support.union(other.support).forEach {
            new.add(it, min(this[it], other[it]))
        }
        return new
    }

    override fun mostCommon(n: Int?): List<Count<T>> {
        val sorted = this.asSequence().sortedByDescending { it.count }.toList()
        return if (n == null) sorted else sorted.take(n)
    }


    // operators

    override operator fun get(element: T): Int {
        return this.mapping.getOrDefault(element, 0)
    }

    override operator fun plus(other: MultiSet<T>): MultiSet<T> {
        val new = Counter<T>()
        this.support.union(other.support).forEach {

            new.add(it, this[it] + other[it])
        }
        return new
    }

    override operator fun minus(other: MultiSet<T>): Counter<T> {
        val new = Counter<T>()
        this.support.union(other.support).forEach {
            // < 0 will be removed by add
            new.add(it, this[it] - other[it])
        }
        return new
    }

    override operator fun plusAssign(other: MultiSet<T>) {
        other.forEach { this.add(it.element, it.count) }
    }

    override operator fun minusAssign(other: MultiSet<T>) {
        other.forEach { this.add(it.element, -it.count) }
    }

    // auotgenerated methods

    override fun hashCode(): Int {
        return mapping.hashCode()
    }

}