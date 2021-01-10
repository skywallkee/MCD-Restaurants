package masterchefdevs.colectiv.ubb.chefs.data.model

data class Layout(
    val floor: String,
    val tables: MutableList<Table>,
    val walls: MutableList<Wall>
) {
    override fun toString(): String {
        return floor
    }
}