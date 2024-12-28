package com.bignerdranch.android.movies

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.time.Year
import java.util.Date
import java.util.UUID

@Entity
data class Movie (@PrimaryKey val id: UUID = UUID.randomUUID(),
             var title: String = "",
             var year: String = "",
             var genre: String = "",
             var poster: String = "",
             var isWatched: Boolean = false){
}