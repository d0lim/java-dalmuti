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

	@Override
	public String toString() {
		return "Player " + name;
	}

	@Override
	public int compareTo(Player o) {
		return this.rank - o.rank;
	}
}
