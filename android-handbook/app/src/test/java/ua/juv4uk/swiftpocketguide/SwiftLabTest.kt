package ua.juv4uk.swiftpocketguide

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SwiftLabTest {
    @Test fun starterCodePassesItsExerciseChecks() {
        SwiftLabContent.exercises.forEach { exercise ->
            val result = SwiftLabAnalyzer.analyze(exercise, exercise.starterCode)
            assertTrue("${exercise.id} should pass", result.passed)
        }
    }

    @Test fun emptyCodeIsRejected() {
        val exercise = SwiftLabContent.exercises.first()
        val result = SwiftLabAnalyzer.analyze(exercise, "   ")
        assertFalse(result.passed)
        assertTrue(result.message.contains("порожній"))
    }

    @Test fun incompleteCodeExplainsMissingRequirements() {
        val exercise = SwiftLabContent.exercises.first()
        val result = SwiftLabAnalyzer.analyze(exercise, "let name = \"Оля\"")
        assertFalse(result.passed)
        assertTrue(result.details.any { it.contains("print") && it.contains("ще не виконано") })
    }
}
