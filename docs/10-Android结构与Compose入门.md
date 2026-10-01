# 第十课：Android 项目结构与 Compose 入门

本课目标：找到 App 的界面入口，读懂一张学习名片，并修改名字、学习目标和间距。第十课代码放在 app 模块；前九课仍可在 basics 模块运行。

## 1. 先看结果

在 Android Studio 打开整个 `D:\Android\Projects\AI\KotlinLearn`，等待 Gradle Sync 完成。打开：

`app/src/main/java/com/example/kotlinlearn/lesson10/Lesson10Screen.kt`

选择编辑器的 Split 或 Design，构建并刷新 Preview。源码中的 `Lesson10Preview` 提供普通宽度与窄屏大字体两种预览，不需要手机。预览用的是与 App 相同的主题和内容函数；项目构建成功不等于 Preview 已经渲染，预览若未出现可点击 Build & Refresh，具体按钮位置随 Android Studio 版本不同。

也可以连接手机或启动模拟器，选择 app 后点击 Run。App 顶部横向滚动到第十课，直接看到名片。首次打开默认选第十课；已经运行过的 App 可能恢复之前选中的课程。

名片默认内容：

```text
我的学习名片
你好，小明
学习目标：做一个自己的 Android App
每天计划：20 分钟
改一处代码，观察一次界面变化。
```

这是静态界面练习，本课通过修改源码更新内容。下一课再加入输入和点击交互。

## 2. 项目里各个文件做什么

| 位置 | 用途 |
| --- | --- |
| settings.gradle.kts | 声明 app 和 basics 两个模块 |
| basics/src/main/kotlin | 前九课的 Kotlin 语言练习 |
| app/build.gradle.kts | Android SDK、Compose 和依赖配置 |
| app/src/main/AndroidManifest.xml | 声明应用以及启动 Activity |
| app/src/main/java/com/example/kotlinlearn/MainActivity.kt | Activity 入口、主题和课程切换页面 |
| app/src/main/java/com/example/kotlinlearn/lesson10/Lesson10Screen.kt | 本课的名片与预览，优先修改这里 |
| app/src/main/res/values/strings.xml | 界面文案和格式化字符串 |

虽然目录叫 java，也可以放 Kotlin 的 .kt 文件。本项目使用 Compose 描述这个界面，不需要额外创建布局 XML；strings.xml 保存的是文字资源。

## 3. 从 Activity 走到名片

```text
点击 App 图标
  → Android 启动 Manifest 声明的 MainActivity
  → onCreate()
  → setContent { ... }
  → KotlinLearnTheme { LessonScreen() }
  → 选中第十课时调用 Lesson10Content()
  → LearningProfileCard(...)
```

Activity 是 Android 提供的界面承载组件。本项目 MainActivity 继承 ComponentActivity；Android 创建它时会调用 onCreate。它并不是只会在整个 App 生命周期中创建一次，例如配置变化可能导致重新创建，后续再学习生命周期。

`setContent { ... }` 设置这个 Activity 的 Compose 内容。花括号是上一课见过的 Lambda 写法，里面描述要显示的界面。前九课的 main() 是控制台入口，Android 应用的启动不会自动调用那些 main()。

MainActivity 已有 Scaffold 处理页面结构，通过 innerPadding 避让系统栏，外层 Column 支持纵向滚动。名片作为其中一个组件使用，不需要再套一个 Activity 或 Scaffold。

## 4. @Composable 与普通函数

下面是帮助理解的简化代码，不必重复粘贴到已有文件：

```kotlin
@Composable
fun Greeting(name: String) {
    Text(text = "你好，$name")
}
```

`@Composable` 标记这个函数参与 Compose 界面的组合。Greeting 接收字符串参数，并调用 Text 显示文字；println 则把内容输出到日志或控制台，不能代替屏幕上的 Text。

Composable 调用需要处于可组合的上下文中，如 setContent 的内容块或另一个 @Composable 函数。不要把 Greeting() 直接放到普通 main() 中运行。

Compose 会根据数据描述界面，必要时可能重新执行可组合函数（重组）。因此不要把“学习次数加一”或保存文件等操作直接写进函数体，期待它只执行一次。本课只读取参数、显示文字。

## 5. Card、Column 与 Text

实际名片的结构是：

```text
Card：卡片容器
  Column：让内容从上到下排列
    Text：我的学习名片
    Text：问候语
    Text：学习目标
    Text：每天计划
    Text：练习提示
```

Column 纵向排列，Row 横向排列。`Arrangement.spacedBy(12.dp)` 指定 Column 中相邻元素的间距。Card、Text 都是现成的可组合函数，调用方式仍是 Kotlin 的函数调用。

`MaterialTheme.typography.headlineSmall` 等样式统一管理字号与字重；本课先使用主题样式。我们为预览复用同一个 KotlinLearnTheme，因此不需要复制两套颜色配置。

## 6. Modifier 控制布局与外观

```kotlin
Card(modifier = modifier.fillMaxWidth()) {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Text(...)
    }
}
```

`fillMaxWidth()` 使用父布局允许的最大宽度；这里不是无条件占满整个屏幕，因为外层页面已有边距。Column 的 padding 是卡片内部文字四周的留白，spacedBy 是相邻文字之间的间距，作用位置不同。

dp 是密度无关的尺寸单位，用于布局；它不等同于设备上的一个物理像素。字体大小通常使用 sp 或主题文字样式。

`modifier: Modifier = Modifier` 是带默认值的参数，让调用者也能提供布局修饰。预览传入了 padding(16.dp)，App 则使用页面自己的边距。Modifier 链的先后顺序可能改变效果，练习时一次只改一个位置。

## 7. 参数和文字资源

最先修改 Lesson10Content 中这三行：

```kotlin
val learnerName = "小明"
val learningGoal = "做一个自己的 Android App"
val dailyMinutes = 20
```

它们传给 LearningProfileCard 的 name、goal 和 dailyMinutes。预览与 App 都调用 Lesson10Content，因此修改此处对两者生效；要让设备上的 App 更新，还需要重新构建安装。

实际问候语使用：

```kotlin
Text(text = stringResource(R.string.lesson_ten_greeting, name))
```

strings.xml 中的资源是 `你好，%1$s`：%1$s 表示第一个字符串参数；计划分钟数使用 %1$d 表示整数。`R.string...` 是构建工具生成的资源标识，不要编辑生成的 R 文件。这里只需认识调用方式，暂时不用修改格式符。

## 8. 预览与构建

`@Preview` 是 Android Studio 工具读取的标记。本课用无参数的 @Composable 包装函数提供预览，内部调用真正的名片组件。新增预览本身不会新增 App 页面；我们已经在课程选择页接入了第十课。

第十课没有 `:basics:runLesson10`，请使用 Preview 或运行 app。PowerShell 构建命令：

```powershell
cd D:\Android\Projects\AI\KotlinLearn
$env:JAVA_HOME = 'C:\Users\94702\.jdks\ms-17.0.20.1'
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new()
$env:JAVA_OPTS = "$env:JAVA_OPTS -Dfile.encoding=UTF-8"
.\gradlew.bat :app:assembleDebug :app:lintDebug --console=plain
```

构建 APK 不会自动安装或打开 App。设备上运行需在 Android Studio 选择设备并点击 Run；没有设备也能先用 Preview 学习。Preview 或真机上检查文字是否换行完整、窄屏大字体是否被裁切。名片没有固定高度，文字可自然换行；完整课程页可滚动。

## 9. 动手练习

1. 把 learnerName 改成你的名字，dailyMinutes 改成 30，刷新预览。
2. 把 learningGoal 改成长一点的目标，比较两种预览中的换行。
3. 把 Column 的 padding 从 20.dp 改成 32.dp，观察文字离卡片边缘的距离。
4. 恢复 padding，再把 spacedBy 从 12.dp 改成 20.dp，解释这次改变的位置与上一步有什么不同。

自查：MainActivity 如何找到这张名片？Text 和 println 的输出位置有什么不同？预览函数为什么不需要 main()？

下一课加入 Compose 状态、输入框、按钮和任务列表，让界面能响应操作。

## 官方资料

- [Compose 入门：setContent、Text 与布局](https://developer.android.com/develop/ui/compose/tutorial)
- [可组合函数预览](https://developer.android.com/develop/ui/compose/tooling/previews)
- [Modifier](https://developer.android.com/develop/ui/compose/modifiers)
- [Activity 生命周期](https://developer.android.com/guide/components/activities/activity-lifecycle)
- [Compose 字符串资源](https://developer.android.com/develop/ui/compose/resources)
