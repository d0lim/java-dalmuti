
public class Card {
	private int number;
	
	public Card(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	@Override
	public String toString() {
		return "Card " + number; 
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.number == ((Card) obj).number;
	}
}