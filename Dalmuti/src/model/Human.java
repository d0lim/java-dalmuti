package model;

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
		super.updateAndDelay();
		
		return Controller.askRevolution();
		
//		System.out.println("������ ���Ͻó���? (Y/N)");
//		Scanner sc = new Scanner(System.in);
//		String input = sc.next();
//		
//		System.out.println(input);
//		
//		if (input.equals("Y")) {
//			return true;
//		}
//		
//		return false;
	}

	
	
//	@Override
//	public Card findTaxCard() {
//		// TODO Auto-generated method stub
//	super.updateAndDelay();
//
//		return null;
//	}
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
