package com.luislucassilva.psiconoprecinho.adapters.r2dbc.formacao

object FormacaoSqlExpressions {

    private const val TABLE_NAME = "formacao"

    const val INSERT = """
        INSERT INTO $TABLE_NAME
        VALUES (:id, :name, :psychologistId);
        SELECT * FROM $TABLE_NAME WHERE idFormacao = :id;
    """

    const val DELETE = """
        DELETE FROM $TABLE_NAME WHERE Psicologo_idPsicologo = :psychologistId;
    """

    const val FIND_BY_PSYCHOLOGIST_ID = """
        SELECT * FROM $TABLE_NAME WHERE Psicologo_idPsicologo = :psychologistId;
    """
}