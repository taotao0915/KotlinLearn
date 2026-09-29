package com.example.kotlinlearn.basics.lesson02

/**
 * 第二课：基本数据类型与运算。
 * 修改商品数量或预算，先预测结果，再运行验证。
 * 今天先关注函数内部；函数的详细用法会在后续课程中练习。
 */
fun runLesson02(): String {
    // String 保存文字。双引号不能省略，商品名可以改成你喜欢的物品。
    val productName: String = "笔记本"

    // Int 保存整数，比如商品数量。
    val quantity: Int = 3

    // Double 保存浮点数，比如本课用于练习的小数 12.5。
    val unitPrice: Double = 12.5
    val budget: Double = 50.0

    // * 是乘法：单价乘数量。这里 Double 与 Int 运算得到 Double。
    val totalPrice: Double = unitPrice * quantity

    // Boolean 只有 true（真）和 false（假）；>= 表示大于或等于。
    val canAfford: Boolean = budget >= totalPrice

    // - 是减法。如果结果是负数，表示预算还差多少。
    val balance: Double = budget - totalPrice

    // 两个整数相除，结果丢弃小数部分；这不是四舍五入。
    val integerDivision: Int = 10 / 3

    // 让至少一个操作数变成 Double，才能进行浮点除法。
    val decimalDivision: Double = 10.0 / 3

    // % 是取余数：10 分成每份 3，最后余下 1。
    val remainder: Int = 10 % 3

    // + 在这里连接字符串；\n 表示换行。
    return "商品：$productName\n" +
        "数量：$quantity 本\n" +
        "单价：$unitPrice 元\n" +
        "总价：$totalPrice 元\n" +
        "预算：$budget 元\n" +
        "预算是否足够：$canAfford\n" +
        "预算减总价：$balance 元\n" +
        "10 / 3 = $integerDivision\n" +
        "10.0 / 3 = $decimalDivision\n" +
        "10 % 3 = $remainder"
}

// 控制台入口。Android App 会直接调用上面的 runLesson02()。
fun main() {
    println(runLesson02())
}
