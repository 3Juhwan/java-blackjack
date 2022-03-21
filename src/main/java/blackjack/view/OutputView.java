package blackjack.view;

import blackjack.model.dto.CardDTO;
import blackjack.model.dto.PlayerDTO;
import blackjack.model.dto.PlayersDTO;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {

    public static final String DISTRIBUTE_CARD_MSG = "%s와 %s에게 2장을 나누었습니다.%n";
    public static final String PLAYER_STATUS_DISPLAY_MSG = "%s: %s%n";
    public static final String PLAYER_CARD_OPEN_MSG = "%s 카드: %s%n";
    public static final String DEALER_TAKE_CARD_MSG = "딜러는 16이하라 한장의 카드를 더 받았습니다.";
    public static final String DISPLAY_RESULT_MSG = "%s 카드: %s - 결과: %d%n";
    public static final String GAME_RESULT_GUIDE_MSG = "## 최종 수익";

    private OutputView() {
    }

    public static void displayErrorMessage(String errorMsg) {
        System.out.println("[ERROR] " + errorMsg);
    }

    public static void printOpenCard(PlayerDTO dealer, PlayersDTO gamers) {
        System.out.printf("\n" + DISTRIBUTE_CARD_MSG, dealer.getName(), printGamerNames(gamers));
        System.out.printf(PLAYER_STATUS_DISPLAY_MSG, dealer.getName(), printOpenCards(dealer.getCards()));
        for (PlayerDTO gamer : gamers.getPlayers()) {
            System.out.printf(PLAYER_CARD_OPEN_MSG, gamer.getName(), printOpenCards(gamer.getCards()));
        }
        System.out.println();
    }

    private static String printGamerNames(PlayersDTO gamers) {
        return gamers.getPlayers().stream()
                .map(PlayerDTO::getName)
                .collect(Collectors.joining(", "));
    }

    private static String printOpenCards(List<CardDTO> openCard) {
        return openCard.stream()
                .map(OutputView::getCardText)
                .collect(Collectors.joining(", "));
    }

    private static String getCardText(CardDTO card) {
        return RankSymbol.getMappingSymbol(card.getRank()) + SuitSymbol.getMappingSymbol(card.getSuit());
    }

    public static void printCard(PlayerDTO player) {
        System.out.printf(PLAYER_STATUS_DISPLAY_MSG, player.getName(), getTakenCards(player));
    }

    private static String getTakenCards(PlayerDTO player) {
        return player.getCards().stream()
                .map(OutputView::getCardText)
                .collect(Collectors.joining(", "));
    }

    public static void printDealerTakeCardMessage() {
        System.out.println("\n" + DEALER_TAKE_CARD_MSG);
    }

    public static void printTotalScore(PlayerDTO dealer, PlayersDTO gamers) {
        System.out.printf("\n" + DISPLAY_RESULT_MSG, dealer.getName(), getTakenCards(dealer), dealer.getScore());
        for (PlayerDTO player : gamers.getPlayers()) {
            System.out.printf(DISPLAY_RESULT_MSG, player.getName(), getTakenCards(player), player.getScore());
        }
    }

    public static void printResults(Map<String, Integer> gamersResult) {
        System.out.println("\n" + GAME_RESULT_GUIDE_MSG);
        for (String name : gamersResult.keySet()) {
            System.out.printf(PLAYER_STATUS_DISPLAY_MSG, name, gamersResult.get(name));
        }
    }
}
