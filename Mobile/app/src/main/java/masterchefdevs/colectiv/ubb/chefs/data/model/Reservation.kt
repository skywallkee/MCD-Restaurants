package masterchefdevs.colectiv.ubb.chefs.data.model

import java.sql.Time
import java.util.*

data class Reservation(
    val id: Int,
    val id_M: Int,
    val id_U: Int,      //id-user
    val nume_pers: String,
    val email: String,
    val data: String,
    val ora : String,
    val timp: String,
    val telefon: String,

    var data_conv : Calendar,
    var timp_conv: Time
) {
}
