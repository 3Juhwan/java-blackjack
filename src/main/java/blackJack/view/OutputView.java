package blackJack.view;

import blackJack.domain.BlackJackGame;
import blackJack.domain.participant.Dealer;
import blackJack.domain.participant.Participant;
import blackJack.domain.participant.Participants;
import blackJack.domain.participant.Player;
import blackJack.domain.result.MatchResult;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {

    private static final String NEWLINE = System.getProperty("line.separator");

    private static final String JOINING_DELIMITER_COMMA = ", ";
    private static final String JOINING_DELIMITER_SPACE = " ";
    private static final int DEFAULT_DEALER_CARD_SIZE = 2;

    private static final String OUTPUT_MESSAGE_INIT_CARD_RESULT =
            NEWLINE.concat("%s와 %s에게 2장의 카드를 나누었습니다.").concat(NEWLINE);
    private static final String OUTPUT_MESSAGE_PARTICIPANT_HOLD_CARD =
            "%s 카드: %s".concat(NEWLINE);
    private static final String OUTPUT_MESSAGE_DEALER_RECEIVE_CARD_COUNT =
            NEWLINE.concat("%s는 %d장의 카드를 더 받았습니다.").concat(NEWLINE);
    private static final String OUTPUT_MESSAGE_PARTICIPANT_GAME_RESULT =
            "%s 카드: %s - 결과: %d".concat(NEWLINE);
    private static final String OUTPUT_MESSAGE_FINAL_MATCH_RESULT = NEWLINE.concat("## 최종 승패");
    private static final String OUTPUT_MESSAGE_FINAL_MATCH_RESULT_INFO = "%s: %s".concat(NEWLINE);

    public static void printErrorMessage(RuntimeException error) {
        System.out.println(error.getMessage());
    }

    public static void printInitCardResult(Participants participants) {
        Dealer dealer = participants.getDealer();
        List<Player> players = participants.getPlayers();

        printInitCardMessage(dealer, players);
        printInitHoldCardMessage(dealer, players);
        System.out.println();
    }

    private static void printInitCardMessage(Dealer dealer, List<Player> players) {
        String playerNames = players.stream()
                .map(Participant::getName)
                .collect(Collectors.joining(JOINING_DELIMITER_COMMA));
        System.out.printf(OUTPUT_MESSAGE_INIT_CARD_RESULT, dealer.getName(), playerNames);
    }

    private static void printInitHoldCardMessage(Dealer dealer, List<Player> players) {
        String firstDealerCardInfo = dealer.getCardsInfo().get(0);
        System.out.printf(OUTPUT_MESSAGE_PARTICIPANT_HOLD_CARD, dealer.getName(), firstDealerCardInfo);

        for (Player player : players) {
            printNowHoldCardInfo(player);
        }
    }

    public static void printNowHoldCardInfo(Player player) {
        String playerCardsInfo = String.join(JOINING_DELIMITER_COMMA, player.getCardsInfo());
        System.out.printf(OUTPUT_MESSAGE_PARTICIPANT_HOLD_CARD, player.getName(), playerCardsInfo);
    }

    public static void printDealerReceiveCardCount(Dealer dealer) {
        System.out.printf(OUTPUT_MESSAGE_DEALER_RECEIVE_CARD_COUNT,
                dealer.getName(), dealer.getCardsInfo().size() - DEFAULT_DEALER_CARD_SIZE);
        System.out.println();
    }

    public static void printGameResult(Participants participants) {
        Dealer dealer = participants.getDealer();
        List<Player> players = participants.getPlayers();

        printParticipantGameResult(dealer);
        for (Player player : players) {
            printParticipantGameResult(player);
        }
    }

    private static void printParticipantGameResult(Participant participant) {
        String playerCardsInfo = String.join(JOINING_DELIMITER_COMMA, participant.getCardsInfo());
        System.out.printf(OUTPUT_MESSAGE_PARTICIPANT_GAME_RESULT, participant.getName(), playerCardsInfo,
                participant.getScore());
    }

    public static void printFinalMatchResult(BlackJackGame blackJackGame) {
        Map<MatchResult, Integer> dealerGameResult = blackJackGame.getDealerGameResult();
        String dealerGameResultToString = getDealerGameResultToString(dealerGameResult);

        System.out.println(OUTPUT_MESSAGE_FINAL_MATCH_RESULT);
        System.out.printf(OUTPUT_MESSAGE_FINAL_MATCH_RESULT_INFO,
                blackJackGame.getDealer().getName(), dealerGameResultToString);
        blackJackGame.getPlayersGameResult().forEach((key, value) -> System.out.printf(
                OUTPUT_MESSAGE_FINAL_MATCH_RESULT_INFO, key.getName(), value.getResult()));
    }

    private static String getDealerGameResultToString(Map<MatchResult, Integer> matchResultIntegerMap) {
        List<String> matchResult = matchResultIntegerMap.entrySet().stream()
                .filter(resultCount -> resultCount.getValue() > 0)
                .map(resultCount -> resultCount.getValue() + resultCount.getKey().getResult())
                .collect(Collectors.toUnmodifiableList());
        return String.join(JOINING_DELIMITER_SPACE, matchResult);
    }
}
