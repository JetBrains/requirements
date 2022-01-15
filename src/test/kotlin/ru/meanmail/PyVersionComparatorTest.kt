package ru.meanmail

import com.jetbrains.python.packaging.PyPackageVersionNormalizer
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class PyVersionComparatorTest {
    @Test
    fun testStrictEquals() {
        val a = PyPackageVersionNormalizer.normalize("1.0.2")
        val b = PyPackageVersionNormalizer.normalize("1.0.2")

        assertTrue(compareVersions(a, "===", b))
        assertTrue(compareVersions(b, "===", a))
    }

    @Test
    fun testStrictEqualsIsFalse() {
        val a = PyPackageVersionNormalizer.normalize("1.2.0")
        val b = PyPackageVersionNormalizer.normalize("1.2")

        assertFalse(compareVersions(a, "===", b))
        assertFalse(compareVersions(b, "===", a))
    }

    @Test
    fun testEquals() {
        val a = PyPackageVersionNormalizer.normalize("1.2")
        val b = PyPackageVersionNormalizer.normalize("1.2.0")

        assertTrue(compareVersions(a, "==", b))
        assertTrue(compareVersions(b, "==", a))
    }

    @Test
    fun testEqualsIsFalse() {
        val a = PyPackageVersionNormalizer.normalize("1.2.0")
        val b = PyPackageVersionNormalizer.normalize("1.2.1")

        assertFalse(compareVersions(a, "==", b))
        assertFalse(compareVersions(b, "==", a))
    }

    @Test
    fun testNotEquals() {
        val a = PyPackageVersionNormalizer.normalize("1.2.1")
        val b = PyPackageVersionNormalizer.normalize("1.2.0")

        assertTrue(compareVersions(a, "!=", b))
        assertTrue(compareVersions(b, "!=", a))
    }

    @Test
    fun testNotEqualsIsFalse() {
        val a = PyPackageVersionNormalizer.normalize("1.2.0")
        val b = PyPackageVersionNormalizer.normalize("1.2")

        assertFalse(compareVersions(a, "!=", b))
        assertFalse(compareVersions(b, "!=", a))
    }

    @Test
    fun testGreaterThan() {
        val a = PyPackageVersionNormalizer.normalize("1.2.1")
        val b = PyPackageVersionNormalizer.normalize("1.2.0")

        assertTrue(compareVersions(a, ">", b))
        assertFalse(compareVersions(b, ">", a))
    }

    @Test
    fun testGreaterThanOrEquals() {
        val a = PyPackageVersionNormalizer.normalize("1.2.1")
        val b = PyPackageVersionNormalizer.normalize("1.2.0")

        assertTrue(compareVersions(a, ">=", b))
        assertFalse(compareVersions(b, ">=", a))
    }

    @Test
    fun testGreaterThanEquals() {
        val a = PyPackageVersionNormalizer.normalize("1.2")
        val b = PyPackageVersionNormalizer.normalize("1.2.0")

        assertTrue(compareVersions(a, ">=", b))
        assertTrue(compareVersions(b, ">=", a))
    }

    @Test
    fun testLessThan() {
        val a = PyPackageVersionNormalizer.normalize("1.2.1")
        val b = PyPackageVersionNormalizer.normalize("2.2.0")

        assertTrue(compareVersions(a, "<", b))
        assertFalse(compareVersions(b, "<", a))
    }

    @Test
    fun testGreaterLessOrEquals() {
        val a = PyPackageVersionNormalizer.normalize("1.2.1")
        val b = PyPackageVersionNormalizer.normalize("2.2.0")

        assertTrue(compareVersions(a, "<=", b))
        assertFalse(compareVersions(b, "<=", a))
    }

    @Test
    fun testLessThanEquals() {
        val a = PyPackageVersionNormalizer.normalize("1.2")
        val b = PyPackageVersionNormalizer.normalize("1.2.0")

        assertTrue(compareVersions(a, "<=", b))
        assertTrue(compareVersions(b, "<=", a))
    }

    @Test
    fun testCompatibility() {
        val a = PyPackageVersionNormalizer.normalize("1.2")
        val b = PyPackageVersionNormalizer.normalize("1.2")

        assertTrue(compareVersions(a, "~=", b))
        assertTrue(compareVersions(b, "~=", a))
    }

    @Test
    fun testCompatibility2() {
        val a = PyPackageVersionNormalizer.normalize("1.2.1")
        val b = PyPackageVersionNormalizer.normalize("1.2")

        assertTrue(compareVersions(a, "~=", b))
        assertFalse(compareVersions(b, "~=", a))
    }

    @Test
    fun testCompatibility3() {
        val a = PyPackageVersionNormalizer.normalize("1.2")
        val b = PyPackageVersionNormalizer.normalize("1.0")

        assertTrue(compareVersions(a, "~=", b))
        assertFalse(compareVersions(b, "~=", a))
    }

    @Test
    fun testNotCompatibility() {
        val a = PyPackageVersionNormalizer.normalize("2")
        val b = PyPackageVersionNormalizer.normalize("1")

        assertFalse(compareVersions(a, "~=", b))
        assertFalse(compareVersions(b, "~=", a))
    }

    @Test
    fun testNotCompatibility2() {
        val a = PyPackageVersionNormalizer.normalize("2.0")
        val b = PyPackageVersionNormalizer.normalize("1.5")

        assertFalse(compareVersions(a, "~=", b))
        assertFalse(compareVersions(b, "~=", a))
    }
}
