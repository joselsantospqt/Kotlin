package com.example.applicationalertaperigo.ui.denuncia

import android.Manifest
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.applicationalertaperigo.R
import com.example.applicationalertaperigo.databinding.FragmentDenunciaCadastroBinding
import android.net.Uri
import androidx.core.app.ActivityCompat
import com.example.applicationalertaperigo.model.denuncia.DadosDenuncia
import com.google.android.gms.location.*
import com.example.applicationalertaperigo.viewModel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


class DenunciaCadastroFragment : Fragment(), LocationListener {

    private var _binding: FragmentDenunciaCadastroBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth
    lateinit var bmp: Bitmap
    private val nomeCollection = "Denuncias"
    private var fotoTirada = false
    private val REQUEST_CODE_PHOTO = 1
    val COARSE_REQUEST = 12345
    val FINE_REQUEST = 67890


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDenunciaCadastroBinding.inflate(inflater, container, false)
        val view = binding.root
        setup(view)
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PHOTO) {
            bmp = data?.extras?.get("data") as Bitmap
            fotoTirada = true
            binding.imageView.setImageBitmap(bmp)
        } else {
            Toast.makeText(this.context, "Erro ao bater foto", Toast.LENGTH_LONG).show()
        }
    }

    override fun onLocationChanged(p0: Location) {
    }

    private fun setup(view: View) {
        carregaDados()
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
                            .navigate(R.id.action_denunciaCadastroFragment_to_homeDashboardFragment)
                    }
                    2 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_denunciaCadastroFragment_to_homePerfilFragment)
                    }
                    4 -> {
                        viewModel.NavegaFragment(0)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_denunciaCadastroFragment_to_denunciaListarFragment)
                    }
                }

            }
        })
    }

    private fun carregaDados() {
        var location: Location? = obterCoordenasRede()
        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude
            val date = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
            val currentDate = date.format(Date())
            binding.inputLongitude.setText(longitude.toString())
            binding.inputLatitude.setText(latitude.toString())
            binding.inputDataHora.setText(currentDate)
        }else{
            Toast.makeText(
                context,
                "Erro, Tente Novamente ou mais tarde.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun setupButton(view: View) {
        binding.btnVoltar.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_denunciaCadastroFragment_to_homeDashboardFragment)
        }
        binding.btnRegistrar.setOnClickListener {
            addProduto()
        }
        binding.btnFoto.setOnClickListener {
            tirarFoto()
        }
    }

    private fun addProduto() {
        if (verificaDados()) {

            var novaDenuncia = DadosDenuncia(
                idUsuario = auth.currentUser?.uid.toString(),
                dateRegistro = binding.inputDataHora.text.toString(),
                latitude = binding.inputLatitude.text.toString(),
                longitude = binding.inputLongitude.text.toString(),
                descricao = binding.inputDescricao.text.toString(),
            )

            val db = Firebase.firestore

            db.collection(nomeCollection)
                .add(novaDenuncia).addOnSuccessListener { documentReference ->
                    uploadFoto(documentReference.id, nomeCollection)
                }
                .addOnFailureListener { e ->
                    Log.i(ContentValues.TAG, "Error ao Cadastar", e)
                }
        }
    }

    private fun verificaDados(): Boolean {
        if (
            binding.inputDescricao.length() < 3 ||
            !fotoTirada
        ) {
            Toast.makeText(this.context, "Dados não preenchidos", Toast.LENGTH_LONG).show()
            return false
        } else {
            return true
        }
    }

    private fun limparCampos() {
        binding.inputDataHora.setText("")
        binding.inputLatitude.setText("")
        binding.inputLongitude.setText("")
        binding.inputDescricao.setText("")
        binding.imageView.setImageResource(R.drawable.ic_person)
    }

    private fun tirarFoto() {
        val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(i, REQUEST_CODE_PHOTO)
    }

    fun uploadFoto(idUpload: String, caminho: String) {
        val progressDialog = ProgressDialog(this.context)
        progressDialog.setMessage("Cadastrando Dados...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val storage = FirebaseStorage.getInstance()
        var storageRef = storage.reference
        val fotoRef = storageRef.child("${caminho}/${idUpload}.jpg")
        val baos = ByteArrayOutputStream()
        bmp?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val uploadTask = fotoRef.putBytes(baos.toByteArray())
        uploadTask.addOnSuccessListener {
            if (progressDialog.isShowing)
                progressDialog.dismiss()
            limparCampos()
        }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this.context, "Falha ao Salvar a imagem", Toast.LENGTH_LONG).show()
            }

    }

    private fun obterCoordenasRede(): Location? {
        var location: Location? = null
        var locationManager =
            context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isProviderEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (isProviderEnabled) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    2000L,
                    0f,
                    this
                )
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }
            return location
        } else {
            this.requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                COARSE_REQUEST
            )
            return location
        }
    }

    private fun obterCoordenasGps(): Location? {
        var location: Location? = null
        val locationManager =
            context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isGpsEnabled) {

            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    2000L,
                    0f,
                    this
                )
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
            return location
        } else {
            requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                FINE_REQUEST
            )
            return location
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == COARSE_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            this.obterCoordenasRede()
        } else if (requestCode == FINE_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            this.obterCoordenasGps()
        }
    }

}