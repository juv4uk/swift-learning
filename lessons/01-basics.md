# Урок 1. Перші рядки Swift

## Мета

Навчитися запускати програму, оголошувати константи й змінні та виводити текст.

## Ключові поняття

- `let` — значення, яке після ініціалізації не можна змінити.
- `var` — змінна, якій можна присвоювати нові значення.
- `String` — текст; `Int` — цілі числа; `Double` — числа з дробовою частиною; `Bool` — `true` або `false`.
- Інтерполяція `\(value)` вставляє значення в рядок.
- Swift часто сам визначає тип за початковим значенням. Це називається type inference.

## Приклад

```swift
let name = "Володимир"
var lessonsCompleted = 0

print("Привіт, \(name)!")
lessonsCompleted += 1
print("Завершено уроків: \(lessonsCompleted)")
```

## Запуск у Windows

```powershell
.\scripts\run-example.ps1 01-hello
```

Не починай із `swift main.swift`, якщо JIT у твоєму Windows toolchain повертає `JIT session error`. Для цього курсу використовуй компіляцію через `swiftc` або Swift Package Manager. За потреби можна додати копію DLL з очікуваною назвою — див. [SETUP-WINDOWS.md](../docs/SETUP-WINDOWS.md).

## Вправи

1. Заміни ім'я на своє та запусти програму.
2. Додай константу `targetHours` типу `Double`.
3. Створи змінну `minutes`, збільш її на 15 і виведи результат.
4. Спробуй змінити `let` після оголошення. Прочитай помилку компілятора й поясни її своїми словами.

### Додаткова практика

Розв'язок лежить у [examples/01-basics-exercises](../examples/01-basics-exercises/main.swift). Спершу напиши його самостійно, а потім порівняй:

1. Оголоси `let student`, `let targetHours: Double` і `var completedHours: Double`.
2. Збільш `completedHours` на `1.5` і порахуй залишок до цілі.
3. Виведи різне повідомлення через `if/else`, коли залишок більший за нуль і коли ні.
4. Виведи масив тем із нумерацією за допомогою `enumerated()`.
5. Порахуй суму `1...3` циклом `for-in`.

```powershell
.\scripts\run-example.ps1 01-basics-exercises
```

```bash
./scripts/run-example.sh 01-basics-exercises
```

## Самоперевірка

- Чим `let` відрізняється від `var`?
- Чому `"42"` і `42` — різні значення?
- Що робить `\(name)` усередині рядка?
