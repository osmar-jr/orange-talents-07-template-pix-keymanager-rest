package br.com.zup.osmarjunior.controller

import br.com.zup.osmarjunior.*
import br.com.zup.osmarjunior.controller.requests.ChaveDetalheReponse
import br.com.zup.osmarjunior.controller.requests.NovoPixRequest
import br.com.zup.osmarjunior.grpcclients.GrpcClientFactory
import br.com.zup.osmarjunior.model.enums.TipoChavePix
import br.com.zup.osmarjunior.model.enums.TipoConta
import com.google.protobuf.Timestamp
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@ExtendWith(MockitoExtension::class)
@MicronautTest
internal class NovaChavePixControllerTest {

    @field:Inject
    lateinit var registraStub: KeyManagerGrpcServiceGrpc.KeyManagerGrpcServiceBlockingStub

    @field:Inject
    lateinit var consultaStub: KeyManagerConsultaServiceGrpc.KeyManagerConsultaServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    companion object {
        val CLIENT_ID = UUID.randomUUID()
        val PIX_ID = UUID.randomUUID()
    }

    @Test
    internal fun `deve criar uma chave pix`() {
        val chavePixResponse = ChavePixResponse.newBuilder()
            .setPixId(PIX_ID.toString())
            .setClientId(CLIENT_ID.toString())
            .build()

        val consultaChaveResponse = consultaChaveResponse()

        given(registraStub.cadastraChavePix(Mockito.any())).willReturn(chavePixResponse)
        given(consultaStub.consultar(Mockito.any())).willReturn(consultaChaveResponse)

        val pixRequest = NovoPixRequest(
            chave = "63778170023",
            tipoDeChave = TipoChavePix.CPF,
            tipoDeConta = TipoConta.CONTA_CORRENTE
        )
        val request = HttpRequest.POST("/api/v1/clientes/${CLIENT_ID}/chaves-pix", pixRequest)
        val response = client.toBlocking().exchange(request, ChaveDetalheReponse::class.java)

        with(response) {
            assertEquals(HttpStatus.CREATED, status)
            assertTrue(headers.contains("Location"))
            assertTrue(header("Location")!!.contains(PIX_ID.toString()))
            assertNotNull(body())
            assertEquals(CLIENT_ID, body.get().clienteId)
            assertNotNull(body.get().criadoEm)
        }

    }

    private fun consultaChaveResponse(): ConsultaChavePixResponse? {
        val instant = LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant()

        return ConsultaChavePixResponse
            .newBuilder()
            .setPixId(PIX_ID.toString())
            .setClientId(CLIENT_ID.toString())
            .setChave(
                ChaveConsultaResponse.newBuilder()
                    .setChave("63778170023")
                    .setTipo(TipoDeChave.CPF)
                    .setConta(
                        ContaConsultaResponse.newBuilder()
                            .setTipo(TipoDeConta.CONTA_CORRENTE)
                            .setAgencia("123456")
                            .setCpfDoTiTular("63778170023")
                            .setNomeDoTitular("Teste")
                            .setNumeroDaConta("654321")
                            .setInstituicao("Instituição de Teste")
                            .build()
                    )
                    .build()
            )
            .setCriadoEm(
                Timestamp.newBuilder()
                    .setSeconds(instant.epochSecond)
                    .setNanos(instant.nano)
                    .build()
            )
            .build()
    }

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    class MockitoStubFactory {

        @Singleton
        fun registraStub() = Mockito.mock(KeyManagerGrpcServiceGrpc.KeyManagerGrpcServiceBlockingStub::class.java)

        @Singleton
        fun consultaStub() =
            Mockito.mock(KeyManagerConsultaServiceGrpc.KeyManagerConsultaServiceBlockingStub::class.java)
    }
}