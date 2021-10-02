package br.com.zup.osmarjunior.handlers

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class GlobalExceptionHandler : ExceptionHandler<StatusRuntimeException, HttpResponse<Any>> {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java.simpleName)

    override fun handle(request: HttpRequest<*>, exception: StatusRuntimeException): HttpResponse<Any> {
        val statusCode = exception.status.code
        val description = exception.status.description ?: ""

        val (statusHttp, message) = when (statusCode) {
            Status.INVALID_ARGUMENT.code -> Pair(HttpStatus.BAD_REQUEST, description)
            Status.PERMISSION_DENIED.code -> Pair(HttpStatus.FORBIDDEN, description)
            Status.ALREADY_EXISTS.code -> Pair(HttpStatus.UNPROCESSABLE_ENTITY, description)
            Status.NOT_FOUND.code -> Pair(HttpStatus.NOT_FOUND, description)
            Status.FAILED_PRECONDITION.code -> Pair(HttpStatus.PRECONDITION_FAILED, description)
            Status.UNAVAILABLE.code -> Pair(HttpStatus.BAD_GATEWAY,
                "Não foi possível completar a requisição. Sistema de contas indisponível.")
            else -> {
                logger.error("Erro inesperado ao consultar o tentar registrar a chave pix.")
                Pair(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Não foi possível completar a requisição. Erro inesperado no sistema.")
            }
        }

        return HttpResponse
            .status<JsonError>(statusHttp)
            .body(JsonError(message))
    }
}