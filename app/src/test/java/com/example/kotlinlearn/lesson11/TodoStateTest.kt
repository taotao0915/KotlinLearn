package com.example.kotlinlearn.lesson11

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TodoStateTest {
    @Test fun blankInputDoesNotCreateTaskOrConsumeId() {
        val state = TodoState()
        assertEquals(state, state.addTask(" \t\n　"))
        assertEquals(state, state.addTask(""))
    }

    @Test fun inputIsTrimmedAndStartsIncomplete() {
        val added = TodoState().addTask("  学习 Kotlin  ")
        assertEquals("学习 Kotlin", added.tasks.single().title)
        assertFalse(added.tasks.single().completed)
        assertEquals(1, added.remainingCount())
    }

    @Test fun duplicateTitlesHaveIndependentIdentity() {
        val state = TodoState().addTask("复习").addTask("复习")
        val checked = state.setCompleted(state.tasks[1].id, true)
        assertFalse(checked.tasks[0].completed)
        assertTrue(checked.tasks[1].completed)
        assertEquals(1, checked.remainingCount())
    }

    @Test fun completedTaskCanBeUnchecked() {
        val state = TodoState().addTask("复习")
        val completed = state.setCompleted(1, true)
        assertEquals(0, completed.remainingCount())
        assertEquals(state, completed.setCompleted(1, false))
    }

    @Test fun deletingOneDuplicatePreservesTheOther() {
        val state = TodoState().addTask("复习").addTask("复习")
        val deleted = state.removeTask(1)
        assertEquals(listOf(state.tasks[1]), deleted.tasks)
        assertEquals(1, deleted.remainingCount())
    }

    @Test fun deletionDoesNotReuseIdsAndEmptyListHasZeroRemaining() {
        val empty = TodoState().addTask("旧任务").removeTask(1)
        assertTrue(empty.tasks.isEmpty())
        assertEquals(0, empty.remainingCount())
        assertEquals(2, empty.addTask("新任务").tasks.single().id)
    }

    @Test fun unknownIdsLeaveDataUnchanged() {
        val state = TodoState().addTask("复习")
        assertEquals(state, state.setCompleted(99, true))
        assertEquals(state, state.removeTask(99))
    }

    @Test fun updatesDoNotMutateEarlierState() {
        val original = TodoState().addTask("复习")
        val added = original.addTask("运行 App")
        val checked = added.setCompleted(1, true)
        checked.removeTask(2)
        assertEquals(1, original.tasks.size)
        assertFalse(original.tasks.single().completed)
        assertEquals(2, added.remainingCount())
        assertEquals(2, checked.tasks.size)
    }
}
