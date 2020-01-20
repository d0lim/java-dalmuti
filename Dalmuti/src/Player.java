import java.util.ArrayList;


public class Player implements Comparable<Player> {
	private String name;
	/**
	 * 0 = ��� ����
	 * 1 = �޹�Ƽ
	 * 2 = ���ֱ�
	 * 3 = ����
	 * 4 = ���
	 * 5�̻��� ����� �� ������ �̸��� �־����� ����
	 */
	private int rank;
	private ArrayList<Card> hand;
	
	public Player(String name) {
		this.name = name;
		this.rank = 0;
		this.hand = new ArrayList<Card>();
		
		System.out.println("Created player " + name);
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

	public void receiveCard(Card card) {
		hand.add(card);
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
