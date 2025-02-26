package blackjack

class BlackJackGame(private val players: Players, private val deck: Blackjack) {

    fun prepareDraw(result: (Players) -> Unit = { }) {
        val allPlayers = players.allPlayers()
        for (player in allPlayers + allPlayers) {
            player.accept(deck.next())
        }
        result(allPlayers)
    }

    fun draw(isDraw: (name: String) -> Boolean, result: (CardPlayer) -> Unit = { }) {
        players.all {
            it.draw(Draw(deck::next, isDraw, result))
        }
    }

    fun endDraw(taken: () -> Unit = { }) {
        val lastTake = players.lastTake()

        if (lastTake.required()) {
            lastTake.take(deck.next())
            taken()
        }
    }
}
