package kit.kboxes.sets

infix fun <T> Set<T>.isSubset(other: Set<T>) = this.all { it in other }

infix fun <T> Set<T>.isTrueSubset(other: Set<T>) = this != other && this isSubset other