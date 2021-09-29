package com.luislucassilva.psiconoprecinho.domain.psychologist

enum class Theme(val id: Int) {
    ANSIEDADE(1);

    companion object {
        fun getEnum(id: Int): Theme {
            return values().first { theme -> (theme.id == id) }
        }
    }

}