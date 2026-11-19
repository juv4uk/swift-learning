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
        assertTrue(HandbookContent.topics.isNotEmpty())
        HandbookContent.topics.forEach {
            assertTrue(it.explanation.isNotBlank())
            assertTrue(it.code.isNotBlank())
            assertTrue(it.takeaway.isNotBlank())
        }
    }
    @Test fun glossaryContainsCoreSwiftTerms() {
        val terms = HandbookContent.glossary.map { it.term }.toSet()
        assertTrue("Optional" in terms)
        assertTrue("Тип" in terms)
        assertTrue("Протокол" in terms)
    }
}
