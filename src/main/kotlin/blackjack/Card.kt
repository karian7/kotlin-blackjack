package blackjack

data class Card(val name: String, val symbol: Symbol) {
    val number: List<Int> = when (name) {
        "A" -> listOf(1, 11)
        "J", "Q", "K" -> listOf(10)
        else -> listOf(name.toInt())
    }
}
