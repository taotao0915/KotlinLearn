package com.example.kotlinlearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlinlearn.basics.runLesson01
import com.example.kotlinlearn.basics.lesson02.runLesson02
import com.example.kotlinlearn.basics.lesson03.runLesson03
import com.example.kotlinlearn.basics.lesson04.runLesson04
import com.example.kotlinlearn.basics.lesson05.runLesson05
import com.example.kotlinlearn.basics.lesson06.runLesson06

// 学习 Kotlin 基础时，先把这个文件当作“显示练习结果的窗口”。
// 你要修改的练习代码在 basics 模块中，各课对应 Lesson01.kt 到 Lesson06.kt。
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinLearnTheme {
                LessonScreen()
            }
        }
    }
}

@Composable
private fun KotlinLearnTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF4256A6),
            background = Color(0xFFF8F9FF),
            surface = Color(0xFFF8F9FF),
        ),
        content = content,
    )
}

@Composable
private fun LessonScreen() {
    // 这里是 Compose 的界面状态；学到 Android 界面时再详细讲解。
    var lessonNumber by rememberSaveable { mutableIntStateOf(6) }
    // 切换课程时清空旧结果，避免把上一课的输出当成本课的结果。
    var output by rememberSaveable(lessonNumber) { mutableStateOf("") }
    val titleRes = when (lessonNumber) {
        1 -> R.string.lesson_title
        2 -> R.string.lesson_two_title
        3 -> R.string.lesson_three_title
        4 -> R.string.lesson_four_title
        5 -> R.string.lesson_five_title
        else -> R.string.lesson_six_title
    }
    val introRes = when (lessonNumber) {
        1 -> R.string.lesson_intro
        2 -> R.string.lesson_two_intro
        3 -> R.string.lesson_three_intro
        4 -> R.string.lesson_four_intro
        5 -> R.string.lesson_five_intro
        else -> R.string.lesson_six_intro
    }
    val conceptsRes = when (lessonNumber) {
        1 -> R.string.concepts_body
        2 -> R.string.lesson_two_concepts
        3 -> R.string.lesson_three_concepts
        4 -> R.string.lesson_four_concepts
        5 -> R.string.lesson_five_concepts
        else -> R.string.lesson_six_concepts
    }
    val runLabelRes = when (lessonNumber) {
        1 -> R.string.run_lesson
        2 -> R.string.run_lesson_two
        3 -> R.string.run_lesson_three
        4 -> R.string.run_lesson_four
        5 -> R.string.run_lesson_five
        else -> R.string.run_lesson_six
    }
    val exerciseRes = when (lessonNumber) {
        1 -> R.string.exercise
        2 -> R.string.lesson_two_exercise
        3 -> R.string.lesson_three_exercise
        4 -> R.string.lesson_four_exercise
        5 -> R.string.lesson_five_exercise
        else -> R.string.lesson_six_exercise
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(stringResource(R.string.app_name), style = MaterialTheme.typography.headlineLarge)
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                FilterChip(
                    selected = lessonNumber == 1,
                    onClick = { lessonNumber = 1 },
                    label = { Text(stringResource(R.string.lesson_one)) },
                )
                FilterChip(
                    selected = lessonNumber == 2,
                    onClick = { lessonNumber = 2 },
                    label = { Text(stringResource(R.string.lesson_two)) },
                )
                FilterChip(
                    selected = lessonNumber == 3,
                    onClick = { lessonNumber = 3 },
                    label = { Text(stringResource(R.string.lesson_three)) },
                )
                FilterChip(
                    selected = lessonNumber == 4,
                    onClick = { lessonNumber = 4 },
                    label = { Text(stringResource(R.string.lesson_four)) },
                )
                FilterChip(
                    selected = lessonNumber == 5,
                    onClick = { lessonNumber = 5 },
                    label = { Text(stringResource(R.string.lesson_five)) },
                )
                FilterChip(
                    selected = lessonNumber == 6,
                    onClick = { lessonNumber = 6 },
                    label = { Text(stringResource(R.string.lesson_six)) },
                )
            }
            Text(
                stringResource(titleRes),
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                stringResource(introRes),
                style = MaterialTheme.typography.bodyLarge,
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(stringResource(R.string.concepts_title), style = MaterialTheme.typography.titleMedium)
                    Text(stringResource(conceptsRes))
                }
            }

            Button(
                onClick = {
                    output = when (lessonNumber) {
                        1 -> runLesson01()
                        2 -> runLesson02()
                        3 -> runLesson03()
                        4 -> runLesson04()
                        5 -> runLesson05()
                        else -> runLesson06()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(runLabelRes))
            }

            Text(stringResource(R.string.output_title), style = MaterialTheme.typography.titleMedium)
            Card(modifier = Modifier.fillMaxWidth()) {
                SelectionContainer {
                    Text(
                        text = output.ifEmpty { stringResource(R.string.output_placeholder) },
                        modifier = Modifier.padding(20.dp),
                        fontFamily = FontFamily.Monospace,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }

            OutlinedButton(
                onClick = { output = "" },
                enabled = output.isNotEmpty(),
            ) {
                Text(stringResource(R.string.clear_output))
            }

            Text(
                stringResource(exerciseRes),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showBackground = true, locale = "zh", widthDp = 360)
@Composable
private fun LessonScreenPreview() {
    KotlinLearnTheme {
        LessonScreen()
    }
}
