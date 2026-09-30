package com.example.kotlinlearn.basics.lesson07

/** 第七课：用 List、Set、Map 管理学习待办事项。 */
fun runLesson07(): String {
    // List<String>：按顺序保存字符串，允许重复；索引从 0 开始。
    val tasks: List<String> = listOf("复习变量", "练习集合", "复习变量")
    var taskLines = ""
    var number = 1
    for (task in tasks) {
        taskLines += "$number. $task\n"
        number++
    }

    // MutableList 支持增删改。val 限制重新赋值，不禁止修改列表内容。
    val editableTasks: MutableList<String> = mutableListOf("复习变量", "练习集合")
    editableTasks.add("运行 App")
    editableTasks[0] = "复习函数"
    editableTasks.remove("练习集合")

    // Set<String>：重复的标签只保留一份；这里用数量和包含关系观察结果。
    val tags: Set<String> = setOf("Kotlin", "Android", "Kotlin")

    // Map<String, Int>：用任务名称（键）查找预计分钟数（值）。
    // to 把一个键和一个值配成一对；键不能重复，值可以重复。
    val minutesByTask: Map<String, Int> = mapOf("复习变量" to 10, "练习集合" to 20)
    // 练习：依次改成 "复习变量"、"运行 App"。
    val selectedTask = "练习集合"
    val minutes: Int? = minutesByTask[selectedTask]
    val timeMessage = if (minutes == null) "暂未安排时间" else "$minutes 分钟"

    return "List：有顺序，可以重复\n" +
        "任务数量：${tasks.size}\n" +
        "第一个任务：${tasks[0]}\n" +
        "索引 9：${tasks.getOrNull(9) ?: "没有这个任务"}\n" +
        taskLines +
        "\nMutableList：增删改\n" +
        "修改后的任务：$editableTasks\n" +
        "\nSet：不重复的标签\n" +
        "标签数量：${tags.size}\n" +
        "包含 Kotlin：${"Kotlin" in tags}\n" +
        "\nMap：按键查找\n" +
        "$selectedTask：$timeMessage\n" +
        "运行 App 的分钟数：${minutesByTask["运行 App"]}\n" +
        "是否安排了运行 App：${"运行 App" in minutesByTask}"
}

fun main() {
    println(runLesson07())
}