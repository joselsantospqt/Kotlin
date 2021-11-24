package com.example.applicationquemvotei

const val OBRIGATORIEDADE = "Obrigatoriedade do voto"
const val TITULO = "Titulo eleitoral"
const val PROIBIDO = -1
const val FACULTATIVO = 0
const val OBRIGATORIO = 1
const val NOME_EXTRA = "nome"
const val CIDADE_EXTRA = "cidade"
const val IDADE_EXTRA = "idade"


val duvidas = listOf(
    Duvida("O voto é obrigatório a partir de que idade?",
        "Além de ser um direito, o voto é obrigatório no Brasil para os cidadãos brasileiros alfabetizados maiores de 18 e menores de 70 anos. É facultativo para quem tem entre 16 e 18 anos, para os maiores de 70 anos e para pessoas analfabetas.",
        OBRIGATORIEDADE),
    Duvida("Tenho 19 anos e não tirei meu título. O que faço?",
        "O brasileiro nato que não se alistar até os 19 anos ou o naturalizado que não se alistar até um ano depois de adquirida a nacionalidade brasileira terá que pagar multa (exigida no ato da inscrição) imposta pelo juiz eleitoral. Não será aplicada multa àquele que requerer sua inscrição eleitoral até 151 dias antes da eleição seguinte à data em que completar 19 anos.",
        OBRIGATORIEDADE),
    Duvida("Os eleitores que prestam o serviço militar obrigatório podem votar?",
        "Não. Durante o período do serviço militar obrigatório os conscritos não votam.",
        OBRIGATORIEDADE),
    Duvida("Como faço para tirar meu título pela primeira vez (realizar o alistamento eleitoral)?",
        "O cidadão deverá comparecer ao cartório eleitoral mais próximo de sua residência portando um documento oficial de identificação com foto, um comprovante de residência e o certificado de quitação do serviço militar obrigatório (para os maiores de 18 anos do sexo masculino).",
        TITULO),
    Duvida("Mudei de cidade. Como faço para transferir meu título?",
        "O eleitor deverá comparecer ao cartório eleitoral mais próximo de sua nova residência munido de um documento oficial de identificação com foto, do título eleitoral e de um comprovante de residência.",
        TITULO)
)