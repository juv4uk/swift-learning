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

## Запуск у Windows, Linux і macOS

Windows / PowerShell:

```powershell
.\scripts\run-example.ps1 01-hello
.\scripts\run-example.ps1 02-values
.\scripts\run-example.ps1 03-conditions-loops
```

Linux / macOS:

```bash
./scripts/run-example.sh 01-hello
./scripts/run-example.sh 02-values
./scripts/run-example.sh 03-conditions-loops
```

На Windows скрипт компілює код через `swiftc`, тому не потрібно запускати окремий файл через JIT-команду `swift main.swift`. Якщо виникають проблеми з бібліотеками toolchain, дивись [інструкцію Windows](../docs/SETUP-WINDOWS.md). Спільні команди для всіх уроків зібрані в [довіднику платформ](../docs/PLATFORM-GUIDE.md).

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
