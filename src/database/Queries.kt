package com.snippet.database

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Queries {
    fun getAllSnippets(): List<Snippet> {
        return transaction {
            SnippetTable.selectAll().map { SnippetTable.toSnippet(it) }
        }
    }

    fun addSnippet(text: String) {
        transaction {
            SnippetTable.insert { it[SnippetTable.text] = text }
        }
    }

    fun deleteSnippet(id: Long) {
        transaction {
            SnippetTable.deleteWhere { SnippetTable.id eq id }
        }
    }
}