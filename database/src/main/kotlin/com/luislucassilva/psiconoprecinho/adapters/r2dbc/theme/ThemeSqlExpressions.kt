package com.luislucassilva.psiconoprecinho.adapters.r2dbc.theme

object ThemeSqlExpressions {
    private const val TABLE_NAME = "Psicologo_has_Tema"

    const val INSERT = """
        INSERT INTO $TABLE_NAME
        VALUES (:idPsicologo, :idTema)
        RETURNING *
    """

    const val DELETE = """
        DELETE FROM $TABLE_NAME WHERE Psicologo_idPsicologo = :idPsicologo
    """

    const val FIND_BY_PSYCHOLOGIST_ID = """
        SELECT * FROM $TABLE_NAME WHERE Psicologo_idPsicologo = :idPsicologo
    """
}