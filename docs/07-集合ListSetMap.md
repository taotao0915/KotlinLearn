# 第七课：集合 List、Set、Map

这一课把多个值放在一起。先学会保存、读取、遍历和修改；筛选等 Lambda 写法留到第九课。

## 1. List：把任务排成一张清单

打开 `basics/src/main/kotlin/com/example/kotlinlearn/basics/lesson07/Lesson07.kt`。

```kotlin
val tasks: List<String> = listOf("复习变量", "练习集合", "复习变量")
```

`List<String>` 中的尖括号表示元素类型：列表里保存的是字符串。`listOf(...)` 创建列表，这里的类型也可以由编译器推断，写成 `val tasks = listOf(...)`。

| 索引（位置编号） | 内容 |
| --- | --- |
| 0 | 复习变量 |
| 1 | 练习集合 |
| 2 | 复习变量 |

List 有顺序，允许重复。`tasks.size` 是 3；`tasks[0]` 是第一个元素，不是第零个任务。最后一个有效索引为 `size - 1`。

```kotlin
val missing: String? = tasks.getOrNull(9)
println(missing ?: "没有这个任务")
```

直接读取不存在的 `tasks[9]` 会抛出越界异常；`getOrNull(9)` 则返回 null。这里复用了上一课的可空类型和默认值。空列表也可以这样安全读取。

## 2. for：依次取出每个任务

```kotlin
for (task in tasks) {
    println(task)
}
```

每一轮，`task` 都是当前的一个字符串。本课源文件额外使用 `number` 编号，并把文字拼入 `taskLines`，方便控制台和 App 共用结果。

## 3. MutableList：增删改

```kotlin
val editableTasks = mutableListOf("复习变量", "练习集合")
editableTasks.add("运行 App")
editableTasks[0] = "复习函数"
editableTasks.remove("练习集合")
println(editableTasks) // [复习函数, 运行 App]
```

依次观察：添加后有三个任务；修改后第一个任务变成复习函数；删除后剩两个任务。`remove(元素)` 删除第一个匹配项，找不到时返回 false；`removeAt(索引)` 才是按位置删除，索引必须有效。

**val 与只读是两个不同的概念。** `val` 让变量不能重新指向另一张清单，但这里的 `MutableList` 仍允许修改内容。`List` 是只读接口，不提供 add/remove；也不能把“只读”理解成底层对象永远不会变化，比如别处可能持有同一个可变列表。

## 4. Set：标签不要重复

```kotlin
val tags = setOf("Kotlin", "Android", "Kotlin")
println(tags.size)        // 2
println("Kotlin" in tags) // true
```

Set 适合关注“有哪些不同的值”。不要把它当作有索引的任务清单；不同 Set 实现对遍历顺序的约定可能不同。需要修改时使用 `mutableSetOf(...)`。

## 5. Map：用名称查找对应的信息

```kotlin
val minutesByTask: Map<String, Int> = mapOf(
    "复习变量" to 10,
    "练习集合" to 20
)
val minutes: Int? = minutesByTask["练习集合"] // 20
val missing: Int? = minutesByTask["运行 App"] // null
```

`String` 是键的类型，`Int` 是值的类型；`to` 把键和值组成一对。每个键只有一个对应值，不同键可以对应相同值。`mapOf` 遇到重复键时保留后面的值。

本例的值不能是 null，但查找不存在的键会得到 null，所以查询结果是 `Int?`。源文件沿用第六课的 `if (minutes == null)` 来显示“暂未安排时间”，而不是把缺失数据误当成安排了 0 分钟。

`"运行 App" in minutesByTask` 检查的是键。需要增删改时：

```kotlin
val times = mutableMapOf("练习集合" to 20)
times["运行 App"] = 15 // 新键：添加
times["练习集合"] = 25 // 已有键：更新
times.remove("运行 App")
```

## 6. 运行并核对

在 Android Studio 等待 Gradle Sync 完成，点击 `Lesson07.kt` 中 `main()` 左侧的运行按钮。也可以在 PowerShell 执行：

```powershell
cd D:\Android\Projects\AI\KotlinLearn
$env:JAVA_HOME = 'C:\Users\94702\.jdks\ms-17.0.20.1'
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new()
$env:JAVA_OPTS = "$env:JAVA_OPTS -Dfile.encoding=UTF-8"
.\gradlew.bat :basics:runLesson07 --console=plain
```

预期输出：

```text
List：有顺序，可以重复
任务数量：3
第一个任务：复习变量
索引 9：没有这个任务
1. 复习变量
2. 练习集合
3. 复习变量

MutableList：增删改
修改后的任务：[复习函数, 运行 App]

Set：不重复的标签
标签数量：2
包含 Kotlin：true

Map：按键查找
练习集合：20 分钟
运行 App 的分钟数：null
是否安排了运行 App：false
```

App 顶部选择第七课，再点击运行第七课。修改源码后需要重新构建安装；每次点击都重新创建本课集合，不会保存上次结果。

## 7. 动手练习：先预测，再运行

1. 在 `tasks` 的 `listOf(...)` 末尾加上 `"运行 App"`，观察 size 和编号列表。
2. 在 `tags` 中再添加一次 `"Android"`，再添加 `"Compose"`。最终应该有几个不同标签？
3. 把 `selectedTask` 改成 `"运行 App"`，观察缺失值提示；再在 Map 中添加 `"运行 App" to 15`，观察三行查询结果如何变化。
4. 可选：用 `val emptyTasks = emptyList<String>()` 创建空列表，运行 `println(emptyTasks.getOrNull(0) ?: "暂无任务")`，然后尝试遍历它。循环会执行几次？

自查：为什么 `val` 声明的 `editableTasks` 可以 add？为什么 List 中重复任务保留，而 Set 中重复标签只有一份？Map 的方括号中放的是索引还是键？

下一课学习类和 data class，把任务名称、预计时间等相关数据放进一个对象。

## 官方资料

- [集合概览：List、Set、Map 与只读/可变接口](https://kotlinlang.org/docs/collections-overview.html)
- [读取集合元素](https://kotlinlang.org/docs/collection-elements.html)
- [getOrNull](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/get-or-null.html)
- [mapOf](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/map-of.html)