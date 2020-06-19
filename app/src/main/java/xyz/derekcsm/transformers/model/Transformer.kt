package xyz.derekcsm.transformers.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Transformer(
    @PrimaryKey
    var id: String,
    var name: String,
    var team: String,
    var strength: Int,
    var intelligence: Int,
    var speed: Int,
    var endurance: Int,
    var rank: Int,
    var courage: Int,
    var firepower: Int,
    var skill: Int,
    @SerializedName("team_icon")
    var teamIcon: String?
)