package com.davioooh.testdoubles

class SignUpService(private val userRepository: UserRepository) {
    fun signUp(signUpRequest: SignUpRequest): UserDTO {
        if (signUpRequest.isValid) {
            val user = userRepository.saveUser(signUpRequest.mapToUser())
            return user.mapToUserDTO()
        } else
            throw IllegalArgumentException("Invalid sign up request")
    }
}

open class SignUpRequest(val userName: String, val email: String, val password: String) {
    open val isValid: Boolean
        get() = userName.isNotBlank()
                && email.isNotBlank()
                && password.isNotBlank()
}

interface UserRepository {
    fun saveUser(user: User): User
}

data class User(val userName: String, val email: String, val password: String)

data class UserDTO(val userName: String, val email: String)


/* object mapping */

fun SignUpRequest.mapToUser() = User(this.userName!!, this.email!!, this.password!!)

fun User.mapToUserDTO() = UserDTO(this.userName, this.email)