package com.luislucassilva.psiconoprecinho.adapters.r2dbc.administrator

object AdministratorSqlExpressions {

    const val TABLE_NAME = "administrador"

    const val FIND_BY_USERNAME_AND_PASSWORD = """
        SELECT * FROM $TABLE_NAME
        WHERE email = :username AND senha = :password
    """

    const val FIND_BY_ID = """
        SELECT * FROM $TABLE_NAME
        WHERE idAdministrador = :id
    """
}