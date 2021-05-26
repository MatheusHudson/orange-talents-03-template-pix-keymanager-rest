package br.com.zup.chave.enum

enum class TipoDaChave {

    CPF {
        override fun valida(chaveValor: String): Pair<Boolean, String> {

            return Pair(chaveValor.matches("^[0-9]{11}\$".toRegex()), "Informe um cpf valido no formato 12345678901")
        }
    },
    CELULAR {
        override fun valida(chaveValor: String): Pair<Boolean, String> {

            return Pair(chaveValor.matches("^\\([1-9]{2}\\) (?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}\$".toRegex())
            ,"Informe um celular valido no formato (31) 98888-8888")

        }
    },
    EMAIL {
        override fun valida(chaveValor: String): Pair<Boolean, String> {

            return Pair(chaveValor.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$".toRegex())
            , "Informe um email valido no formato email@.... ")
        }
    },
    CHAVEALEATORIA {
        override fun valida(chaveValor: String): Pair<Boolean, String> {
            return Pair(chaveValor.isBlank(), "Para uma chave aleatoria, não insira valor de chave!")
        }
    };


    companion object {
        val todasChavesString =
            "${TipoDaChave.values().map { tipoDaChave -> tipoDaChave.name }}".substringBeforeLast(", UNRECOGNIZED")
    }


    abstract fun valida(chaveValor: String): Pair<Boolean, String>
}