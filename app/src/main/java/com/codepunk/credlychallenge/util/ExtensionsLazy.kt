package com.codepunk.credlychallenge.util

/**
 * A thread-safe way of determining whether this [Lazy] instance was initialized, but also forcing
 * initialization to occur if not.
 */
@Suppress("UNUSED")
fun <T> Lazy<T>.wasInitialized(): Boolean =
    synchronized(this) { isInitialized().also { value } }

/**
 * Calls [onConsume] if this [Lazy] instance is uninitialized, and returns the encapsulated value.
 */
@Suppress("UNUSED")
fun <T> Lazy<T>.consume(onConsume: (T) -> Unit): T =
    if (wasInitialized()) value else value.also(onConsume)