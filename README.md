# KotlinLearn：从零学习 Kotlin

这个项目同时包含一个 Android App 和一个可在电脑上直接运行的 Kotlin 练习模块。它们共用同一份练习代码；没有手机或模拟器，也可以先学习 Kotlin。

## 第一次使用

1. 在 Android Studio 中选择 **Open**，打开整个 `D:\Android\Projects\AI\KotlinLearn` 文件夹。
2. 等待 Gradle Sync 完成。首次缺少依赖时需要联网。
3. 本机默认 `JAVA_HOME` 是 Java 8。请在 **Settings > Build, Execution, Deployment > Build Tools > Gradle > Gradle JDK** 中选择 JDK 17；已检测到的安装路径为 `C:\Users\94702\.jdks\ms-17.0.20.1`。也可使用 Android Studio 自带的兼容 JDK。
4. 阅读 [第一课：变量与字符串](docs/01-变量与字符串.md)。今天只需要修改 `Lesson01.kt`。

### 方式 A：在电脑上练习（无需 Android 设备）

在 Android Studio 的 Terminal 中使用 PowerShell 执行：

```powershell
cd D:\Android\Projects\AI\KotlinLearn
$env:JAVA_HOME = 'C:\Users\94702\.jdks\ms-17.0.20.1'
.\gradlew.bat :basics:run --console=plain
```

这里只修改当前终端的环境变量，不会修改系统设置。也可以展开 `basics/src/main/kotlin/com/example/kotlinlearn/basics/Lesson01.kt`，点击 `main()` 左侧的运行按钮。

默认输出：

```text
你好，我叫 小明，今天是我学习 Kotlin 的第 1 天。
完成今天的练习后，计数变成了 2。
```

如果旧版 PowerShell 中中文乱码，先执行 `chcp 65001` 再运行，或使用 Android Studio 的 Run 窗口。

### 方式 B：在 Android App 中练习

1. 连接已开启 USB 调试的 Android 7.0 或更新版本手机，并在手机上允许调试；或在 Android Studio 的 Device Manager 中创建并启动模拟器。
2. 在顶部选择 `app` 和目标设备，点击绿色 Run 按钮。
3. App 启动后，点击 **运行第一课** 查看结果。
4. 修改 `Lesson01.kt` 后，需要再次 Run 来重新构建安装；已经安装的 App 不会自动读取电脑上的源码。

## 今天需要认识的文件

| 文件 | 用途 |
| --- | --- |
| `basics/src/main/kotlin/com/example/kotlinlearn/basics/Lesson01.kt` | 第一课练习，优先阅读和修改 |
| `docs/01-变量与字符串.md` | 分步讲解、练习与常见疑问 |
| `docs/学习路线.md` | 后续学习顺序 |
| `app/src/main/java/com/example/kotlinlearn/MainActivity.kt` | Android 界面，先保持原样 |
| `app/src/main/AndroidManifest.xml` | Android 应用和启动页面的声明 |
| `app/build.gradle.kts` | Android 构建配置，暂时不需要修改 |

`basics` 是不依赖 Android 的 Kotlin/JVM 模块，`app` 是使用它的 Android 模块。先在 `basics` 里掌握语言，再逐步学习 Android 界面。

## 构建与检查

先按上面的命令设置当前终端的 `JAVA_HOME`，然后执行：

```powershell
.\gradlew.bat :basics:run :app:assembleDebug :app:lintDebug --console=plain
```

APK 生成位置：`app/build/outputs/apk/debug/app-debug.apk`。

本项目固定使用本机已有的兼容版本，便于开始学习：AGP 8.13.2、Gradle 8.14.3、Kotlin 2.2.21、Compose BOM 2025.04.01、compileSdk/targetSdk 35、minSdk 24、JDK 17。它们不是对“当前最新版”的声明。

`local.properties` 保存本机 SDK 路径，不应提交到版本库。换电脑时由 Android Studio 重新生成即可。

## 官方资料

- [Kotlin 基础语法](https://kotlinlang.org/docs/basic-syntax.html)
- [Compose 配置说明](https://developer.android.com/develop/ui/compose/setup-compose-dependencies-and-compiler)
- [AGP 8.13 兼容性说明](https://developer.android.com/build/releases/agp-8-13-0-release-notes)

先完成第一课的小练习，再进入下一课；不需要一次读完所有官方资料。
