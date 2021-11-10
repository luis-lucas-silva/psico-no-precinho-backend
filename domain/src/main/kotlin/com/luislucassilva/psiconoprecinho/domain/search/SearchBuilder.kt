package com.luislucassilva.psiconoprecinho.domain.search

class SearchBuilder {
    private var search = ""
    private var indexControl = 0

    fun fromFilters(filters: List<Map<String, Any>>): SearchBuilder {

        indexControl = filters.size - 1
        filters.forEachIndexed { index, filter ->
            filter.map { (filter, value) ->
                addFilter(filter, value, index)
            }

        }
        return this
    }

    private fun addFilter(filter: String, value: Any, currentPostion: Int) {
        if (filter == "CIDADE") {
            search = search.plus("e.Cidade = \'$value\' ")

            if (currentPostion != indexControl) {
                search = search.plus("and ")
            }
        } else if (filter == "ESTADO") {
            search = search.plus("e.Estado = \'$value\' ")

            if (currentPostion != indexControl) {
                search = search.plus("and ")
            }
        } else if (filter == "GENERO") {
            search = search.plus("p.Genero = \'$value\' ")

            if (currentPostion != indexControl) {
                search = search.plus("and ")
            }
        } else if (filter == "VALOR") {
            val linkedHashMapValue = value as LinkedHashMap<*, *>
            val valorMin = linkedHashMapValue["MIN"]
            val valorMax = linkedHashMapValue["MAX"]

            search = search.plus("p.ValorMin >= $valorMin and p.ValorMax <= $valorMax ")

            if (currentPostion != indexControl) {
                search = search.plus("and ")
            }
        }
        else if (filter == "TEMAS") {
            var themesSearch = ""
            val themesList = value as List<*>
            themesList.forEach { theme ->
                themesSearch = themesSearch.plus("$theme, ")
            }
            themesSearch = themesSearch.dropLast(2)

            search = search.plus("idPsicologo in (select Psicologo_idPsicologo from Psicologo_has_Tema where Tema_idTema in ($themesSearch))")
        }
    }

    fun build() = search
}