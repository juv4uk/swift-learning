package ua.juv4uk.swiftpocketguide

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@Composable
fun OfflineLabScreen(context: Context) {
    val clipboard = LocalClipboardManager.current
    val preferences = remember { context.getSharedPreferences("swift-pocket-guide", Context.MODE_PRIVATE) }
    val exercises = SwiftLabContent.exercises
    var selectedExercise by remember { mutableStateOf(exercises.first()) }
    var code by remember {
        mutableStateOf(preferences.getString("lab_code_${exercises.first().id}", exercises.first().starterCode) ?: exercises.first().starterCode)
    }
    var result by remember { mutableStateOf<SwiftLabResult?>(null) }
    var execution by remember { mutableStateOf<SwiftLabExecutionResult?>(null) }
    var completed by remember {
        mutableStateOf(preferences.getStringSet("lab_completed", emptySet())?.toSet() ?: emptySet())
    }

    fun selectExercise(exercise: SwiftLabExercise) {
        selectedExercise = exercise
        code = preferences.getString("lab_code_${exercise.id}", exercise.starterCode) ?: exercise.starterCode
        result = null
        execution = null
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("Офлайн-лабораторія Swift", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(6.dp))
            Text(
                "Пиши код без мережі, зберігай свої спроби та перевіряй вимоги вправ. Лабораторія не замінює справжній Swift-компілятор.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text("Прогрес: ${completed.size} із ${exercises.size} вправ", style = MaterialTheme.typography.labelLarge)
        }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(exercises, key = { it.id }) { exercise ->
                    FilterChip(
                        selected = selectedExercise.id == exercise.id,
                        onClick = { selectExercise(exercise) },
                        label = { Text(exercise.title) }
                    )
                }
            }
        }
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(selectedExercise.title, style = MaterialTheme.typography.titleLarge)
                    Text(selectedExercise.instruction)
                    Text("Очікуваний результат:", style = MaterialTheme.typography.labelLarge)
                    SelectionContainer { Text(selectedExercise.expectedOutput, fontFamily = FontFamily.Monospace) }
                }
            }
        }
        item {
            OutlinedTextField(
                value = code,
                onValueChange = {
                    code = it
                    preferences.edit().putString("lab_code_${selectedExercise.id}", it).apply()
                    result = null
                    execution = null
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 10,
                label = { Text("Твій Swift-код") },
                textStyle = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace),
                supportingText = { Text("Код зберігається автоматично на цьому пристрої.") }
            )
        }
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = {
                        result = SwiftLabAnalyzer.analyze(selectedExercise, code)
                        if (result?.passed == true) {
                            completed = completed + selectedExercise.id
                            preferences.edit().putStringSet("lab_completed", completed).apply()
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) { Text("Перевірити") }
                OutlinedButton(
                    onClick = {
                        code = selectedExercise.starterCode
                        preferences.edit().putString("lab_code_${selectedExercise.id}", code).apply()
                        result = null
                        execution = null
                    },
                    modifier = Modifier.weight(1f)
                ) { Text("Скинути") }
            }
        }
        item {
            OutlinedButton(
                onClick = {
                    clipboard.setText(AnnotatedString(code))
                    android.widget.Toast.makeText(context, "Код скопійовано", android.widget.Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Скопіювати код") }
        }
        item {
            OutlinedButton(
                onClick = { execution = SwiftLabInterpreter.execute(code) },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Запустити навчальний приклад ▶") }
        }
        result?.let { check ->
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (check.passed) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(if (check.passed) "Перевірку пройдено ✓" else "Потрібно допрацювати", style = MaterialTheme.typography.titleMedium)
                        Text(check.message)
                        check.details.forEach { Text(it, fontFamily = FontFamily.Monospace) }
                    }
                }
            }
        }
        execution?.let { run ->
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (run.success) MaterialTheme.colorScheme.secondaryContainer
                        else MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(if (run.success) "Результат виконання" else "Помилка навчального запуску", style = MaterialTheme.typography.titleMedium)
                        if (run.success) Text(run.output, fontFamily = FontFamily.Monospace)
                        else Text(run.error ?: "Невідома помилка")
                    }
                }
            }
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Що вміє ця версія", style = MaterialTheme.typography.titleMedium)
                    Text("• працює повністю офлайн\n• має навчальні шаблони\n• зберігає код локально\n• перевіряє базові вимоги вправ\n• запускає обмежену навчальну підмножину")
                    Text(
                        "Запуск підтримує базові let/var, числа, рядки, print, арифметику, if/else, масиви та for-in. Це навчальний інтерпретатор, а не повний Swift-компілятор. Для запуску справжнього скомпільованого Swift-коду використовуй екран «Swift на пристрої».",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
