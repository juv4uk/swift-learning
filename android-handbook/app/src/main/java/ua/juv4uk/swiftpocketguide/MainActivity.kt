package ua.juv4uk.swiftpocketguide

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private enum class GuideTab(val title: String, val symbol: String) {
    HOME("Головна", "⌂"), TOPICS("Теми", "▤"), QUIZ("Тест", "?"),
    GLOSSARY("Словник", "А"), PROGRESS("Прогрес", "✓"), PLAYGROUND("Swift IDE", "▶")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SwiftPocketGuideApp() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwiftPocketGuideApp() {
    val context = LocalContext.current
    val preferences = remember { context.getSharedPreferences("swift-pocket-guide", Context.MODE_PRIVATE) }
    var completed by remember { mutableStateOf(preferences.getStringSet("completed", emptySet())?.toSet() ?: emptySet()) }
    var bookmarks by remember { mutableStateOf(preferences.getStringSet("bookmarks", emptySet())?.toSet() ?: emptySet()) }
    var darkTheme by remember { mutableStateOf(preferences.getBoolean("dark_theme", false)) }
    var selectedTab by remember { mutableStateOf(GuideTab.HOME) }
    var selectedTopic by remember { mutableStateOf<HandbookTopic?>(null) }
    var search by remember { mutableStateOf("") }

    fun toggle(current: Set<String>, id: String, key: String, update: (Set<String>) -> Unit) {
        val next = if (id in current) current - id else current + id
        update(next)
        preferences.edit().putStringSet(key, next).apply()
    }

    MaterialTheme(colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(selectedTopic?.title ?: "Swift Pocket Guide")
                            if (selectedTopic == null) Text("Кишеньковий довідник початківця", style = MaterialTheme.typography.labelSmall)
                        }
                    },
                    navigationIcon = {
                        if (selectedTopic != null) TextButton(onClick = { selectedTopic = null }) { Text("← Назад") }
                    },
                    actions = {
                        IconButton(onClick = {
                            darkTheme = !darkTheme
                            preferences.edit().putBoolean("dark_theme", darkTheme).apply()
                        }) { Text(if (darkTheme) "☀" else "☾", style = MaterialTheme.typography.titleLarge) }
                    }
                )
            },
            bottomBar = {
                if (selectedTopic == null) {
                    NavigationBar {
                        GuideTab.entries.forEach { tab ->
                            NavigationBarItem(
                                selected = selectedTab == tab,
                                onClick = { selectedTab = tab },
                                icon = { Text(tab.symbol) },
                                label = { Text(tab.title) }
                            )
                        }
                    }
                }
            }
        ) { padding ->
            Surface(Modifier.fillMaxSize().padding(padding), color = MaterialTheme.colorScheme.background) {
                val topic = selectedTopic
                if (topic != null) {
                    TopicDetail(
                        topic, topic.id in completed, topic.id in bookmarks,
                        onComplete = { toggle(completed, topic.id, "completed") { completed = it } },
                        onBookmark = { toggle(bookmarks, topic.id, "bookmarks") { bookmarks = it } }
                    )
                } else when (selectedTab) {
                    GuideTab.HOME -> HomeScreen(completed.size, { selectedTab = GuideTab.TOPICS }, { selectedTab = GuideTab.QUIZ }, { selectedTab = GuideTab.PLAYGROUND }) { selectedTopic = it }
                    GuideTab.TOPICS -> TopicsScreen(search, { search = it }, completed, bookmarks) { selectedTopic = it }
                    GuideTab.QUIZ -> QuizScreen()
                    GuideTab.PLAYGROUND -> SwiftPlaygroundScreen()
                    GuideTab.GLOSSARY -> GlossaryScreen(search, { search = it })
                    GuideTab.PROGRESS -> ProgressScreen(completed, bookmarks) { selectedTopic = it }
                }
            }
        }
    }
}

@Composable
private fun HomeScreen(
    completedCount: Int, onOpenTopics: () -> Unit, onOpenQuiz: () -> Unit,
    onOpenPlayground: () -> Unit, onOpenTopic: (HandbookTopic) -> Unit
) {
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer), shape = RoundedCornerShape(28.dp)) {
                Column(Modifier.padding(22.dp)) {
                    Text("Вивчай Swift у власному темпі", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("Короткі пояснення, приклади коду та словник — усе під рукою, навіть офлайн.")
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = onOpenTopics) { Text("Почати навчання") }
                    OutlinedButton(onClick = onOpenQuiz, modifier = Modifier.fillMaxWidth()) { Text("Перевірити знання →") }
                    Button(onClick = onOpenPlayground, modifier = Modifier.fillMaxWidth()) { Text("Відкрити Swift IDE ▶") }
                }
            }
        }
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("Теми", HandbookContent.topics.size.toString(), Modifier.weight(1f))
                StatCard("Вивчено", "$completedCount / ${HandbookContent.topics.size}", Modifier.weight(1f))
            }
        }
        item { Text("З чого почати", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) }
        items(HandbookContent.topics.take(3)) { topic -> TopicCard(topic, false, false) { onOpenTopic(topic) } }
        item { Text("Порада: набирай приклади самостійно та змінюй їх, щоб побачити результат.", color = MaterialTheme.colorScheme.onSurfaceVariant) }
    }
}

@Composable
private fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier) {
        Column(Modifier.padding(16.dp)) {
            Text(value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun TopicsScreen(
    search: String, onSearch: (String) -> Unit, completed: Set<String>,
    bookmarks: Set<String>, onOpen: (HandbookTopic) -> Unit
) {
    val categories = listOf("Усі") + HandbookContent.topics.map { it.category }.distinct()
    var category by remember { mutableStateOf("Усі") }
    val filtered = HandbookContent.topics.filter { topic ->
        (category == "Усі" || topic.category == category) &&
            (search.isBlank() || listOf(topic.title, topic.subtitle, topic.explanation, topic.category).any { it.contains(search, true) })
    }
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item { OutlinedTextField(search, onSearch, Modifier.fillMaxWidth(), label = { Text("Знайти тему") }, singleLine = true) }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { item -> FilterChip(selected = category == item, onClick = { category = item }, label = { Text(item) }) }
            }
        }
        items(filtered, key = { it.id }) { topic -> TopicCard(topic, topic.id in completed, topic.id in bookmarks) { onOpen(topic) } }
        if (filtered.isEmpty()) item { Text("Нічого не знайдено. Спробуй інший запит.") }
    }
}

@Composable
private fun TopicCard(topic: HandbookTopic, completed: Boolean, bookmarked: Boolean, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(topic.category, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.weight(1f))
                if (completed) Text("✓ Вивчено", style = MaterialTheme.typography.labelSmall)
                if (bookmarked) Text("★", color = MaterialTheme.colorScheme.tertiary)
            }
            Text(topic.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(topic.subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun TopicDetail(
    topic: HandbookTopic, isCompleted: Boolean, isBookmarked: Boolean,
    onComplete: () -> Unit, onBookmark: () -> Unit
) {
    val clipboard = LocalClipboardManager.current
    val context = LocalContext.current
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text(topic.category, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            Text(topic.subtitle, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Text(topic.explanation, style = MaterialTheme.typography.bodyLarge)
        }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Приклад коду", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(Modifier.weight(1f))
                TextButton(onClick = {
                    clipboard.setText(AnnotatedString(topic.code))
                    Toast.makeText(context, "Код скопійовано", Toast.LENGTH_SHORT).show()
                }) { Text("Копіювати") }
            }
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), modifier = Modifier.fillMaxWidth()) {
                SelectionContainer { Text(topic.code, Modifier.padding(16.dp), style = MaterialTheme.typography.bodyMedium) }
            }
        }
        item {
            Text("Запам'ятай", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Card { Text(topic.takeaway, Modifier.padding(16.dp)) }
        }
        item {
            Button(onClick = onComplete, modifier = Modifier.fillMaxWidth()) {
                Text(if (isCompleted) "Позначено як вивчене ✓" else "Позначити як вивчене")
            }
            OutlinedButton(onClick = onBookmark, modifier = Modifier.fillMaxWidth()) {
                Text(if (isBookmarked) "Прибрати із закладок ★" else "Додати в закладки ☆")
            }
        }
    }
}

@Composable
private fun QuizScreen() {
    val questions = QuizContent.questions
    var questionIndex by remember { mutableIntStateOf(0) }
    var selectedAnswer by remember { mutableIntStateOf(-1) }
    var score by remember { mutableIntStateOf(0) }
    var finished by remember { mutableStateOf(false) }
    val question = questions[questionIndex]

    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            Text("Мінітест Swift", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("Питання ${questionIndex + 1} із ${questions.size} · правильних відповідей: $score")
            LinearProgressIndicator(progress = { (questionIndex + if (selectedAnswer >= 0) 1 else 0).toFloat() / questions.size }, modifier = Modifier.fillMaxWidth())
        }
        if (finished) {
            item {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                    Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Тест завершено!", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Text("$score із ${questions.size} правильних відповідей", style = MaterialTheme.typography.titleLarge)
                        Text(if (score == questions.size) "Чудово! Усі відповіді правильні." else "Гарна практика. Переглянь теми й спробуй ще раз.")
                        Button(onClick = { questionIndex = 0; selectedAnswer = -1; score = 0; finished = false }) { Text("Пройти ще раз") }
                    }
                }
            }
        } else {
            item { Text(question.prompt, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold) }
            items(question.options.size) { index ->
                val chosen = selectedAnswer == index
                val correct = index == question.correctIndex
                OutlinedButton(
                    onClick = {
                        if (selectedAnswer == -1) {
                            selectedAnswer = index
                            if (correct) score++
                        }
                    },
                    enabled = selectedAnswer == -1,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selectedAnswer >= 0 && correct) MaterialTheme.colorScheme.primaryContainer
                            else if (chosen && !correct) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Text(question.options[index], modifier = Modifier.fillMaxWidth())
                }
            }
            if (selectedAnswer >= 0) {
                item {
                    Card {
                        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(if (selectedAnswer == question.correctIndex) "Правильно! ✓" else "Не зовсім. Правильна відповідь виділена.")
                            Text(question.explanation)
                        }
                    }
                    Button(onClick = {
                        if (questionIndex == questions.lastIndex) finished = true
                        else { questionIndex++; selectedAnswer = -1 }
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text(if (questionIndex == questions.lastIndex) "Побачити результат" else "Наступне питання")
                    }
                }
            }
        }
    }
}

@Composable
private fun GlossaryScreen(search: String, onSearch: (String) -> Unit) {
    val entries = HandbookContent.glossary.filter { search.isBlank() || it.term.contains(search, true) || it.meaning.contains(search, true) }
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item { OutlinedTextField(search, onSearch, Modifier.fillMaxWidth(), label = { Text("Пошук терміна") }, singleLine = true) }
        items(entries) { entry ->
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(entry.term, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(4.dp))
                    Text(entry.meaning, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        if (entries.isEmpty()) item { Text("Термінів за таким запитом немає.") }
    }
}

@Composable
private fun ProgressScreen(completed: Set<String>, bookmarks: Set<String>, onOpen: (HandbookTopic) -> Unit) {
    val percent = completed.size.toFloat() / HandbookContent.topics.size.toFloat()
    val saved = HandbookContent.topics.filter { it.id in bookmarks }
    val done = HandbookContent.topics.filter { it.id in completed }
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("Твій прогрес", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("${completed.size} із ${HandbookContent.topics.size} тем вивчено")
            LinearProgressIndicator(progress = { percent }, modifier = Modifier.fillMaxWidth())
        }
        item { Text("Закладки", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) }
        if (saved.isEmpty()) item { Text("Поки немає закладок. Додавай їх у картках тем.") }
        else items(saved) { TopicCard(it, it.id in completed, true) { onOpen(it) } }
        item { Text("Вивчені теми", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) }
        if (done.isEmpty()) item { Text("Тут з'являться теми, які ти позначиш як вивчені.") }
        else items(done) { TopicCard(it, true, it.id in bookmarks) { onOpen(it) } }
    }
}
