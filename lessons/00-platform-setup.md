# Урок 0. Підготуй середовище Swift

Цей урок потрібен незалежно від операційної системи. **Не потрібно купувати Mac, щоб пройти основний курс Swift.** Уроки, приклади, Swift Package Manager і підсумковий консольний проєкт розраховані на Windows, Linux і macOS.

## 1. Обери свою систему

- **Windows 10/11 x64 або ARM64:** встанови Swift toolchain, Visual Studio 2022 Build Tools із C++ toolchain та Windows SDK. Увімкни Developer Mode, як вимагає офіційна інструкція.
- **Linux:** використовуй підтримуваний дистрибутив і Swiftly. Swift.org перелічує Ubuntu 22.04+, Debian 12+, Fedora 41+, Amazon Linux 2023 та Red Hat Universal Base Image 9. Підтримувані архітектури й точні версії залежать від релізу.
- **macOS:** встанови Xcode або Command Line Tools. Цей напрям також дозволяє пізніше перейти до SwiftUI.

Детальні інструкції:
- [Swift на Windows](../docs/SETUP-WINDOWS.md)
- [Swift на Linux](../docs/SETUP-LINUX.md)
- [Swift на macOS](../docs/SETUP-MACOS.md)
- [Актуальна підтримка платформ на Swift.org](https://www.swift.org/platform-support/)

## 2. Перевір, що компілятор доступний

Відкрий новий термінал після встановлення.

У PowerShell:

```powershell
swift --version
swiftc --version
swift package --version
```

У Bash (Linux або macOS):

```bash
swift --version
swiftc --version
swift package --version
```

Для цього курсу використовуй стабільний Swift 6.4.2 або сумісний новіший стабільний реліз. Не використовуй development snapshot як основний навчальний toolchain.

## 3. Отримай навчальний репозиторій

PowerShell:

```powershell
git clone https://github.com/juv4uk/swift-learning.git
Set-Location .\swift-learning
```

Bash:

```bash
git clone https://github.com/juv4uk/swift-learning.git
cd swift-learning
```

## 4. Запусти перший приклад

Windows / PowerShell:

```powershell
.\scripts\run-example.ps1 01-hello
```

Linux / macOS:

```bash
./scripts/run-example.sh 01-hello
```

Якщо PowerShell блокує запуск локального навчального скрипту політикою виконання, тимчасово дозволь скрипти лише в поточному вікні:

```powershell
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass
```

Альтернатива — компілюй файл без скрипту через `swiftc`. У Windows програма матиме суфікс `.exe`; у Linux/macOS — зазвичай без суфікса.

## 5. Перевір Swift Package Manager

Команди однакові в PowerShell і Bash:

```text
swift build
swift run
swift test
```

- `swift build` компілює пакет.
- `swift run` запускає навчальну CLI-програму.
- `swift test` запускає автоматичні тести.

## 6. Зрозумій одну важливу відмінність

На Linux та macOS можна використовувати інтерактивний Swift REPL там, де його підтримує встановлений toolchain. На Windows інтерактивний REPL не підтримується. Це не обмежує проходження курсу: використовуй `swiftc`, `swift run` і `swift test`.

Сам Swift-код уроків спільний. Відрізняються команди оболонки, розширення виконуваного файлу та системні інструменти.

## Контрольна точка

Переходь до уроку 1, коли всі три команди версії працюють, приклад `01-hello` запускається, а `swift build` і `swift test` завершуються успішно.
