package ua.juv4uk.swiftpocketguide

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

/**
 * Native Swift runtime verification screen. It contains no browser-based editor;
 * executable Swift code comes from the library packaged in this APK.
 */
@Composable
fun SwiftRuntimeScreen() {
    var runtimeStatus by remember { mutableStateOf("Натисни кнопку, щоб виконати перевірку.") }
    var verified by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("Swift на пристрої", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Локальний нативний модуль Swift для Android ARM64. Екран не відкриває зовнішніх редакторів або вебсайтів.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Приклад Swift", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "let answer = 20 + 22\nprint(answer)",
                        fontFamily = FontFamily.Monospace,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        "Наведений приклад показує синтаксис Swift. Наразі APK виконує перевірку заздалегідь скомпільованої Swift-функції, а не компілює довільний текст із редактора.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        item {
            Button(
                onClick = {
                    runtimeStatus = try {
                        val result = SwiftNative.nativeAdd(20, 22)
                        val major = SwiftNative.nativeMajorVersion()
                        if (result == 42 && major == 6) {
                            verified = true
                            "Успіх: нативна бібліотека Swift $major повернула $result. Виконання відбулося локально."
                        } else {
                            verified = false
                            "Неочікуваний результат: $result (Swift $major)."
                        }
                    } catch (error: Throwable) {
                        verified = false
                        "Нативний Swift-модуль недоступний: ${error.javaClass.simpleName}: ${error.message ?: "помилка завантаження"}"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Запустити локальну перевірку Swift")
            }
        }
        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (verified) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(runtimeStatus, Modifier.padding(16.dp), style = MaterialTheme.typography.bodyMedium)
            }
        }
        item {
            Text("Що вже працює", style = MaterialTheme.typography.titleMedium)
            Text("• Swift-бібліотека зібрана для arm64-v8a\n• Kotlin викликає її через JNI\n• перевірка працює без мережі")
        }
        item {
            Text("Для написання вправ", style = MaterialTheme.typography.titleMedium)
            Text("Відкрий «Офлайн-лабораторію Swift» у розділі «Ще». Там можна редагувати шаблони, зберігати власний код і перевіряти навчальні вимоги без мережі. Повного компілятора довільного коду в APK поки немає.")
        }
    }
}
