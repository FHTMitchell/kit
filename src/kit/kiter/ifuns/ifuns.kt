package kit.kiter.ifuns

val <T> Iterable<T>.seq get() = this.asSequence()