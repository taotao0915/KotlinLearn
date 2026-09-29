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
