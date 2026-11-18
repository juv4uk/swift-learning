# Практична робота зі Swift на різних ОС

Основні уроки використовують один і той самий Swift-код. Відмінності зосереджені у встановленні toolchain, терміналі та системних шляхах.

## Команди поруч

| Дія | Windows PowerShell | Linux / macOS Bash |
|---|---|---|
| Перейти до репозиторію | `Set-Location .\swift-learning` | `cd swift-learning` |
| Створити теку | `New-Item -ItemType Directory build -Force` | `mkdir -p build` |
| Запустити приклад | `.\scripts\run-example.ps1 01-hello` | `./scripts/run-example.sh 01-hello` |
| Зібрати пакет | `swift build` | `swift build` |
| Запустити пакет | `swift run` | `swift run` |
| Запустити тести | `swift test` | `swift test` |
| Видалити результати збірки | `Remove-Item -Recurse -Force .build` | `rm -rf .build` |

## Який приклад запускати в кожному уроці

Запускай команди з кореня репозиторію. На Windows використовуй PowerShell, на Linux — Bash.

| Урок | Приклади |
|---|---|
| 1. Основи | `01-hello`, `01-basics-exercises`, `02-values`, `03-conditions-loops` |
| 2. Функції, колекції, Optional | `04-functions`, `05-collections-optionals` |
| 3. Власні типи | `06-structs-enums` |
| 4. Протоколи й generics | `07-protocols-generics` |
| 5. Closures і помилки | Скомпілюй власний `main.swift` командами нижче |
| 6. Пам'ять і конкурентність | `08-errors-async` |
| 7. Пакети й тести | `swift build`, `swift test`, `swift run` |
| 8. Підсумковий проєкт | `swift build`, `swift test`, `swift run` |

### PowerShell

```powershell
.\scripts\run-example.ps1 01-basics-exercises
.\scripts\run-example.ps1 04-functions
.\scripts\run-example.ps1 05-collections-optionals
.\scripts\run-example.ps1 06-structs-enums
.\scripts\run-example.ps1 07-protocols-generics
.\scripts\run-example.ps1 08-errors-async
```

### Bash

```bash
./scripts/run-example.sh 01-basics-exercises
./scripts/run-example.sh 04-functions
./scripts/run-example.sh 05-collections-optionals
./scripts/run-example.sh 06-structs-enums
./scripts/run-example.sh 07-protocols-generics
./scripts/run-example.sh 08-errors-async
```

## Компіляція окремого файлу

Припустимо, ти створив `main.swift` із кодом уроку 5.

PowerShell:

```powershell
New-Item -ItemType Directory -Force build | Out-Null
swiftc .\main.swift -o .\build\lesson.exe
.\build\lesson.exe
```

Bash:

```bash
mkdir -p build
swiftc ./main.swift -o ./build/lesson
./build/lesson
```

Для файлів з точкою входу `@main` може знадобитися прапорець `-parse-as-library`. У прикладі `08-errors-async` це вже враховано в обох скриптах курсу.

## Правила кросплатформного Swift-коду

1. Не склеюй шляхи рядками з `/` або `\\`. Користуйся `URL` і `FileManager` з Foundation.
2. Пам'ятай, що Linux зазвичай чутливий до регістру назв файлів: `StudyItem.swift` та `studyitem.swift` — різні шляхи.
3. Не покладайся на абсолютні шляхи на кшталт `C:\\Users\\...` або `/home/user/...`. Визначай потрібну теку засобами Foundation або передавай шлях як аргумент.
4. Не припускай, що в усіх системах однакова системна програма доступна за однаковою назвою.
5. Якщо пакет збирається на одній ОС, це ще не доводить, що він перевірений на інших. Записуй фактичну ОС, версію Swift і результат команд у звіт.

Приклад побудови тимчасового шляху без ручного роздільника:

```swift
import Foundation

let outputURL = FileManager.default.temporaryDirectory
    .appendingPathComponent("study-items.json")
print(outputURL.path)
```

## Інструменти редагування

[Офіційне розширення Swift для Visual Studio Code](https://marketplace.visualstudio.com/items?itemName=swiftlang.swift-vscode) доступне на Windows, Linux і macOS. Встанови розширення після toolchain. SourceKit-LSP забезпечує мовні функції, але не замінює компілятор.

## Що доступно без Mac

На Windows і Linux можна вивчити мову, створювати CLI-програми та бібліотеки, працювати з пакетами, JSON, тестами й конкурентністю. Xcode та SwiftUI для нативної розробки Apple — окремий напрям на macOS.
