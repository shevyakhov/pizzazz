package pizza_logic

data class PizzaEntity(
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrls: List<String>,
    val description: String
)
