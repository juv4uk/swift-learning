// swift-tools-version: 6.0
import PackageDescription

let package = Package(
    name: "SwiftAndroidRuntime",
    products: [
        .library(name: "SwiftAndroidRuntime", type: .dynamic, targets: ["SwiftAndroidRuntime"])
    ],
    targets: [
        .target(name: "SwiftAndroidRuntime")
    ]
)
