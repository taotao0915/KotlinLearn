package com.example.kotlinlearn.lesson11

// 标题可以重复，id 用来区分同名任务。
data class TodoItem(val id: Int, val title: String, val completed: Boolean = false)

// 普通 Kotlin 数据与函数，不依赖 Compose，便于单独验证。
data class TodoState(
    val tasks: List<TodoItem> = emptyList(),
    val nextId: Int = 1,
) {
    fun addTask(input: String): TodoState {
        val title = input.trim()
        if (title.isEmpty()) return this
        return copy(tasks = tasks + TodoItem(nextId, title), nextId = nextId + 1)
    }

    fun setCompleted(id: Int, completed: Boolean): TodoState {
        return copy(tasks = tasks.map { task ->
            if (task.id == id) task.copy(completed = completed) else task
        })
    }

    fun removeTask(id: Int): TodoState {
        return copy(tasks = tasks.filter { it.id != id })
    }

    fun remainingCount(): Int {
        return tasks.count { !it.completed }
    }
}
