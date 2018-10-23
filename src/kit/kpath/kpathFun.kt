
package kit.kpath

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

fun File.toKPath() = KPath(this.toString())

fun Path.toKPath() = KPath(this)



