package blackjack.domain.cardgame;

import blackjack.domain.player.Dealer;
import blackjack.domain.player.Name;
import blackjack.domain.player.Player;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record CardGameResult(Map<Name, WinningStatus> totalResult) {

    public static CardGameResult of(final Dealer dealer, final List<Player> players) {
        return players.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toMap(
                                        Player::name,
                                        player -> WinningStatus.doesPlayerWin(dealer, player),
                                        (x, y) -> x,
                                        LinkedHashMap::new
                                ), CardGameResult::new));
    }

    public Map<Name, WinningStatus> totalResult() {
        return Collections.unmodifiableMap(totalResult);
    }
}
