package model;

import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import controller.Controller;
import model.Card;
import model.Player;
import view.MainView;

public class Human extends AI {

	public Human(String name) {
		super(name);
	}

	@Override
	public boolean wantsRevolution() {
		super.delay();
		
		return controller.askRevolution();
	}

	
	
	@Override
	public Card findTaxCard() {
		super.delay();
		Card tax;
		while(true) {
			System.out.println("�������� �� ī�带 �Է��Ͻÿ�.");
			Scanner sc = new Scanner(System.in);
			tax = new Card(sc.nextInt());
			
			if(!this.hand.contains(tax)) {
				System.out.println("�ش� ī�尡 �� �п� �����ϴ�.\n");
				continue;	
			}
			else if(this.rank > 2) {
				if(!tax.equals(Collections.min(hand))) {
					System.out.println("���� ���� ���� ī�带 �������� ���� �մϴ�.\n");
					continue;
				}
			}
			System.out.println("��ȣ ���¾� ���� �߳��� ����ù�~!");
			break;
		}
		
		return tax;
		
		
//		try {
//			wait();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		controller.askToChooseTaxCard();
	}
//
//	@Override
//	public int[] selectCards(int exCardNum, int exCardsCount) {
//		// TODO Auto-generated method stub
//		super.updateAndDelay();
//
//		return null;
//	}
//
//	@Override
//	public boolean wantsPass() {
//		// TODO Auto-generated method stub
//		super.updateAndDelay();
//
//		return false;
//	}
	
}
