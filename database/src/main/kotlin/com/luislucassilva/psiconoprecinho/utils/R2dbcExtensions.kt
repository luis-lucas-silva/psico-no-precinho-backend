package com.luislucassilva.psiconoprecinho.utils

import io.r2dbc.spi.Row
import org.springframework.r2dbc.core.DatabaseClient
import java.time.Instant
import java.time.LocalDate
import java.time.OffsetDateTime

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified T> Row.get(identifier: String): T =
    this.get(identifier, T::class.java)!!

inline fun <reified T> Row.getOrNull(identifier: String): T? =
    this.get(identifier, T::class.java)

inline fun <reified T> DatabaseClient.GenericExecuteSpec.bindOrNull(name: String, value: T?) =
    value?.let { this.bind(name, it as Any) } ?: this.bindNull(name, T::class.java)

inline fun <reified T> DatabaseClient.GenericExecuteSpec.bindIfNotNull(name: String, value: T?) =
    value?.let { this.bind(name, it as Any) } ?: this

inline fun <reified T> DatabaseClient.GenericExecuteSpec.bindIfNotEmpty(name: String, value: Collection<T>) =
    value.takeIf { it.isNotEmpty() }?.let { this.bind(name, it as Any) } ?: this

fun <T> String.where(sql: String, value: T?) =
    value?.let { "${this.trimEnd()} $sql" } ?: this

fun <T> String.where(sql: String, value: Collection<T>) =
    this.trimEnd() + " " + (value.takeIf { it.isNotEmpty() }?.let { sql } ?: "")

fun Row.getInstant(columnName: String): Instant? {
    val result = this.get(columnName)

    return result?.let {
        (it as OffsetDateTime).toInstant()
    } ?: null
}