package br.com.zup.edu.pix.client

import br.com.edu.TipoDeConta
import br.com.zup.edu.pix.cadastra.ChavePix

import br.com.zup.edu.pix.validacao.TipoDaChave

data class CreatePixKeyRequest(
    val keyType: KeyType,
    val key: String,
    val bankAccount: BankAccount,
    val owner: Owner,
) {
    fun paraCreatePixKeyRequest ( chavePix: ChavePix): CreatePixKeyRequest{
        return CreatePixKeyRequest(
            keyType = KeyType.getTipo(chavePix),
            key = chavePix.chave,
            bankAccount = BankAccount(
                participant = chavePix.conta.instituicao!!.ispb,
                branch = chavePix.conta.agencia!!,
                accountNumber = chavePix.conta.numero!!,
                accountType = AccountType.getAccountType(chavePix)
            ),
            owner = Owner(
                type = OwnerType.NATURAL_PERSON,
                name = chavePix.conta.titular!!.nome!!,
                taxIdNumber = chavePix.conta.titular!!.nome!!
            )

        )
    }
}

data class Owner(
    val type: OwnerType,
    val name: String,
    val taxIdNumber: String
) {

}

enum class OwnerType {
    NATURAL_PERSON, LEGAL_PERSON
}

enum class KeyType {
    CPF,
    CNPJ,
    PHONE,
    EMAIL,
    RANDOM;

    companion object {
        fun getTipo(chavePix: ChavePix): KeyType {
            if (CPF.equals(chavePix.tipo.name)) {
                return CPF
            } else if (CNPJ.equals(chavePix.tipo.name)) {
                return CNPJ
            } else if ("CELULAR".equals(chavePix.tipo.name)) {
                return PHONE
            } else if (EMAIL.equals(chavePix.tipo.name)) {
                return EMAIL
            }
            return RANDOM
        }
    }

}

data class BankAccount(
    val participant: String,
    val branch: String,
    val accountNumber: String,
    val accountType: AccountType,
) {

}

enum class AccountType {
    CACC, SVGS;

    companion object {
        fun getAccountType(chavePix: ChavePix): AccountType {
            if ("CONTA_CORRENTE".equals(chavePix.tipoDeConta.name)) {
                return CACC
            }
            return SVGS
        }
    }
}
