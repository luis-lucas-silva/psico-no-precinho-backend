package com.luislucassilva.psiconoprecinho.adapters.r2dbc.patient

object PatientSqlExpressions {

    private const val TABLE_NAME = "paciente"

    const val FIND_BY_USERNAME_AND_PASSWORD = """
        SELECT * FROM $TABLE_NAME
        LEFT JOIN endereco on ${TABLE_NAME}.Endereco_idEndereco = endereco.idEndereco
        LEFT JOIN contato ON ${TABLE_NAME}.Contato_idContato = contato.idContato
        WHERE email = :username AND senha = :password
    """
    const val INSERT = """
        INSERT INTO $TABLE_NAME VALUES (:id, :name, :document, :photo, :gender, :birthdayDate,
        :email, :password, :addressId, :contactId)
        SELECT * FROM $TABLE_NAME 
        LEFT JOIN endereco ON ${TABLE_NAME}.Endereco_idEndereco = endereco.idEndereco
        LEFT JOIN contato ON ${TABLE_NAME}.Contato_idContato = contato.idContato
        WHERE idPaciente = :id
    """

    const val FIND_BY_ID = """
        SELECT * FROM $TABLE_NAME 
        LEFT JOIN endereco ON ${TABLE_NAME}.Endereco_idEndereco = endereco.idEndereco
        LEFT JOIN contato ON ${TABLE_NAME}.Contato_idContato = contato.idContato
        WHERE idPaciente = :id;
    """

    const val UPDATE = """
        UPDATE $TABLE_NAME SET
        NomePaciente = :name,
        Documento = :document,
        Foto = :photo,
        Genero = :gender,
        Nascimento = :birthdayDate,
        Email = :email,
        Senha = :password
        WHERE idPaciente = :id;
        
        SELECT * FROM $TABLE_NAME 
        LEFT JOIN endereco ON ${TABLE_NAME}.Endereco_idEndereco = endereco.idEndereco
        LEFT JOIN contato ON ${TABLE_NAME}.Contato_idContato = contato.idContato
        WHERE idPaciente = :id;
    """

    const val SEARCH = """
        SELECT * FROM $TABLE_NAME p 
        LEFT JOIN endereco e ON p.Endereco_idEndereco = e.idEndereco
        LEFT JOIN contato c ON p.Contato_idContato = c.idContato
        WHERE 
    """

    const val UPDATE_PHOTO_BY_ID = """
        UPDATE $TABLE_NAME SET
        Foto = :photo
        WHERE idPaciente = :id
    """

}