/// A tiny native Swift library used to verify Swift execution inside the Android app.
@_cdecl("swift_android_add")
public func swiftAndroidAdd(_ lhs: Int32, _ rhs: Int32) -> Int32 {
    lhs &+ rhs
}

@_cdecl("swift_android_runtime_major")
public func swiftAndroidRuntimeMajor() -> Int32 {
    6
}
