package ua.juv4uk.swiftpocketguide

data class QuizQuestion(
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

object QuizContent {
    val questions = listOf(
        QuizQuestion("Яке ключове слово створює значення, яке не можна переприсвоїти?", listOf("var", "let", "func", "guard"), 1, "let оголошує константу. Для значення, яке змінюватиметься, використовуй var."),
        QuizQuestion("Що надрукує print(2 + 3)?", listOf("23", "5", "2 + 3", "Помилка"), 1, "Оператор + додає два цілі числа, тому результат — 5."),
        QuizQuestion("Який тип призначений для істинного або хибного значення?", listOf("String", "Int", "Bool", "Double"), 2, "Bool має два значення: true та false."),
        QuizQuestion("З якого індексу починається Array у Swift?", listOf("0", "1", "-1", "Залежить від масиву"), 0, "Індекси масиву починаються з нуля: перший елемент має індекс 0."),
        QuizQuestion("Що означає nil для Optional?", listOf("Нуль", "Порожній рядок", "Відсутність значення", "Помилку компіляції"), 2, "nil означає, що Optional зараз не містить значення."),
        QuizQuestion("Який цикл зручно використовувати для перебору елементів масиву?", listOf("repeat-until", "for-in", "foreach()", "loop"), 1, "for-in перебирає послідовність або елементи колекції."),
        QuizQuestion("Яке ключове слово позначає асинхронне очікування?", listOf("try", "defer", "await", "throws"), 2, "await позначає місце, де асинхронна функція може призупинитися до отримання результату."),
        QuizQuestion("Що описує протокол у Swift?", listOf("Лише збережені дані", "Вимоги до поведінки типу", "Готовий об'єкт", "Цикл виконання"), 1, "Протокол задає вимоги до властивостей і методів, яким має відповідати тип.")
    )
}
