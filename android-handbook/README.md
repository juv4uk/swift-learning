# Swift Pocket Guide — Android

**Swift Pocket Guide** — офлайн-довідник українською для тих, хто починає вивчати Swift. Це супутній застосунок до [swift-learning](https://github.com/juv4uk/swift-learning).

## Можливості

- 21 тематична картка: від першої програми й базових типів до замикань, generics, Codable та мережевих запитів.
- Пояснення українською, приклади Swift-коду й короткі практичні акценти в кожній темі.
- Пошук і фільтр тем за категоріями.
- Розширений словник із 26 термінів та пошуком.
- Закладки та позначення вивчених тем.
- Мінітест із 13 запитань, миттєвим зворотним зв'язком і підсумковим результатом.
- Копіювання прикладів Swift-коду в буфер обміну.
- Світла й темна теми з локальним збереженням вибору.
- Локальне збереження прогресу через SharedPreferences.
- Власний адаптивний значок застосунку.
- Увесь навчальний матеріал вбудований: мережа не потрібна.

## Технології

- Kotlin та Jetpack Compose / Material 3.
- Android Gradle Plugin 9.4.0.
- Compose BOM 2026.09.00.
- Мінімальна версія Android: API 26 (Android 8.0).
- JDK 17.

Версії взято з актуальних офіційних Android-рекомендацій на момент створення. AGP 9.4.0 вимагає Gradle 9.6.0. Compose BOM синхронізує версії Compose-бібліотек.

## Як відкрити

1. Встанови Android Studio та Android SDK Platform 37.
2. Відкрий теку `android-handbook` як окремий Gradle-проєкт.
3. Переконайся, що Gradle використовує JDK 17.
4. Якщо Gradle Wrapper ще не створено, встанови Gradle 9.6.0 і в цій теці виконай:

```bash
gradle wrapper --gradle-version 9.6.0
```

5. Синхронізуй Gradle та запусти конфігурацію `app` на емуляторі або Android-пристрої.

## Команди після створення Wrapper

Windows PowerShell:

```powershell
.\gradlew.bat :app:testDebugUnitTest
.\gradlew.bat :app:assembleDebug
```

Linux / macOS:

```bash
./gradlew :app:testDebugUnitTest
./gradlew :app:assembleDebug
```

APK після успішної збірки буде в `app/build/outputs/apk/debug/`.

## Структура

```
android-handbook/
├── app/src/main/java/ua/juv4uk/swiftpocketguide/
│   ├── MainActivity.kt
│   ├── HandbookContent.kt
│   ├── QuizContent.kt
│   ├── SwiftRuntimeScreen.kt
│   └── SwiftNative.kt
├── app/src/main/res/
│   ├── drawable/ic_launcher_foreground.xml
│   └── mipmap-anydpi-v26/ic_launcher.xml
├── app/src/test/
├── app/build.gradle.kts
├── build.gradle.kts
└── settings.gradle.kts
```

## Swift на Android

Застосунок містить нативну Swift-бібліотеку, яку CI збирає офіційним Swift 6.4.2 Android SDK для `aarch64-unknown-linux-android30` і пакує як `arm64-v8a`. Кнопка «Перевірити Swift на телефоні» викликає функцію, написану на Swift, через JNI; цей тест виконується локально й не потребує мережі.

Важливо: це доводить виконання скомпільованого Swift-коду на ARM64 Android, але ще **не** є повноцінним локальним компілятором довільного Swift-коду. Застосунок не містить онлайн-IDE або віддаленого редактора. Екран викликає лише заздалегідь скомпільовані Swift-функції; локальний редактор і компіляція довільних програм залишаються окремим наступним етапом.

### Збірка Swift-модуля

У CI використовуються Swift 6.4.2, Swift SDK for Android і Android NDK 30. Вихідна бібліотека лежить у `android-swift-runtime/`; її ARM64 `.so` та JNI-міст автоматично додаються до APK у `app/src/main/jniLibs/arm64-v8a/`.

Локальне складання потребує Linux/macOS host toolchain Swift тієї самої версії, Swift SDK for Android 6.4.2 та Android NDK 30. Приклад команди:

```bash
cd android-swift-runtime
swift build --configuration release --swift-sdk swift-6.4.2-RELEASE_android --triple aarch64-unknown-linux-android30 --static-swift-stdlib --product SwiftAndroidRuntime
```

## Офіційні матеріали

- [Android Gradle Plugin 9.4.0](https://developer.android.com/build/releases/agp-9-4-0-release-notes)
- [Jetpack Compose setup](https://developer.android.com/develop/ui/compose/setup-compose-dependencies-and-compiler)
- [Compose BOM](https://developer.android.com/develop/ui/compose/bom)


