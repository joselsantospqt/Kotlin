package applicationcadastroconta.ui

import android.app.Application
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.lifecycle.*
import applicationcadastroconta.domain.Conta
import applicationcadastroconta.infra.ContaRepository

class MainActivityViewModel(application: Application) : AndroidViewModel(application){
   private  lateinit var contaRepository: ContaRepository
   lateinit var contas: LiveData<List<Conta>>
   lateinit var conta: MutableLiveData<Conta>

   init {
       contaRepository = ContaRepository(application)
//       contas = contaRepository.listarContasLiveData().asLiveData()
       conta = MutableLiveData(Conta(0, "", "", ""))
   }

    fun obterPorId(id: Int): Conta{
        return  contaRepository.obterPorId(id)
    }

    fun salvarConta(conta: Conta){
        return contaRepository.salvarConta(conta)
    }
}
class MainActivityViewModelFactory(private val application:Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainActivityViewModel::class.java))
        {
            return MainActivityViewModel(application) as T
        }
    throw IllegalArgumentException("ViewModel não é compativel com essa Factory")
    }
}

