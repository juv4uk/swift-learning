# Налаштування Swift на Linux

Цей курс можна повністю пройти на Linux: вивчити мову, створювати консольні програми й бібліотеки, працювати зі Swift Package Manager, JSON, асинхронністю та тестами. Mac для цього не потрібен.

## 1. Перевір підтримку дистрибутива

Swift офіційно публікує toolchain для кількох дистрибутивів і версій Linux. Серед актуальних платформ — Ubuntu 22.04+, Debian 12+, Fedora 41+, Amazon Linux 2023 та Red Hat Universal Base Image 9. Список змінюється, тому перед встановленням звір свій дистрибутив з [офіційною таблицею підтримки](https://www.swift.org/platform-support/).

## 2. Встанови Swift через Swiftly

[Swiftly](https://www.swift.org/install/linux/) — офіційно рекомендований менеджер toolchain від Swift.org. Він встановлює Swift, допоміжні компоненти та дозволяє перемикатися між версіями.

У Bash виконай команду з офіційної інструкції:

```bash
curl -O https://download.swift.org/swiftly/linux/swiftly-$(uname -m).tar.gz
tar zxf swiftly-$(uname -m).tar.gz
./swiftly init --quiet-shell-followup
. "${SWIFTLY_HOME_DIR:-$HOME/.local/share/swiftly}/env.sh"
hash -r
```

Закрий і знову відкрий термінал, якщо команда `swift` ще не знайдена. Для встановлення стабільного toolchain, наприклад 6.4.2:

```bash
swiftly install 6.4.2
swiftly use 6.4.2
```

Якщо Swiftly не підтримує твій дистрибутив або архітектуру, скористайся офіційною сторінкою встановлення й обери відповідний tarball чи контейнер.

## 3. Перевір середовище

```bash
swift --version
swiftc --version
swift package --version
```

## 4. Підготуй інструменти

Для Git, редактора та системних інструментів використовуй пакунковий менеджер свого дистрибутива. Наприклад, на Ubuntu/Debian:

```bash
sudo apt update
sudo apt install git curl build-essential
```

Якщо компілятор повідомляє про відсутню системну бібліотеку, встановлюй саме названий пакет для свого дистрибутива. Не потрібно навмання встановлювати всі залежності.

## 5. Отримай курс і запусти перший приклад

```bash
git clone https://github.com/juv4uk/swift-learning.git
cd swift-learning
./scripts/run-example.sh 01-hello
```

Скрипт компілює приклад через `swiftc`, а потім запускає створений виконуваний файл.

## 6. Збери весь навчальний пакет

У корені репозиторію:

```bash
swift build
swift run
swift test
```

Для оптимізованої збірки:

```bash
swift build -c release
```

## 7. Редактор

Можна використовувати Visual Studio Code зі Swift extension або будь-який редактор із підтримкою Language Server Protocol через SourceKit-LSP. Редактор — це інструмент; компілятор і Swift toolchain встановлюються окремо.

## Важливо

- На Linux доступні Swift compiler, Swift Package Manager, SourceKit-LSP та налагодження на платформах, де це зазначено в офіційній таблиці.
- Уроки 1–7 і консольний підсумковий проєкт проходяться без Mac.
- Xcode та SwiftUI для нативної розробки Apple потребують macOS. Водночас Swift на Linux — не «урізана навчальна версія»: це повноцінний інструментарій для серверів, CLI, бібліотек і системних програм.

Корисні офіційні сторінки:
- [Встановлення Swift на Linux](https://www.swift.org/install/linux/)
- [Підтримувані платформи](https://www.swift.org/platform-support/)
- [Початок роботи зі Swift](https://www.swift.org/getting-started/)
