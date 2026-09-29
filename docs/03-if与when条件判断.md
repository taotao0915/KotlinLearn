# 第 3 课：if 与 when 条件判断

今天把第二课的 `true` / `false` 变成能读懂的提示。先掌握 `if / else`，运行一次，再继续学习 `when`。

## 1. 先回顾上一课

每本笔记本 12.5 元，预算 50 元：买 3 本总价 37.5 元，买 4 本总价 50 元，买 5 本总价 62.5 元。

`budget >= totalPrice` 会产生一个 `Boolean`。这节课让程序根据这个值选择要显示的文字。

打开 `basics/src/main/kotlin/com/example/kotlinlearn/basics/lesson03/Lesson03.kt`。本课只需要修改这个文件中的 `quantity` 和 `budget`。

## 2. if：条件成立时做什么

```kotlin
val message = if (budget >= totalPrice) {
    "预算足够，可以购买。"
} else {
    "预算不足，还差 ${totalPrice - budget} 元。"
}
```

按顺序读：

1. 先计算小括号中的条件 `budget >= totalPrice`。
2. 如果结果是 `true`，选择第一个大括号里的文字。
3. 如果结果是 `false`，选择 `else` 后面的文字。
4. 把选中的文字保存到 `message`。另一个分支不会执行。

这里的 `if` 能产生一个值，所以可以放在 `=` 右边。两个分支给出的都是字符串，`message` 因此被推断为 `String`。给变量赋值的这种写法需要 `else`，保证条件不成立时也有结果。

`message` 仍然可以用 `val`：程序只会选一个分支完成初始化，并没有给它重复赋值。

`${totalPrice - budget}` 是第一课学过的字符串模板，这里先做减法，再把计算结果放进文字。

## 3. 先运行一次

Android Studio 完成 Gradle Sync 后，点击 `Lesson03.kt` 中 `main()` 左侧的绿色箭头。

也可以在 PowerShell 终端执行：

```powershell
cd D:\Android\Projects\AI\KotlinLearn
$env:JAVA_HOME = 'C:\Users\94702\.jdks\ms-17.0.20.1'
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new()
$env:JAVA_OPTS = "$env:JAVA_OPTS -Dfile.encoding=UTF-8"
.\gradlew.bat :basics:runLesson03 --console=plain
```

这些环境设置只对当前终端及其子进程生效。前两课命令仍是 `:basics:run` 和 `:basics:runLesson02`。

Android App 中选择顶部 **第三课**，点击 **运行第三课**。修改源码后要重新构建并运行 App，手机中已安装的代码才会更新。

默认输出：

```text
商品：笔记本
数量：3 本
总价：37.5 元
预算：50.0 元
if 判断：预算足够，可以购买。
when 判断：购买后剩余 12.5 元。
```

## 4. when：进一步区分三种情况

“预算足够”还可以细分成“有结余”和“刚好用完”：

```kotlin
val detail = when {
    budget > totalPrice -> "购买后剩余 ${budget - totalPrice} 元。"
    budget == totalPrice -> "预算刚好用完。"
    else -> "还需要准备 ${totalPrice - budget} 元。"
}
```

每行箭头 `->` 左边是条件，右边是该条件成立时的结果。程序从上往下检查，遇到第一个成立的条件就选中它；`else` 负责前面都不成立的情况。

这里的 `when` 没有括号里的被判断对象，各分支直接写布尔条件，并且整体用来给变量赋值，因此需要 `else`。

不要把第一条改成 `budget >= totalPrice` 后仍期待第二条处理“刚好够”：相等时第一条就已经成立，程序不会继续选择第二条。分支的顺序和条件是否重叠都很重要。

## 5. 小练习：让每个分支都执行一次

保持单价 12.5、预算 50.0，每次只修改 `quantity`：

| 数量 | 先自己计算总价 | 先预测 if 提示 | 先预测 when 提示 |
| --- | --- | --- | --- |
| 3 | ______ | ______ | ______ |
| 4 | ______ | ______ | ______ |
| 5 | ______ | ______ | ______ |

每次先写下预测，再运行核对。完成后把数量恢复到 3，保持笔记与默认示例一致也可以。

再加一个练习：保持数量为 5，把预算改为 70.0，预测新的提示。

## 6. 几个容易混淆的写法

| 写法 | 含义 |
| --- | --- |
| `=` | 赋值，例如 `val quantity = 3` |
| `==` | 判断值是否相等 |
| `!=` | 判断值是否不相等 |
| `>` / `<` | 大于 / 小于，不包含相等 |
| `>=` / `<=` | 大于或等于 / 小于或等于 |

条件必须是 `Boolean`。例如 `if (quantity > 0)` 合法，但不能把整数 `quantity` 直接写成 `if (quantity)`。

本课沿用 12.5 和 50.0 这组能用二进制浮点数精确表示的教学数值。其他小数计算可能产生精度误差，不能把示例推广为所有金额都适合用 `Double` 和 `==` 判断；精确金额可以使用整数“分”，后面再展开。

## 7. 可选：when 的另一种写法

当你要检查某个值是哪一种情况时，可以把它放在 `when` 的小括号中：

```kotlin
val paymentMethod = "微信"
val paymentTip = when (paymentMethod) {
    "微信" -> "请打开微信"
    "支付宝" -> "请打开支付宝"
    else -> "请使用其他支付方式"
}
```

这个小例子用于认识语法，并未加入购物程序。它只选择一段文字，不会调用支付服务。先完成主练习，再尝试把这个例子加入函数，并把 `paymentTip` 放进返回的字符串中。

## 8. 检查理解

你应该能说明：`if` 为什么能赋值给 `val`、`else` 在什么情况下被选择、为什么 `when` 的分支顺序会影响结果。

完成后发我你的预测或运行结果。下一课学习循环，让程序自动计算买 1～5 本的情况。

参考：[Kotlin 官方条件判断说明](https://kotlinlang.org/docs/control-flow.html)。
