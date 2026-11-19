package ua.juv4uk.swiftpocketguide

/**
 * JNI bridge to the Swift library compiled for Android arm64-v8a.
 * The library is packaged by the Android CI build.
 */
internal object SwiftNative {
    init {
        System.loadLibrary("SwiftAndroidRuntime")
        System.loadLibrary("swiftbridge")
    }

    @JvmStatic
    external fun nativeAdd(lhs: Int, rhs: Int): Int

    @JvmStatic
    external fun nativeMajorVersion(): Int
}
