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
	}

	private void collectTaxes() {
		
		 ArrayList hand = (ArrayList) players[0].getHand().clone();
	      // ((ArrayList<Card>)hand).add(new Card(9999999));

	      // �÷��̾���� �ڵ带

	      System.out.println(hand);
	      System.out.println(players[0].getHand());
//	      Arrays.sort(hand);
	      
	      
// ���⼭���� �ٽ� �����ؾ� �ϴ� �κ�.....
//		ArrayList<ArrayList<Card>> hand = new ArrayList<>();
//		
//		//�޹�Ƽ���� ������ ��ü �÷��̾��� �и� deep copy�ؼ� hand�� �߰�
//		for (int i = 0; i < players.length; i++) {
//			hand.add((ArrayList<Card>) players[i].getHand().clone());
//			Collections.sort(hand.get(i), new Comparator<Card>() {
//
//				@Override
//				public int compare(Card o1, Card o2) {
//					// TODO Auto-generated method stub
//					return o1.getNumber()-o2.getNumber();
//				}
//				
//			});
//		}
//		System.out.println(hand);
//		
//		//TODO ���� ���ݱ�ȯ.
//		
//		players[players.length-1]hand.get(players.length-i).get(0);
		
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
