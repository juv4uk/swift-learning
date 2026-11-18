# Налаштування Swift на macOS

## 1. Встанови Xcode

Встанови Xcode з Mac App Store. Після першого запуску дочекайся встановлення додаткових компонентів.

За потреби встанови Command Line Tools:

```bash
xcode-select --install
```

## 2. Перевір середовище

```bash
swift --version
xcodebuild -version
```

## 3. Збери навчальний пакет

У Terminal відкрий корінь репозиторію:

```bash
swift build
swift run
swift test
```

## 4. Відкрий у Xcode

Відкрий `Package.swift` через Xcode. Можна редагувати файли, запускати executable target і тести.

## 5. Перейди до SwiftUI

Після завершення базового курсу створи окремий проєкт із шаблоном App. Спершу зроби один екран зі списком. Переноси перевірену модель і логіку з пакета, а не переписуй їх усередині View.

Приклад `08-errors-async` використовує `@main`; запускай його через `./scripts/run-example.sh 08-errors-async`, який додає потрібний прапорець `-parse-as-library`.
