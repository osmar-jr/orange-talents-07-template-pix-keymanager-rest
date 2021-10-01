package br.com.zup.osmarjunior.model.enums

import br.com.zup.osmarjunior.TipoDeConta

enum class TipoConta(val grpcTipoDeConta: TipoDeConta) {
    CONTA_CORRENTE(TipoDeConta.CONTA_CORRENTE),
    CONTA_POUPANCA(TipoDeConta.CONTA_POUPANCA);
}
