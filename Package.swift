// swift-tools-version: 6.0
import PackageDescription

let package = Package(
    name: "SwiftLearning",
    platforms: [
        .macOS(.v13)
    ],
    products: [
        .library(name: "SwiftLearningCore", targets: ["SwiftLearningCore"]),
        .executable(name: "swift-learning", targets: ["SwiftLearningCLI"])
    ],
    targets: [
        .target(name: "SwiftLearningCore"),
        .executableTarget(
            name: "SwiftLearningCLI",
            dependencies: ["SwiftLearningCore"]
        ),
        .testTarget(
            name: "SwiftLearningCoreTests",
            dependencies: ["SwiftLearningCore"]
        )
    ]
)
