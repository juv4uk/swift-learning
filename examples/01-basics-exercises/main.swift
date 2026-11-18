// Додаткова практика до уроку 1.
// Запуск (Windows):    .\scripts\run-example.ps1 01-basics-exercises
// Запуск (Linux/macOS): ./scripts/run-example.sh 01-basics-exercises

let student = "Володимир"
let targetHours = 6.5
var completedHours = 2.0
let topics = ["let/var", "типи", "інтерполяція", "умови", "цикли"]

print("Студент: \(student)")
print("Ціль: \(targetHours) год, вже виконано: \(completedHours) год")

completedHours += 1.5
let remaining = targetHours - completedHours

if remaining > 0 {
    print("Залишилось: \(remaining) год")
} else {
    print("Ціль досягнуто!")
}

for (index, topic) in topics.enumerated() {
    print("\(index + 1). \(topic)")
}

var total = 0
for hour in 1...3 {
    total += hour
}
print("Сума годин: \(total)")
