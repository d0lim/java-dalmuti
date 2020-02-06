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
	
	public int selectCard(int exCardNum) {
		// ���� ���� �� ���÷��̾ ī�� ����
		int selectedCardNum = 0;
		if (exCardNum == 0) {
			Card selectedCard = Collections.max(this.hand);
			selectedCardNum = selectedCard.getNumber();
			return selectedCardNum;
		}
		
		// �� �÷��̾�� ī�� ����
		ArrayList<Integer> handInfo = new ArrayList<Integer>();		
		for(int i = 0; i < hand.size(); i++) {
			handInfo.add(hand.get(i).getNumber());
		}
		
		selectedCardNum = Collections.max(handInfo);
		while(selectedCardNum >= exCardNum) {
			handInfo.remove(selectedCardNum);
			selectedCardNum = Collections.max(handInfo);
		}
		
		return selectedCardNum;
		
	}
	
	// ���� ���� �� ���÷��̾ �ش� ī�� �� �� �� �� ���ϱ�
	public int cardsCountToPlay(int selectedCardNum, int exCardsCount) {
		int cardsCount = 0;
		
			for(int i = 0; i < hand.size(); i++) {
				if(hand.get(i).getNumber() == selectedCardNum) {
					cardsCount++;
				}
			}
			//return cardsCount;
		if(exCardsCount == 0) {
			return cardsCount;
		}
		else if(exCardsCount <= cardsCount) {
			cardsCount = exCardsCount;
		}
		else {
			//pass �ؾ� �ϴ� ���
			return -1;
		}
		return cardsCount;
		
	}
	
	public boolean wantsPass() {
		return Math.random() < 0.2;
	}
	
	// ���� ī�忡 ���� ������ �޾Ƽ� �� ī�� �� ��� �� �󸶳� ���� ���� ��, ī�� ����
	public boolean playCards(int exCardNum, int exCardsCount) {
		if(!wantsPass() && exCardsCount != -1) {
			int selectedCardNum = selectCard(exCardNum);
			int cardsCount = cardsCountToPlay(selectedCardNum, exCardsCount);
			Card card = new Card(selectedCardNum);
			for (int i = 0; i < cardsCount; i++) {
				this.getHand().remove(card);
			}
			return true;
		}
		return false;
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
