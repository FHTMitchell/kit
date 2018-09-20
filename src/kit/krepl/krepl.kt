package kit.krepl

/**
 *  Passes the object to f (equivalent to f(this))
 *
 *  (3.0).pipe(math::sqrt) == math.sqrt(3.0)
 *  "hello" pipe ::println == println("hello")
 */
infix fun <T, R> T.pipe(f: (T) -> R): R = f(this)