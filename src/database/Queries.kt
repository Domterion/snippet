package com.snippet.database

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Queries {
    fun getAllSnippets(): List<Snippet> {
        return transaction {
            SnippetTable.selectAll().map { SnippetTable.toSnippet(it) }
        }
    }

    fun getSnippet(id: Long): Snippet? {
        return transaction {
            SnippetTable.select { SnippetTable.id eq id }.map { SnippetTable.toSnippet(it) }.firstOrNull()
        }
    }

    fun addSnippet(text: String): Snippet? {
        return transaction {
            SnippetTable.insert { it[SnippetTable.text] = text }.resultedValues?.map { SnippetTable.toSnippet(it) }
                ?.firstOrNull()
        }
    }

    fun deleteSnippet(id: Long) {
        transaction {
            SnippetTable.deleteWhere { SnippetTable.id eq id }
        }
    }
}