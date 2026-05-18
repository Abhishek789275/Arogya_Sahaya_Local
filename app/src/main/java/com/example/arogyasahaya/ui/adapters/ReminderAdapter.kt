package com.example.arogyasahaya.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.arogyasahaya.R
import com.example.arogyasahaya.data.entities.Medication

class ReminderAdapter(
    private var medications: List<Medication>,
    private val onTakeClick: (Medication) -> Unit,
    private val onSkipClick: (Medication) -> Unit
) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    class ReminderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvMedName)
        val tvDosage: TextView = view.findViewById(R.id.tvMedDosage)
        val tvTime: TextView = view.findViewById(R.id.tvMedTime)
        val btnTake: Button = view.findViewById(R.id.btnTake)
        val btnSkip: Button = view.findViewById(R.id.btnSkip)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val med = medications[position]
        holder.tvName.text = med.medicineName
        holder.tvDosage.text = med.dosage
        holder.tvTime.text = if (med.specificTime.isNotEmpty()) med.specificTime else med.timeOfDay

        if (med.isTaken) {
            holder.btnTake.text = "Taken"
            holder.btnTake.isEnabled = false
            holder.btnSkip.isEnabled = false
        } else {
            holder.btnTake.text = "Mark Taken"
            holder.btnTake.isEnabled = true
            holder.btnSkip.isEnabled = true
        }

        holder.btnTake.setOnClickListener {
            onTakeClick(med)
        }

        holder.btnSkip.setOnClickListener {
            onSkipClick(med)
        }
    }

    override fun getItemCount() = medications.size

    fun updateData(newList: List<Medication>) {
        medications = newList
        notifyDataSetChanged()
    }
}
