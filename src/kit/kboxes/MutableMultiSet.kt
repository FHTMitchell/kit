package kit.kboxes

interface MutableMultiSet<E> : MultiSet<E> {

    override fun copy(): MutableMultiSet<E>

    operator fun minusAssign(other: MultiSet<E>)

}