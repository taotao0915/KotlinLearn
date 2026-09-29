package com.example.kotlinlearn.basics.lesson03

/**
 * 第三课：if 与 when 条件判断。
 * 依次把 quantity 改成 3、4、5，先预测提示，再运行观察。
 */
fun runLesson03(): String {
    // 沿用第二课的商品与预算。这次学习如何根据计算结果选择提示。
    val productName = "笔记本"
    val quantity = 3
    val unitPrice = 12.5
    val budget = 50.0
    val totalPrice = unitPrice * quantity

    // if 的条件必须是 Boolean；条件成立时取第一个分支，否则取 else 分支。
    // 这里把整个 if 的结果保存为 message，类型会被推断为 String。
    val message = if (budget >= totalPrice) {
        "预算足够，可以购买。"
    } else {
        "预算不足，还差 ${totalPrice - budget} 元。"
    }

    // when 从上往下判断，只选中第一个成立的分支。
    // -> 左侧是条件，右侧是结果；else 处理前面的条件都不成立的情况。
    val detail = when {
        budget > totalPrice -> "购买后剩余 ${budget - totalPrice} 元。"
        budget == totalPrice -> "预算刚好用完。"
        else -> "还需要准备 ${totalPrice - budget} 元。"
    }

    return "商品：$productName\n" +
        "数量：$quantity 本\n" +
        "总价：$totalPrice 元\n" +
        "预算：$budget 元\n" +
        "if 判断：$message\n" +
        "when 判断：$detail"
}

fun main() {
    println(runLesson03())
}
