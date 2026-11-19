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
print("Мене звати \(name), мені \(age).")""",
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
    print("Крок номер \(number)")
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
    return "Привіт, \(name)!"
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
        "Книга: \(title)"
    }
}""",
            "Протокол описує, що тип уміє робити, а не те, як саме він це реалізує."),
        HandbookTopic("errors","Безпека","Обробка помилок","throws, do-catch та try",
            "Функція з throws може повідомити про помилку. Виклик позначають try, а обробляють за допомогою do-catch. Помилки краще пояснювати зрозумілою мовою.",
            """enum LoginError: Error { case emptyName }
func login(name: String) throws {
    guard !name.isEmpty else { throw LoginError.emptyName }
    print("Вітаю, \(name)!")
}
do {
    try login(name: "")
} catch {
    print("Не вдалося увійти: \(error)")
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
            "Не кожну функцію потрібно робити async. Використовуй асинхронність там, де є операція очікування."),
        HandbookTopic("strings","Основи","Робота з текстом","Інтерполяція та багаторядкові рядки",
            "String — це не просто набір символів. Swift дозволяє вставляти значення в текст через інтерполяцію \(вираз), об'єднувати рядки та записувати багаторядковий текст у потрійних лапках. Не плутай порожній рядок \"\" із nil: це різні стани.",
            """let name = "Софія"
let lessons = 4
let message = "Привіт, \(name)! Ти пройшла \(lessons) уроки."

let poem = "Swift — це мова,\\nу якій важлива ясність."
print(message)
print(poem)""",
            "Інтерполяція \(name) вставляє значення, а не друкує назву змінної."),
        HandbookTopic("tuples","Дані","Кортежі","Кілька значень в одному результаті",
            "Кортеж (tuple) групує кілька значень, які можуть мати різні типи. Він зручний для невеликого локального результату. Якщо дані мають власний сенс і використовуються в багатьох місцях, краще створити struct.",
            """let response = (code: 200, message: "OK")
print(response.code)
print(response.message)

func minMax(_ numbers: [Int]) -> (min: Int, max: Int)? {
    guard let minimum = numbers.min(), let maximum = numbers.max() else {
        return nil
    }
    return (minimum, maximum)
}

if let bounds = minMax([8, 3, 12, 5]) {
    print("Від \(bounds.min) до \(bounds.max)")
}""",
            "Кортеж — легка упаковка кількох значень, але не заміна повноцінній моделі даних."),
        HandbookTopic("closures","Організація коду","Замикання","Функції, які можна передавати як значення",
            "Замикання (closure) — блок коду, який можна зберегти в змінній або передати іншій функції. Воно часто описує дію: як відсортувати список, перетворити елемент або відреагувати на подію. $0 означає перший параметр, $1 — другий.",
            """let names = ["Олена", "Іван", "Марія"]
let sorted = names.sorted { first, second in
    first.count < second.count
}

let numbers = [1, 2, 3, 4]
let doubled = numbers.map { $0 * 2 }
let even = numbers.filter { $0.isMultiple(of: 2) }

print(sorted)
print(doubled)
print(even)""",
            "Не скорочуй closure до $0, якщо через це код стає незрозумілим."),
        HandbookTopic("value-reference","Власні типи","struct чи class?","Типи-значення та типи-посилання",
            "struct має семантику значення: присвоєння створює незалежну копію. class має семантику посилання: дві змінні можуть вказувати на той самий об'єкт. Структури — чудовий стандартний вибір для моделей даних; клас потрібен, коли важлива спільна ідентичність або спадкування.",
            """struct Counter {
    var value = 0
}
var first = Counter()
var second = first
second.value = 10
print(first.value) // 0 — незалежна копія

final class Player {
    var name: String
    init(name: String) { self.name = name }
}
let playerA = Player(name: "Леся")
let playerB = playerA
playerB.name = "Орися"
print(playerA.name) // Орися — той самий об'єкт""",
            "Обирай struct, якщо не потрібна спільна змінна ідентичність. Не використовуй class лише за звичкою з інших мов."),
        HandbookTopic("initializers","Власні типи","Ініціалізатори","Як створюється коректний об'єкт",
            "Ініціалізатор init задає правила створення значення. Він має підготувати всі властивості до використання. self допомагає відрізнити властивість екземпляра від параметра. Ініціалізатор init? може відхилити некоректні дані.",
            """struct User {
    let name: String
    var age: Int

    init?(name: String, age: Int) {
        guard !name.isEmpty, age >= 0 else { return nil }
        self.name = name
        self.age = age
    }
}

if let user = User(name: "Андрій", age: 21) {
    print("\(user.name): \(user.age)")
}""",
            "Створи об'єкт так, щоб він одразу був у допустимому стані."),
        HandbookTopic("extensions","Можливості мови","Розширення","Додаємо поведінку до наявного типу",
            "extension додає обчислювані властивості, методи та інші можливості до вже наявного типу — навіть якщо його код не твій. Розширення не створює підклас і не змінює початкове оголошення.",
            """extension Int {
    var isEven: Bool {
        self % 2 == 0
    }
}

let values = [2, 5, 8, 11]
for value in values {
    print("\(value): парне? \(value.isEven)")
}""",
            "Додавай у extension поведінку, яка природно належить типу."),
        HandbookTopic("generics","Можливості мови","Узагальнення","Один алгоритм для багатьох типів",
            "Generics дозволяють писати функції та типи, які працюють із різними конкретними типами, не втрачаючи перевірку типів. T — параметр типу. Обмеження протоколом дозволяє вимагати потрібні можливості.",
            """func firstElement<T>(of items: [T]) -> T? {
    items.first
}

let firstNumber = firstElement(of: [10, 20, 30])
let firstWord = firstElement(of: ["Swift", "Kotlin"])

print(firstNumber ?? 0)
print(firstWord ?? "Список порожній")""",
            "Generics прибирають дублювання, але не роби API складнішим заради абстракції."),
        HandbookTopic("codable","Дані","Codable та JSON","Зберігаємо й обмінюємося даними",
            "Codable — псевдонім для Encodable та Decodable. JSONEncoder перетворює модель на JSON, а JSONDecoder відновлює її. Це часто використовують для API, налаштувань і файлів.",
            """import Foundation

struct Book: Codable {
    let title: String
    let pages: Int
}

let book = Book(title: "Мова Swift", pages: 240)
do {
    let data = try JSONEncoder().encode(book)
    let json = String(data: data, encoding: .utf8) ?? ""
    print(json)

    let restored = try JSONDecoder().decode(Book.self, from: data)
    print(restored.title)
} catch {
    print("Помилка кодування: \(error)")
}""",
            "Не зберігай паролі й секретні ключі у звичайному JSON-файлі."),
        HandbookTopic("networking","Сучасний Swift","Мережеві запити","URLSession та перевірка HTTP-відповіді",
            "URLSession виконує HTTP-запити, а async/await робить асинхронний код послідовним на вигляд. Перевіряй статус HTTP, обробляй помилки мережі та не вважай будь-яку відповідь успішною лише тому, що сервер відповів.",
            """import Foundation

func downloadText() async throws -> String {
    guard let url = URL(string: "https://example.com") else {
        throw URLError(.badURL)
    }
    let (data, response) = try await URLSession.shared.data(from: url)
    guard let http = response as? HTTPURLResponse,
          (200..<300).contains(http.statusCode) else {
        throw URLError(.badServerResponse)
    }
    return String(decoding: data, as: UTF8.self)
}

Task {
    do {
        let text = try await downloadText()
        print(text.prefix(120))
    } catch {
        print("Не вдалося завантажити: \(error)")
    }
}""",
            "Мережа може зникнути, сервер — повернути помилку, а відповідь — бути не тим форматом."),
        HandbookTopic("actors","Сучасний Swift","Actor і безпечний спільний стан","Захищаємо дані в конкурентному коді",
            "Коли кілька асинхронних задач звертаються до змінного стану, звичайний клас може створити гонку даних. actor ізолює власний стан: доступ до нього координується Swift Concurrency. Звернення ззовні часто потребує await. Actor не означає, що весь код усередині виконується паралельно.",
            """actor ScoreStore {
    private var score = 0

    func add(_ points: Int) {
        score += points
    }

    func currentScore() -> Int {
        score
    }
}

let store = ScoreStore()
Task {
    await store.add(10)
    let total = await store.currentScore()
    print("Рахунок: \(total)")
}""",
            "Не роби всі типи actor автоматично. Використовуй ізоляцію там, де справді є спільний змінний стан."),
        HandbookTopic("swiftui-state","Apple-платформи","SwiftUI: стан і події","Як інтерфейс реагує на користувача",
            "SwiftUI описує інтерфейс як функцію стану. @State зберігає локальний стан View, а зміна цього стану спричиняє оновлення відповідної частини інтерфейсу. Button запускає дію, Text показує актуальне значення. Не змінюй стан напряму з довільного потоку.",
            """import SwiftUI

struct CounterView: View {
    @State private var count = 0

    var body: some View {
        VStack(spacing: 16) {
            Text("Натискань: \(count)")
            Button("Додати") {
                count += 1
            }
        }
        .padding()
    }
}""",
            "View — це опис інтерфейсу, а не місце для довготривалої бізнес-логіки. Для передавання стану дочірньому View вивчи @Binding."),
        HandbookTopic("main-actor","Сучасний Swift","@MainActor та інтерфейс","Оновлюємо UI на головному акторі",
            "Інтерфейсні фреймворки очікують, що зміни стану UI відбуваються на головному акторі. @MainActor ізолює тип або функцію на MainActor. Це не те саме, що просто запустити довільний код у фоновому потоці: ізоляція задає правило доступу до стану.",
            """@MainActor
final class ProfileModel {
    private(set) var name = "Гість"

    func rename(to newName: String) {
        name = newName
    }
}

let model = ProfileModel()
Task { @MainActor in
    model.rename(to: "Олена")
    print(model.name)
}""",
            "Позначай @MainActor саме той стан і код, які належать UI. Не блокуй головний актор довгими обчисленнями."),
        HandbookTopic("task-cancellation","Сучасний Swift","Скасування задач","Не витрачаємо ресурси даремно",
            "Скасування в Swift Concurrency кооперативне: Task позначається як скасована, але код має реагувати на це. Довгі цикли можуть періодично викликати Task.checkCancellation(). Асинхронні операції на кшталт Task.sleep кидають CancellationError після скасування.",
            """func preparePreview() async throws -> String {
    try Task.checkCancellation()
    try await Task.sleep(for: .seconds(1))
    try Task.checkCancellation()
    return "Попередній перегляд готовий"
}

let task = Task {
    do {
        print(try await preparePreview())
    } catch is CancellationError {
        print("Операцію скасовано")
    } catch {
        print("Інша помилка: \\(error)")
    }
}

// За потреби власник може викликати:
// task.cancel()""",
            "Не створюй нескінченні задачі без життєвого циклу. Скасовуй роботу, коли результат більше не потрібен."),
        HandbookTopic("sendable","Сучасний Swift","Sendable і передавання даних","Безпечні значення між задачами",
            "Sendable описує типи, значення яких можна безпечно передавати між ізольованими контекстами конкурентного виконання. Структури з незмінними Sendable-полями часто відповідають цьому протоколу автоматично. Для власного спільного змінного стану краще використовувати actor, а не оголошувати тип @unchecked Sendable без доведених гарантій.",
            """struct NotificationMessage: Sendable {
    let title: String
    let body: String
}

actor NotificationQueue {
    private var items: [NotificationMessage] = []

    func enqueue(_ message: NotificationMessage) {
        items.append(message)
    }

    func count() -> Int {
        items.count
    }
}

let queue = NotificationQueue()
Task {
    await queue.enqueue(
        NotificationMessage(
            title: "Вітаємо",
            body: "Навчання триває!"
        )
    )
    print(await queue.count())
}""",
            "Sendable — це гарантія безпечного передавання, а не команда запускати код паралельно."),
        HandbookTopic("unit-tests","Якість коду","Модульні тести","Перевіряємо поведінку автоматично",
            "Модульний тест перевіряє невелику одиницю поведінки ізольовано від решти системи. Тест має мати зрозумілий сценарій, підготовку даних, виконання та перевірку результату. Тестуй також межові випадки: порожні дані, нуль, неправильний ввід.",
            """import XCTest

struct PriceCalculator {
    func total(price: Int, quantity: Int) -> Int {
        price * quantity
    }
}

final class PriceCalculatorTests: XCTestCase {
    func testTotalForTwoItems() {
        let calculator = PriceCalculator()
        XCTAssertEqual(
            calculator.total(price: 25, quantity: 2),
            50
        )
    }

    func testZeroQuantity() {
        XCTAssertEqual(
            PriceCalculator().total(price: 25, quantity: 0),
            0
        )
    }
}""",
            "Тест повинен падати, коли поведінка неправильна. Не підганяй очікуваний результат під помилку реалізації."),
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
        GlossaryEntry("IDE","Інтегроване середовище розробки: редактор, збірка, запуск і налагодження."),
        GlossaryEntry("Array","Упорядкована колекція елементів, доступних за індексом."),
        GlossaryEntry("Dictionary","Колекція пар ключ-значення."),
        GlossaryEntry("Set","Колекція унікальних значень."),
        GlossaryEntry("Closure","Самодостатній блок коду, який можна передати або зберегти."),
        GlossaryEntry("Tuple","Група кількох значень, можливо різних типів."),
        GlossaryEntry("struct","Тип-значення, який об'єднує дані та поведінку."),
        GlossaryEntry("class","Тип-посилання з підтримкою спадкування."),
        GlossaryEntry("init","Спеціальний метод, який створює й ініціалізує екземпляр типу."),
        GlossaryEntry("extension","Механізм додавання можливостей до наявного типу."),
        GlossaryEntry("Generics","Узагальнений код, що працює з різними типами зі збереженням типобезпеки."),
        GlossaryEntry("Codable","Протоколи для кодування та декодування даних."),
        GlossaryEntry("URLSession","API Foundation для мережевих запитів."),
        GlossaryEntry("HTTP","Протокол обміну даними між клієнтом і сервером."),
        GlossaryEntry("JSON","Текстовий формат представлення структурованих даних."),
        GlossaryEntry("Actor","Тип ізоляції стану в Swift Concurrency, що захищає власні змінні дані."),
        GlossaryEntry("SwiftUI","Декларативний фреймворк Apple для створення інтерфейсів."),
        GlossaryEntry("@State","Властивість SwiftUI для локального стану View."),
        GlossaryEntry("@Binding","Посилання на стан, яким володіє інший компонент інтерфейсу."),
        GlossaryEntry("Модульний тест","Автоматична перевірка невеликої одиниці поведінки програми."),
        GlossaryEntry("Гонка даних","Помилка конкурентного доступу, коли результат залежить від непередбачуваного порядку операцій."),
        GlossaryEntry("@MainActor","Актор ізоляції, який координує доступ до коду та стану головного актора."),
        GlossaryEntry("CancellationError","Помилка, яку асинхронна операція може кинути у відповідь на скасування задачі."),
        GlossaryEntry("Sendable","Протокол Swift для типів, значення яких безпечно передавати між конкурентними контекстами.")
    )
}
