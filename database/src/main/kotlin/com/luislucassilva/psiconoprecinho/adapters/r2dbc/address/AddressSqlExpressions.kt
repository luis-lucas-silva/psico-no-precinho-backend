package com.luislucassilva.psiconoprecinho.adapters.r2dbc.address

object AddressSqlExpressions {

     private const val TABLE_NAME = "endereco"

    const val INSERT = """
        INSERT INTO $TABLE_NAME VALUES (:id, :logradouro, :numero, :complemento, :cep, :bairro, 
        :cidade, :estado);
        SELECT * FROM $TABLE_NAME WHERE idEndereco = :id;
    """

    const val UPDATE = """
        UPDATE $TABLE_NAME SET
        Logradouro = :logradouro,
        Numero = :numero,
        Complemento = :complemento,
        CEP = :cep,
        Bairro = :bairro,
        Cidade = :cidade,
        Estado = :Estado)
        WHERE idEndereco = :id;
        
        SELECT * FROM $TABLE_NAME WHERE idEndereco = :id;
    """
}