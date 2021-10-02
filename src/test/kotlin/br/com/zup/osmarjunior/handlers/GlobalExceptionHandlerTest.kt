package br.com.zup.osmarjunior.handlers

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@MicronautTest
internal class GlobalExceptionHandlerTest{

    val request = HttpRequest.GET<Any>("/")

    @Test
    internal fun `deve retornar BAD_REQUEST quando StatusException for INVALID_ARGUMENT`(){
        val message = "Dados inválidos"
        val exception = StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription(message))

        val response = GlobalExceptionHandler().handle(request, exception)

        with(response){
            assertEquals(HttpStatus.BAD_REQUEST, status)
            assertNotNull(body())
            assertEquals(message, (body() as JsonError).message)
        }
    }

    @Test
    internal fun `deve retornar FORBIDDEN quando StatusException for PERMISSION_DENIED`(){
        val message = "Permissão negada."
        val exception = StatusRuntimeException(Status.PERMISSION_DENIED.withDescription(message))

        val response = GlobalExceptionHandler().handle(request, exception)

        with(response){
            assertEquals(HttpStatus.FORBIDDEN, status)
            assertNotNull(body())
            assertEquals(message, (body() as JsonError).message)
        }
    }

    @Test
    internal fun `deve retornar UNPROCESSABLE_ENTITY quando StatusException for ALREADY_EXISTS`(){
        val message = "O recurso já existe no sistema."
        val exception = StatusRuntimeException(Status.ALREADY_EXISTS.withDescription(message))

        val response = GlobalExceptionHandler().handle(request, exception)

        with(response){
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status)
            assertNotNull(body())
            assertEquals(message, (body() as JsonError).message)
        }
    }

    @Test
    internal fun `deve retornar NOT_FOUND quando StatusException for NOT_FOUND`(){
        val message = "Não encontrado."
        val exception = StatusRuntimeException(Status.NOT_FOUND.withDescription(message))

        val response = GlobalExceptionHandler().handle(request, exception)

        with(response){
            assertEquals(HttpStatus.NOT_FOUND, status)
            assertNotNull(body())
            assertEquals(message, (body() as JsonError).message)
        }
    }

    @Test
    internal fun `deve retornar PRECONDITION_FAILED quando StatusException for FAILED_PRECONDITION`(){
        val message = "Não disponível para o estado atual do recurso."
        val exception = StatusRuntimeException(Status.FAILED_PRECONDITION.withDescription(message))

        val response = GlobalExceptionHandler().handle(request, exception)

        with(response){
            assertEquals(HttpStatus.PRECONDITION_FAILED, status)
            assertNotNull(body())
            assertEquals(message, (body() as JsonError).message)
        }
    }

    @Test
    internal fun `deve retornar BAD_GATEWAY quando StatusException for UNAVAILABLE`(){
        val message = "Não foi possível completar a requisição. Sistema de contas indisponível."

        val exception = StatusRuntimeException(Status.UNAVAILABLE)

        val response = GlobalExceptionHandler().handle(request, exception)

        with(response){
            assertEquals(HttpStatus.BAD_GATEWAY, status)
            assertNotNull(body())
            assertEquals(message, (body() as JsonError).message)
        }
    }

    @Test
    internal fun `deve retornar INTERNAL_SERVER_ERROR quando StatusException for UNKNOWN`(){
        val message = "Não foi possível completar a requisição. Erro inesperado no sistema."

        val exception = StatusRuntimeException(Status.UNKNOWN)

        val response = GlobalExceptionHandler().handle(request, exception)

        with(response){
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, status)
            assertNotNull(body())
            assertEquals(message, (body() as JsonError).message)
        }
    }
}