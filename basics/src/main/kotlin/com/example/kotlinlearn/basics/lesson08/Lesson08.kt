package com.example.kotlinlearn.basics.lesson08

// 接口约定：实现它的对象能够返回自己的说明文字。
interface Describable {
    fun describe(): String
}

// 普通类：属性保存数据，成员函数描述行为。
class Learner(val name: String, var completedLessons: Int = 0) : Describable {
    fun completeLesson() {
        completedLessons++
    }

    override fun describe(): String {
        return "$name 已完成 $completedLessons 课"
    }
}

// 数据类：把一个任务的相关数据放在一起。
// data 自动提供基于主构造函数属性的 equals、toString、copy 等功能。
data class StudyTask(
    val title: String,
    val minutes: Int,
    val completed: Boolean = false,
) : Describable {
    override fun describe(): String {
        val status = if (completed) "已完成" else "待完成"
        return "$title：$minutes 分钟，$status"
    }
}

// 参数使用接口类型：Learner 和 StudyTask 都可以传进来。
fun describeItem(item: Describable): String {
    return item.describe()
}

fun runLesson08(): String {
    // 练习 1：修改姓名，或再调用一次 completeLesson()。
    val learner = Learner(name = "小明")
    val before = learner.describe()
    learner.completeLesson()

    // 练习 2：修改预计分钟数，观察任务列表和剩余时间。
    val task = StudyTask(title = "练习类", minutes = 20)
    val finishedTask = task.copy(completed = true)
    val sameData = task.copy()

    // 复用第七课的 List，以及第四课的 for 和第三课的 if。
    val tasks: List<StudyTask> = listOf(
        task,
        StudyTask(title = "复习集合", minutes = 10, completed = true),
    )
    var taskLines = ""
    var remainingMinutes = 0
    for (item in tasks) {
        taskLines += "${item.describe()}\n"
        if (!item.completed) {
            remainingMinutes += item.minutes
        }
    }

    return "class：属性与行为\n" +
        "调用前：$before\n" +
        "调用后：${learner.describe()}\n" +
        "\ndata class：数据与复制\n" +
        "自动生成的文字：$task\n" +
        "原任务完成状态：${task.completed}\n" +
        "副本完成状态：${finishedTask.completed}\n" +
        "相同数据是否相等（==）：${task == sameData}\n" +
        "是否同一个对象（===）：${task === sameData}\n" +
        "原任务与完成副本是否相等：${task == finishedTask}\n" +
        "\nList<StudyTask>：任务清单\n" +
        taskLines +
        "剩余预计时间：$remainingMinutes 分钟\n" +
        "\ninterface：同一个函数处理不同对象\n" +
        "学习者：${describeItem(learner)}\n" +
        "任务：${describeItem(finishedTask)}"
}

fun main() {
    println(runLesson08())
}