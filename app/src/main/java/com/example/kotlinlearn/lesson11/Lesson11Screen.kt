package com.example.kotlinlearn.lesson11

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlinlearn.KotlinLearnTheme
import com.example.kotlinlearn.R

@Composable
fun Lesson11Content(modifier: Modifier = Modifier) {
    // remember 跨重组保留状态；离开本课或 Activity 重建后会重新初始化。
    var draft by remember { mutableStateOf("") }
    var todo by remember { mutableStateOf(TodoState()) }

    TodoPanel(
        draft = draft,
        todo = todo,
        onDraftChange = { draft = it },
        onAdd = {
            if (draft.isNotBlank()) {
                todo = todo.addTask(draft)
                draft = ""
            }
        },
        onCompletedChange = { id, checked -> todo = todo.setCompleted(id, checked) },
        onDelete = { id -> todo = todo.removeTask(id) },
        modifier = modifier,
    )
}

// 状态通过参数向下传，操作通过回调向上传：子界面不单独保存一份任务数据。
@Composable
private fun TodoPanel(
    draft: String,
    todo: TodoState,
    onDraftChange: (String) -> Unit,
    onAdd: () -> Unit,
    onCompletedChange: (Int, Boolean) -> Unit,
    onDelete: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = draft,
            onValueChange = onDraftChange,
            label = { Text(stringResource(R.string.lesson_eleven_input_label)) },
            placeholder = { Text(stringResource(R.string.lesson_eleven_input_hint)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = onAdd,
            enabled = draft.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.lesson_eleven_add))
        }
        // 直接从当前列表计算，避免额外维护一个可能不一致的计数状态。
        Text(
            text = stringResource(R.string.lesson_eleven_summary, todo.tasks.size, todo.remainingCount()),
            style = MaterialTheme.typography.titleMedium,
        )
        if (todo.tasks.isEmpty()) {
            Text(stringResource(R.string.lesson_eleven_empty))
        }
        // 外层课程页已经可以滚动；少量练习数据用 Column，避免嵌套无界 LazyColumn。
        todo.tasks.forEach { task ->
            key(task.id) {
                TodoRow(
                    task = task,
                    onCompletedChange = { checked -> onCompletedChange(task.id, checked) },
                    onDelete = { onDelete(task.id) },
                )
            }
        }
        Text(
            text = stringResource(R.string.lesson_eleven_memory_note),
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
private fun TodoRow(task: TodoItem, onCompletedChange: (Boolean) -> Unit, onDelete: () -> Unit) {
    val deleteDescription = stringResource(R.string.lesson_eleven_delete_description, task.title)
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            // 整个文字区域可勾选；Checkbox 自身不再注册第二个点击回调。
            Row(
                modifier = Modifier
                    .weight(1f)
                    .toggleable(value = task.completed, role = Role.Checkbox, onValueChange = onCompletedChange)
                    .heightIn(min = 48.dp)
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(checked = task.completed, onCheckedChange = null)
                Text(
                    text = task.title,
                    modifier = Modifier.weight(1f).padding(start = 8.dp),
                    textDecoration = if (task.completed) TextDecoration.LineThrough else TextDecoration.None,
                )
            }
            TextButton(onClick = onDelete, modifier = Modifier.semantics { contentDescription = deleteDescription }) {
                Text(stringResource(R.string.lesson_eleven_delete))
            }
        }
    }
}

@Preview(name = "待办交互", showBackground = true, widthDp = 360, locale = "zh")
@Composable
private fun Lesson11Preview() {
    KotlinLearnTheme {
        Surface {
            Lesson11Content(Modifier.verticalScroll(rememberScrollState()).padding(16.dp))
        }
    }
}

@Preview(name = "窄屏与长标题", showBackground = true, widthDp = 320, fontScale = 1.3f, locale = "zh")
@Composable
private fun TodoPanelPreview() {
    val sample = TodoState()
        .addTask("练习输入、添加和勾选完成，观察界面如何更新")
        .addTask("复习 Lambda")
        .setCompleted(2, true)
    KotlinLearnTheme {
        Surface {
            TodoPanel(
                draft = "阅读 Compose 文档",
                todo = sample,
                onDraftChange = {}, onAdd = {}, onCompletedChange = { _, _ -> }, onDelete = {},
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}
