package kit.kboxes.option

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ResultTest {

    class ZeroDivisionException(msg: String = ""): ArithmeticException(msg)

    fun div(a: Double, b: Double): Result<Double, ZeroDivisionException> = when (b) {
        0.0 -> Err(ZeroDivisionException("Cannot divide by zero"))
        else -> Ok(a / b)
    }

    @Test
    fun `test double division with result`() {

        val ok1 = div(4.5, 3.0)
        val ok2 = div(-6.28, 3.14)
        val err = div(100.0, 0.0)

        assertTrue { ok1 is Ok }
        assertTrue { ok2 is Ok }
        assertTrue { err is Err }
    }



}