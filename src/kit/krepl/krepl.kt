package kit.krepl

fun log(vararg arg: Any?, sep: String = " ", end: String = "\n") {
    arg.forEach {
        print(it)
        print(sep)
    }
    print(end)
}

