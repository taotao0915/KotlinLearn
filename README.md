# KotlinLearn：从零学习 Kotlin

这个项目同时包含一个 Android App 和一个可在电脑上直接运行的 Kotlin 练习模块。它们共用同一份练习代码；没有手机或模拟器，也可以先学习 Kotlin。

## 第一次使用

1. 在 Android Studio 中选择 **Open**，打开整个 `D:\Android\Projects\AI\KotlinLearn` 文件夹。
2. 等待 Gradle Sync 完成。首次缺少依赖时需要联网。
3. 本机默认 `JAVA_HOME` 是 Java 8。请在 **Settings > Build, Execution, Deployment > Build Tools > Gradle > Gradle JDK** 中选择 JDK 17；已检测到的安装路径为 `C:\Users\94702\.jdks\ms-17.0.20.1`。也可使用 Android Studio 自带的兼容 JDK。
4. 阅读 [第一课：变量与字符串](docs/01-变量与字符串.md)。今天只需要修改 `Lesson01.kt`。

已经学完第一课，可以继续 [第二课：基本数据类型与运算](docs/02-基本数据类型与运算.md)。第二课练习文件是 `basics/src/main/kotlin/com/example/kotlinlearn/basics/lesson02/Lesson02.kt`。

学完第二课后，继续 [第三课：if 与 when 条件判断](docs/03-if与when条件判断.md)，把真假值变成不同的提示。练习文件是 `basics/src/main/kotlin/com/example/kotlinlearn/basics/lesson03/Lesson03.kt`。

接着学习 [第四课：for、while 与范围](docs/04-for与while循环.md)，自动计算买 1～5 本的情况，并模拟每天存钱直到达到目标。练习文件是 `basics/src/main/kotlin/com/example/kotlinlearn/basics/lesson04/Lesson04.kt`。

[第五课：函数、参数和返回值](docs/05-函数参数与返回值.md) 将总价计算和预算判断写成可复用的函数。练习文件是 `basics/src/main/kotlin/com/example/kotlinlearn/basics/lesson05/Lesson05.kt`。

[第六课：可空类型与空值处理](docs/06-可空类型与空值处理.md) 用昵称和数量输入练习 `String?`、`?.`、`?:` 以及转换失败的处理。练习文件是 `basics/src/main/kotlin/com/example/kotlinlearn/basics/lesson06/Lesson06.kt`。

### 方式 A：在电脑上练习（无需 Android 设备）

在 Android Studio 的 Terminal 中使用 PowerShell 执行：

```powershell
cd D:\Android\Projects\AI\KotlinLearn
$env:JAVA_HOME = 'C:\Users\94702\.jdks\ms-17.0.20.1'
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new()
$env:JAVA_OPTS = "$env:JAVA_OPTS -Dfile.encoding=UTF-8"
.\gradlew.bat :basics:run --console=plain
```

这里只修改当前终端的环境变量与编码，不会修改系统设置。UTF-8 设置用于让 PowerShell 和 Gradle 的中文输出一致。也可以展开 `basics/src/main/kotlin/com/example/kotlinlearn/basics/Lesson01.kt`，点击 `main()` 左侧的运行按钮。

默认输出：

```text
你好，我叫 小明，今天是我学习 Kotlin 的第 1 天。
完成今天的练习后，计数变成了 2。
```

每次打开新终端时，重新执行上述环境设置；也可以直接使用 Android Studio 的 Run 窗口运行 `main()`。

第二课使用单独的命令，第一课入口保持不变：

```powershell
.\gradlew.bat :basics:runLesson02 --console=plain
```

也可以在 `Lesson02.kt` 中点击 `main()` 左侧的运行按钮。第二课用 12.5 元的笔记本练习数据类型、总价计算和整数除法。

第三课的运行命令：

```powershell
.\gradlew.bat :basics:runLesson03 --console=plain
```

也可以在 `Lesson03.kt` 中运行 `main()`。本课依次修改数量，观察预算有结余、刚好够和不足时的不同提示。

第四课的运行命令：

```powershell
.\gradlew.bat :basics:runLesson04 --console=plain
```

也可以在 `Lesson04.kt` 中运行 `main()`。观察 `for` 的五轮购物计算和 `while` 的四轮储蓄过程。

第五课的运行命令：

```powershell
.\gradlew.bat :basics:runLesson05 --console=plain
```

也可以在 `Lesson05.kt` 中运行 `main()`。先读计算总价的函数定义，再对照调用位置理解参数和返回值。

第六课的运行命令：

```powershell
.\gradlew.bat :basics:runLesson06 --console=plain
```

也可以在 `Lesson06.kt` 中运行 `main()`。比较 null、空字符串、普通字符串，以及不同数量输入的结果。

### 方式 B：在 Android App 中练习

1. 连接已开启 USB 调试的 Android 7.0 或更新版本手机，并在手机上允许调试；或在 Android Studio 的 Device Manager 中创建并启动模拟器。
2. 在顶部选择 `app` 和目标设备，点击绿色 Run 按钮。
3. App 顶部可选择 **第一课** 到 **第六课**，再点击相应的运行按钮。首次打开默认显示第六课，切换课程会清空旧结果；课程按钮区域可以横向滚动。
4. 修改各课的 Kotlin 源文件后，需要再次 Run 来重新构建安装；已经安装的 App 不会自动读取电脑上的源码。

## 今天需要认识的文件

| 文件 | 用途 |
| --- | --- |
| `basics/src/main/kotlin/com/example/kotlinlearn/basics/Lesson01.kt` | 第一课练习，优先阅读和修改 |
| `docs/01-变量与字符串.md` | 分步讲解、练习与常见疑问 |
| `basics/src/main/kotlin/com/example/kotlinlearn/basics/lesson02/Lesson02.kt` | 第二课：购物计算与数值运算 |
| `docs/02-基本数据类型与运算.md` | 第二课讲解、预测练习与整数除法 |
| `basics/src/main/kotlin/com/example/kotlinlearn/basics/lesson03/Lesson03.kt` | 第三课：根据预算选择提示 |
| `docs/03-if与when条件判断.md` | 第三课讲解、分支顺序与边界练习 |
| `basics/src/main/kotlin/com/example/kotlinlearn/basics/lesson04/Lesson04.kt` | 第四课：重复计算和模拟储蓄 |
| `docs/04-for与while循环.md` | 第四课讲解、循环过程与范围练习 |
| `basics/src/main/kotlin/com/example/kotlinlearn/basics/lesson05/Lesson05.kt` | 第五课：定义函数并传入不同数据 |
| `docs/05-函数参数与返回值.md` | 第五课讲解、调用过程与函数编写练习 |
| `basics/src/main/kotlin/com/example/kotlinlearn/basics/lesson06/Lesson06.kt` | 第六课：处理缺失昵称与无效数量输入 |
| `docs/06-可空类型与空值处理.md` | 第六课讲解、空值对照与输入转换练习 |
| `docs/学习路线.md` | 后续学习顺序 |
| `app/src/main/java/com/example/kotlinlearn/MainActivity.kt` | Android 界面，先保持原样 |
| `app/src/main/AndroidManifest.xml` | Android 应用和启动页面的声明 |
| `app/build.gradle.kts` | Android 构建配置，暂时不需要修改 |

`basics` 是不依赖 Android 的 Kotlin/JVM 模块，`app` 是使用它的 Android 模块。先在 `basics` 里掌握语言，再逐步学习 Android 界面。

## 构建与检查

先按上面的命令设置当前终端的 `JAVA_HOME`，然后执行：

```powershell
.\gradlew.bat :basics:run :basics:runLesson02 :basics:runLesson03 :basics:runLesson04 :basics:runLesson05 :basics:runLesson06 :app:assembleDebug :app:lintDebug --console=plain
```

APK 生成位置：`app/build/outputs/apk/debug/app-debug.apk`。

本项目固定使用本机已有的兼容版本，便于开始学习：AGP 8.13.2、Gradle 8.14.3、Kotlin 2.2.21、Compose BOM 2025.04.01、compileSdk/targetSdk 35、minSdk 24、JDK 17。它们不是对“当前最新版”的声明。

`local.properties` 保存本机 SDK 路径，不应提交到版本库。换电脑时由 Android Studio 重新生成即可。

## 官方资料

- [Kotlin 基础语法](https://kotlinlang.org/docs/basic-syntax.html)
- [Compose 配置说明](https://developer.android.com/develop/ui/compose/setup-compose-dependencies-and-compiler)
- [AGP 8.13 兼容性说明](https://developer.android.com/build/releases/agp-8-13-0-release-notes)

按课程顺序完成小练习，再进入下一课；不需要一次读完所有官方资料。
