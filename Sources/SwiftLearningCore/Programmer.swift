/// A tiny model used in the first lesson.
public struct Programmer: Equatable {
    public let name: String
    public var experience: Int

    public init(name: String, experience: Int) {
        self.name = name
        self.experience = experience
    }

    /// Returns a Ukrainian introduction for this programmer.
    public var introduction: String {
        "Привіт! Я \(name).\nМій досвід: \(experience) \(yearsWord)."
    }

    private var yearsWord: String {
        let value = experience.magnitude
        let lastTwoDigits = value % 100

        if (11...14).contains(lastTwoDigits) {
            return "років"
        }

        switch value % 10 {
        case 1:
            return "рік"
        case 2...4:
            return "роки"
        default:
            return "років"
        }
    }
}
