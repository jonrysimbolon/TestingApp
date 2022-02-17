package com.example.testingapp


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest {

    @Test
    fun `empty username returns false`(){
        val result = RegistrationUtil.validateRegistrationInput(
            "",
            "123",
            "123"
        )

        assertThat(result).isFalse()

    }

    @Test
    fun `valid username and correctly repeated password returns true`(){
        val result = RegistrationUtil.validateRegistrationInput(
            "Risky",
            "123",
            "123"
        )

        assertThat(result).isTrue()

    }

    @Test
    fun `username already exists returns false`(){
        val result = RegistrationUtil.validateRegistrationInput(
            "Jonry",
            "123",
            "123"
        )

        assertThat(result).isFalse()

    }

    @Test
    fun `incorrectly confirmed password returns false`(){
        val result = RegistrationUtil.validateRegistrationInput(
            "Andi",
            "123456",
            "abcdefg"
        )

        assertThat(result).isFalse()
    }

    @Test
    fun `empty password returns false`(){
        val result = RegistrationUtil.validateRegistrationInput(
            "Andi",
            "",
            ""
        )

        assertThat(result).isFalse()
    }

    @Test
    fun `less than 2 digit password returns false`(){
        val result = RegistrationUtil.validateRegistrationInput(
            "Andi",
            "abcdefgh",
            "abcdefgh"
        )

        assertThat(result).isFalse()
    }

}