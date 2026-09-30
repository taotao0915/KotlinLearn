plugins {
    id("org.jetbrains.kotlin.jvm")
    application
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass.set("com.example.kotlinlearn.basics.Lesson01Kt")
    applicationDefaultJvmArgs = listOf("-Dfile.encoding=UTF-8")
}

// 保留 :basics:run 运行第一课，第二课使用单独的任务。
tasks.register<JavaExec>("runLesson02") {
    group = "application"
    description = "运行第二课：基本数据类型与运算"
    dependsOn(tasks.named("classes"))
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.example.kotlinlearn.basics.lesson02.Lesson02Kt")
    jvmArgs("-Dfile.encoding=UTF-8")
}

tasks.register<JavaExec>("runLesson03") {
    group = "application"
    description = "运行第三课：if 与 when 条件判断"
    dependsOn(tasks.named("classes"))
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.example.kotlinlearn.basics.lesson03.Lesson03Kt")
    jvmArgs("-Dfile.encoding=UTF-8")
}

tasks.register<JavaExec>("runLesson04") {
    group = "application"
    description = "运行第四课：for、while 与范围"
    dependsOn(tasks.named("classes"))
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.example.kotlinlearn.basics.lesson04.Lesson04Kt")
    jvmArgs("-Dfile.encoding=UTF-8")
}

tasks.register<JavaExec>("runLesson05") {
    group = "application"
    description = "运行第五课：函数、参数和返回值"
    dependsOn(tasks.named("classes"))
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.example.kotlinlearn.basics.lesson05.Lesson05Kt")
    jvmArgs("-Dfile.encoding=UTF-8")
}

tasks.register<JavaExec>("runLesson06") {
    group = "application"
    description = "运行第六课：可空类型与空值处理"
    dependsOn(tasks.named("classes"))
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.example.kotlinlearn.basics.lesson06.Lesson06Kt")
    jvmArgs("-Dfile.encoding=UTF-8")
}

tasks.register<JavaExec>("runLesson07") {
    group = "application"
    description = "运行第七课：List、Set、Map 与集合操作"
    dependsOn(tasks.named("classes"))
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.example.kotlinlearn.basics.lesson07.Lesson07Kt")
    jvmArgs("-Dfile.encoding=UTF-8")
}

tasks.register<JavaExec>("runLesson08") {
    group = "application"
    description = "运行第八课：类、data class 与接口"
    dependsOn(tasks.named("classes"))
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.example.kotlinlearn.basics.lesson08.Lesson08Kt")
    jvmArgs("-Dfile.encoding=UTF-8")
}