package com.example.wiki_bids

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wiki_bids.databinding.FragmentViewBirdBinding

class ViewBirdFragment : Fragment() {

    private var _binding: FragmentViewBirdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewBirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvViewBirdName.text = arguments?.getString("name")
        binding.tvViewBirdLocation.text = "Local: ${arguments?.getString("location")}"
        
        val country = arguments?.getString("country")
        if (!country.isNullOrEmpty()) {
            binding.tvViewBirdLocation.text = "${binding.tvViewBirdLocation.text} (${country})"
        }

        binding.tvViewBirdSex.text = "Sexo: ${arguments?.getString("sex")}"
        binding.tvViewBirdDescription.text = arguments?.getString("description")

        val photoPath = arguments?.getString("photoPath")
        if (!photoPath.isNullOrEmpty()) {
            try {
                binding.ivViewBirdPhoto.setImageURI(Uri.parse(photoPath))
            } catch (e: SecurityException) {
                binding.ivViewBirdPhoto.setImageResource(android.R.drawable.ic_menu_gallery)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
