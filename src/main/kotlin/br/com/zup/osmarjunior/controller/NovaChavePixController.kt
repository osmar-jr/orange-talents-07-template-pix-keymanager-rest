package br.com.zup.osmarjunior.controller

import br.com.zup.osmarjunior.controller.requests.ChaveDetalheReponse
import br.com.zup.osmarjunior.controller.requests.NovoPixRequest
import br.com.zup.osmarjunior.repository.ChavePixRepository
import br.com.zup.osmarjunior.service.RegistraPixService
import br.com.zup.osmarjunior.shared.handlers.ErrorAroundHandler
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import jakarta.inject.Inject
import javax.transaction.Transactional
import javax.validation.Valid

@ErrorAroundHandler
@Validated
@Controller("/api/v1/chaves-pix")
class NovaChavePixController(
    @Inject val service: RegistraPixService,
    @Inject val repository: ChavePixRepository
) {

    @Post
    @Transactional
    fun registra(@Body @Valid novoPixRequest: NovoPixRequest): HttpResponse<ChaveDetalheReponse> {

        val chavePix = service.registra(novoPixRequest)
        repository.save(chavePix)

        val url = UriBuilder.of("/api/v1/chaves-pix/{pixId}")
            .expand(mutableMapOf(Pair("pixId", chavePix.id.toString())))

        return HttpResponse.created(ChaveDetalheReponse(chavePix), url)
    }
}