# Swift Pocket Guide — Android

**Swift Pocket Guide** — офлайн-довідник українською для тих, хто починає вивчати Swift. Це супутній застосунок до [swift-learning](https://github.com/juv4uk/swift-learning).

## Можливості першої версії

- 12 тематичних карток: від першої програми до async/await.
- Пояснення українською та приклади Swift-коду.
- Пошук і фільтр тем за категоріями.
- Словник базових термінів.
- Закладки та позначення вивчених тем.
- Локальне збереження прогресу через SharedPreferences.
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
│   └── HandbookContent.kt
├── app/src/test/
├── app/build.gradle.kts
├── build.gradle.kts
└── settings.gradle.kts
```

## Межі першої версії

Це локальний довідник, а не інтерактивний компілятор Swift. Приклади можна виділяти й копіювати вручну; застосунок не виконує Swift-код. Подальші кроки: тести знань, інтерактивні картки, темна тема та резервне копіювання прогресу.

## Офіційні матеріали

- [Android Gradle Plugin 9.4.0](https://developer.android.com/build/releases/agp-9-4-0-release-notes)
- [Jetpack Compose setup](https://developer.android.com/develop/ui/compose/setup-compose-dependencies-and-compiler)
- [Compose BOM](https://developer.android.com/develop/ui/compose/bom)
