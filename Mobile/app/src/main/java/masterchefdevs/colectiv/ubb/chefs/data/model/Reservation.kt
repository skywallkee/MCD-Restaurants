package masterchefdevs.colectiv.ubb.chefs.data.model

import java.sql.Date
import java.sql.Time

data class Reservation(
    val id: Int,
    val id_M: Int,
    val id_U: Int,      //probabil id_R
    val data: Date,
    val ora : Time,
    val timp: Time,
    val telefon: String,
    val nume_pers: String,
    val email: String,

) {
}