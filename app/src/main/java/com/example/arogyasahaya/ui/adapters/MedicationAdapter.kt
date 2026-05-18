package com.example.arogyasahaya.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.arogyasahaya.R
import com.example.arogyasahaya.data.entities.Medication

class MedicationAdapter(
    private var medications: List<Medication>,
    private val onDeleteClick: (Medication) -> Unit
) : RecyclerView.Adapter<MedicationAdapter.MedViewHolder>() {

    class MedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvMedName)
        val tvDosage: TextView = view.findViewById(R.id.tvMedDosage)
        val tvTime: TextView = view.findViewById(R.id.tvMedTime)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medication, parent, false)
        return MedViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedViewHolder, position: Int) {
        val med = medications[position]
        holder.tvName.text = med.medicineName
        holder.tvDosage.text = med.dosage
        holder.tvTime.text = med.timeOfDay
        
        holder.btnDelete.setOnClickListener {
            onDeleteClick(med)
        }
    }

    override fun getItemCount() = medications.size

    fun updateData(newList: List<Medication>) {
        medications = newList
        notifyDataSetChanged()
    }
}
