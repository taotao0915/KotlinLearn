package com.example.kotlinlearn.lesson10

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlinlearn.KotlinLearnTheme
import com.example.kotlinlearn.R

/** 第十课：这是界面代码，在 App 或 Android Studio Preview 中查看。 */
@Composable
fun Lesson10Content(modifier: Modifier = Modifier) {
    // 练习 1：只修改这三项演示数据，再刷新预览或重新运行 App。
    val learnerName = "小明"
    val learningGoal = "做一个自己的 Android App"
    val dailyMinutes = 20

    LearningProfileCard(
        name = learnerName,
        goal = learningGoal,
        dailyMinutes = dailyMinutes,
        modifier = modifier,
    )
}

// 和第五课的函数一样，参数由调用者传入；加上 @Composable 后可以描述界面。
@Composable
fun LearningProfileCard(
    name: String,
    goal: String,
    dailyMinutes: Int,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            // 练习 2：把卡片内容四周的留白从 20.dp 改成 32.dp。
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = stringResource(R.string.lesson_ten_card_title),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = stringResource(R.string.lesson_ten_greeting, name),
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = stringResource(R.string.lesson_ten_goal, goal),
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = stringResource(R.string.lesson_ten_daily_minutes, dailyMinutes),
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = stringResource(R.string.lesson_ten_card_hint),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

// Preview 只供 Android Studio 预览使用；App 使用上面的 Lesson10Content。
@Preview(name = "学习名片", showBackground = true, widthDp = 360, locale = "zh")
@Preview(name = "窄屏大字体", showBackground = true, widthDp = 320, fontScale = 1.3f, locale = "zh")
@Composable
private fun Lesson10Preview() {
    KotlinLearnTheme {
        Surface {
            Lesson10Content(modifier = Modifier.padding(16.dp))
        }
    }
}
