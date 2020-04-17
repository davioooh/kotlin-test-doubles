package com.davioooh.testdoubles

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class SignUpServiceTest {

    @Test
    fun `should throw an exception when sign up request is not valid`() {
        val signUpService = SignUpService(DummyUserRepository())

        assertThrows<IllegalArgumentException> {
            signUpService.signUp(InvalidSignUpRequestStub())
        }
    }

    @Test
    fun `should create a new user when sign up request is valid`() {
        val repositorySpy = SuccessfullySavingUserRepositorySpy()
        val signUpService = SignUpService(repositorySpy)

        val newUser = signUpService.signUp(
                SignUpRequest("user", "address@mail.com", "pwd")
        )

        assertEquals(UserDTO("user", "address@mail.com"), newUser)
        assertEquals(1, repositorySpy.numberOfCalls)
    }

    @Test
    fun `should throw an exception when username is already used`() {
        val signUpService = SignUpService(FakeUserRepository(User("user", "", "")))

        assertThrows<IllegalArgumentException> {
            signUpService.signUp(SignUpRequest("user", "address@mail.com", "pwd"))
        }
    }

}