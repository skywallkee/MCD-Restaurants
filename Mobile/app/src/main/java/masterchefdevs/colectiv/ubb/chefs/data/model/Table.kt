package masterchefdevs.colectiv.ubb.chefs.data.model

import android.widget.Button

data class Table (
    var id_button: Int,
    var button: Button,
    val id: Int,
    val id_R: Int,
    val nume: Number,
    val nr_locuri: Int,
    var reserved: Boolean = false,
    val etaj: String,
    val Ax: Int,
    val Ay: Int,
    val Bx: Int,
    val By: Int,
    val Cx: Int,
    val Cy: Int,
    val Dx: Int,
    val Dy: Int,    //de completat ?
){
}