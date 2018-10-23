package kit.kboxes.multisets

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

internal class CounterTest {

    val c = Counter.ofCounts(1 to 3, 2 to 2, 3 to 1).toMultiSet()
    val d = Counter.ofCounts(1 to 2, 2 to 1).toMultiSet()

    @Test
    fun `test toMultiSet and equals`() {
        val dd = d.toMultiSet()
        assertEquals(d, dd)
    }

    @Test
    fun `test removeAll`() {
        val mutD = d.toMutableMultiSet()
        mutD.removeAll(d.elements)
        assertEquals(Counter.of<Int>(), mutD)
    }

}