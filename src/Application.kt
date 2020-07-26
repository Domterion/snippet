package com.snippet

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.auth.*
import com.fasterxml.jackson.databind.*
import com.snippet.database.Queries
import com.snippet.database.Snippet
import com.snippet.database.SnippetTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import freemarker.cache.ClassTemplateLoader
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.jackson.*
import io.ktor.server.netty.EngineMain
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.IllegalArgumentException
import java.util.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(Authentication) {
    }
    install(StatusPages) {
        exception<Throwable> { e -> call.respondText(e.localizedMessage, ContentType.Text.Plain, HttpStatusCode.InternalServerError) }
    }
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    database()

    routing {
        get("/") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("snippets" to Queries.getAllSnippets())))
        }

        post("/action") {
            val params = call.receiveParameters()

            when (params["action"]) {
                "delete" -> {
                    val id = params["id"]?.toLong() ?: throw IllegalArgumentException("Id is required but missing.")
                    Queries.deleteSnippet(id)
                }
                "create" -> {
                    val text = params["text"] ?: throw IllegalArgumentException("Text is required but missing.")
                    if(text.length > 2000 || text.isEmpty()) throw IllegalArgumentException("Text can not be more than 2000 characters long.")
                    Queries.addSnippet(text)
                }
                else -> throw IllegalArgumentException("Invalid action.")
            }
            call.respondRedirect("/")
        }

        static("/static") {
            resources("static")
        }
    }
}

fun database() {
    val config = HikariConfig("/hikari.properties")
    val ds = HikariDataSource(config)
    Database.connect(ds)

    transaction {
        SchemaUtils.create(SnippetTable)
    }
}