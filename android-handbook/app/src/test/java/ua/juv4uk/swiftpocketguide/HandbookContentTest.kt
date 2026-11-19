package ua.juv4uk.swiftpocketguide

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class HandbookContentTest {
    @Test fun topicIdsAreUnique() {
        val ids = HandbookContent.topics.map { it.id }
        assertEquals(ids.size, ids.toSet().size)
    }

    @Test fun everyTopicHasAnExplanationAndExample() {
        assertEquals(27, HandbookContent.topics.size)
        HandbookContent.topics.forEach {
            assertTrue(it.explanation.isNotBlank())
            assertTrue(it.code.isNotBlank())
            assertTrue(it.takeaway.isNotBlank())
        }
    }

    @Test fun advancedTopicsArePresent() {
        val ids = HandbookContent.topics.map { it.id }.toSet()
        assertTrue("closures" in ids)
        assertTrue("generics" in ids)
        assertTrue("codable" in ids)
        assertTrue("networking" in ids)
    }

    @Test fun quizHasQuestionsAndValidAnswerIndexes() {
        assertEquals(13, QuizContent.questions.size)
        QuizContent.questions.forEach { question ->
            assertTrue(question.options.isNotEmpty())
            assertTrue(question.correctIndex in question.options.indices)
            assertTrue(question.explanation.isNotBlank())
        }
    }

    @Test fun glossaryContainsCoreAndPracticalSwiftTerms() {
        val terms = HandbookContent.glossary.map { it.term }.toSet()
        assertTrue("Optional" in terms)
        assertTrue("Тип" in terms)
        assertTrue("Протокол" in terms)
        assertTrue("Closure" in terms)
        assertTrue("Codable" in terms)
        assertTrue("URLSession" in terms)
    }
}
