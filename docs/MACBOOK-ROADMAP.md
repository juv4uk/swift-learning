# Apple-етап: підготовка до роботи на MacBook

Цей документ доповнює восьмитижневий фундамент Swift. Його проходять після отримання MacBook. Він не прив'язаний до конкретного номера Xcode: інсталюй стабільну версію, сумісну з твоєю моделлю Mac та встановленою macOS.

## Перед початком

1. Звір точну модель Mac, чип, обсяг пам'яті й доступну версію macOS.
2. Перевір сумісність macOS та Xcode на [Apple Xcode system requirements](https://developer.apple.com/xcode/system-requirements/).
3. Встанови Xcode з офіційного джерела Apple і відкрий його один раз для встановлення компонентів.
4. Перевір у Terminal:

```bash
sw_vers
xcodebuild -version
swift --version
xcode-select -p
git --version
```

5. Відкрий навчальний репозиторій, виконай `swift build`, `swift test`, `swift run`.

## Що треба вміти на macOS

- Finder: робочі каталоги, приховані файли, розширення, переміщення та пошук.
- Terminal: `pwd`, `ls`, `cd`, `mkdir`, `cp`, `mv`, `cat`, `open`.
- Права доступу та системні діалоги дозволів; не вимикай системний захист заради зручності.
- Git: clone, status, diff, add, commit, pull, push, branch.
- Xcode: project/workspace, target, scheme, build settings, assets, run destination.
- Debugging: breakpoints, variables, console, call stack, basic LLDB.
- Simulator: запуск, вибір пристрою, тестування розмірів вікон/екранів.
- Signing: Apple Account, team, bundle identifier, provisioning і capabilities на базовому рівні.

Homebrew — необов'язковий менеджер пакетів, а не обов'язкова умова для Swift/Xcode. Встановлюй додаткові інструменти лише тоді, коли вони потрібні.

## Фінальний критерій

Ти готовий до початкової самостійної розробки на Mac, коли можеш без покрокового супроводу створити Xcode-проєкт, написати SwiftUI-екран, під'єднати модель і сховище, запустити тести, знайти помилку через debugger і підготувати збірку для локального тестування.

Публікація в App Store — окрема наступна тема: вона потребує додаткового вивчення облікового запису розробника, правил App Store Review та процесу App Store Connect.
