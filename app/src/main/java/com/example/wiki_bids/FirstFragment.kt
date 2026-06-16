package com.example.wiki_bids

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wiki_bids.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: BirdAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DatabaseHelper(requireContext())
        
        adapter = BirdAdapter(
            birds = dbHelper.getAllBirds(),
            onView = { bird ->
                val bundle = Bundle().apply {
                    putString("name", bird.name)
                    putString("location", bird.location)
                    putString("country", bird.country)
                    putString("sex", bird.sex)
                    putString("description", bird.description)
                    putString("photoPath", bird.photoPath)
                }
                findNavController().navigate(R.id.action_FirstFragment_to_ViewBirdFragment, bundle)
            },
            onEdit = { bird ->
                val bundle = Bundle().apply {
                    putInt("birdId", bird.id)
                    putString("name", bird.name)
                    putString("location", bird.location)
                    putString("country", bird.country)
                    putString("sex", bird.sex)
                    putString("description", bird.description)
                    putString("photoPath", bird.photoPath)
                }
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            },
            onDelete = { bird ->
                dbHelper.deleteBird(bird.id)
                refreshList()
            }
        )

        binding.rvBirds.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBirds.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun refreshList() {
        adapter.updateData(dbHelper.getAllBirds())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
