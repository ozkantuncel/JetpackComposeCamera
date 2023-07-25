package com.example.jetpackcomposecamera.util

import java.util.regex.Pattern

object Constant {

    const val DATABASE_NAME = "ImageDatabase"

    private const val PASSWORD_MIN_LENGTH = 8
    private val PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^*&+=])(?=\\S+\$).{$PASSWORD_MIN_LENGTH,}"
    )

    fun isStrongPassword(password: String): Boolean {
        return PASSWORD_PATTERN.matcher(password).matches()
    }
}