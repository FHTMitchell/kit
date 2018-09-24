package kit.kboxes.multisets

fun <T> Iterable<T>.toMultiSet(): MultiSet<T> = Counter(this)

fun <T> Map<T, Int>.toMultiSet(): MultiSet<T> = Counter(this.toList())
