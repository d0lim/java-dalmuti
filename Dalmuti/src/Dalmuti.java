
public class Dalmuti {

	public Dalmuti() {
		System.out.println("Welcome to Dalmuti.");
		
		//ī�� ����
		Card card[] = new Card[57];
		int index = 0;
		for(int i = 1; i <= 10; i++) {			
			for(int j = 0; j < i; j++) {
				
				card[index] = new Card(i);
				//System.out.printf("%d,%d/", index,i);
				index++;	
			}
			System.out.println();
		}
		card[55] = new Card(13);
		card[56] = new Card(13);
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Dalmuti game = new Dalmuti();
	}

}
