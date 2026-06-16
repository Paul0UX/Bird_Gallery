package com.example.wiki_bids

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.wiki_bids.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: DatabaseHelper
    private var selectedPhotoUri: Uri? = null
    private var birdId: Int = -1

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedPhotoUri = it
            binding.ivBirdDetailPhoto.setImageURI(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = DatabaseHelper(requireContext())

        // Check if editing
        birdId = arguments?.getInt("birdId", -1) ?: -1
        if (birdId != -1) {
            binding.etBirdName.setText(arguments?.getString("name"))
            binding.etBirdLocation.setText(arguments?.getString("location"))
            binding.etBirdCountry.setText(arguments?.getString("country"))
            binding.etBirdDescription.setText(arguments?.getString("description"))
            
            val sex = arguments?.getString("sex")
            if (sex == "Macho") binding.rbMale.isChecked = true
            else if (sex == "Fêmea") binding.rbFemale.isChecked = true

            val photoPath = arguments?.getString("photoPath")
            if (!photoPath.isNullOrEmpty()) {
                try {
                    selectedPhotoUri = Uri.parse(photoPath)
                    binding.ivBirdDetailPhoto.setImageURI(selectedPhotoUri)
                } catch (e: SecurityException) {
                    binding.ivBirdDetailPhoto.setImageResource(android.R.drawable.ic_menu_gallery)
                }
            }
        }

        binding.btnSelectPhoto.setOnClickListener {
            selectImageLauncher.launch("image/*")
        }

        binding.btnSaveBird.setOnClickListener {
            saveBird()
        }
    }

    private fun saveBird() {
        val name = binding.etBirdName.text.toString()
        val location = binding.etBirdLocation.text.toString()
        val country = binding.etBirdCountry.text.toString()
        val description = binding.etBirdDescription.text.toString()
        
        var photoPath = arguments?.getString("photoPath") ?: ""
        
        // Se uma nova foto foi selecionada, salve-a internamente
        selectedPhotoUri?.let { uri ->
            if (uri.toString() != photoPath) {
                photoPath = saveImageToInternalStorage(uri)
            }
        }

        val sex = if (binding.rbMale.isChecked) "Macho" 
                  else if (binding.rbFemale.isChecked) "Fêmea" 
                  else ""

        if (name.isEmpty() || location.isEmpty() || sex.isEmpty()) {
            Toast.makeText(requireContext(), "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show()
            return
        }

        if (birdId == -1) {
            val result = dbHelper.addBird(name, location, country, sex, description, photoPath)
            if (result > -1) {
                Toast.makeText(requireContext(), "Ave cadastrada!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Erro ao salvar", Toast.LENGTH_SHORT).show()
            }
        } else {
            val result = dbHelper.updateBird(birdId, name, location, country, sex, description, photoPath)
            if (result > 0) {
                Toast.makeText(requireContext(), "Dados atualizados!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Erro ao atualizar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveImageToInternalStorage(uri: Uri): String {
        try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val file = java.io.File(requireContext().filesDir, "bird_${System.currentTimeMillis()}.jpg")
            val outputStream = java.io.FileOutputStream(file)
            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            return Uri.fromFile(file).toString()
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
