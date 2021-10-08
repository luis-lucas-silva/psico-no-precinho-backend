package com.luislucassilva.psiconoprecinho.domain.search

data class SearchRequest(
    val filters: List<String>,
    val values: List<Map<String, Any>>
)