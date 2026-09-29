package com.example.kotlinlearn.basics

/**
 * 第一课：变量与字符串。
 * 先只关注 runLesson01 中的代码；package、fun、return 会在课程笔记中简要说明。
 * 修改这里后，重新运行控制台或 App，两者都会使用修改后的代码。
 */
fun runLesson01(): String {
    // val：初始化后不能重新赋值。把“小明”改成你的名字试试。
    val learnerName = "小明"

    // var：可以重新赋值。Kotlin 根据 1 推断出这里的类型是 Int（整数）。
    var learningDays = 1

    // 双引号包围的是字符串；$变量名 会把变量当前的值放进字符串。
    val before = "你好，我叫 $learnerName，今天是我学习 Kotlin 的第 $learningDays 天。"

    // = 表示赋值：先算出右边的结果，再保存到左边的变量。
    learningDays = learningDays + 1

    val after = "完成今天的练习后，计数变成了 $learningDays。"

    // return 把结果交给调用者；\n 表示换行。
    return "$before\n$after"
}

// 这是控制台程序的入口。Android App 的入口由 AndroidManifest.xml 配置。
fun main() {
    println(runLesson01())
}
