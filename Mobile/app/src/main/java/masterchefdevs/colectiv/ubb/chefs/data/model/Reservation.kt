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
/*
 "id": 4,
        "data": "2020-12-18",
        "ora": "16:48:52.285246",
        "timp": "01:00:00",
        "telefon": "0747184539",
        "nume_pers": "Gelu",
        "email": "gelu@gmail.com",
        "id_M": 2,
        "id_U": 4
    },
 */
