package com.example.applicationquemvotei


fun deveVotar(idade: Int): Int {
    when(idade) {
        in 18..69 -> return OBRIGATORIO
        in 0..15 -> return PROIBIDO
        else -> return FACULTATIVO
    }
}