import java.util.ArrayList;
import java.util.Collections;

public class Player implements Comparable<Player> {
	private static int numberOfPlayers = 0;

	private String name;
	/**
	 * 0 = ��� ���� 1 = �޹�Ƽ DALMUTI 2 = ���ֱ� BISHOP 3 = ���� STONECUTTER 4 = ��� PEASANT
	 * 5�̻��� ����� �� ������ �̸��� �־����� ����
	 */
	private int rank;
	private ArrayList<Card> hand;

	public Player(String name) {
		this.name = name;
		this.rank = 0;
		this.hand = new ArrayList<Card>();

		System.out.println("Created player " + name);
		numberOfPlayers++;
	}

	public boolean wantsRevolution() {
		return Math.random() < 0.5;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void giveCard(Card card, Player toPlayer) {
		this.hand.remove(card);
		toPlayer.receiveCard(card);
	}

	public void receiveCard(Card card) {
		hand.add(card);
	}

	public void payTax(int countOfCardsForTax, Player toPlayer) {

		while (countOfCardsForTax != 0) {
			Card tax = this.findTaxCard();

			System.out.println(tax);
			this.giveCard(tax, toPlayer);
			countOfCardsForTax--;
		}
	}

	public Card findTaxCard() {

		Card tax = Collections.min(this.hand);

		if (this.rank <= 2) {
			int taxIndex = (int) (Math.random() * this.getHand().size());
			tax = this.getHand().get(taxIndex);
		}
		return tax;

	}

	public int[] selectCards(int exCardNum, int exCardsCount) {

		int[] cardsInfo = new int[2];
		int[] handCount = new int[14];

		for (int i = 0; i < hand.size(); i++) {
			handCount[hand.get(i).getNumber()] += 1;
		}

		if (exCardNum == 0) {
			for (int i = 13; i > 0; i--) {
				if (handCount[i] > 0) {
					cardsInfo[0] = i;
					cardsInfo[1] = handCount[i];
				}
			}
			
		} else {
			for (int i = exCardNum - 1; i > 0; i--) {
				if (handCount[i] >= exCardsCount) {
					cardsInfo[0] = i;
					cardsInfo[1] = exCardsCount;
					System.out.println(cardsInfo[0] + " " + cardsInfo[1]);
				}
			}
		}
		
		return cardsInfo;
	}

	public boolean wantsPass() {
		return Math.random() < 0.2;
	}

	// ���� ī�忡 ���� ������ �޾Ƽ� �� ī�� �� ��� �� �󸶳� ���� ���� ��, ī�� ����
	public int[] playCards(int exCardNum, int exCardsCount) {
		// cardsInfo�� ���� ī�带 ���� �´��� ����
		int[] cardsInfo = new int[2];
		if (!wantsPass()) {
			cardsInfo = selectCards(exCardNum, exCardsCount);

			if (cardsInfo[0] != 0 && cardsInfo[1] != 0) {
				Card card = new Card(cardsInfo[0]);
				for (int i = 0; i < cardsInfo[1]; i++) {
					this.getHand().remove(card);
				}
			}
		}
		
		if (cardsInfo[0] != 0 && cardsInfo[1] != 0) {
			System.out.println(cardsInfo[0] + "��" + cardsInfo[1] + "�� �½��ϴ�.\n");
		}
		else {
			System.out.println("�н��߽��ϴ�.\n");
		}
		return cardsInfo;
	}

	public boolean handIsEmpty() {
		return hand.size() == 0;
	}
	
	@Override
	public String toString() {
		return "Player " + name;
	}

	@Override
	public int compareTo(Player o) {
		return this.rank - o.rank;
	}

}
