package br.com.zup.osmarjunior.model.enums

import br.com.zup.osmarjunior.TipoDeChave
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator

enum class TipoChavePix(val grpcTipoChave: TipoDeChave) {
    CPF(TipoDeChave.CPF) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) return false

            if (!chave.matches("[0-9]+".toRegex())) return false

            return CPFValidator().run {
                initialize(null)
                isValid(chave, null)
            }
        }
    },

    CNPJ(TipoDeChave.CNPJ) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) return false

            if (!chave.matches("[0-9]{14}".toRegex())) return false

            return CNPJValidator().run {
                initialize(null)
                isValid(chave, null)
            }
        }

    },

    EMAIL(TipoDeChave.EMAIL) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) return false

            return EmailValidator().run {
                initialize(null)
                isValid(chave, null)
            }
        }
    },

    CELULAR(TipoDeChave.CELULAR) {
        override fun valida(chave: String?): Boolean {

            if (chave.isNullOrBlank()) return false
            return chave.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }
    },

    ALEATORIA(TipoDeChave.ALEATORIA) {
        override fun valida(chave: String?): Boolean {
            return true
        }
    };

    abstract fun valida(chave: String?): Boolean
}
