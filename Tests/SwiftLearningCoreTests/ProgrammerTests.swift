import XCTest
@testable import SwiftLearningCore

final class ProgrammerTests: XCTestCase {
    func testIntroductionUsesCorrectUkrainianYearsForm() {
        let programmer = Programmer(name: "Володимир", experience: 3)

        XCTAssertEqual(
            programmer.introduction,
            "Привіт! Я Володимир.\nМій досвід: 3 роки."
        )
    }

    func testYearWordForOne() {
        XCTAssertTrue(Programmer(name: "A", experience: 1).introduction.hasSuffix("1 рік."))
    }

    func testYearWordForTwoToFour() {
        XCTAssertTrue(Programmer(name: "A", experience: 4).introduction.hasSuffix("4 роки."))
    }

    func testYearWordForFiveAndTeenValues() {
        XCTAssertTrue(Programmer(name: "A", experience: 5).introduction.hasSuffix("5 років."))
        XCTAssertTrue(Programmer(name: "A", experience: 12).introduction.hasSuffix("12 років."))
    }

    func testValueCanBeChangedWhenVariableIsMutable() {
        var programmer = Programmer(name: "Володимир", experience: 3)
        programmer.experience = 4

        XCTAssertEqual(programmer.experience, 4)
    }
}
