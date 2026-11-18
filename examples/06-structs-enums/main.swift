struct Programmer {
    let name: String
    var experience: Int

    func introduce() {
        print("Привіт! Я \(name).")
        print("Мій досвід: \(experience) роки.")
    }
}

enum StudyStatus {
    case planned
    case inProgress
    case completed
}

let developer = Programmer(name: "Володимир", experience: 3)
developer.introduce()

let status: StudyStatus = .inProgress
switch status {
case .planned:
    print("Тема запланована")
case .inProgress:
    print("Тема вивчається")
case .completed:
    print("Тему завершено")
}
