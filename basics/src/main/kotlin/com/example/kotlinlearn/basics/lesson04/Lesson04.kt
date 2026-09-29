package com.example.kotlinlearn.basics.lesson04

/**
 * 第四课：for、while 与范围。
 * 先观察每轮变量怎样变化，再修改范围或每天存入的金额。
 */
fun runLesson04(): String {
    val unitPrice = 12.5
    val budget = 50.0
    var output = "for：买 1～5 本笔记本\n"

    // 1..5 包含两端：quantity 依次是 1、2、3、4、5。
    // quantity 由 for 自动提供，不需要自己写 quantity = quantity + 1。
    for (quantity in 1..5) {
        // 这些变量在每一轮都会重新创建和计算。
        val totalPrice = unitPrice * quantity
        val message = if (budget >= totalPrice) {
            "预算足够"
        } else {
            "预算不足"
        }

        // 把这一轮的结果接到已有文字后面，供控制台和 App 一起使用。
        output = output + "买 $quantity 本：$totalPrice 元，$message\n"
    }

    // while 的练习：每天存入一些钱，直到达到或超过目标。
    val dailySaving = 10
    val targetMoney = 35

    // 存入金额必须为正数，否则不能靠增加余额到达正数目标。
    if (dailySaving <= 0) {
        return "请把 dailySaving 设为大于 0 的整数，再运行。"
    }

    var savedMoney = 0
    var day = 0
    output = output + "\nwhile：每天存 $dailySaving 元，目标 $targetMoney 元\n"

    // 每轮开始前都检查条件；余额达到或超过目标后就停止。
    while (savedMoney < targetMoney) {
        day = day + 1
        savedMoney = savedMoney + dailySaving
        output = output + "第 $day 天：已存 $savedMoney 元\n"
    }

    return output + "达到目标，共用了 $day 天。"
}

fun main() {
    println(runLesson04())
}
