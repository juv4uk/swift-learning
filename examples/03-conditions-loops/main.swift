let score = 82

if score >= 90 {
    print("Відмінно")
} else if score >= 70 {
    print("Добре")
} else {
    print("Потрібно ще потренуватися")
}

for day in 1...5 {
    print("День навчання №\(day)")
}

let command = "start"
switch command {
case "start":
    print("Починаємо")
case "pause":
    print("Пауза")
default:
    print("Невідома команда")
}
