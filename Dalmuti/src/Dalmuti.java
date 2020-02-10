import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Dalmuti {
	private final int NUMBER_OF_PLAYERS = 4;
	private Player[] players;
	private ArrayList<Card> cards;

	private int round = 1;
	private int firstPlayer = 0;
	private Card currentCard = null;
	private int currentCount = 0;
	private boolean[] skipped;

	public Dalmuti() {
		System.out.println("Welcome to Dalmuti.");

		createCards();
		createPlayers();
		designateRanks();
		handOutCards();
		if (someoneWantsRevolution()) {
			revolution();
		} else {
			collectTaxes();
		}
		playGame();
	}

	private void playGame() {
		while (!hasGameEnded()/* ��ΰ� ī�带 ������ */) {
			System.out.println("Round " + round);

			firstPlayer = playRound(firstPlayer);
			round++;
		}
	}

	private int playRound(int firstPlayer) {
		int currentPlayer = firstPlayer;
		currentCard = null;
		currentCount = 0;
		skipped = new boolean[players.length];

		ArrayList<Card> playedCards = null;

		while (true/* �ƹ��� �� ī�尡 ���� */) {
			System.out.println();

			skipped[currentPlayer] = true;

			if (players[currentPlayer].getHand().size() > 0) {
				System.out.println(players[currentPlayer] + "'s turn");

				playedCards = players[currentPlayer].playCards(currentCard, currentCount);

				if (playedCards.size() > 0) {
					skipped = new boolean[players.length];
					currentCard = playedCards.get(0);
					currentCount = playedCards.size();
				}
			}

			currentPlayer = (currentPlayer + 1) % 4;

			if (hasRoundEnded()) {
				return currentPlayer;
			}
		}
	}

	private boolean hasGameEnded() {
		int nPeopleWithCards = 0;

		for (int i = 0; i < players.length; i++) {
			int nCards = players[i].getHand().size();
			if (nCards > 0) {
				nPeopleWithCards++;
			}
		}
		System.out.println("ī�尡 ���� �ִ� ��� ��: " + nPeopleWithCards);

		return nPeopleWithCards < 1;
	}

	private boolean hasRoundEnded() {
		int skippedCount = 0;
		for (int i = 0; i < players.length; i++) {
//			System.out.print(skipped[i] + " ");
			if (skipped[i] == true) {
				skippedCount++;
			}
		}
//		System.out.println();
		return skippedCount == players.length - 1;
	}

	private void collectTaxes() {
		for (int i = players.length - 1; i >= 0; i--) {
			int rank = players[i].getRank();
			players[i].payTaxTo(players[players.length - rank]);
		}
	}

	private void revolution() {
		// ���� ���� ��� �ݴ�
		System.out.println("����! ��޼��� ����!!!!!!!!!!");
		for (int j = 0; j < players.length; j++) {
			int newRank = players.length - j;
			players[j].setRank(newRank);
		}
		Arrays.sort(players);
		for (int k = 0; k < players.length; k++) {
//			System.out.println(players[k]);
		}
	}

	private boolean someoneWantsRevolution() {
		// ���� ���� üũ
		for (int i = 0; i < players.length; i++) {
			ArrayList<Card> hand = players[i].getHand();
			int indexFirst13 = hand.indexOf(new Card(13));
			int indexLast13 = hand.lastIndexOf(new Card(13));
			if (indexFirst13 != indexLast13) {
//				System.out.println(players[i] + "�� 13�� 2�� ������");
				if (players[i].wantsRevolution()) {
					return true;
				}
			}
		}

		return false;
	}

	private void handOutCards() {

		Collections.shuffle(cards);

		for (int i = 0; i < cards.size(); i++) {
			int receiver = i % players.length;
			players[receiver].receiveCard(cards.get(i));
		}

		for (int i = 0; i < players.length; i++) {
//			System.out.println(players[i].getHand());
			// System.out.println(players[i].getHand().size());
		}

	}

	private void designateRanks() {
		// �̱�
		ArrayList<int[]> firstRank = new ArrayList<>();
		for (int i = 0; i < players.length; i++) {
			int tuple[] = new int[2];
			tuple[0] = i;
			tuple[1] = cards.get(i).getNumber();
			firstRank.add(tuple);
		}
		// TODO ������ϱ�
		Collections.sort(firstRank, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				// TODO Auto-generated method stub
				return o1[1] - o2[1];
			}
		});
		for (int i = 0; i < players.length; i++) {
//			System.out.printf("%d %d\n", firstRank.get(i)[0], firstRank.get(i)[1]);
		}

		for (int i = 0; i < players.length; i++) {
			players[firstRank.get(i)[0]].setRank(i + 1);
		}
		// sort players based on rank

		Arrays.sort(players);
		for (int i = 0; i < players.length; i++) {
			System.out.println(players[i]);
		}
	}

	private void createPlayers() {
		players = new Player[NUMBER_OF_PLAYERS];
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player("Me" + i);
		}
	}

	private void createCards() {
		cards = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			for (int j = 0; j < i; j++) {

				cards.add(new Card(i));

			}
		}
		cards.add(new Card(13));
		cards.add(new Card(13));
		// ����
		Collections.shuffle(cards);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Dalmuti game = new Dalmuti();

	}

}
