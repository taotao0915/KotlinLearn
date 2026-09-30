package com.example.kotlinlearn.basics.lesson06

/**
 * 第六课：可空类型、安全调用与默认值。
 * 先读 describeNickname，再修改 runLesson06 中的 nickname 和 quantityInput。
 */
fun describeNickname(nickname: String?): String {
    // String? 允许字符串或 null。?: 只在左侧为 null 时使用右侧的默认值。
    val displayName: String = nickname ?: "游客"

    // ?.：有昵称就读取 length，没有昵称就得到 null；所以结果类型是 Int?。
    val originalLength: Int? = nickname?.length
    val shownLength: Int = originalLength ?: 0

    // 方括号让空字符串更容易观察。这里统计的是原昵称，不是默认名称“游客”。
    return "显示名称：[$displayName]，原昵称长度：$shownLength"
}

fun describeQuantity(input: String): String {
    // 能转换为 Int 时返回整数；格式不对或超出 Int 范围时返回 null。
    val quantity: Int? = input.toIntOrNull()

    if (quantity == null) {
        return "请输入有效的整数。"
    }

    // null 的情况已经返回。quantity 是不会重新赋值的局部 val，
    // 编译器能确定后面的 quantity 非空，这叫智能转换。
    if (quantity <= 0) {
        return "数量必须大于 0。"
    }

    val totalPrice = 12.5 * quantity
    return "购买 $quantity 本，总价 $totalPrice 元。"
}

fun runLesson06(): String {
    // 练习 1：依次改成 "Kotlin"、""，观察默认名称是否会被使用。
    val nickname: String? = null

    // 练习 2：依次改成 "4"、"abc"、"0"，观察提示。
    val quantityInput = "3"

    return "昵称示例\n" +
        "当前昵称：${describeNickname(nickname)}\n" +
        "有值对照：${describeNickname("小明")}\n" +
        "空字符串：${describeNickname("")}\n" +
        "文字 null：${describeNickname("null")}\n" +
        "\n数量输入示例\n" +
        "当前输入（$quantityInput）：${describeQuantity(quantityInput)}\n" +
        "输入 abc：${describeQuantity("abc")}\n" +
        "输入 0：${describeQuantity("0")}\n" +
        "输入 -2：${describeQuantity("-2")}\n" +
        "超出 Int 范围：${describeQuantity("2147483648")}"
}

fun main() {
    println(runLesson06())
}
