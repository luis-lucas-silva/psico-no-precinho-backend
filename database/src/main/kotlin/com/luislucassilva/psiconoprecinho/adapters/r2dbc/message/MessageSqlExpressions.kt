package com.luislucassilva.psiconoprecinho.adapters.r2dbc.message

object MessageSqlExpressions {

    private const val TABLE_NAME = "mensagem"

    const val INSERT = """
         INSERT INTO $TABLE_NAME VALUES (:id, :content, :date, :chat, :sender, :receiver)
         
         RETURNING *
    """

    const val FIND_BY_CHAT = """
        SELECT * FROM $TABLE_NAME
        WHERE Conversa_idConversa = :id
    """

    const val FIND_BY_ID = """
        SELECT * FROM $TABLE_NAME
        WHERE idMensagem = :id
    """
}