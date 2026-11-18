# Налаштування Swift на Windows

Windows — повноцінна платформа для проходження основного курсу: компілятор, Swift Package Manager, консольні програми, бібліотеки й тести. Для Xcode та SwiftUI потрібен Mac, але для вивчення самої мови — ні.

## 1. Встанови компоненти Windows

Swift для Windows використовує MSVC ABI. Потрібні Visual Studio 2022 Build Tools або Visual Studio з компонентом **Desktop development with C++**, MSVC C++ build tools відповідної архітектури та Windows SDK.

Офіційна інструкція: [Swift для Windows](https://www.swift.org/install/windows/). Вона містить актуальні команди WinGet та перелік залежностей. Якщо компілятор не знаходить linker або SDK, спочатку перевір ці компоненти.

## 2. Встанови та перевір Swift

Встанови стабільний toolchain з офіційної сторінки Swift.org, потім відкрий новий PowerShell:

```powershell
swift --version
swiftc --version
swift package --version
```

У цьому курсі рекомендовано Swift 6.4.2 або новішу сумісну стабільну версію.

## 3. Отримай курс

```powershell
git clone https://github.com/juv4uk/swift-learning.git
Set-Location .\swift-learning
```

## 4. Перший приклад

```powershell
.\scripts\run-example.ps1 01-hello
```

Скрипт використовує `swiftc`, тому не залежить від JIT-режиму команди `swift main.swift`.

За бажанням скомпілюй файл вручну:

```powershell
New-Item -ItemType Directory -Force build | Out-Null
swiftc .\examples\01-hello\main.swift -o .\build\lesson.exe
.\build\lesson.exe
```

## 5. Збери пакет і запусти тести

```powershell
swift build
swift run
swift test
```

## 6. Редактор

Visual Studio Code зі Swift extension може бути зручним редактором. SourceKit-LSP надає мовні функції редактору, який підтримує Language Server Protocol. Редактор не замінює компілятор, MSVC та Windows SDK.

## Обмеження та можливості

- Усі основні теми курсу, приклади, пакети й тести можна виконувати на Windows.
- У Swift для Windows немає інтерактивного REPL, тому використовуй `swiftc` або Swift Package Manager.
- Xcode та SwiftUI для нативних застосунків Apple потребують macOS.
- Приклад `08-errors-async` використовує `@main`, тому скрипт автоматично додає `-parse-as-library`.

### JIT-режим `swift file.swift` на Windows

У Swift 6.4.2 інтерпретатор може падати з помилкою:

```text
JIT session error: Symbols not found: [ $ss27_allocateUninitializedArrayySayxG_BptBwlFyp_Tg5 ]
```

Причина: інтерпретатор шукає `libswiftSwiftOnoneSupport.dll`, а у Windows-тулчейні файл називається `swiftSwiftOnoneSupport.dll`. Помилку виправлено в [PR #94206](https://github.com/swiftlang/swift/pull/94206), але стабільного релізу з нею може ще не бути.

Надійні варіанти, яких дотримується курс:

- компілювати окремий файл через `swiftc` — див. розділ 4;
- користуватися Swift Package Manager: `swift build`, `swift run`;
- запустити інтерпретатор з оптимізацією: `swift -O main.swift`.

Якщо потрібно саме `swift main.swift` без `-O`, створіть копію DLL з очікуваною назвою:

```powershell
$runtime = "$env:LOCALAPPDATA\Programs\Swift\Runtimes\6.4.2\usr\bin"
Copy-Item "$runtime\swiftSwiftOnoneSupport.dll" "$runtime\libswiftSwiftOnoneSupport.dll"
```

Замініть `6.4.2` на версію свого runtime. Після оновлення тулчейну копію доведеться повторити; коли вийде версія з виправленням — вона стане непотрібною.

Корисні сторінки:
- [Встановлення Swift на Windows](https://www.swift.org/install/windows/)
- [Підтримувані платформи](https://www.swift.org/platform-support/)
