package com.luislucassilva.psiconoprecinho.adapters.r2dbc.address

object AddressSqlExpressions {

     private const val TABLE_NAME = "endereco"

    const val INSERT = """
        INSERT INTO $TABLE_NAME VALUES (:id, :logradouro, :numero, :complemento, :cep, :bairro, :cidade, :estado)
        RETURNING *
    """
    const val UPDATE = """
        UPDATE $TABLE_NAME SET
        Logradouro = :logradouro,
        Numero = :numero,
        Complemento = :complemento,
        CEP = :cep,
        Bairro = :bairro,
        Cidade = :cidade,
        Estado = :estado
        WHERE idEndereco = :id
        RETURNING *
    """

}