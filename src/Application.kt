package com.snippet

import Config
import com.fasterxml.jackson.databind.SerializationFeature
import com.snippet.database.Queries
import com.snippet.database.SnippetTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.jackson.jackson
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.netty.EngineMain
import io.ktor.util.escapeHTML
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

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
            val params = call.request.queryParameters

            val id = params["id"]?.toLong()

            if (id != null) {
                val query =
                    Queries.getSnippet(id.toLong()) ?: throw IllegalArgumentException("Snippet $id was not found.")
                call.respond(FreeMarkerContent("new.ftl", mapOf("snippet" to query)))
            }

            call.respond(FreeMarkerContent("new.ftl", null))
        }

        get("/new") {

            call.respond(FreeMarkerContent("new.ftl", null))
        }

        post("/action") {
            val params = call.receiveParameters()

            when (params["action"]) {
                "delete" -> {
                    val id = params["id"]?.toLong() ?: throw IllegalArgumentException("Id is required but missing.")
                    Queries.deleteSnippet(id)
                }
                "create" -> {
                    val text =
                        params["text"]?.escapeHTML() ?: throw IllegalArgumentException("Text is required but missing.")
                    if (text.length > Config.CHAR_LIMIT || text.isEmpty()) throw IllegalArgumentException("Text can not be more than ${Config.CHAR_LIMIT} characters long.")
                    val s = Queries.addSnippet(text) ?: throw Exception("Something failed, please try again.")

                    call.respondRedirect("/?id=${s.id}")
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