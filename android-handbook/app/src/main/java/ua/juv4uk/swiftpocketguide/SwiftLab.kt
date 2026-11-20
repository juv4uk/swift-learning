package ua.juv4uk.swiftpocketguide

/** A small, honest offline checker for beginner exercises. It is not a Swift compiler. */
data class SwiftLabExercise(
    val id: String,
    val title: String,
    val instruction: String,
    val starterCode: String,
    val expectedOutput: String,
    val checks: List<SwiftLabCheck>
)

data class SwiftLabCheck(val label: String, val passes: (String) -> Boolean)

data class SwiftLabResult(
    val passed: Boolean,
    val message: String,
    val details: List<String>
)

object SwiftLabContent {
    val exercises = listOf(
        SwiftLabExercise(
            id = "hello",
            title = "Привітання",
            instruction = "Створи константу name і виведи привітання через print.",
            starterCode = "let name = \"Оля\"\nprint(\"Привіт, \\(name)!\")",
            expectedOutput = "Привіт, Оля!",
            checks = listOf(
                SwiftLabCheck("Є константа name", { Regex("\\blet\\s+name\\b").containsMatchIn(it) }),
                SwiftLabCheck("Є виклик print", { Regex("\\bprint\\s*\\(").containsMatchIn(it) }),
                SwiftLabCheck("Є текстове значення", { Regex("name\\s*=\\s*\"[^\"]+\"").containsMatchIn(it) })
            )
        ),
        SwiftLabExercise(
            id = "variables",
            title = "Змінна і число",
            instruction = "Створи var minutes, збільш значення та виведи його.",
            starterCode = "var minutes = 30\nminutes += 15\nprint(minutes)",
            expectedOutput = "45",
            checks = listOf(
                SwiftLabCheck("Є змінна minutes", { Regex("\\bvar\\s+minutes\\b").containsMatchIn(it) }),
                SwiftLabCheck("Значення змінюється", { Regex("minutes\\s*(\\+=|-=|=)").containsMatchIn(it) }),
                SwiftLabCheck("Результат виводиться", { Regex("\\bprint\\s*\\(\\s*minutes").containsMatchIn(it) })
            )
        ),
        SwiftLabExercise(
            id = "conditions",
            title = "Умова",
            instruction = "Перевір через if, чи залишилися хвилини до навчальної цілі.",
            starterCode = "let completed = 3\nlet target = 5\n\nif completed < target {\n    print(\"Потрібно ще вчитися\")\n} else {\n    print(\"Ціль виконано\")\n}",
            expectedOutput = "Потрібно ще вчитися",
            checks = listOf(
                SwiftLabCheck("Є if", { Regex("\\bif\\b").containsMatchIn(it) }),
                SwiftLabCheck("Є порівняння", { Regex("(==|!=|<=|>=|<|>)").containsMatchIn(it) }),
                SwiftLabCheck("Є альтернативна гілка", { Regex("\\belse\\b").containsMatchIn(it) }),
                SwiftLabCheck("Є виведення", { Regex("\\bprint\\s*\\(").containsMatchIn(it) })
            )
        ),
        SwiftLabExercise(
            id = "collections",
            title = "Масив тем",
            instruction = "Створи масив topics і пройди його циклом for-in.",
            starterCode = "let topics = [\"let і var\", \"Функції\", \"Optional\"]\n\nfor topic in topics {\n    print(topic)\n}",
            expectedOutput = "let і var\nФункції\nOptional",
            checks = listOf(
                SwiftLabCheck("Є масив topics", { Regex("\\b(let|var)\\s+topics\\s*=\\s*\\[").containsMatchIn(it) }),
                SwiftLabCheck("Є цикл for-in", { Regex("\\bfor\\s+\\w+\\s+in\\s+topics").containsMatchIn(it) }),
                SwiftLabCheck("Цикл щось виводить", { Regex("for[\\s\\S]*print\\s*\\(").containsMatchIn(it) })
            )
        )
    )
}

object SwiftLabAnalyzer {
    fun analyze(exercise: SwiftLabExercise, code: String): SwiftLabResult {
        val normalized = code.trim()
        if (normalized.isBlank()) {
            return SwiftLabResult(false, "Код порожній. Напиши рішення або встав шаблон і зміни його.", emptyList())
        }
        val details = exercise.checks.map { check ->
            if (check.passes(normalized)) "✓ ${check.label}" else "○ ${check.label} — ще не виконано"
        }
        val passed = exercise.checks.all { it.passes(normalized) }
        val message = if (passed) {
            "Усі навчальні вимоги виконано. Це структурна перевірка, а не повна компіляція Swift."
        } else {
            "Є що допрацювати. Перевір підказки нижче й запусти перевірку ще раз."
        }
        return SwiftLabResult(passed, message, details)
    }
}
