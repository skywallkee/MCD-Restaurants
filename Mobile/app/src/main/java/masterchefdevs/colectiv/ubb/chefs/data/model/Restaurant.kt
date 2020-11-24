package masterchefdevs.colectiv.ubb.chefs.data.model

data class Restaurant(
    val id: String,
    val name: String,
    val address: String,
    val stars: Int,
    val layouts: List<Layout> //de completat
) {
}