package com.davioooh.testdoubles

import org.junit.jupiter.api.Assertions.assertEquals

/**
 * Dummy
 */
class DummyUserRepository : UserRepository {
    override fun saveUser(user: User): User =
            throw UnsupportedOperationException()

    override fun existsUserWithEmail(email: String): Boolean =
            throw UnsupportedOperationException()
}

/**
 * Stub
 */
class InvalidSignUpRequestStub : SignUpRequest("", "", "") {
    override val isValid: Boolean
        get() = false
}

/**
 * Spy
 */
class SuccessfullySavingUserRepositorySpy : UserRepository {
    var numberOfCalls: Int = 0
        private set

    override fun saveUser(user: User): User {
        numberOfCalls++
        return user
    }

    override fun existsUserWithEmail(email: String): Boolean = false
}

/**
 * Mock
 */
class EmailAlreadyUsedUserRepositoryMock(
        private val expectedNumberOfCalls: Int? = null,
        private val expectedEmail: String? = null) : UserRepository {

    private var numberOfCalls: Int = 0

    override fun saveUser(user: User): User =
            throw UnsupportedOperationException()

    override fun existsUserWithEmail(email: String): Boolean {
        numberOfCalls++
        if (expectedEmail != null) assertEquals(expectedEmail, email)
        return true
    }

    fun verify() {
        if (expectedNumberOfCalls != null) assertEquals(expectedNumberOfCalls, numberOfCalls)
    }
}

/**
 * Fake
 */
class FakeUserRepository(vararg initUsers: User) : UserRepository {
    private val db = mutableListOf(*initUsers)

    override fun saveUser(user: User): User {
        if (db.find { it.userName == user.userName } != null)
            throw IllegalArgumentException("User '${user.userName}' already exists")

        db.add(user)
        return user
    }

    override fun existsUserWithEmail(email: String): Boolean =
            db.any { it.email == email }
}