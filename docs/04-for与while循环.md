# 第 4 课：for、while 与范围

今天让程序自动重复执行代码。目标是读懂每轮变量的变化，并判断循环何时停止。

练习文件：`basics/src/main/kotlin/com/example/kotlinlearn/basics/lesson04/Lesson04.kt`。

## 1. 把第三课的计算重复五次

上一课手动修改购买数量。这次用 `for` 自动取出 1、2、3、4、5：

```kotlin
for (quantity in 1..5) {
    val totalPrice = 12.5 * quantity
    println("买 $quantity 本：$totalPrice 元")
}
```

从左往右读：让 `quantity` 依次取范围 `1..5` 中的值，每取一个值，就执行一次大括号内的代码。

`..` 包含两端，所以这里一共执行五轮。`quantity` 由 `for` 自动提供，每轮取下一个值；循环体内不能重新给它赋值，也不需要自己递增它。

| 第几轮 | quantity | totalPrice |
| --- | --- | --- |
| 1 | 1 | 12.5 |
| 2 | 2 | 25.0 |
| 3 | 3 | 37.5 |
| 4 | 4 | 50.0 |
| 5 | 5 | 62.5 |

循环体里的 `totalPrice` 每轮都会重新创建，因此使用 `val` 没有问题。循环结束后不能在外面直接使用这个局部变量，也不能直接使用循环参数 `quantity`。

项目的完整练习还会用第三课的 `if` 判断预算是否足够。预算是 50 元时，买 4 本也算足够，买 5 本就不够了。

## 2. 项目为什么把输出放进一个变量？

上面简化例子中的 `println()` 把结果打印到控制台。项目需要同时给 Android 界面显示结果，因此把文字逐行积累到 `output`：

```kotlin
output = output + "买 $quantity 本：$totalPrice 元，$message\n"
```

右边先取已有文字，再接上本轮文字，最后保存回 `output`。所以 `output` 使用 `var`，而且要在循环外初始化，才能保留前面几轮的结果。`\n` 表示换行。

小练习：把 `1..5` 改为 `1..8`，同时把 `output` 的初始标题改为“买 1～8 本笔记本”，先预测会有几行购物结果，再运行。

## 3. while：条件成立就再执行一轮

第二个例子从 0 元开始，每天存 10 元，目标是至少 35 元：

```kotlin
var savedMoney = 0
var day = 0

while (savedMoney < 35) {
    day = day + 1
    savedMoney = savedMoney + 10
    println("第 $day 天：已存 $savedMoney 元")
}
```

执行顺序是：检查条件 → 条件成立则执行循环体 → 回到条件检查。条件为 `false` 时，接着执行循环后面的代码。

| 检查时余额 | savedMoney < 35 | 本轮结束后 |
| --- | --- | --- |
| 0 | true | 第 1 天，10 元 |
| 10 | true | 第 2 天，20 元 |
| 20 | true | 第 3 天，30 元 |
| 30 | true | 第 4 天，40 元 |
| 40 | false | 停止，不进入第 5 轮 |

所以一共执行四次。目标是“至少 35 元”，存到 40 元符合目标，不要求刚好等于 35。

完整源码用 `dailySaving` 保存每天存入的金额，用 `targetMoney` 保存目标，方便你修改。`savedMoney` 和 `day` 每轮都会重新赋值，因此使用 `var`。

如果起始时条件已经是 `false`，`while` 一次也不执行。可以把 `targetMoney` 临时改为 `0` 来观察，然后恢复成 `35`。

## 4. 让 while 能够停止

本例每轮都执行 `savedMoney = savedMoney + dailySaving`，余额才会逐步到达目标。若删掉这行，余额会一直是 0，条件就一直成立，形成无限循环。

也不要把条件随意改为 `savedMoney != targetMoney`：每次加 10 时不会恰好得到 35。这里用 `<` 表示“还没达到目标就继续”。

代码会在 `dailySaving <= 0` 时直接返回修改提示，避免零增长或负增长导致循环无法结束。今天的练习使用 7、10 等较小的正整数，不需要修改这段检查。

## 5. 运行本课

Android Studio 完成 Gradle Sync 后，点击 `Lesson04.kt` 中 `main()` 左侧的运行按钮。

也可以在 PowerShell 中执行：

```powershell
cd D:\Android\Projects\AI\KotlinLearn
$env:JAVA_HOME = 'C:\Users\94702\.jdks\ms-17.0.20.1'
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new()
$env:JAVA_OPTS = "$env:JAVA_OPTS -Dfile.encoding=UTF-8"
.\gradlew.bat :basics:runLesson04 --console=plain
```

Android App 中选择顶部的 **第四课**，点击 **运行第四课**。如果没看到第四课按钮，可以横向滑动课程区域。修改源码后，需要重新构建运行 App。

默认输出：

```text
for：买 1～5 本笔记本
买 1 本：12.5 元，预算足够
买 2 本：25.0 元，预算足够
买 3 本：37.5 元，预算足够
买 4 本：50.0 元，预算足够
买 5 本：62.5 元，预算不足

while：每天存 10 元，目标 35 元
第 1 天：已存 10 元
第 2 天：已存 20 元
第 3 天：已存 30 元
第 4 天：已存 40 元
达到目标，共用了 4 天。
```

## 6. 动手练习

每次只改一个地方，先预测，再运行：

1. 把购买范围改为 `1..8`，同步修改标题，观察购物结果的行数。
2. 保持目标 35，把 `dailySaving` 从 `10` 改成 `7`，预测需要几天、最后余额是多少。
3. 把 `dailySaving` 恢复为 10，目标改成 30，观察第三天之后是否还会多执行一次。

不需要同时改 `for` 和 `while`，它们是两个独立的例子。

## 7. 可选：认识其他范围写法

| 写法 | 依次取得的值 |
| --- | --- |
| `1..5` | 1、2、3、4、5 |
| `1..<5` | 1、2、3、4，不包含右端的 5 |
| `5 downTo 1` | 5、4、3、2、1 |
| `1..5 step 2` | 1、3、5 |

可以用这些写法替换 `for` 中的 `1..5`，观察循环次数的变化。做完后把练习恢复为默认值，方便对照笔记。

先掌握“遍历一组值用 `for`，根据条件继续重复用 `while`”的用法。后面还会学习对列表使用 `for`。

下一课学习函数、参数和返回值，把已经写过的计算整理成可复用的操作。

参考：[Kotlin 官方循环说明](https://kotlinlang.org/docs/control-flow.html#for-loops)。
