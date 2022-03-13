package com.lydiatest.contactapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lydiatest.contactapp.model.ContactResult
import kotlinx.android.synthetic.main.dialog_contact_detail.*
import kotlinx.android.synthetic.main.info_item.view.*


/* Created by *-----* Alexandre Thauvin *-----* */

class ContactDetailDialog : DialogFragment() {
    private var isPasswordDisplayed = false
    private lateinit var contact: ContactResult.Contact
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_contact_detail, container)
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        initView()
        initListeners(v)
    }

    private fun initView(){
        val bundle = arguments
        bundle?.let {
            contact = it.getSerializable(CONTACT) as ContactResult.Contact
            if (!contact.picture.thumbnail.isNullOrEmpty()) {
                Glide.with(requireContext())
                    .load(contact.picture.large)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivProfilePicture)
            }
            tvContactName.text = getString(R.string.contact_detail_title_and_full_name,
                contact.name.title, contact.name.first, contact.name.last)
            llGender.tvTitleInfo.text = getString(R.string.contact_detail_gender)
            llGender.tvValueInfo.text = contact.gender
            llEmail.tvTitleInfo.text = getString(R.string.contact_detail_email)
            llEmail.tvValueInfo.text = contact.email
            llLocation.tvTitleInfo.text = getString(R.string.contact_detail_address)
            llLocation.tvValueInfo.text = getString(R.string.contact_detail_address_format,
                contact.location.street, contact.location.postcode, contact.location.city, contact.location.state)
            llLogin.tvTitleInfo.text = getString(R.string.contact_detail_login)
            val salt = getString(R.string.contact_detail_salt, contact.login.salt)
            val md5 = getString(R.string.contact_detail_md5, contact.login.md5)
            val username = getString(R.string.contact_detail_username, contact.login.username)
            val sha1 = getString(R.string.contact_detail_sha1, contact.login.sha1)
            val sha256 = getString(R.string.contact_detail_sha256, contact.login.sha256)
            llLogin.tvValueInfo.text = getString(R.string.contact_detail_login_format,
            username, salt, md5, sha1, sha256)
            llRegistered.tvTitleInfo.text = getString(R.string.contact_detail_registered)
            llRegistered.tvValueInfo.text = contact.registered.toString()
            llDob.tvTitleInfo.text = getString(R.string.contact_detail_dob)
            llDob.tvValueInfo.text = contact.dob.toString()
            llPhone.tvTitleInfo.text = getString(R.string.contact_detail_phone)
            llPhone.tvValueInfo.text = contact.phone
            llCell.tvTitleInfo.text = getString(R.string.contact_detail_cell)
            llCell.tvValueInfo.text = contact.cell
            llNat.tvTitleInfo.text = getString(R.string.contact_detail_nat)
            llNat.tvValueInfo.text = contact.nat
            llId.tvTitleInfo.text = getString(R.string.contact_detail_id)
            llId.tvValueInfo.text = getString(R.string.contact_detail_id_format,
            contact.id.name, contact.id.value)
        }
    }

    private fun initListeners(v: View){
        v.findViewById<ImageView>(R.id.ivCross).setOnClickListener {
            this.dismiss()
        }
        v.findViewById<ImageView>(R.id.ivEye).setOnClickListener {
            if (isPasswordDisplayed){
                ivEye.setImageDrawable(resources.getDrawable(R.drawable.ic_hidden, null))
                tvPassword.text = getString(R.string.contact_detail_password_hidden)
            }
            else {
                ivEye.setImageDrawable(resources.getDrawable(R.drawable.ic_eye, null))
                tvPassword.text = contact.login.password
            }
            isPasswordDisplayed = !isPasswordDisplayed
        }
    }

    override fun onResume() {
        super.onResume()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window!!.setLayout(width, height)
    }

    companion object {
        const val CONTACT = "CONTACT"
    }
}