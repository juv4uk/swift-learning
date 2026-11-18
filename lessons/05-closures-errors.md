# Урок 5. Closures, трансформації та помилки

## Closures

Closure — блок поведінки, який можна зберегти у змінній або передати функції. Він може захоплювати значення з навколишнього контексту.

```swift
let doubled = [1, 2, 3].map { number in
    number * 2
}
```

## Основні операції

- `map` перетворює кожен елемент.
- `filter` залишає елементи, що відповідають умові.
- `sorted` повертає впорядковану копію.
- `reduce` зводить послідовність до одного результату.

## Обробка помилок

Функція з `throws` може повідомити про помилку. Виклик позначається `try`, а результат обробляється через `do-catch` або передається далі.

```swift
enum InputError: Error {
    case emptyName
}

func validate(name: String) throws {
    guard !name.isEmpty else {
        throw InputError.emptyName
    }
}
```

## Запуск власного прикладу

Фрагменти цього уроку скопіюй у файл `main.swift`, додай виклики функцій і скомпілюй.

Windows / PowerShell:

```powershell
New-Item -ItemType Directory -Force build | Out-Null
swiftc .\main.swift -o .\build\lesson05.exe
.\build\lesson05.exe
```

Linux / macOS:

```bash
mkdir -p build
swiftc ./main.swift -o ./build/lesson05
./build/lesson05
```

## Вправи

1. Із масиву чисел створи масив квадратів через `map`.
2. Залиш лише парні числа через `filter`.
3. Порахуй суму через `reduce`.
4. Напиши функцію, яка кидає помилку для порожнього рядка.
5. Оброби обидва сценарії: успіх і помилку.
