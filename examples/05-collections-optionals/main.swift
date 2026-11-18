let topics = ["Типи", "Функції", "Структури"]
let minutesByTopic = ["Типи": 30, "Функції": 45]
let uniqueTopics: Set<String> = ["Swift", "Xcode", "SwiftUI", "Swift"]

for topic in topics {
    print("Тема: \(topic)")
}

let selectedTopic = "Функції"
if let minutes = minutesByTopic[selectedTopic] {
    print("На тему \(selectedTopic) заплановано \(minutes) хв.")
} else {
    print("Для цієї теми часу ще не заплановано.")
}

print("Унікальних назв: \(uniqueTopics.count)")
