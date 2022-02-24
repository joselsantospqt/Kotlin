package com.example.applicationsalarioliquido

import java.util.*

data class model(
    var data_Consulta: String,
    var hora_Consulta: String,
    var salario_Bruto: String,
    var salario_Liquido: String,
    var dependentes: Int,
    var pensao_Alimencia: String,
    var plano_Saude: String,
    var outros_Descontos: String,
    var desconto_INSS: String,
    var aliquota_IRPF: String,
    var porcentagem_desconto: Double,
    var total_desconto: String
)