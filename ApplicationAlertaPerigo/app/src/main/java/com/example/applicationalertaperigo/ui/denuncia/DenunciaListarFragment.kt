package com.example.applicationalertaperigo.ui.denuncia

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applicationalertaperigo.R
import com.example.applicationalertaperigo.databinding.FragmentDenunciaListarBinding
import com.example.applicationalertaperigo.model.denuncia.DadosDenunciaComFoto
import com.example.applicationalertaperigo.viewModel.DenunciaViewModel
import com.example.applicationalertaperigo.viewModel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class DenunciaListarFragment : Fragment(), DialogInterface.OnClickListener {

    private var _binding: FragmentDenunciaListarBinding? = null
    private val binding get() = _binding!!
    private val nomeCollection = "Denuncias"
    lateinit var adapter: DenunciaAdapter
    lateinit var listaFora: List<DadosDenunciaComFoto>
    private lateinit var auth: FirebaseAuth
    private val cargaViewModel: DenunciaViewModel by activityViewModels()
    private val viewModel: HomeViewModel by activityViewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch() {
            delay(2000)
            adapter.submitList(listaFora)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDenunciaListarBinding.inflate(inflater, container, false)
        val view = binding.root
        setup(view)

        return view
    }

    private fun setup(view: View) {
        setupObservers(view)
        setupListerner(view)
        setupRecyclerView()
    }

    private fun setupObservers(view: View) {
        viewModel.trocaFragment.observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    1 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_denunciaListarFragment_to_homeDashboardFragment)
                    }
                    2 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_denunciaListarFragment_to_homePerfilFragment)
                    }
                    3 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_denunciaListarFragment_to_denunciaCadastroFragment)
                    }
                }
            }
        })

    }

    private fun setupListerner(view: View) {
        binding.btnVoltar.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_denunciaListarFragment_to_homeDashboardFragment)
        }
    }

    fun setupRecyclerView() {
        val progressDialog = ProgressDialog(this.context)
        progressDialog.setMessage("Carregando Dados...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val db = Firebase.firestore
        val listaDenuncias: MutableList<DadosDenunciaComFoto> = mutableListOf()
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val listaTempoReal = db
            .collection(nomeCollection)
            .whereEqualTo("idUsuario", auth.currentUser?.uid)
            .orderBy("titulo", Query.Direction.DESCENDING)
            .addSnapshotListener { documents, e ->
                if (e != null) {
                    Log.i(ContentValues.TAG, "Erro ao carregar as informações", e)
                    return@addSnapshotListener
                }

                if (documents != null) {
                    for (document in documents.documentChanges) {
                        when (document.type) {
                            DocumentChange.Type.ADDED -> {
                                Log.i(
                                    "Download",
                                    "Nova Denuncia ${document.document.data.get("descricao")}"
                                )
                                /*Baixa a foto a partir do document.id*/
                                /*  val fotoRef =
                                      storageRef.child("$nomeCollection/${document.document.id}.jpg")
                                      https://stackoverflow.com/questions/56912364/displaying-image-from-firebase-cloud-firestore-by-download-url
                                https://github.com/bumptech/glide
                                      .addOnSuccessListener {*/
                                val entrada = DadosDenunciaComFoto(
                                    id = document.document.id,
                                    dateRegistro = document.document.data.get("dateRegistro")
                                        .toString(),
                                    latitude = document.document.data.get("latitude")
                                        .toString(),
                                    longitude = document.document.data.get("longitude")
                                        .toString(),
                                    descricao = document.document.data.get("descricao")
                                        .toString(),
                                    titulo = document.document.data.get("titulo")
                                        .toString(),
                                    foto = null
                                )
                                listaDenuncias.add(entrada)
                                /* }.addOnFailureListener {
                                     Log.i(
                                         "Download",
                                         "Erro ao carregar a imagem do : " +
                                                 "${document.document.id}"
                                     )
                                 }*/

                            }
                            DocumentChange.Type.MODIFIED -> {
                                Log.i(
                                    "Download",
                                    "Denuncia Alterada ${document.document.data.get("descricao")}"
                                )
//                                https://firebase.google.com/docs/firestore/query-data/listen?hl=pt&authuser=0
                            }
                            DocumentChange.Type.REMOVED -> Log.i(
                                "Download",
                                "Denuncia Removido ${document.document.data.get("descricao")}"
                            )
                        }
                    }
                }

                listaFora = listaDenuncias
                adapter = DenunciaAdapter {
                    Toast.makeText(context, "Cliquei no item: ${it.id}", Toast.LENGTH_LONG)
                        .show()
                    Log.i("AÇÃO DO CLICK", "Testeando Denuncia: ${it?.id}")

                    var produto = DadosDenunciaComFoto(
                        id = it.id,
                        dateRegistro = it.dateRegistro,
                        latitude = it.latitude,
                        longitude = it.longitude,
                        descricao = it.descricao
                    )
                    cargaViewModel.setData(produto)

                    val alertDialog = AlertDialog.Builder(requireContext())
                    alertDialog.setTitle("Alerta")
                    alertDialog.setMessage("Opção do item : ${it.descricao}")
                    alertDialog.setPositiveButton("Editar", this)
                    alertDialog.setNegativeButton("Excluir", this)
                    alertDialog.setCancelable(true)//alerta modal
                    alertDialog.show()
                }

                binding.recyclerViewlistDenuncias.layoutManager = LinearLayoutManager(this.context)
                binding.recyclerViewlistDenuncias.adapter = adapter

                adapter.submitList(listaDenuncias)

                if (progressDialog.isShowing)
                    progressDialog.dismiss()
            }
    }

    override fun onClick(dialog: DialogInterface?, id: Int) {
        val alertDialog = dialog as AlertDialog // Cast implicito
        val rotuloBotao = alertDialog.getButton(id).text
        val navController = findNavController()
        when (rotuloBotao) {
            "Editar" -> navController.navigate(R.id.action_denunciaListarFragment_to_denunciaEditarFragment)
            "Excluir" -> navController.navigate(R.id.action_denunciaListarFragment_to_denunciaExcluirFragment)
        }
    }

}