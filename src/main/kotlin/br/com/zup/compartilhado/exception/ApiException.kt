package br.com.zup.compartilhado.exception

import io.micronaut.http.MutableHttpResponse

class ApiException(override val message: String, val status: MutableHttpResponse<Any> ) : RuntimeException()