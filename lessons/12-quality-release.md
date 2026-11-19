# Урок 12. Якість, налагодження та збірка

## Мета
Навчитися перевіряти застосунок, знаходити помилки й готувати повторювану збірку для локального тестування.

## Теми
- Xcode Test Navigator та unit-тести бізнес-логіки.
- Breakpoints, variables, console, call stack, базовий LLDB.
- Доступність: VoiceOver labels, Dynamic Type, клавіатурна навігація, контраст.
- Git: чистий diff, зрозумілі коміти, README та скриншоти.
- Debug і Release configurations.
- Signing basics: bundle identifier, Team, provisioning, capabilities.
- Різниця між Run, Archive та Export.

## Практичне завдання
Підготуй Study Tracker до демонстрації:
1. Додай тести на додавання, редагування, видалення та валідацію.
2. Відтворюй одну помилку навмисно й знайди її через breakpoint.
3. Перевір основні сценарії клавіатурою та VoiceOver.
4. Перевір, що назви елементів доступні, а текст не обрізається при збільшеному розмірі.
5. Зроби чисту збірку й зафіксуй кроки локального запуску.
6. Досліди Archive/Export та signing, не публікуючи застосунок без окремого рішення.

## Критерії готовності
- [ ] Тести проходять і перевіряють поведінку, а не деталі реалізації.
- [ ] Можу поставити breakpoint і пояснити шлях виконання.
- [ ] Основні дії доступні без миші.
- [ ] Відомі Debug/Release та призначення signing.
- [ ] Інша людина може зібрати та запустити проєкт за README.
- [ ] Додав скриншоти й опис архітектури до README.

## Матеріали
- [Xcode documentation](https://developer.apple.com/documentation/xcode)
- [Human Interface Guidelines](https://developer.apple.com/design/human-interface-guidelines/)
- [Apple App Development Training](https://developer.apple.com/tutorials/app-dev-training/)
