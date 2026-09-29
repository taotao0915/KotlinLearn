package com.example.kotlinlearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

// 第一课先把这个文件当作“显示练习结果的窗口”。
// 你要修改的练习代码在 basics 模块的 Lesson01.kt 中。
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
    var output by rememberSaveable { mutableStateOf("") }

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
            Text(stringResource(R.string.lesson_title), style = MaterialTheme.typography.titleLarge)
            Text(stringResource(R.string.lesson_intro), style = MaterialTheme.typography.bodyLarge)

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(stringResource(R.string.concepts_title), style = MaterialTheme.typography.titleMedium)
                    Text(stringResource(R.string.concepts_body))
                }
            }

            Button(
                onClick = { output = runLesson01() },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.run_lesson))
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

            Text(stringResource(R.string.exercise), style = MaterialTheme.typography.bodyMedium)
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
