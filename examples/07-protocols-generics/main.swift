protocol Describable {
    var summary: String { get }
}

struct Book: Describable {
    let title: String
    let pages: Int

    var summary: String {
        "\(title) — \(pages) сторінок"
    }
}

func printSummary<T: Describable>(of value: T) {
    print(value.summary)
}

let book = Book(title: "The Swift Programming Language", pages: 600)
printSummary(of: book)
