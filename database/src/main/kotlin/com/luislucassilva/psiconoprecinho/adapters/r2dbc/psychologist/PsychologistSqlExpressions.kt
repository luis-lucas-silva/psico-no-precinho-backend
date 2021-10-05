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
        INSERT INTO $TABLE_NAME VALUES (:id, :name, :documentType, :document, :photo, :crp, :birthdayDate, 
        :gender, :minValue, :maxValue, :description,
        :email, :password, :status, :addressId, :contactId);
        
        SELECT * FROM $TABLE_NAME 
        LEFT JOIN endereco ON ${TABLE_NAME}.Endereco_idEndereco = endereco.idEndereco
        LEFT JOIN contato ON ${TABLE_NAME}.Contato_idContato = contato.idContato
        WHERE idPsicologo = :id;
    """

    const val FIND_BY_ID = """
        SELECT * FROM ${TABLE_NAME} 
        LEFT JOIN endereco ON ${TABLE_NAME}.Endereco_idEndereco = endereco.idEndereco
        LEFT JOIN contato ON ${TABLE_NAME}.Contato_idContato = contato.idContato
        WHERE idPsicologo = :id
    """

    const val UPDATE = """
        UPDATE $TABLE_NAME SET 
        NomePsicologo = :name,
        TipoDocumento = :documentType,
        Documento = :document,
        Foto = :photo,
        CRP = :crp,
        Nascimento = :birthdayDate,
        Genero = :gender,
        ValorMin = :minValue,
        ValorMax = :maxValue,
        Descricao = :description,
        Email = :email,
        Senha = :password
        WHERE idPsicologo = :id;
        
        SELECT * FROM $TABLE_NAME 
        LEFT JOIN endereco ON $TABLE_NAME.Endereco_idEndereco = endereco.idEndereco
        LEFT JOIN contato ON $TABLE_NAME.Contato_idContato = contato.idContato
        WHERE idPsicologo = :id;
    """
}