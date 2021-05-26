package br.com.zup

import br.com.zup.chave.enum.TipoDaChave
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@MicronautTest
class TipoDaChaveTest {


    @Test
    @DisplayName("deveRetornarTrueParaUmCpfValido")
    fun test1() {

       assertTrue(TipoDaChave.CPF.valida("12346578901").first)
    }
    @Test
    @DisplayName("naoDeveRetornarTrueParaUmCpf")
    fun test2() {

       assertFalse(TipoDaChave.CPF.valida("12346578901445").first)
    }



    @Test
    @DisplayName("deveRetornarTrueParaUmeEmailValido")
    fun test3() {

       assertTrue(TipoDaChave.EMAIL.valida("test@test.com.br").first)
    }
    @Test
    @DisplayName("naoDeveRetornarTrueParaUmEmail")
    fun test4() {

       assertFalse(TipoDaChave.EMAIL.valida("teste.com.br").first)
    }


    @Test
    @DisplayName("deveRetornarTrueParaUmCelularValido")
    fun test5() {

       assertTrue(TipoDaChave.CELULAR.valida("(31) 98888-8888").first)
    }
    @Test
    @DisplayName("naoDeveRetornarTrueParaUmCelular")
    fun test6() {

       assertFalse(TipoDaChave.CPF.valida("98888-888").first)
    }


    @Test
    @DisplayName("deveRetornarTrueParaUmaChaveAleaotiraValido")
    fun test7() {

       assertTrue(TipoDaChave.CHAVEALEATORIA.valida("").first)
    }
    @Test
    @DisplayName("naoDeveRetornarTrueParaUmChaveAleatoria")
    fun test8() {

       assertFalse(TipoDaChave.CHAVEALEATORIA.valida("12346578901445").first)
    }



}