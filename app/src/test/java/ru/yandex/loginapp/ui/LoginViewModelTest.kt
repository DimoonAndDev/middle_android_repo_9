package ru.yandex.loginapp.ui

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.yandex.loginapp.LoginScreenState
import ru.yandex.loginapp.LoginViewModel

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private lateinit var loginViewModel: LoginViewModel
    private var testDispatcher: TestDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        loginViewModel = LoginViewModel()
    }


    @After
    fun teardown() {
        Dispatchers.resetMain()
    }


    @Test
    fun testFieldsNotEmpty() {
        loginViewModel.login("", "")
        assertEquals(loginViewModel.state.value, LoginScreenState.EmptyFieldsError)

    }

    @Test
    fun testEmailCorrectFormat() {
        loginViewModel.login("test", "test")
        assertEquals(loginViewModel.state.value, LoginScreenState.EmailValidationError)

    }

    @Test
    fun testLoadingState() {
        loginViewModel.login("test@test.test", "test")
        testDispatcher.scheduler.runCurrent()
        assertEquals(loginViewModel.state.value, LoginScreenState.Loading)
    }
    @Test
    fun testSuccessState() {
        loginViewModel.login("test@test.test", "test")
        testDispatcher.scheduler.advanceTimeBy(4000)
        assertEquals(loginViewModel.state.value, LoginScreenState.Success)
    }

}