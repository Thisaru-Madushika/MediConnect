package com.example.mediconnect

/**
 * A simple repository to store user credentials for demo purposes.
 */
object UserRepo {
    private var savedEmail: String? = null
    private var savedPassword: String? = null
    private var savedName: String? = null

    /**
     * Registers a new user.
     */
    fun registerUser(name: String, email: String, pass: String) {
        savedName = name
        savedEmail = email
        savedPassword = pass
    }

    /**
     * Updates only the user name.
     */
    fun setUserName(name: String) {
        savedName = name
    }

    /**
     * Checks if a user is registered.
     */
    fun hasAccount(): Boolean = savedEmail != null

    /**
     * Validates login credentials.
     */
    fun validateLogin(email: String, pass: String): Boolean {
        return email == savedEmail && pass == savedPassword
    }

    /**
     * Gets the registered user's name.
     */
    fun getUserName(): String = savedName ?: "User"
}