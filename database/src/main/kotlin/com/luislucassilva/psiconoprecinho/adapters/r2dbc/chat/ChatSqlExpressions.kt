package com.luislucassilva.psiconoprecinho.adapters.r2dbc.chat

object ChatSqlExpressions {
    private const val TABLE_NAME = "conversa"

    const val FIND_BY_PATIENT = """
        SELECT * FROM $TABLE_NAME
        WHERE Paciente_idPaciente = :patient
    """

    const val FIND_BY_PSYCHOLOGIST = """
        SELECT * FROM $TABLE_NAME
        WHERE Psicologo_idPsicologo = :psychologist
    """

    const val INSERT = """
        INSERT INTO $TABLE_NAME VALUES(:id, :patient, :psychologist)
        
        SELECT * FROM $TABLE_NAME WHERE idConversa = :id
    """

    const val FIND_BY_ID = """
        SELECT * FROM $TABLE_NAME
        WHERE idConversa = :id
    """

    const val FIND_MESSAGES = """
        SELECT * FROM Mensagem
        WHERE Conversa_idConversa = :id
    """


}