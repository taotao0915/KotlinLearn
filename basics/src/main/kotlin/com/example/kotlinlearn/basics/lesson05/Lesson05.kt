package com.example.kotlinlearn.basics.lesson05

/**
 * 第五课：函数、参数和返回值。
 * 先读 calculateTotalPrice，再到 runLesson05 中找到它的调用位置。
 */

// fun 定义函数；括号内是参数；括号后的 Double 是返回值类型。
fun calculateTotalPrice(unitPrice: Double, quantity: Int): Double {
    // return 把计算结果交给调用者，并结束本次函数调用。
    return unitPrice * quantity
}

// 将第三课的条件判断放进函数，传入不同数据就能得到不同提示。
fun createBudgetMessage(totalPrice: Double, budget: Double): String {
    return when {
        budget > totalPrice -> "预算足够，还剩 ${budget - totalPrice} 元。"
        budget == totalPrice -> "预算刚好够。"
        else -> "预算不足，还差 ${totalPrice - budget} 元。"
    }
}

// 可选内容：调用时不提供 name，就使用默认值“同学”。
fun greet(name: String = "同学"): String {
    return "你好，$name！"
}

fun runLesson05(): String {
    val unitPrice = 12.5
    val budget = 50.0

    // 先执行 greet，再把它返回的文字保存到 output。
    var output = greet("小明") + "\n"
    output = output + "默认参数：${greet()}\n"

    // 复用第四课的循环，同一个函数依次接收不同数量。
    for (quantity in 3..5) {
        // 按参数顺序传值：第一个是单价，第二个是数量。
        val totalPrice = calculateTotalPrice(unitPrice, quantity)
        val message = createBudgetMessage(totalPrice, budget)
        output = output + "买 $quantity 本：$totalPrice 元，$message\n"
    }

    // 命名参数：明确指定给哪个参数传值，全部写出名称时可以调整顺序。
    val namedTotal = calculateTotalPrice(quantity = 2, unitPrice = 20.0)
    return output + "命名参数：2 本 × 20.0 元 = $namedTotal 元"
}

// main 负责启动控制台示例；runLesson05 返回的同一份文字也可以交给 App 显示。
fun main() {
    println(runLesson05())
}
