package applicationcadastroconta.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contas")
data class Conta(
    @PrimaryKey(autoGenerate = true)
    var id: Int = -1,
    @ColumnInfo(name = "nome")
    var nome: String = "",
    @ColumnInfo(name = "email")
    var email: String = "",
    @ColumnInfo(name = "fone")
    var fone: String = "",
)
