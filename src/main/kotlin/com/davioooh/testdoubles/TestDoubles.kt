package com.davioooh.testdoubles

/**
 * Dummy
 */
class DummyUserRepository : UserRepository {
    override fun saveUser(user: User): User =
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
}
//
///**
// * Mock
// */
//class AcceptingAuthorizerVerificationMock : Job {
//    var authorizeWasCalled = false
//    fun authorize(username: String?, password: String?): Boolean {
//        authorizeWasCalled = true
//        return true
//    }
//
//    fun verify(): Boolean {
//        return authorizedWasCalled
//    }
//}

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
}