package ua.juv4uk.swiftpocketguide

/**
 * A deliberately small interpreter for the first Swift lessons.
 * It is an educational runner, not the Swift compiler.
 */
sealed class SwiftLabValue {
    data class Number(val value: Int) : SwiftLabValue()
    data class Text(val value: String) : SwiftLabValue()
    data class Boolean(val value: Boolean) : SwiftLabValue()
    data class ArrayValue(val values: List<SwiftLabValue>) : SwiftLabValue()
}

data class SwiftLabExecutionResult(val success: Boolean, val output: String, val error: String? = null)

object SwiftLabInterpreter {
    fun execute(code: String): SwiftLabExecutionResult {
        return try {
            val environment = linkedMapOf<String, SwiftLabValue>()
            val output = mutableListOf<String>()
            executeLines(code.lines(), environment, output)
            SwiftLabExecutionResult(true, output.joinToString("\n").ifBlank { "(програма нічого не вивела)" })
        } catch (error: LabRuntimeException) {
            SwiftLabExecutionResult(false, "", error.message)
        } catch (_: Exception) {
            SwiftLabExecutionResult(false, "", "Не вдалося виконати цей приклад. Спробуй простішу конструкцію з шаблонів.")
        }
    }

    private fun executeLines(
        source: List<String>,
        environment: MutableMap<String, SwiftLabValue>,
        output: MutableList<String>
    ) {
        var index = 0
        while (index < source.size) {
            val line = source[index].trim()
            when {
                line.isBlank() || line.startsWith("//") || line == "{" || line == "}" -> index++
                line.startsWith("for ") -> {
                    val header = Regex("for\\s+(\\w+)\\s+in\\s+(\\w+)\\s*\\{").matchEntire(line)
                        ?: fail("Очікується цикл у форматі for item in items { ... }.")
                    val body = collectBlock(source, index)
                    val items = environment[header.groupValues[2]] as? SwiftLabValue.ArrayValue
                        ?: fail("Масив '${header.groupValues[2]}' не знайдено.")
                    items.values.forEach { item ->
                        environment[header.groupValues[1]] = item
                        executeLines(body.lines, environment, output)
                    }
                    index += body.consumed
                }
                line.startsWith("if ") -> {
                    val condition = Regex("if\\s+(.+)\\s*\\{").matchEntire(line)
                        ?: fail("Очікується умова у форматі if condition { ... }.")
                    val first = collectBlock(source, index)
                    var next = index + first.consumed
                    var second: Block? = null
                    if (next < source.size && source[next].trim().startsWith("else")) {
                        second = collectBlock(source, next)
                        next += second.consumed
                    }
                    val chosen = if (evaluateCondition(condition.groupValues[1], environment)) first.lines else second?.lines
                    if (chosen != null) executeLines(chosen, environment, output)
                    index = next
                }
                line.startsWith("print") -> {
                    val match = Regex("print\\s*\\((.*)\\)").matchEntire(line)
                        ?: fail("Перевір дужки у виклику print(...).")
                    output += evaluate(match.groupValues[1], environment)
                    index++
                }
                Regex("(?:let|var)\\s+\\w+\\s*=.*").matches(line) -> {
                    val match = Regex("(?:let|var)\\s+(\\w+)\\s*=\\s*(.*)").matchEntire(line)
                        ?: fail("Не вдалося прочитати оголошення змінної.")
                    environment[match.groupValues[1]] = evaluateValue(match.groupValues[2], environment)
                    index++
                }
                Regex("\\w+\\s*(\\+=|-=|=).*").matches(line) -> {
                    val match = Regex("(\\w+)\\s*(\\+=|-=|=)\\s*(.*)").matchEntire(line)
                        ?: fail("Не вдалося прочитати присвоєння.")
                    val name = match.groupValues[1]
                    val current = environment[name] ?: fail("Змінну '$name' ще не оголошено.")
                    val value = evaluateValue(match.groupValues[3], environment)
                    environment[name] = when (match.groupValues[2]) {
                        "=" -> value
                        "+=" -> SwiftLabValue.Number(asNumber(current) + asNumber(value))
                        "-=" -> SwiftLabValue.Number(asNumber(current) - asNumber(value))
                        else -> fail("Оператор не підтримується.")
                    }
                    index++
                }
                else -> fail("Рядок не підтримується в навчальному запуску: $line")
            }
        }
    }

    private fun evaluateCondition(expression: String, environment: Map<String, SwiftLabValue>): Boolean {
        val match = Regex("(.+?)\\s*(==|!=|<=|>=|<|>)\\s*(.+)").matchEntire(expression.trim())
            ?: fail("Підтримуються прості порівняння: <, >, ==, !=, <=, >=.")
        val left = evaluateValue(match.groupValues[1], environment)
        val right = evaluateValue(match.groupValues[3], environment)
        val comparison = when {
            left is SwiftLabValue.Number && right is SwiftLabValue.Number -> left.value.compareTo(right.value)
            else -> asText(left).compareTo(asText(right))
        }
        return when (match.groupValues[2]) {
            "==" -> comparison == 0
            "!=" -> comparison != 0
            "<" -> comparison < 0
            ">" -> comparison > 0
            "<=" -> comparison <= 0
            ">=" -> comparison >= 0
            else -> false
        }
    }

    private fun evaluate(expression: String, environment: Map<String, SwiftLabValue>): String =
        asText(evaluateValue(expression.trim(), environment))

    private fun evaluateValue(expression: String, environment: Map<String, SwiftLabValue>): SwiftLabValue {
        val value = expression.trim()
        if (value.startsWith("\"") && value.endsWith("\"")) {
            val text = value.substring(1, value.length - 1).replace(Regex("\\\\((\\w+)\\)")) { match ->
                asText(environment[match.groupValues[1]] ?: fail("Змінну '${match.groupValues[1]}' не знайдено."))
            }
            return SwiftLabValue.Text(text)
        }
        if (value.matches(Regex("-?\\d+"))) return SwiftLabValue.Number(value.toInt())
        if (value == "true" || value == "false") return SwiftLabValue.Boolean(value.toBoolean())
        if (value.startsWith("[") && value.endsWith("]")) {
            val parts = value.substring(1, value.length - 1).splitCommaSeparated()
            return SwiftLabValue.ArrayValue(parts.filter { it.isNotBlank() }.map { evaluateValue(it, environment) })
        }
        val arithmetic = Regex("(.+?)\\s*([+-])\\s*(.+)").matchEntire(value)
        if (arithmetic != null) {
            val left = asNumber(evaluateValue(arithmetic.groupValues[1], environment))
            val right = asNumber(evaluateValue(arithmetic.groupValues[3], environment))
            return SwiftLabValue.Number(if (arithmetic.groupValues[2] == "+") left + right else left - right)
        }
        return environment[value] ?: fail("Значення '$value' не знайдено.")
    }

    private fun asNumber(value: SwiftLabValue): Int = (value as? SwiftLabValue.Number)?.value
        ?: fail("Ця операція очікує число.")

    private fun asText(value: SwiftLabValue): String = when (value) {
        is SwiftLabValue.Number -> value.value.toString()
        is SwiftLabValue.Text -> value.value
        is SwiftLabValue.Boolean -> value.value.toString()
        is SwiftLabValue.ArrayValue -> value.values.joinToString(", ") { asText(it) }
    }

    private data class Block(val lines: List<String>, val consumed: Int)

    private fun collectBlock(source: List<String>, start: Int): Block {
        var depth = 0
        val body = mutableListOf<String>()
        var index = start
        do {
            val current = source[index]
            val opens = current.count { it == '{' }
            val closes = current.count { it == '}' }
            depth += opens - closes
            if (index != start) body += current
            index++
        } while (index < source.size && depth > 0)
        if (depth != 0) fail("Блок має незакриту фігурну дужку.")
        if (body.isNotEmpty() && body.last().trim() == "}") body.removeAt(body.lastIndex)
        return Block(body, index - start)
    }

    private fun String.splitCommaSeparated(): List<String> {
        val result = mutableListOf<String>()
        var start = 0
        var quotes = false
        for (index in indices) {
            if (this[index] == '"') quotes = !quotes
            if (this[index] == ',' && !quotes) {
                result += substring(start, index).trim()
                start = index + 1
            }
        }
        result += substring(start).trim()
        return result
    }

    private fun fail(message: String): Nothing = throw LabRuntimeException(message)
    private class LabRuntimeException(message: String) : RuntimeException(message)
}
