package br.com.zup.osmarjunior.handlers

import br.com.zup.osmarjunior.shared.handlers.ErrorAroundHandler
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.aop.InterceptorBean
import io.micronaut.aop.MethodInterceptor
import io.micronaut.aop.MethodInvocationContext
import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.validation.ConstraintViolationException

@Singleton
@InterceptorBean(ErrorAroundHandler::class)
class ErrorAroundHandlerInterceptor : MethodInterceptor<Any, Any> {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java.simpleName)

    override fun intercept(context: MethodInvocationContext<Any, Any>): Any? {

        try {

            return context.proceed()

        } catch (exception: StatusRuntimeException) {
            val statusCode = exception.status.code
            val description = exception.status.description ?: ""

            val (statusHttp, message) = when (statusCode) {
                Status.INVALID_ARGUMENT.code -> Pair(HttpStatus.BAD_REQUEST, description)
                Status.PERMISSION_DENIED.code -> Pair(HttpStatus.FORBIDDEN, description)
                Status.ALREADY_EXISTS.code -> Pair(HttpStatus.UNPROCESSABLE_ENTITY, description)
                Status.NOT_FOUND.code -> Pair(HttpStatus.NOT_FOUND, description)
                Status.FAILED_PRECONDITION.code -> Pair(HttpStatus.PRECONDITION_FAILED, description)
                else -> {
                    logger.error("Erro inesperado ao consultar o tentar registrar a chave pix.")
                    Pair(HttpStatus.BAD_GATEWAY, "Não foi possivel completar a requisição.")
                }
            }

            throw HttpStatusException(statusHttp, message)

        } catch (exception: ConstraintViolationException) {
            logger.error("Erro de validação dos campos.", exception)

            throw HttpStatusException(HttpStatus.BAD_REQUEST, exception.constraintViolations)

        } catch (exception: Exception) {
            logger.error("Erro inesperado no sistema interno.", exception)

            throw HttpStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro inesperado no sistema. ${exception.message}"
            )
        }
    }

}