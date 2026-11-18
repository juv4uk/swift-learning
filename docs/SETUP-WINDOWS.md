# Налаштування Swift на Windows

## 1. Перевір компілятор

```powershell
swift --version
swiftc --version
```

У цьому курсі рекомендовано Swift 6.4 або новішу сумісну версію.

## 2. Перевір інструменти C/C++

Офіційний Windows toolchain Swift використовує MSVC ABI. Для збирання зазвичай потрібні Visual Studio Build Tools із компонентом **Desktop development with C++** та Windows SDK. Якщо `swiftc` не знаходить linker або SDK, перевір ці компоненти.

## 3. Перший приклад

У PowerShell із кореня репозиторію:

```powershell
.\scripts\run-example.ps1 01-hello
```

Скрипт використовує `swiftc`, тому не залежить від JIT-режиму команди `swift file.swift`.

## 4. Зібрати пакет

```powershell
swift build
swift run
swift test
```

Якщо команда завершується помилкою, збережи повний текст першої помилки компілятора або linker. Не намагайся виправляти її випадковим перевстановленням усіх компонентів.

## 5. Редактор

Visual Studio Code зі Swift extension може бути зручним редактором. Редактор не замінює компілятор і Windows SDK.

## Обмеження

Swift на Windows підходить для вивчення мови, консольних програм, бібліотек і пакетів. Для Xcode, SwiftUI та створення нативних застосунків Apple потрібен Mac.

Примітка: приклад `08-errors-async` використовує `@main`, тому скрипт автоматично додає `-parse-as-library`. Для решти прикладів використовується звичайний режим компіляції.
