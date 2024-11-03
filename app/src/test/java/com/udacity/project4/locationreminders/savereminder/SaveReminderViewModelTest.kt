package com.udacity.project4.locationreminders.savereminder

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.locationreminders.MainCoroutinesRules
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.data.FakeTestRepository
import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.local.LocalDB
import com.udacity.project4.locationreminders.data.local.RemindersLocalRepository
import com.udacity.project4.locationreminders.getOrAwaitValue
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import com.udacity.project4.locationreminders.reminderslist.RemindersListViewModel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.pauseDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import kotlin.test.Test

//@Config(sdk = [29])
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SaveReminderViewModelTest : AutoCloseKoinTest() {


    //TODO: provide testing to the SaveReminderView and its live data objects

    private lateinit var saveReminderViewModel: SaveReminderViewModel

    private lateinit var reminderLocalRepository: FakeDataSource


    private lateinit var appContext: Application


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutinesRules()


    @Before
    fun setupViewModel() {

        //Get our real repository
        reminderLocalRepository = FakeDataSource()

        //clear the data to start fresh

        saveReminderViewModel =
            SaveReminderViewModel(ApplicationProvider.getApplicationContext(), reminderLocalRepository)

    }

    @Test
    fun saveNewReminder_checkReminderValue() = mainCoroutineRule.runBlockingTest  {

//        mainCoroutineRule.pauseDispatcher()

        val reminder = ReminderDTO("title10", "description10", "location10", 0.0, 0.0)

        saveReminderViewModel.saveReminder(
            ReminderDataItem(
                reminder.title,
                reminder.description,
                reminder.location,
                reminder.latitude,
                reminder.longitude
            )
        )

        val value = saveReminderViewModel.createGeofence.getOrAwaitValue()

        assertThat(value, CoreMatchers.not(CoreMatchers.nullValue()))

        assertThat(value!!.title, `is`(reminder.title))


    }


}