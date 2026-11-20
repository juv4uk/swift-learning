package ua.juv4uk.swiftpocketguide

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SwiftLabInterpreterTest {
    @Test fun allStarterExamplesRunLocally() {
        SwiftLabContent.exercises.forEach { exercise ->
            val result = SwiftLabInterpreter.execute(exercise.starterCode)
            assertTrue("${exercise.id}: ${result.error}", result.success)
            assertTrue(result.output.isNotBlank())
        }
    }

    @Test fun arithmeticAndInterpolationProduceOutput() {
        val result = SwiftLabInterpreter.execute(
            "let name = \"Оля\"\nlet answer = 20 + 22\nprint(\"Привіт, \\(name)! \\(answer)\")"
        )
        assertTrue(result.success)
        assertEquals("Привіт, Оля! 42", result.output)
    }

    @Test fun unsupportedSyntaxReturnsAnEducationalError() {
        val result = SwiftLabInterpreter.execute("struct Student {}")
        assertFalse(result.success)
        assertTrue(result.error?.contains("не підтримується") == true)
    }
}
