# Налаштування Swift та Xcode на macOS

Ця інструкція — старт Apple-етапу курсу. Перед встановленням перевір сумісність саме своєї моделі Mac.

## 1. Перевір сумісність

Відкрий [офіційну таблицю системних вимог Xcode](https://developer.apple.com/xcode/system-requirements/). Переконайся, що:
- твоя модель Mac підтримує потрібну версію macOS;
- ця версія macOS підтримує обраний стабільний Xcode;
- на диску достатньо вільного місця для Xcode, SDK, Simulator та проєктів.

Не орієнтуйся на номер Xcode, записаний у старій інструкції або відео. Не встановлюй beta як основне навчальне середовище без окремої потреби.

## 2. Встанови Xcode

Встанови стабільний Xcode з Mac App Store або іншого офіційного каналу Apple. Запусти Xcode та дочекайся встановлення додаткових компонентів. За потреби встанови Command Line Tools:

```bash
xcode-select --install
```

## 3. Перевір середовище

У Terminal виконай:

```bash
sw_vers
xcodebuild -version
swift --version
xcode-select -p
git --version
```

Якщо Xcode не вибраний як активний developer directory, перевір налаштування Xcode → Settings → Locations → Command Line Tools.

## 4. Перевір навчальний пакет

```bash
git clone https://github.com/juv4uk/swift-learning.git
cd swift-learning
swift build
swift run
swift test
```

## 5. Відкрий пакет у Xcode

Відкрий `Package.swift` через Xcode. Знайди targets, схеми, вихідні файли та тести. Запусти тести через Test Navigator.

## 6. Створи перший застосунок

Після завершення тижнів 1–8 створи окремий проєкт із шаблоном macOS App та SwiftUI. Виконай Apple-етап:
- [План підготовки до MacBook](MACBOOK-ROADMAP.md)
- [Тиждень 9 — macOS та Xcode](../lessons/09-macos-xcode.md)
- [Тиждень 10 — SwiftUI](../lessons/10-swiftui-interface.md)
- [Тиждень 11 — дані й архітектура](../lessons/11-data-architecture.md)
- [Тиждень 12 — якість і збірка](../lessons/12-quality-release.md)

Спершу зроби один простий екран зі станом. Потім перенеси перевірену модель і бізнес-логіку з кросплатформного Study Tracker. Не змішуй правила предметної області з SwiftUI View.

## 7. Корисні інструменти

- Xcode — IDE, компілятор, debugger, тестування та Simulator.
- Terminal — робота з Git, SwiftPM та файлами.
- Git — історія змін і резервна точка для експериментів.
- Homebrew — необов'язковий менеджер пакетів. Він не потрібен для базового встановлення Swift/Xcode.

Приклад `08-errors-async` використовує `@main`; запускай його через `./scripts/run-example.sh 08-errors-async`, який додає потрібний прапорець `-parse-as-library`.
