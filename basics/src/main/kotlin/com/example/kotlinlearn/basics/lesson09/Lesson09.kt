package com.example.kotlinlearn.basics.lesson09

import com.example.kotlinlearn.basics.lesson08.StudyTask

// 扩展函数写在类外。this 是调用它的任务，省略 this 也可以读取 minutes。
fun StudyTask.fitsIn(availableMinutes: Int): Boolean {
    return this.minutes <= availableMinutes
}

// 给任务列表定义一个统计函数。本例只读取数据，不修改任务或原列表。
fun List<StudyTask>.remainingMinutes(): Int {
    return this.filter { !it.completed }.sumOf { it.minutes }
}

fun runLesson09(): String {
    val tasks: List<StudyTask> = listOf(
        StudyTask("练习 Lambda", 20),
        StudyTask("复习集合", 10, completed = true),
        StudyTask("运行 App", 15),
    )
    // 练习：依次改成 20、10、0；任务的预计分钟数保持为正数。
    val availableMinutes = 15

    // 第一种写法：用已经学过的循环筛选未完成任务。
    val pendingByLoop = mutableListOf<StudyTask>()
    var minutesByLoop = 0
    for (task in tasks) {
        if (!task.completed) {
            pendingByLoop.add(task)
            minutesByLoop += task.minutes
        }
    }

    // (StudyTask) -> Boolean：接收一个任务，返回一个真假值。
    // Lambda 的最后一个表达式就是这里的结果，不需要写 return。
    val isPending: (StudyTask) -> Boolean = { task -> !task.completed }
    val pending = tasks.filter(isPending)
    // 同样的规则直接传入 filter；单个参数还可以简写成 it。
    val pendingByNamedLambda = tasks.filter { task -> !task.completed }
    val pendingByIt = tasks.filter { !it.completed }

    // map 转换每个元素：任务对象 -> 任务名称。它不是第七课的 Map 类型。
    val pendingTitles: List<String> = pending.map { it.title }
    val totalMinutes: Int = pending.sumOf { it.minutes }
    // Lambda 可以读取外部的 availableMinutes。
    val shortTasks = pending.filter { it.fitsIn(availableMinutes) }
    val shortTitles = shortTasks.map { it.title }

    // map + copy 生成完成副本，原任务对象的 completed 不会改变。
    val allDone = tasks.map { it.copy(completed = true) }
    val emptyTasks = emptyList<StudyTask>()

    return "Lambda：把判断规则传给函数\n" +
        "第一个任务待完成：${isPending(tasks[0])}\n" +
        "循环筛选与 Lambda 筛选相同：${pendingByLoop == pending}\n" +
        "显式参数与 it 写法相同：${pendingByNamedLambda == pendingByIt}\n" +
        "\nfilter、map、sumOf\n" +
        "待完成任务：$pendingTitles\n" +
        "循环统计：$minutesByLoop 分钟\n" +
        "sumOf 统计：$totalMinutes 分钟\n" +
        "\n扩展函数：任务与列表\n" +
        "$availableMinutes 分钟内可完成的待办：$shortTitles\n" +
        "列表扩展统计：${tasks.remainingMinutes()} 分钟\n" +
        "\n原数据与边界情况\n" +
        "原列表数量：${tasks.size}\n" +
        "原列表完成状态：${tasks.map { it.completed }}\n" +
        "完成副本的剩余时间：${allDone.remainingMinutes()} 分钟\n" +
        "空列表的剩余时间：${emptyTasks.remainingMinutes()} 分钟"
}

fun main() {
    println(runLesson09())
}
