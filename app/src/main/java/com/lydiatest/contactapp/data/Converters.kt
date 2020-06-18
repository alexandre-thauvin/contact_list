package com.lydiatest.contactapp.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.lydiatest.contactapp.model.ContactResult

/* Created by *-----* Alexandre Thauvin *-----* */

class Converters {

    @TypeConverter fun nameToStringJson(name: ContactResult.Name): String{
        return Gson().toJson(name)
    }

    @TypeConverter fun locationToStringJson(location: ContactResult.Location): String{
        return Gson().toJson(location)
    }

    @TypeConverter fun loginToStringJson(login: ContactResult.Login): String{
        return Gson().toJson(login)
    }

    @TypeConverter fun idToStringJson(id: ContactResult.Id): String{
        return Gson().toJson(id)
    }

    @TypeConverter fun pictureToStringJson(picture: ContactResult.Picture): String{
        return Gson().toJson(picture)
    }

    @TypeConverter fun stringJsonToName(json: String): ContactResult.Name{
        return Gson().fromJson(json, ContactResult.Name::class.java)
    }

    @TypeConverter fun stringJsonToLocation(json: String): ContactResult.Location{
        return Gson().fromJson(json, ContactResult.Location::class.java)
    }

    @TypeConverter fun stringJsonToLogin(json: String): ContactResult.Login{
        return Gson().fromJson(json, ContactResult.Login::class.java)
    }

    @TypeConverter fun stringJsonToId(json: String): ContactResult.Id{
        return Gson().fromJson(json, ContactResult.Id::class.java)
    }

    @TypeConverter fun stringJsonToPicture(json: String): ContactResult.Picture{
        return Gson().fromJson(json, ContactResult.Picture::class.java)
    }
}