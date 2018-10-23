package kit.kpath

import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.OpenOption
import java.nio.file.Paths
import java.nio.file.attribute.FileAttribute
import java.nio.file.Path as NioPath

open class KPath(nioPath: NioPath) {

    // properties

    private val path: NioPath = nioPath

    val parent: KPath
        get() = this.path.parent.toKPath()

    val root: KPath
        get() = this.path.root.toKPath()

    val extension: String = extRegex.find(this.toString())?.groups?.get(1)?.value ?: ""

    // constructors

    constructor(first: String, vararg others: String): this(Paths.get(first, *others))

    // companion (statics)

    companion object {

        val separator: String = File.separator!!

        val cwd: KPath
            get() = KPath(System.getProperty("user.dir")!!)

        val home: KPath
            get() = KPath(System.getProperty("user.home")!!)

        private val extRegex: Regex = "[a-zA-Z0-9_\\-][.]([^\\\\/]+)$".toRegex()

    }

    // overrides

    override fun toString(): String = this.path.toString()

    override fun hashCode(): Int = this.path.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (this.javaClass != other.javaClass) return false
        return this.path == (other as KPath).path
    }

    // functions

    fun toNioPath(): NioPath = this.path

    fun toIoFile(): File = this.path.toFile()!!

    fun exists() = Files.exists(this.path)

    fun join(vararg others: KPath) = this.join(*others.map { it.toString() }.toTypedArray())

    fun join(vararg others: String) = KPath(Paths.get(this.toString(), *others))

    fun isDir() = Files.isDirectory(this.path)

    fun isFile() = Files.isRegularFile(this.path)

    fun makeDir(vararg attrs: FileAttribute<*>) {
        Files.createDirectory(this.path, *attrs)
    }

    fun iterDir(filter: (KPath) -> Boolean = { true }): Sequence<KPath> =
                Files.newDirectoryStream(this.path)!!.asSequence().map { KPath(it) }.filter(filter)

    fun iterDirOrNull(filter: (KPath) -> Boolean = { true }): Sequence<KPath>? =
            if (this.isDir()) this.iterDir(filter) else null

    fun iterParts(): Sequence<KPath> = this.path.asSequence().map { KPath(it!!) }

    fun readFile(vararg options: OpenOption): OutputStream = Files.newOutputStream(this.path, *options)!!

    fun writeFile(vararg options: OpenOption): InputStream = Files.newInputStream(this.path, *options)!!

    fun readFileOrNull(vararg options: OpenOption): OutputStream? =
            try { this.readFile(*options) } catch (e: IOException) { null }

    fun writeFileOrNull(vararg options: OpenOption): InputStream? =
            try { this.writeFile(*options) } catch (e: IOException) { null }


    // operators

    operator fun div(other: KPath) = this.join(other)

    operator fun div(other: String) = this.join(other)

}