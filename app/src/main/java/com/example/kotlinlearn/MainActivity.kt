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
import androidx.compose.foundation.layout.imePadding
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
import com.example.kotlinlearn.basics.lesson07.runLesson07
import com.example.kotlinlearn.basics.lesson08.runLesson08
import com.example.kotlinlearn.basics.lesson09.runLesson09
import com.example.kotlinlearn.lesson10.Lesson10Content
import com.example.kotlinlearn.lesson11.Lesson11Content

// 这里是 Android 入口与课程选择页面，第十课开始学习它的界面结构。
// 前九课在 basics 模块；名片和待办页面分别在 app 的 lesson10、lesson11 目录。
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
internal fun KotlinLearnTheme(content: @Composable () -> Unit) {
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
    // 课程编号属于界面状态；第十一课开始学习状态与事件。
    var lessonNumber by rememberSaveable { mutableIntStateOf(11) }
    // 切换课程时清空旧结果，避免把上一课的输出当成本课的结果。
    var output by rememberSaveable(lessonNumber) { mutableStateOf("") }
    val titleRes = when (lessonNumber) {
        1 -> R.string.lesson_title
        2 -> R.string.lesson_two_title
        3 -> R.string.lesson_three_title
        4 -> R.string.lesson_four_title
        5 -> R.string.lesson_five_title
        6 -> R.string.lesson_six_title
        7 -> R.string.lesson_seven_title
        8 -> R.string.lesson_eight_title
        9 -> R.string.lesson_nine_title
        10 -> R.string.lesson_ten_title
        else -> R.string.lesson_eleven_title
    }
    val introRes = when (lessonNumber) {
        1 -> R.string.lesson_intro
        2 -> R.string.lesson_two_intro
        3 -> R.string.lesson_three_intro
        4 -> R.string.lesson_four_intro
        5 -> R.string.lesson_five_intro
        6 -> R.string.lesson_six_intro
        7 -> R.string.lesson_seven_intro
        8 -> R.string.lesson_eight_intro
        9 -> R.string.lesson_nine_intro
        10 -> R.string.lesson_ten_intro
        else -> R.string.lesson_eleven_intro
    }
    val conceptsRes = when (lessonNumber) {
        1 -> R.string.concepts_body
        2 -> R.string.lesson_two_concepts
        3 -> R.string.lesson_three_concepts
        4 -> R.string.lesson_four_concepts
        5 -> R.string.lesson_five_concepts
        6 -> R.string.lesson_six_concepts
        7 -> R.string.lesson_seven_concepts
        8 -> R.string.lesson_eight_concepts
        9 -> R.string.lesson_nine_concepts
        10 -> R.string.lesson_ten_concepts
        else -> R.string.lesson_eleven_concepts
    }
    val runLabelRes = when (lessonNumber) {
        1 -> R.string.run_lesson
        2 -> R.string.run_lesson_two
        3 -> R.string.run_lesson_three
        4 -> R.string.run_lesson_four
        5 -> R.string.run_lesson_five
        6 -> R.string.run_lesson_six
        7 -> R.string.run_lesson_seven
        8 -> R.string.run_lesson_eight
        else -> R.string.run_lesson_nine
    }
    val exerciseRes = when (lessonNumber) {
        1 -> R.string.exercise
        2 -> R.string.lesson_two_exercise
        3 -> R.string.lesson_three_exercise
        4 -> R.string.lesson_four_exercise
        5 -> R.string.lesson_five_exercise
        6 -> R.string.lesson_six_exercise
        7 -> R.string.lesson_seven_exercise
        8 -> R.string.lesson_eight_exercise
        9 -> R.string.lesson_nine_exercise
        10 -> R.string.lesson_ten_exercise
        else -> R.string.lesson_eleven_exercise
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
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
                FilterChip(
                    selected = lessonNumber == 7,
                    onClick = { lessonNumber = 7 },
                    label = { Text(stringResource(R.string.lesson_seven)) },
                )
                FilterChip(
                    selected = lessonNumber == 8,
                    onClick = { lessonNumber = 8 },
                    label = { Text(stringResource(R.string.lesson_eight)) },
                )
                FilterChip(
                    selected = lessonNumber == 9,
                    onClick = { lessonNumber = 9 },
                    label = { Text(stringResource(R.string.lesson_nine)) },
                )
                FilterChip(
                    selected = lessonNumber == 10,
                    onClick = { lessonNumber = 10 },
                    label = { Text(stringResource(R.string.lesson_ten)) },
                )
                FilterChip(
                    selected = lessonNumber == 11,
                    onClick = { lessonNumber = 11 },
                    label = { Text(stringResource(R.string.lesson_eleven)) },
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

            if (lessonNumber == 11) {
                Lesson11Content()
            } else if (lessonNumber == 10) {
                Lesson10Content()
            } else {
                Button(
                    onClick = {
                        output = when (lessonNumber) {
                            1 -> runLesson01()
                            2 -> runLesson02()
                            3 -> runLesson03()
                            4 -> runLesson04()
                            5 -> runLesson05()
                            6 -> runLesson06()
                            7 -> runLesson07()
                            8 -> runLesson08()
                            else -> runLesson09()
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
