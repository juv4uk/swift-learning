import Foundation

enum StudyError: Error {
    case itemNotFound
}

func loadLessonTitle(found: Bool) async throws -> String {
    try await Task.sleep(for: .milliseconds(150))

    guard found else {
        throw StudyError.itemNotFound
    }

    return "Асинхронність у Swift"
}

@main
struct Example {
    static func main() async {
        do {
            let title = try await loadLessonTitle(found: true)
            print("Завантажено: \(title)")
        } catch {
            print("Не вдалося завантажити урок: \(error)")
        }
    }
}
