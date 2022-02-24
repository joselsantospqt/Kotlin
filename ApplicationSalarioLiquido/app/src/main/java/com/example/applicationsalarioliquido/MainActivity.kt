package com.example.applicationsalarioliquido

import android.content.Intent
import android.icu.text.NumberFormat
import android.icu.util.Currency
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.Period
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


private lateinit var botao_calcular: Button
private lateinit var salarioBruto: EditText
private lateinit var dependentes: EditText
private lateinit var pensaoAlimencia: EditText
private lateinit var planoSaude: EditText
private lateinit var outrosDescontos: EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        salarioBruto = findViewById<EditText>(R.id.txtSalarioBruto)
        dependentes = findViewById<EditText>(R.id.txtNumeroDependentes)
        pensaoAlimencia = findViewById<EditText>(R.id.txtPensaoAlimenticia)
        planoSaude = findViewById<EditText>(R.id.txtPlanoSaude)
        outrosDescontos = findViewById<EditText>(R.id.txtDescontos)
        botao_calcular = findViewById<Button>(R.id.btnCalcular)
        botao_calcular.setOnClickListener {


            if (salarioBruto.text.isNullOrEmpty() || pensaoAlimencia.text.isNullOrEmpty() || planoSaude.text.isNullOrEmpty() || outrosDescontos.text.isNullOrEmpty()) {
                Toast.makeText(this, "Todos os campos são obrigatorios", Toast.LENGTH_LONG).show()
            } else {
                val SalarioBruto = trataValor(salarioBruto.text.toString())
                val PensaoAlimenticia = trataValor(pensaoAlimencia.text.toString())
                val PlanoSaude = trataValor(planoSaude.text.toString())
                val Descontos = trataValor(outrosDescontos.text.toString())

                var desconto_INSS = 0.0
                var aliquota_IRPF = 0.0
                var porcentagem_desconto = 0.0
                var salarioLiquido: Double


                if (SalarioBruto <= 1659.38) {
                    porcentagem_desconto = 0.08
                    desconto_INSS = porcentagem_desconto * SalarioBruto
                } else if (SalarioBruto in 1659.39..2765.66) {
                    porcentagem_desconto = 0.09
                    desconto_INSS = porcentagem_desconto * SalarioBruto
                } else if (SalarioBruto in 2765.67..5531.31) {
                    porcentagem_desconto = 0.11
                    desconto_INSS = porcentagem_desconto * SalarioBruto
                } else if (SalarioBruto > 5531.31) {
                    desconto_INSS = 608.44
                }

                if (SalarioBruto in 1903.99..2826.65) {
                    aliquota_IRPF = SalarioBruto * 0.075
                } else if (SalarioBruto in 2826.66..3751.05) {
                    aliquota_IRPF = SalarioBruto * 0.15
                } else if (SalarioBruto >= 4664.68) {
                    aliquota_IRPF = SalarioBruto * 0.225
                } else if (SalarioBruto > 5531.31) {
                    aliquota_IRPF = SalarioBruto * 0.275
                }

                //Salário Líquido = Salário Bruto – INSS – IRPF – Pensão Alimentícia – Número de dependentes * R$ 189,59
                salarioLiquido =
                    SalarioBruto - desconto_INSS - aliquota_IRPF - PensaoAlimenticia - dependentes.text.toString()
                        .toInt() - 189.59

                val total_desconto =
                    desconto_INSS + aliquota_IRPF + PensaoAlimenticia + dependentes.text.toString()
                        .toInt() + 189.59
                val formatterDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                val formatterTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)
                var obj = model(
                    LocalDateTime.now().format(formatterDate).toString(),
                    LocalDateTime.now().format(formatterTime).toString(),
                    convertMoeda(SalarioBruto), convertMoeda(salarioLiquido),
                    dependentes.text.toString().toInt(),
                    convertMoeda(PensaoAlimenticia),
                    convertMoeda(PlanoSaude),
                    convertMoeda(Descontos),
                    convertMoeda(desconto_INSS),
                    convertMoeda(aliquota_IRPF),
                    porcentagem_desconto,
                    convertMoeda(total_desconto))

                var list = "Data da Consulta =  ${obj.data_Consulta}\r\n" +
                        "Hora da Consulta =  ${obj.hora_Consulta}\n" +
                        "Número de Dependentes =  ${obj.dependentes}\n" +
                        "Pensão alimentícia = ${obj.pensao_Alimencia}\n" +
                        "Plano de Saúde = ${obj.plano_Saude}\n" +
                        "Outros descontos = ${obj.outros_Descontos}\n" +
                        "Salário Líquido = ${obj.salario_Liquido}\n" +
                        "Total de descontos = ${obj.total_desconto}\n" +
                        "Porcentagem de descontos = ${obj.porcentagem_desconto}"

                val file = File(this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "teste.txt")
                val fos = FileOutputStream(file)
                fos.write(list.toByteArray())
                fos.close()

               // val load = FileInputStream(file)
               // val bytes = String(load.readBytes())
               // load.close()

               val intent = Intent(this, MainActivity2::class.java)

                startActivity(intent)



            }

        }
    }

    fun convertMoeda(moeda: Double): String {
        Locale.setDefault(Locale("pt", "BR"))
        var format = NumberFormat.getCurrencyInstance()
        //format.setCurrency(Currency.getInstance("BRL"))
        return format.format(moeda).toString();
    }

    fun trataValor(valor: String): Double {
        return valor.replace(("[^\\d,]").toRegex(), "").replace(",", ".").toDouble()
    }
}