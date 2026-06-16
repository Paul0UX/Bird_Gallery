package com.example.wiki_bids

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wiki_bids.databinding.ItemBirdBinding

class BirdAdapter(
    private var birds: List<Bird>,
    private val onView: (Bird) -> Unit,
    private val onEdit: (Bird) -> Unit,
    private val onDelete: (Bird) -> Unit
) : RecyclerView.Adapter<BirdAdapter.BirdViewHolder>() {

    class BirdViewHolder(val binding: ItemBirdBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirdViewHolder {
        val binding = ItemBirdBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BirdViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BirdViewHolder, position: Int) {
        val bird = birds[position]
        holder.binding.tvBirdName.text = bird.name
        holder.binding.tvBirdLocation.text = bird.location
        holder.binding.tvBirdSex.text = bird.sex
        
        if (!bird.photoPath.isNullOrEmpty()) {
            try {
                holder.binding.ivBirdPhoto.setImageURI(Uri.parse(bird.photoPath))
            } catch (e: SecurityException) {
                holder.binding.ivBirdPhoto.setImageResource(android.R.drawable.ic_menu_gallery)
            }
        } else {
            holder.binding.ivBirdPhoto.setImageResource(android.R.drawable.ic_menu_gallery)
        }

        holder.itemView.setOnClickListener { onView(bird) }
        holder.binding.btnEdit.setOnClickListener { onEdit(bird) }
        holder.binding.btnDelete.setOnClickListener { 
            androidx.appcompat.app.AlertDialog.Builder(holder.itemView.context)
                .setTitle("Excluir Ave")
                .setMessage("Tem certeza que deseja excluir esta ave?")
                .setPositiveButton("Sim") { _, _ -> onDelete(bird) }
                .setNegativeButton("Não", null)
                .show()
        }
    }

    override fun getItemCount() = birds.size

    fun updateData(newBirds: List<Bird>) {
        birds = newBirds
        notifyDataSetChanged()
    }
}
