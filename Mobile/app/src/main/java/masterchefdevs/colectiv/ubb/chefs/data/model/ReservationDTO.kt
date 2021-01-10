package masterchefdevs.colectiv.ubb.chefs.data.model

data class ReservationDTO (
    val id_M: Int,
    val id_U: Int,
    val data: String,   //
    val ora: String,    //
    val timp: String,   //
    val telefon: String,        //--
    val nume_pers: String,  //--
    val email: String   //--
    ){
}

//{
//    "id_M" : "6",
//    "id_U": "2",
//    "data" : "2020-11-10",
//    "ora" : "14:00",
//    "timp" : "2:0",
//    "telefon" : "0747182539",
//    "nume_pers" : "Nume",
//    "email" : "email"
//}