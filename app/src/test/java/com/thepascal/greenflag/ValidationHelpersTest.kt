package com.thepascal.greenflag

import org.junit.Assert.assertEquals
import org.junit.Test

class ValidationHelpersTest {

    @Test
    fun `test isEmailValid with blank email`() {
        assertEquals(false, isEmailValid(""))
    }

    @Test
    fun `test isEmailValid with invalid email_1`() {
        assertEquals(false, isEmailValid("test@testcom"))
    }

    @Test
    fun `test isEmailValid with invalid email_2`() {
        assertEquals(false, isEmailValid("testtestcom"))
    }

    @Test
    fun `test isEmailValid with invalid email_3`() {
        assertEquals(false, isEmailValid("testtest.com"))
    }

    @Test
    fun `test isEmailValid with valid email`() {
        assertEquals(true, isEmailValid("test@test.com"))
    }

    @Test
    fun `test isPasswordValid with invalid password`() {
        assertEquals(false, isPasswordValid("password"))
    }

    @Test
    fun `test isPasswordValid with password with uppercase but no digit`() {
        assertEquals(false, isPasswordValid("Password"))
    }

    @Test
    fun `test isPasswordValid with invalid short password`() {
        assertEquals(false, isPasswordValid("Peace8!"))
    }

    @Test
    fun `test isPasswordValid with valid password`() {
        assertEquals(true, isPasswordValid("Password7"))
    }
}