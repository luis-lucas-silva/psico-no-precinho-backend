package com.luislucassilva.psiconoprecinho.adapters.r2dbc.psychologist

object PsychologistSqlExpressions {

    private const val TABLE_NAME = "psicologo"

    const val FIND_BY_USERNAME_AND_PASSWORD = """
        SELECT * FROM $TABLE_NAME 
        LEFT JOIN endereco ON ${TABLE_NAME}.Endereco_idEndereco = endereco.idEndereco
        LEFT JOIN contato ON ${TABLE_NAME}.Contato_idContato = contato.idContato
        WHERE email = :username AND senha = :password
    """

    const val INSERT = """
        INSERT INTO $TABLE_NAME VALUES (:id, :name, :document, :documentType, :photo, :crp, :birthdayDate, :gender, :minValue, :maxValue, :description,
        :email, :password, :status, :addressId, :contactId);
        SELECT * FROM $TABLE_NAME 
        LEFT JOIN endereco ON ${TABLE_NAME}.Endereco_idEndereco = endereco.idEndereco
        LEFT JOIN contato ON ${TABLE_NAME}.Contato_idContato = contato.idContato
        WHERE idPsicologo = :id;
    """
}