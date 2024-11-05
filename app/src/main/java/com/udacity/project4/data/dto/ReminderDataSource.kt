package com.udacity.project4.data.dto

import kotlinx.coroutines.flow.Flow

/**
 * Main entry point for accessing reminders data.
 */
interface ReminderDataSource {
    fun getReminders(): Result<Flow<List<ReminderDTO>>>
    suspend fun saveReminder(reminder: ReminderDTO)
    fun getReminder(id: String): Result<Flow<ReminderDTO?>>
    suspend fun deleteAllReminders()
}