import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Dalmuti {
	private final int NUMBER_OF_PLAYERS = 4;
	private final int NUMBER_OF_GAMES = 5;
	private Player[] players;
	private ArrayList<Card> cards;
	private int newRank;
	
	public Dalmuti() {
		System.out.println("Welcome to Dalmuti.");

//		createCards();
//		createPlayers();
//		designateRanks();
//		
//		for (int nGames = 0; nGames < NUMBER_OF_GAMES; nGames++) {
//			Arrays.sort(players);
//			handOutCards();
//			if (someoneWantsRevolution()) {
//				revolution();
//			} else {
//				collectTaxes();
//			}
//			System.out.println(nGames+"��° �����Դϴ�.");
//			playGame();
//			aggregateScore();
//		}
//		
//		for(int i = 0; i < players.length; i++) {
//			System.out.println("���� ����!");
//			System.out.println((i+1)+"���� "+players[i]+" ������ "+players[i].getScore());
//		}
//		
	}
	
	Player[] getPlayers() {
		return players;
	}

	private void playGame() {
		// TODO �޹�Ƽ�� ī�带 ���� �� ���� ������� �� ���� ������ ī�带 �ش簳����ŭ ������. ��ΰ� ������ �� ���� ��
		// ���� ���忡 ���������� �� ����� ���� ������ �� �÷��̾ ��.
		// �� ���ӿ� ���� ���尡 �ִ�. �Ѹ� ���� ��ΰ� ī�带 �������� �� �� ���� ��.
		
		int firstPlayer = 0;
		int thisRound = 0;
		newRank = 1;
//		System.out.println("\n���� ����!!\n");
		while (true) {
//			System.out.println("\n**********" + thisRound + "��° ���� �Դϴ�.*************\n");
//			System.out.println("���÷��̾�� " + firstPlayer + "�Դϴ�.");
			firstPlayer = playRound(firstPlayer);

			thisRound++;
			if(firstPlayer == -1) {
				break;
			}
		}
		
//		for (Player player: players) {
//			System.out.print(player + "   ");
//		}
//		System.out.println();
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

			if (players[turn].handIsEmpty()) {
				passCount++;
			}
			else {
//				System.out.println(turn + "��° player �����Դϴ�.");
//				System.out.println(players[turn].getHand());
				
				play = players[turn].playCards(exCardNum, exCardsCount);

				if (play[0] == 0 && play[1] == 0) {
					passCount++;
				} else {
					exCardNum = play[0];
					exCardsCount = play[1];
					passCount = 0;
				}
				if(players[turn].handIsEmpty()) {
					players[turn].setRank(newRank);
					newRank++;
					
//					System.out.println("#######" + players[turn] + "��!!!!!!!����");
//					System.out.println("########�� ��ũ��"+players[turn].getRank());	
				}
			}
			
			if (passCount == players.length - 1) {
//				System.out.println("���� ��");
				turn = (turn + 1) % players.length;
				break;
			}
			totalTurn++;
			
			int donePlayers = 0;
			for (int i = 0; i < players.length; i++) {

				if (players[i].handIsEmpty()) {
					donePlayers++;
				}
			}
			
//			System.out.println(donePlayers + "���� �и� ��� �����߽��ϴ�.");
			
			if (donePlayers == players.length - 1) {
				for (int i = 0; i < players.length; i++) {
					if(!players[i].handIsEmpty()) {
						players[i].setRank(newRank);
						break;
					}
				}
				turn = -1;
				break;
			}
		}
		return turn;
	}
	
	private void aggregateScore() {
		
		for(int i = 0; i<players.length; i++) {
			players[i].addScore(players.length - players[i].getRank());
		}
		
		Arrays.sort(players, new Comparator<Player>() {

			@Override
			public int compare(Player o1, Player o2) {
				// TODO Auto-generated method stub
				return o2.getScore() - o1.getScore();
			}
		});
	}

	private void collectTaxes() {
		players[players.length - 1].payTax(2, players[0]);
		players[players.length - 2].payTax(1, players[1]);
		players[1].payTax(1, players[players.length - 2]);
		players[0].payTax(2, players[players.length - 2]);

//		System.out.println("���� ��ȯ �Ϸ�");
		for (int i = 0; i < 4; i++) {
//			System.out.println(players[i].getHand());
		}
	}

	private void revolution() {
		// ���� ���� ��� �ݴ�
//		System.out.println("����! ��޼��� ����!!!!!!!!!!");
		for (int j = 0; j < players.length; j++) {
			int newRank = players.length - j;
			players[j].setRank(newRank);
		}
		Arrays.sort(players);
//		for (int k = 0; k < players.length; k++) {
//			System.out.println(players[k]);
//		}
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

//		for (int i = 0; i < players.length; i++) {
//			System.out.println(players[i].getHand());
//			// System.out.println(players[i].getHand().size());
//		}

	}

	void designateRanks() {
		// �̱�
		int rank = 0;
		for(int i = 0; i < players.length; i++) {
			rank = cards.get(i).getNumber();
			players[i].setRank(rank);
			
//			System.out.println(rank);
		}

		Arrays.sort(players);
		for (int i = 0; i < players.length; i++) {
			players[i].setRank(i + 1);
//			System.out.println(players[i]);
//			System.out.println("��ũ��"+players[i].getRank()+"�Դϴ�!!!");
		}
	}

	void createPlayers() {
		players = new Player[NUMBER_OF_PLAYERS];
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player("Me" + i);
		}
	}

	void createCards() {
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
