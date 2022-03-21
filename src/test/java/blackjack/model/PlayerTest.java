package blackjack.model;

import static blackjack.model.Rank.*;
import static blackjack.model.Suit.*;
import static org.assertj.core.api.Assertions.assertThat;

import blackjack.model.player.Dealer;
import blackjack.model.player.Gamer;
import blackjack.model.player.Player;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PlayerTest {

    protected static Stream<Arguments> providePlayers() {
        return Stream.of(
                Arguments.of(new Gamer("player",
                        List.of(new Card(ACE, SPADE), new Card(JACK, HEART)), new Betting(1000)), 21),
                Arguments.of(new Dealer(
                        List.of(new Card(ACE, DIAMOND), new Card(JACK, DIAMOND), new Card(KING, CLOVER))), 21),
                Arguments.of(new Dealer(
                        List.of(new Card(ACE, DIAMOND), new Card(ACE, SPADE), new Card(NINE, CLOVER))), 21),
                Arguments.of(new Gamer("player",
                        List.of(new Card(QUEEN, CLOVER), new Card(JACK, HEART), new Card(KING, DIAMOND)),
                        new Betting(1000)), 30),
                Arguments.of(new Gamer("player",
                        List.of(new Card(THREE, DIAMOND), new Card(TWO, DIAMOND)), new Betting(1000)), 5)
        );
    }

    @ParameterizedTest(name = "[{index}] 점수 반환 테스트")
    @MethodSource("providePlayers")
    @DisplayName("플레이어 점수 반환 테스트")
    void gamerScore(Player player, int expect) {
        assertThat(player.score().getValue()).isEqualTo(expect);
    }
}
