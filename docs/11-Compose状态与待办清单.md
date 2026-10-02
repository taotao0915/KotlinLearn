# 第十一课：Compose 状态、输入、点击和列表

本课目标：输入一个任务，点击添加，勾选或删除，观察剩余数量实时更新。先操作，再读代码。前十课保留；本课的任务数据从空列表开始。

## 1. 先运行一个可以操作的界面

在 Android Studio 等待 Gradle Sync 完成，选择 app 和设备，点击 Run。App 顶部选择“第十一课”（课程按钮可横向滚动）。也可打开 `app/src/main/java/com/example/kotlinlearn/lesson11/Lesson11Screen.kt`，在 Preview 中找到“待办交互”，开启 Interactive Mode 练习；普通静态预览不会接受输入。“窄屏与长标题”预览是固定样例，回调为空，不用于交互。

依次操作：

1. 空列表显示“还没有任务，先添加一项吧。”，添加按钮不可用。
2. 输入“学习 Compose”，添加按钮可用；点击后列表多一项，输入框清空，显示“共 1 项，待完成 1 项”。
3. 点击任务文字或复选框，任务加上删除线，待完成变为 0；再次点击可取消完成。
4. 再添加同名任务，分别勾选。它们是两个独立任务，不会一起变动。
5. 点击某一项的“删除”，只移除这一项；全部删除后回到空列表。
6. 输入几个空格，添加按钮仍不可用；输入“  复习  ”后添加，保存的标题为“复习”。

本课用 remember 保存当前页面内存状态。切换到其他课程再回来，或 Activity 因旋转屏幕等原因重建后，任务和输入会重置。数据尚未写入磁盘；后续课程再逐步加入状态保存和持久化。

## 2. 从普通变量到状态

```kotlin
var draft by remember { mutableStateOf("") }
var todo by remember { mutableStateOf(TodoState()) }
```

逐个理解：

- `mutableStateOf` 创建 Compose 能观察到的状态容器。修改值时，读取它的界面可以重新组合，显示新数据。
- `remember` 在当前组合位置保留这个容器，避免每次重组都从初始值开始。
- `by` 是委托写法，让我们用 draft 直接读取和赋值，不必每次写 draftState.value；需要 getValue/setValue 的 import。
- 普通 `var draft = ""` 既不能自动通知 Compose 更新，也不能替代 remember 的保留作用。

先把“重组”理解成 Compose 根据新的状态重新执行相关界面函数。不要在函数体直接调用 addTask，否则重组时可能重复添加；更新操作放在事件回调里。

## 3. 输入框的两个关键参数

简化写法：

```kotlin
OutlinedTextField(
    value = draft,
    onValueChange = { newText -> draft = newText },
    label = { Text("任务名称") },
)
```

value 决定显示什么。用户输入后，onValueChange 收到新文字；我们赋值给 draft，界面再读取新值。第九课学过的 Lambda 在这里就是回调。

项目里把界面拆成 TodoPanel，Lesson11Content 保存状态，并传入 `onDraftChange = { draft = it }`。TodoPanel 再把这个函数传给输入框的 onValueChange，数据流是一样的。

```text
键盘输入 → onValueChange → draft 改变 → 输入框显示新的 draft
点击添加 → onAdd → todo 改变 → 列表与统计更新
```

## 4. 按钮只在点击时执行

```kotlin
onAdd = {
    if (draft.isNotBlank()) {
        todo = todo.addTask(draft)
        draft = ""
    }
}
```

Button 的 onClick 收到这个函数，但声明界面时不会立刻执行它；点击后才执行。`enabled = draft.isNotBlank()` 禁用空白输入，数据函数还会再次检查，保证数据规则独立成立。

`trim()` 去掉首尾空白，中间空格保留。添加完成后清空 draft，所以输入框自动清空。

## 5. TodoState 如何更新列表

打开同目录的 `TodoState.kt`。其中的 TodoItem 使用第八课的数据类，每个任务有 id、title、completed。

```kotlin
data class TodoItem(val id: Int, val title: String, val completed: Boolean = false)
```

TodoState 保存 tasks 和下一次分配的 nextId。每次添加成功才增加编号，删除任务不会让编号倒退。名称可以重复，操作通过编号定位。

添加的核心代码：

```kotlin
return copy(tasks = tasks + TodoItem(nextId, title), nextId = nextId + 1)
```

`+` 生成包含新元素的列表，copy 返回新的状态对象，调用者通过 `todo = ...` 赋值。我们没有直接修改原列表里的元素。

完成状态用第九课的 map 和第八课的 copy：

```kotlin
tasks.map { task ->
    if (task.id == id) task.copy(completed = completed) else task
}
```

这里只替换匹配编号的任务；其他任务保留。删除使用 `filter { it.id != id }`。这些函数是普通 Kotlin，可以独立测试，与 Android 无关。

不要换成一个普通 mutableListOf 后只调用 add，期待 Compose 一定刷新：普通可变集合内部的改变不自动成为可观察状态。本课采用“只读列表 + 新状态赋值”的方式。

## 6. 子组件只接收数据和事件

TodoPanel 的参数包括：

| 参数 | 类型/用途 |
| --- | --- |
| draft、todo | 当前要显示的数据 |
| onDraftChange | (String) -> Unit，新输入文字 |
| onAdd | () -> Unit，添加操作 |
| onCompletedChange | (Int, Boolean) -> Unit，任务编号和勾选值 |
| onDelete | (Int) -> Unit，删除指定编号 |

把状态放在调用者，让子组件接收参数，这种做法称为“状态提升”。TodoPanel 不另外保存一份任务数据，因此不会与上层产生两套不一致的状态。

`todo.remainingCount()` 通过 `count { !it.completed }` 从当前列表计算数量，不需要再保存一个手动加减的计数变量。

## 7. 列表、勾选与键

```kotlin
todo.tasks.forEach { task ->
    key(task.id) {
        TodoRow(/* 当前任务和回调 */)
    }
}
```

forEach 依次为每个任务提供界面内容。key 使用稳定编号区分条目，不用名称或列表索引，避免同名或删除后的身份混淆。

TodoRow 的文字区域通过 toggleable 处理勾选，Checkbox 只展示 checked，onCheckedChange 设为 null，避免同一操作被重复注册；删除按钮单独处理删除。标题可换行，完成时加删除线。

本课继续使用 Column，因为外层课程页已经有纵向滚动，练习数据较少。Column 会组合所有条目；更大的列表适合 LazyColumn 按需组合，但不能直接把没有高度约束的 LazyColumn 嵌入当前纵向滚动容器。后续独立任务页面再介绍这种布局。

MainActivity 的外层增加了 imePadding，在软键盘出现时为输入区域提供避让空间；仍需在设备上检查键盘弹出、长标题和滚动体验。

## 8. remember 不等于永久保存

| 方式 | 本课需要理解的边界 |
| --- | --- |
| remember | 保留重组之间的内存值；离开组合或 Activity 重建会丢失 |
| rememberSaveable | 可为受支持的数据保存、恢复部分 UI 状态，不是数据库 |
| 本地存储 | 用于重新启动后仍需保留的数据，后续再做 |

本课所有待办状态统一使用 remember。不要直接把 TodoState 放入 rememberSaveable 就认为能保存：自定义数据通常需要 Saver 或其他可保存表示。现有课程选择使用 rememberSaveable 保存简单编号，和本课任务状态是两件事。

## 9. 检查与练习

重新构建 App 后按第一节操作。没有设备时可先使用交互预览。不要点击源码中的普通 main()，本课没有控制台入口。

已加入 TodoStateTest，覆盖空白输入、去首尾空白、同名任务独立勾选和删除、取消完成、空列表、编号不复用及原状态不被修改。这些单元测试只检查数据逻辑，不代表键盘和设备 UI 已测试。

PowerShell：

```powershell
cd D:\Android\Projects\AI\KotlinLearn
$env:JAVA_HOME = 'C:\Users\94702\.jdks\ms-17.0.20.1'
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new()
$env:JAVA_OPTS = "$env:JAVA_OPTS -Dfile.encoding=UTF-8"
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:lintDebug --console=plain
```

动手练习：

1. 找到 `enabled = draft.isNotBlank()`，解释输入空格时按钮为什么不可用。
2. 临时注释添加回调里的 `draft = ""`，重新运行，观察输入框为何不再清空；理解后恢复。
3. 新增一行统计“已完成”的 Text，使用 `todo.tasks.count { it.completed }`，不要另建一个计数状态。
4. 两次添加同一个名字，只勾选其中一个，解释为什么需要 id。

下一课学习 ViewModel、协程和 Flow，逐步把页面状态与异步操作交给更合适的位置。

## 官方资料

- [Compose 状态、重组与状态提升](https://developer.android.com/develop/ui/compose/state)
- [输入框](https://developer.android.com/develop/ui/compose/text/user-input)
- [列表与滚动嵌套](https://developer.android.com/develop/ui/compose/lists)
- [保存 UI 状态](https://developer.android.com/develop/ui/compose/state-saving)
