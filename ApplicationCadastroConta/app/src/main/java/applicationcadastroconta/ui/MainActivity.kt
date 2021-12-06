package applicationcadastroconta.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import applicationcadastroconta.domain.Conta
import applicationcadastroconta.ui.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewmodel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewmodel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.viewmodel = viewmodel
        binding.lifecycleOwner = this

        binding.lstConta.layoutManager = LinearLayoutManager(this)
        val adapter = ListaContaAdapter()
        adapter.setRecycleViewItemListener(this)
        binding.lstConta.adapter = adapter
        if(viewmodel.contas.value != null){
            adapter.listaContas = viewmodel.contas.value!!
        }
        viewmodel.contas.observe(this, Observer{it.let{adapter.listaContas = it}})

        binding.btnSalvar.setOnClickListener {
            var conta = Conta(
                0,
                binding.txtNome.text.toString(),
                binding.txtEmail.text.toString(),
                binding.txtFone.text.toString()

            )
            viewmodel.salvarConta(conta)
            binding.txtNome.setText("")
            binding.txtEmail.setText("")
            binding.txtFone.setText("")
        }
    }

    override  fun recyclerViewItemClicked(view: View, id: Int){
        viewmodel.conta.value = viewmodel.obterPorId(id)

    }
}