package br.com.zup.osmarjunior.controller

import br.com.zup.osmarjunior.controller.requests.ChaveDetalheReponse
import br.com.zup.osmarjunior.controller.requests.NovoPixRequest
import br.com.zup.osmarjunior.service.RegistraPixService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import jakarta.inject.Inject
import java.util.*
import javax.validation.Valid

@Validated
@Controller("/api/v1/clientes/{clienteId}")
class NovaChavePixController(
    @Inject val service: RegistraPixService
) {

    @Post(value = "/chaves-pix")
    fun registra(
        @PathVariable("clienteId") clienteId: UUID,
        @Body @Valid novoPixRequest: NovoPixRequest
    ): HttpResponse<ChaveDetalheReponse> {

        val chavePix = service.registra(novoPixRequest, clienteId)

        val url = UriBuilder.of("/api/v1/clientes/{clienteId}/chaves-pix/{pixId}")
            .expand(mutableMapOf(Pair("pixId", chavePix.pixIdGrpc)))

        return HttpResponse.created(ChaveDetalheReponse(chavePix), url)
    }
}