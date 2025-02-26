package blackjack

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlackjackGameTest {
    private val deck = blackjack {
        normal(2..10)
    }.build()

    @Test
    internal fun `딜러와 플레이어가 있다`() {
        val players = listOf(CardPlayer.Player("pobi"), CardPlayer.Player("json"))

        assertThat(Players(players).allPlayers())
            .hasSize(3)
            .containsAll(players + CardPlayer.Dealer())
    }

    @Test
    internal fun `처음엔 두장씩 준다`() {
        val players = Players(CardPlayer.Player("pobi"), CardPlayer.Player("json"))
        BlackJackGame(players, deck).prepareDraw()

        assertThat(players.allPlayers()).allSatisfy {
            assertThat(it.cards).hasSize(2)
        }
    }

    @Test
    @OptIn(ExperimentalStdlibApi::class)
    internal fun `한장을 받는다`() {
        val pobi = CardPlayer.Player("pobi")
        val game = BlackJackGame(Players(pobi), deck)

        var invokecount = 0
        val answer = mutableListOf(true, false)
        game.draw({ answer.removeFirst() }) {
            assertThat(it).isEqualTo(pobi)
            assertThat(it.cards).hasSize(1)
            invokecount++
        }
        assertThat(invokecount).isEqualTo(1)
    }

    @Test
    @OptIn(ExperimentalStdlibApi::class)
    internal fun `결과를 알 수 있다`() {
        val players = Players(CardPlayer.Player("pobi"))
        val game = BlackJackGame(players, deck)
        val answer = mutableListOf(true, false)
        game.draw({ answer.removeFirst() })
        game.endDraw()

        assertThat(players).allSatisfy {
            assertThat(it.cards).hasSize(1)
            assertThat(it.score()).isNotZero()
        }
    }

    @Test
    fun `딜러가 마지막 카드를 받는다`() {
        val dealer = CardPlayer.Dealer()
        BlackJackGame(Players(dealer), deck).endDraw()

        assertThat(dealer.cards).hasSize(1)
    }
}
