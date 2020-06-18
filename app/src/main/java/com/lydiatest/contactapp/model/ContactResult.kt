package com.lydiatest.contactapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/* Created by *-----* Alexandre Thauvin *-----* */

data class ContactResult(
    @SerializedName("results")
    var contacts: MutableList<Contact> = mutableListOf()
):Serializable {

    @Entity(tableName = "contacts")
    data class Contact(
        var gender: String = "",
        @PrimaryKey
        var email: String = "",
        var registered: Long = 0,
        var dob: Long = 0,
        var phone: String = "",
        var cell: String = "",
        var id: Id = Id(),
        var picture: Picture = Picture(),
        var name: Name = Name(),
        var login: Login = Login(),
        var location: Location = Location(),
        var nat: String = ""
    ):Serializable

    data class Name(
        var title: String = "",
        var first: String = "",
        var last: String = ""
    ):Serializable

    data class Location(
        var street: String = "",
        var city: String = "",
        var state: String = "",
        var postcode: String = ""
    ):Serializable

    data class Login(
        var username: String = "",
        var password: String = "",
        var salt: String = "",
        var md5: String = "",
        var sha1: String = "",
        var sha256: String = ""
        ):Serializable

    data class Id(var name: String = "",
    var value: String = ""):Serializable

    data class Picture(var large: String = "",
    var medium: String = "",
    var thumbnail: String = ""):Serializable
}