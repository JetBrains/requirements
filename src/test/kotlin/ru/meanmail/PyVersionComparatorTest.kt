package ru.meanmail

import com.intellij.testFramework.UsefulTestCase
import com.jetbrains.python.packaging.PyPackageVersionNormalizer

class PyVersionComparatorTest : UsefulTestCase() {
    fun testStrictEquals() {
        val a = PyPackageVersionNormalizer.normalize("1.0.2")
        val b = PyPackageVersionNormalizer.normalize("1.0.2")

        assertTrue(compareVersions(a, "===", b))
        assertTrue(compareVersions(b, "===", a))
    }

    fun testStrictEqualsIsFalse() {
        val a = PyPackageVersionNormalizer.normalize("1.2.0")
        val b = PyPackageVersionNormalizer.normalize("1.2")

        assertFalse(compareVersions(a, "===", b))
        assertFalse(compareVersions(b, "===", a))
    }

    fun testEquals() {
        val a = PyPackageVersionNormalizer.normalize("1.2")
        val b = PyPackageVersionNormalizer.normalize("1.2.0")

        assertTrue(compareVersions(a, "==", b))
        assertTrue(compareVersions(b, "==", a))
    }

    fun testEqualsIsFalse() {
        val a = PyPackageVersionNormalizer.normalize("1.2.0")
        val b = PyPackageVersionNormalizer.normalize("1.2.1")

        assertFalse(compareVersions(a, "==", b))
        assertFalse(compareVersions(b, "==", a))
    }

    fun testNotEquals() {
        val a = PyPackageVersionNormalizer.normalize("1.2.1")
        val b = PyPackageVersionNormalizer.normalize("1.2.0")

        assertTrue(compareVersions(a, "!=", b))
        assertTrue(compareVersions(b, "!=", a))
    }

    fun testNotEqualsIsFalse() {
        val a = PyPackageVersionNormalizer.normalize("1.2.0")
        val b = PyPackageVersionNormalizer.normalize("1.2")

        assertFalse(compareVersions(a, "!=", b))
        assertFalse(compareVersions(b, "!=", a))
    }

    fun testGreaterThan() {
        val a = PyPackageVersionNormalizer.normalize("1.2.1")
        val b = PyPackageVersionNormalizer.normalize("1.2.0")

        assertTrue(compareVersions(a, ">", b))
        assertFalse(compareVersions(b, ">", a))
    }

    fun testGreaterThanOrEquals() {
        val a = PyPackageVersionNormalizer.normalize("1.2.1")
        val b = PyPackageVersionNormalizer.normalize("1.2.0")

        assertTrue(compareVersions(a, ">=", b))
        assertFalse(compareVersions(b, ">=", a))
    }

    fun testGreaterThanEquals() {
        val a = PyPackageVersionNormalizer.normalize("1.2")
        val b = PyPackageVersionNormalizer.normalize("1.2.0")

        assertTrue(compareVersions(a, ">=", b))
        assertTrue(compareVersions(b, ">=", a))
    }

    fun testLessThan() {
        val a = PyPackageVersionNormalizer.normalize("1.2.1")
        val b = PyPackageVersionNormalizer.normalize("2.2.0")

        assertTrue(compareVersions(a, "<", b))
        assertFalse(compareVersions(b, "<", a))
    }

    fun testGreaterLessOrEquals() {
        val a = PyPackageVersionNormalizer.normalize("1.2.1")
        val b = PyPackageVersionNormalizer.normalize("2.2.0")

        assertTrue(compareVersions(a, "<=", b))
        assertFalse(compareVersions(b, "<=", a))
    }

    fun testLessThanEquals() {
        val a = PyPackageVersionNormalizer.normalize("1.2")
        val b = PyPackageVersionNormalizer.normalize("1.2.0")

        assertTrue(compareVersions(a, "<=", b))
        assertTrue(compareVersions(b, "<=", a))
    }

    fun testCompatibility() {
        val a = PyPackageVersionNormalizer.normalize("1.2")
        val b = PyPackageVersionNormalizer.normalize("1.2")

        assertTrue(compareVersions(a, "~=", b))
        assertTrue(compareVersions(b, "~=", a))
    }

    fun testCompatibility2() {
        val a = PyPackageVersionNormalizer.normalize("1.2.1")
        val b = PyPackageVersionNormalizer.normalize("1.2")

        assertTrue(compareVersions(a, "~=", b))
        assertFalse(compareVersions(b, "~=", a))
    }

    fun testCompatibility3() {
        val a = PyPackageVersionNormalizer.normalize("1.2")
        val b = PyPackageVersionNormalizer.normalize("1.0")

        assertTrue(compareVersions(a, "~=", b))
        assertFalse(compareVersions(b, "~=", a))
    }

    fun testNotCompatibility() {
        val a = PyPackageVersionNormalizer.normalize("2")
        val b = PyPackageVersionNormalizer.normalize("1")

        assertFalse(compareVersions(a, "~=", b))
        assertFalse(compareVersions(b, "~=", a))
    }

    fun testNotCompatibility2() {
        val a = PyPackageVersionNormalizer.normalize("2.0")
        val b = PyPackageVersionNormalizer.normalize("1.5")

        assertFalse(compareVersions(a, "~=", b))
        assertFalse(compareVersions(b, "~=", a))
    }
}
