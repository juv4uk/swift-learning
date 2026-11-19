package ua.juv4uk.swiftpocketguide

data class HandbookTopic(
    val id: String, val category: String, val title: String, val subtitle: String,
    val explanation: String, val code: String, val takeaway: String
)
data class GlossaryEntry(val term: String, val meaning: String)

object HandbookContent {
    val topics = listOf(
        HandbookTopic("first-program","Початок","Перша програма","Як вивести текст на екран",
            "Програма — це набір інструкцій для комп'ютера. У Swift функція print() виводить значення в консоль. Текст записують у подвійних лапках.",
            """print("Привіт, Swift!")
print("Я вивчаю програмування.")""",
            "Запусти приклад і зміни текст усередині лапок. Крапка з комою наприкінці рядка зазвичай не потрібна."),
        HandbookTopic("constants-variables","Основи","Константи й змінні","let, var і типи значень",
            "let створює константу: після ініціалізації її не можна переприсвоїти. var створює змінну, яку можна змінювати. Swift часто сам визначає тип — це називається виведенням типу.",
            """let language = "Swift"
var lessonsCompleted = 0
lessonsCompleted += 1

let year: Int = 2026
let progress: Double = 0.25
let isLearning: Bool = true""",
            "Використовуй let за замовчуванням. Обирай var лише тоді, коли значення справді має змінюватися."),
        HandbookTopic("types","Основи","Базові типи","String, Int, Double, Bool",
            "Тип визначає, які значення може містити змінна та які операції дозволені. String — текст, Int — ціле число, Double — дробове число, Bool — true або false.",
            """let name: String = "Олена"
let age: Int = 20
let temperature: Double = 21.5
let isStudent: Bool = true
print("Мене звати \\(name), мені \\(age).")""",
            "Число 42 і текст \"42\" — різні значення. Swift не змішує їх автоматично."),
        HandbookTopic("conditions","Керування","Умови","if, else та switch",
            "Умова дозволяє виконати різні інструкції. У Swift умова if повинна мати тип Bool — числа й рядки не трактуються як true або false.",
            """let score = 82
if score >= 90 {
    print("Відмінно")
} else if score >= 60 {
    print("Зараховано")
} else {
    print("Спробуй ще раз")
}
switch score {
case 90...100: print("Високий результат")
case 60..<90: print("Добрий результат")
default: print("Потрібна практика")
}""",
            "Діапазон 60..<90 включає 60, але не включає 90."),
        HandbookTopic("loops","Керування","Цикли","Повторення дій",
            "for-in перебирає елементи колекції або діапазону. while повторює блок, поки умова істинна. Стеж, щоб умова while колись стала хибною.",
            """for number in 1...5 {
    print("Крок номер \\(number)")
}
var countdown = 3
while countdown > 0 {
    print(countdown)
    countdown -= 1
}""",
            "Для відомої кількості повторень зазвичай зручний for-in."),
        HandbookTopic("functions","Організація коду","Функції","Іменовані блоки повторного використання",
            "Функція об'єднує інструкції під одним ім'ям. Параметри передають вхідні значення, а return повертає результат. Тип результату вказують після стрілки ->.",
            """func greet(name: String) -> String {
    return "Привіт, \\(name)!"
}
let message = greet(name: "Іван")
print(message)

func add(_ a: Int, _ b: Int) -> Int {
    a + b
}""",
            "Функція має виконувати зрозумілу задачу. Краще кілька невеликих функцій, ніж одна величезна."),
        HandbookTopic("collections","Дані","Колекції","Array, Dictionary та Set",
            "Array зберігає впорядкований список. Dictionary зберігає пари ключ-значення. Set зберігає унікальні значення без гарантованого порядку.",
            """let languages = ["Swift", "Kotlin", "Rust"]
print(languages[0])
var scores = ["Оля": 95, "Тарас": 88]
scores["Іра"] = 91
let uniqueNumbers: Set = [1, 2, 2, 3]
print(uniqueNumbers.count) // 3""",
            "Індекси Array починаються з нуля. Перед доступом до елемента переконайся, що індекс існує."),
        HandbookTopic("optionals","Безпека","Optional","Значення, якого може не бути",
            "Optional означає, що значення або існує, або дорівнює nil. Це спосіб явно показати можливу відсутність даних і змусити обробити такий випадок.",
            """let possibleNumber = Int("123")
if let number = possibleNumber {
    print(number * 2)
} else {
    print("Це не число")
}
let username: String? = nil
print(username ?? "Гість")""",
            "Не використовуй ! без упевненості, що значення точно існує. Безпечніший шлях — if let, guard let або ??."),
        HandbookTopic("struct-enum","Власні типи","Структури й переліки","Моделюємо предметну область",
            "struct описує власний тип значення з властивостями та методами. enum задає обмежений набір станів. Це допомагає виражати правила без довільних рядків.",
            """enum StudyStatus {
    case planned
    case inProgress
    case completed
}
struct StudyItem {
    let title: String
    var status: StudyStatus
}
var lesson = StudyItem(title: "Основи Swift", status: .planned)
lesson.status = .inProgress""",
            "Якщо набір станів відомий наперед, enum зазвичай безпечніший за рядки на кшталт \"done\"."),
        HandbookTopic("protocols","Власні типи","Протоколи","Спільна поведінка різних типів",
            "Протокол описує вимоги до типу: які властивості або методи він повинен надати. Різні структури й класи можуть відповідати одному протоколу.",
            """protocol Describable {
    var description: String { get }
}
struct Book: Describable {
    let title: String
    var description: String {
        "Книга: \\(title)"
    }
}""",
            "Протокол описує, що тип уміє робити, а не те, як саме він це реалізує."),
        HandbookTopic("errors","Безпека","Обробка помилок","throws, do-catch та try",
            "Функція з throws може повідомити про помилку. Виклик позначають try, а обробляють за допомогою do-catch. Помилки краще пояснювати зрозумілою мовою.",
            """enum LoginError: Error { case emptyName }
func login(name: String) throws {
    guard !name.isEmpty else { throw LoginError.emptyName }
    print("Вітаю, \\(name)!")
}
do {
    try login(name: "")
} catch {
    print("Не вдалося увійти: \\(error)")
}""",
            "Не приховуй помилки порожнім catch. Виріши, чи показати повідомлення, повторити дію або передати помилку вище."),
        HandbookTopic("async","Сучасний Swift","Асинхронність","async та await",
            "Асинхронні функції дозволяють чекати на повільну операцію, наприклад мережевий запит, не блокуючи потік. await позначає місце, де функція може призупинитися.",
            """func loadMessage() async -> String {
    return "Дані отримано"
}
Task {
    let message = await loadMessage()
    print(message)
}""",
            "Не кожну функцію потрібно робити async. Використовуй асинхронність там, де є операція очікування.")
    )

    val glossary = listOf(
        GlossaryEntry("Алгоритм","Послідовність кроків для розв'язання задачі."),
        GlossaryEntry("Аргумент","Конкретне значення, яке передають параметру під час виклику функції."),
        GlossaryEntry("Змінна","Іменоване місце для значення, яке може змінюватися."),
        GlossaryEntry("Константа","Іменоване значення, яке після ініціалізації не можна переприсвоїти."),
        GlossaryEntry("Компілятор","Інструмент, який перетворює вихідний код на форму, придатну для виконання."),
        GlossaryEntry("Метод","Функція, оголошена всередині типу."),
        GlossaryEntry("Параметр","Ім'я та тип вхідного значення, яке приймає функція."),
        GlossaryEntry("Протокол","Опис вимог до властивостей і методів типу."),
        GlossaryEntry("Тип","Опис множини можливих значень і дозволених операцій."),
        GlossaryEntry("Optional","Тип, який представляє або значення, або його відсутність (nil)."),
        GlossaryEntry("nil","Спеціальне значення Swift, яке означає відсутність значення в Optional."),
        GlossaryEntry("IDE","Інтегроване середовище розробки: редактор, збірка, запуск і налагодження.")
    )
}
