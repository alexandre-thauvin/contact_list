package com.lydiatest.contactapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lydiatest.contactapp.R
import com.lydiatest.contactapp.di.ContactApp
import com.lydiatest.contactapp.model.ContactResult
import kotlinx.android.synthetic.main.contact_item.view.*

/* Created by *-----* Alexandre Thauvin *-----* */

class ContactListAdapter(private val listener: (ContactResult.Contact) -> Unit) : RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {
    private val contacts = ArrayList<ContactResult.Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.update(contacts[position], listener)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    class ContactViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        init {
            ContactApp.contactAppComponent.inject(this)
        }

        fun update(contact: ContactResult.Contact, listener: (ContactResult.Contact) -> Unit)= with(itemView) {
            if (!contact.picture.thumbnail.isNullOrEmpty()) {
                Glide.with(context)
                    .load(contact.picture.large)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivProfilePicture)
            }
            tvContactName.text = resources.getString(R.string.common_full_name, contact.name.first, contact.name.last)
            tvEmail.text = contact.email
            rlItem.setOnClickListener { listener(contact) }
        }
    }

    fun updateList(list: List<ContactResult.Contact>){
        contacts.clear()
        contacts.addAll(list)
        notifyDataSetChanged()
    }
}