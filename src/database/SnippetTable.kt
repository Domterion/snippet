package com.snippet.database

import Config
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

data class Snippet(val id: Long, val text: String, val created: LocalDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC))

object SnippetTable: Table("snippet") {
    val id = long("id").autoIncrement()
    val text = varchar("text", Config.CHAR_LIMIT)
    val created = datetime("created").clientDefault { LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC) }

    override val primaryKey = PrimaryKey(id)

    fun toSnippet(it: ResultRow): Snippet = Snippet(id = it[SnippetTable.id], text = it[SnippetTable.text], created = it[SnippetTable.created])
}