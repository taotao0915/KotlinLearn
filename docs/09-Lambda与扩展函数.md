# 第九课：Lambda 与扩展函数

目标：读懂 `tasks.filter { !it.completed }`，并能独立修改筛选条件。先对照循环，再学习简写。本课通过 import 复用第八课的 StudyTask，不需要重新定义数据类；前一课的示例函数不会因此自动运行。

## 1. 从熟悉的循环开始

本课有三个任务：练习 Lambda（20 分钟，未完成）、复习集合（10 分钟，已完成）、运行 App（15 分钟，未完成）。

```kotlin
val pendingByLoop = mutableListOf<StudyTask>()
for (task in tasks) {
    if (!task.completed) {
        pendingByLoop.add(task)
    }
}
```

`!` 把真假值取反。对每一个任务判断“是否未完成”，成立才加入结果列表。两个待完成任务合计 35 分钟。

## 2. 把判断写成 Lambda

```kotlin
val isPending: (StudyTask) -> Boolean = { task -> !task.completed }
println(isPending(tasks[0])) // true
val pending = tasks.filter(isPending)
```

Lambda 是一种函数表达式，可以保存到变量或作为参数传给函数。按顺序拆开：

- `(StudyTask) -> Boolean` 是函数类型：接收一个 StudyTask，返回 Boolean。
- `{ task -> !task.completed }` 是 Lambda：箭头前是参数，后面是计算结果的代码。
- `isPending(tasks[0])` 调用这个函数值，得到一个 Boolean。
- `filter(isPending)` 把规则传给 filter，让它对列表元素应用规则；传入的是函数值，不是某一次调用产生的 true/false。

本例 Lambda 的最后一个表达式就是返回结果。这里不要把它改成普通函数中的 `return`；Lambda 的提前返回有单独规则，后续需要时再学习。

## 3. 尾随 Lambda 与 it

以下三种写法在这里得到相同结果：

```kotlin
tasks.filter({ task -> !task.completed })
tasks.filter { task -> !task.completed }
tasks.filter { !it.completed }
```

最后一个参数为函数类型时，传入的 Lambda 可以放到圆括号外，叫尾随 Lambda。如果它还是唯一实参，圆括号也可以省略。

这里 Lambda 只有一个可推断的参数，可以省去 `task ->`，用默认名字 `it` 引用当前任务。`it` 在这里是一个 StudyTask，不是整个列表，也不是索引；显式参数 task 和隐式参数 it 是两种写法，不要混用。嵌套复杂时优先起清晰的参数名。

## 4. filter、map、sumOf 各做一件事

```kotlin
val pending = tasks.filter { !it.completed }
val titles = pending.map { it.title }
val minutes = pending.sumOf { it.minutes }
```

| 操作 | Lambda 的结果 | 本例返回值 |
| --- | --- | --- |
| filter | Boolean，决定保留与否 | List<StudyTask>，两个未完成任务 |
| map | 转换后的值 | List<String>，两个任务名称 |
| sumOf | 要累加的数值 | Int，35 |

`map` 是转换函数，和第七课的键值容器 `Map` 不同。在这个普通 List 示例中，map 对每个元素产生一个结果；filter 可能减少数量。

这些例子不修改原列表。filter 的结果包含原任务对象的引用，不是任务的深复制；Lambda 本身也能执行修改，所以不能把“使用 filter/map”理解成永远没有副作用。

可以串起来写，但先拆开看每一步：

```kotlin
val minutes = tasks.filter { !it.completed }.sumOf { it.minutes }
```

## 5. 扩展函数：在类外定义点号调用的函数

```kotlin
fun StudyTask.fitsIn(availableMinutes: Int): Boolean {
    return this.minutes <= availableMinutes
}

val task = StudyTask("运行 App", 15)
println(task.fitsIn(15)) // true，包含刚好相等
```

`StudyTask` 是接收者类型；`this` 是点号左边的那个对象。可以省略 this，写成 `minutes <= availableMinutes`。

函数写在类外，可以用已有的公共属性，却没有修改 StudyTask 的类定义，也不能因此访问它的 private 成员。扩展按接收者的编译期类型解析，不是接口 override 那样的动态分派；本课只扩展明确的 StudyTask 类型。

本课先得到未完成任务，再按可用时间筛选：

```kotlin
val availableMinutes = 15
val shortTasks = pending.filter { it.fitsIn(availableMinutes) }
```

Lambda 能读取外部的 availableMinutes。这里返回“运行 App”；已经完成的“复习集合”虽然只有 10 分钟，也不会出现，因为前一步已将它排除。fitsIn 自身只比较时间，不检查完成状态。

列表也可以有扩展函数：

```kotlin
fun List<StudyTask>.remainingMinutes(): Int {
    return this.filter { !it.completed }.sumOf { it.minutes }
}
```

调用 `tasks.remainingMinutes()` 就复用了这段统计逻辑。在别的包中使用本课扩展时需要导入对应函数；本课 main 与扩展同包，不需要额外导入。

## 6. 空列表和完成副本

```kotlin
val allDone = tasks.map { it.copy(completed = true) }
val emptyTasks = emptyList<StudyTask>()
println(allDone.remainingMinutes())    // 0
println(emptyTasks.remainingMinutes()) // 0
```

map 与上一课的 copy 配合，得到完成副本列表；原任务仍是未完成、已完成、未完成。全部完成或没有任务时，筛选结果为空，Int 的 sumOf 返回 0。

## 7. 运行与输出

打开 `basics/src/main/kotlin/com/example/kotlinlearn/basics/lesson09/Lesson09.kt`，等待 Gradle Sync 完成后运行 main()。PowerShell 命令：

```powershell
cd D:\Android\Projects\AI\KotlinLearn
$env:JAVA_HOME = 'C:\Users\94702\.jdks\ms-17.0.20.1'
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new()
$env:JAVA_OPTS = "$env:JAVA_OPTS -Dfile.encoding=UTF-8"
.\gradlew.bat :basics:runLesson09 --console=plain
```

默认输出：

```text
Lambda：把判断规则传给函数
第一个任务待完成：true
循环筛选与 Lambda 筛选相同：true
显式参数与 it 写法相同：true

filter、map、sumOf
待完成任务：[练习 Lambda, 运行 App]
循环统计：35 分钟
sumOf 统计：35 分钟

扩展函数：任务与列表
15 分钟内可完成的待办：[运行 App]
列表扩展统计：35 分钟

原数据与边界情况
原列表数量：3
原列表完成状态：[false, true, false]
完成副本的剩余时间：0 分钟
空列表的剩余时间：0 分钟
```

App 已提供第九课入口。每次点击运行都使用源码里的初始数据；修改后需要重新构建安装 App。

## 8. 动手练习

1. 把 availableMinutes 依次改成 20、10、0，先预测可完成的待办名称再运行。其他统计会跟着变化吗？
2. 把“运行 App”的 completed 改成 true，预测剩余时间和待完成名称。
3. 在 runLesson09 中另写 `val completedTitles = tasks.filter { it.completed }.map { it.title }`，使用 println 查看已完成名称，不改动原有筛选规则。
4. 可选：把 `pending.map { it.title }` 改成显式参数的写法 `pending.map { task -> task.title }`，确认结果相同。

练习中任务分钟数保持正整数。第 1 题默认数据下的答案依次为两个待办、空列表、空列表；总待办时间仍为 35，因为它不依赖 availableMinutes。第 2 题恢复默认时间后，剩余时间为 20。

下一课进入 Android：认识 Activity、setContent 和 @Composable，并动手修改第一个界面。

## 官方资料

- [Lambda、函数类型、尾随 Lambda 与 it](https://kotlinlang.org/docs/lambdas.html)
- [扩展函数](https://kotlinlang.org/docs/extensions.html)
- [filter 筛选](https://kotlinlang.org/docs/collection-filtering.html)
- [map 转换](https://kotlinlang.org/docs/collection-transformations.html)
- [集合统计](https://kotlinlang.org/docs/collection-aggregate.html)
