import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Dalmuti {
	private final int NUMBER_OF_PLAYERS = 4;
	private Player[] players;
	private ArrayList<Card> cards;

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
		// TODO �޹�Ƽ�� ī�带 ���� �� ���� ������� �� ���� ������ ī�带 �ش簳����ŭ ������. ��ΰ� ������ �� ���� ��
		// ���� ���忡 ���������� �� ����� ���� ������ �� �÷��̾ ��.
		// �� ���ӿ� ���� ���尡 �ִ�. �Ѹ� ���� ��ΰ� ī�带 �������� �� �� ���� ��.

		int firstPlayer = 0;
		int thisRound = 0;
		System.out.println("\n���� ����!!\n");
		while (true) {
			System.out.println("\n**********" + thisRound + "��° ���� �Դϴ�.*************\n");
			System.out.println("���÷��̾�� " + firstPlayer + "�Դϴ�.");
			firstPlayer = playRound(firstPlayer);

			thisRound++;
			if(firstPlayer == -1) {
				break;
			}
		}
	}

	private int playRound(int firstPlayer) {

		int totalTurn = firstPlayer;
		int turn = 0;
		int[] play = new int[2];
		int passCount = 0;
		int exCardNum = 0;
		int exCardsCount = 0;

		while (true) {
			turn = totalTurn % players.length;

			System.out.println(turn + "��° player �����Դϴ�.");
			System.out.println(players[turn].getHand());

			play = players[turn].playCards(exCardNum, exCardsCount);

			if (play[0] == 0 && play[1] == 0) {
				passCount++;
			} else {
				exCardNum = play[0];
				exCardsCount = play[1];
				passCount = 0;
			}

			if (passCount == players.length) {
				System.out.println("���� ��");
				break;
			}

			totalTurn++;
			
			int donePlayers = 0;
			for (int i = 0; i < players.length; i++) {

				if (players[i].getHand().size() == 0) {
					donePlayers++;
				}
			}
			
			System.out.println(donePlayers + "���� �и� ��� �����߽��ϴ�.");
			
			if (donePlayers == players.length - 1) {
				turn = -1;
				break;
			}
			

		}
		return turn;
	}

	private void collectTaxes() {
		players[players.length - 1].payTax(2, players[0]);
		players[players.length - 2].payTax(1, players[1]);
		players[1].payTax(1, players[players.length - 2]);
		players[0].payTax(2, players[players.length - 2]);

		System.out.println("���� ��ȯ �Ϸ�");
		for (int i = 0; i < 4; i++) {
			System.out.println(players[i].getHand());
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
			System.out.println(players[k]);
		}
	}

	private boolean someoneWantsRevolution() {
		// ���� ���� üũ
		for (int i = 0; i < players.length; i++) {
			ArrayList<Card> hand = players[i].getHand();
			int indexFirst13 = hand.indexOf(new Card(13));
			int indexLast13 = hand.lastIndexOf(new Card(13));
			if (indexFirst13 != indexLast13) {
				System.out.println(players[i] + "�� 13�� 2�� ������");
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
			System.out.println(players[i].getHand());
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
			System.out.printf("%d %d\n", firstRank.get(i)[0], firstRank.get(i)[1]);

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
			System.out.printf("%d %d\n", firstRank.get(i)[0], firstRank.get(i)[1]);
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
