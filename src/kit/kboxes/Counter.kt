@file:Suppress("MemberVisibilityCanBePrivate")

package kit.kboxes

import kit.kiter.repeat
import java.util.*

open class Counter<T>() : MutableMultiSet<T> {

    // constructors

    constructor(itr: Iterable<T>) : this() {
        this.addAllTo(itr)
    }

    constructor(pairs: Collection<Pair<T, Int>>) : this() {
        this.mapping.putAll(pairs)
    }

    // properties

    protected val mapping: MutableMap<T, Int> = HashMap()

    override val size: Int
        get() = this.mapping.size

    override val keys: Set<T>
        get() = this.mapping.keys

    override val elements: List<T>
        get() = this.asSequence().map { (k, v) -> k.repeat(v) } .flatten().toList()


    // functions

    override fun toString(): String {
        return "Counter${this.mapping}"
    }

    override fun addTo(obj: T) {
        // this[obj] += 1 fails (bug)
        this.addTo(obj, 1)
    }
    override fun addTo(obj: T, times: Int) {
        val new = this[obj] + times
        when (new) {
            0 -> this.mapping.remove(obj)
            else -> this.mapping[obj] = new
        }
    }

    override fun addAllTo(itr: Iterable<T>) {
        this.addTo(itr.first())
        itr.forEach(this::addTo)
    }

    override fun iterator(): Iterator<Pair<T, Int>> {
        return this.mapping.asSequence().map { (k, v) -> Pair(k, v) }.iterator()
    }

    override operator fun get(key: T): Int {
        return this.mapping.getOrDefault(key, 0)
    }

    override fun mostCommon(n: Int?) : List<Pair<T, Int>> {
        val sorted = this.toList().sortedByDescending { it.second }
        return if (n == null) sorted else sorted.take(n)
    }

    override operator fun minus(other: MultiSet<T>): Counter<T> {
        val new = Counter<T>()
        for (key in this.keys.union(other.keys)) {
            new.addTo(key, this[key] - other[key])
        }
        return new
    }

    override fun minusAssign(other: MultiSet<T>) {

        for ((key, value) in other.iterator()) {
            this.addTo(key, -value)
        }
    }

    override fun copy(): Counter<T> {
        return Counter(this.elements)
    }

}