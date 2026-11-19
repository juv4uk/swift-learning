#include <jni.h>
#include <cstdint>

extern "C" std::int32_t swift_android_add(std::int32_t lhs, std::int32_t rhs);
extern "C" std::int32_t swift_android_runtime_major();

extern "C" JNIEXPORT jint JNICALL
Java_ua_juv4uk_swiftpocketguide_SwiftNative_nativeAdd(
    JNIEnv *, jclass, jint lhs, jint rhs
) {
    return static_cast<jint>(swift_android_add(lhs, rhs));
}

extern "C" JNIEXPORT jint JNICALL
Java_ua_juv4uk_swiftpocketguide_SwiftNative_nativeMajorVersion(
    JNIEnv *, jclass
) {
    return static_cast<jint>(swift_android_runtime_major());
}
