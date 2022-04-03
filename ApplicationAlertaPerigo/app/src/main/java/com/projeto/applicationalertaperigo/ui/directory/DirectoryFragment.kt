package com.projeto.applicationalertaperigo.ui.directory

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import br.edu.infnet.dr4_e5_a1_criptostring.Criptografador
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.projeto.applicationalertaperigo.R
import com.projeto.applicationalertaperigo.databinding.FragmentDenunciaCadastroBinding
import com.projeto.applicationalertaperigo.databinding.FragmentDirectoryBinding
import com.projeto.applicationalertaperigo.model.denuncia.DadosDenuncia
import com.projeto.applicationalertaperigo.viewModel.HomeViewModel


class DirectoryFragment : Fragment() {

    private var _binding: FragmentDirectoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val viewModel: HomeViewModel by activityViewModels()
    val READ_REQUEST = 888
    private val cripto = Criptografador()
    val gson = Gson()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDirectoryBinding.inflate(inflater, container, false)
        val view = binding.root
        setup(view)

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == READ_REQUEST && resultCode == AppCompatActivity.RESULT_OK)
            carregaDocumento(data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            this.abrirDiretorio()
        } else
            Toast.makeText(context, "A permissão foi negada", Toast.LENGTH_LONG).show()
    }

    private fun setup(view: View) {
        setupButton(view)
        setupObservers(view)
    }

    private fun setupObservers(view: View) {
        viewModel.trocaFragment.observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    1 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_directoryFragment_to_homeDashboardFragment)
                    }
                    2 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_directoryFragment_to_homePerfilFragment)
                    }
                    3 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_directoryFragment_to_denunciaCadastroFragment)
                    }
                    4 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_directoryFragment_to_denunciaListarFragment)
                    }
                }

            }
        })
    }

    private fun setupButton(view: View) {
        binding.btnArquivo.setOnClickListener {
            abrirDiretorio()
        }

        binding.btnDescripto.setOnClickListener {
            binding.textView3.setText(cripto.decipher(binding.textView3.text.toString()))
        }
    }

    private fun abrirDiretorio() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "A permissão foi concedida", Toast.LENGTH_LONG).show()
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.setType("*/*")
            startActivityForResult(intent, READ_REQUEST)
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                READ_REQUEST
            )
        }
    }

    private fun carregaDocumento(data: Intent?) {
        binding.textView3.setText(null)
        val fis = getActivity()?.getContentResolver()?.openInputStream(data?.getData()!!)
        val bytes = fis!!.readBytes()
        fis!!.close()
        val uri: Uri = data?.data!!
        val documentFile = DocumentFile.fromSingleUri(requireContext(), uri)
        if (documentFile!!.type.equals("text/plain")) {
            binding.textView3.setText(String(bytes))
            Toast.makeText(requireContext(), "Carregado com sucesso !", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(
                requireContext(),
                "O tipo de arquivo é incompativel.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}