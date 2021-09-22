package com.luislucassilva.psiconoprecinho.adapters.r2dbc.contact

object ContactSqlExpressions {

    private const val TABLE_NAME = "contato"

    const val INSERT = """
        INSERT INTO $TABLE_NAME VALUES (:id, :type, :number);
        SELECT * FROM $TABLE_NAME where idContato = :id;
    """
}