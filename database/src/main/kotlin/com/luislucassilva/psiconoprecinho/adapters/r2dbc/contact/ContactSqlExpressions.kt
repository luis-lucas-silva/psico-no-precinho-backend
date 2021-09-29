package com.luislucassilva.psiconoprecinho.adapters.r2dbc.contact

object ContactSqlExpressions {

    private const val TABLE_NAME = "contato"

    const val INSERT = """
        INSERT INTO $TABLE_NAME VALUES (:id, :type, :number);
        SELECT * FROM $TABLE_NAME WHERE idContato = :id;
    """

    const val UPDATE = """
        UPDATE $TABLE_NAME SET
        Tipo = :type,
        Telefone = :number
        WHERE idContato = :id;
        
        SELECT * FROM $TABLE_NAME WHERE idContato = :id;
    """
}