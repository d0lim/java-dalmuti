package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import model.Card;
import model.Dalmuti;
import model.Player;
import view.MainView;

public class Controller {
	Dalmuti model = new Dalmuti();
	MainView view = new MainView();

	public Controller() {
		model.createCards();
		model.createPlayers();
		
		Player[] players = model.getPlayers();
		for (int i = 0; i < players.length; i++) {
			players[i].controller = this;
		}
		
		model.designateRanks();

		for (int nGames = 0; nGames < model.NUMBER_OF_GAMES; nGames++) {
			Arrays.sort(model.getPlayers());
			model.clearHands();
			model.handOutCards();
			
			updateView();

			if (model.someoneWantsRevolution()) {
				model.revolution();
								
				System.out.println("������ �ߴ�");

			} else {
				System.out.println("���� �ȱ�");

				model.collectTaxes();
			}

			updateView();

			System.out.println(nGames + "��° �����Դϴ�.");
			playGame();
			model.aggregateScore();
			
			updateView();
			
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controller controller = new Controller();
	}
	
	public void playGame() {
		// TODO �޹�Ƽ�� ī�带 ���� �� ���� ������� �� ���� ������ ī�带 �ش簳����ŭ ������. ��ΰ� ������ �� ���� ��
		// ���� ���忡 ���������� �� ����� ���� ������ �� �÷��̾ ��.
		// �� ���ӿ� ���� ���尡 �ִ�. �Ѹ� ���� ��ΰ� ī�带 �������� �� �� ���� ��.

		int firstPlayer = 0;
		int thisRound = 0;
		model.newRank = 1;
//		System.out.println("\n���� ����!!\n");
		while (true) {
//			System.out.println("\n**********" + thisRound + "��° ���� �Դϴ�.*************\n");
//			System.out.println("���÷��̾�� " + firstPlayer + "�Դϴ�.");
			firstPlayer = playRound(firstPlayer);

			thisRound++;
			if (firstPlayer == -1) {
				break;
			}
		}

//		for (Player player: players) {
//			System.out.print(player + "   ");
//		}
//		System.out.println();
	}

	private int playRound(int firstPlayer) {

		model.totalTurn = firstPlayer;
		model.turn = 0;
		model.play = new int[2];
		model.passCount = 0;
		model.exCardNum = 0;
		model.exCardsCount = 0;
		
		Player[] players = model.getPlayers();
		
		while (true) {
			model.turn = model.totalTurn % players.length;

			if (players[model.turn].handIsEmpty()) {
				model.passCount++;
			} else {
//				System.out.println(turn + "��° player �����Դϴ�.");
//				System.out.println(players[turn].getHand());

				model.play = players[model.turn].playCards(model.exCardNum, model.exCardsCount);

				updateView();
				
				if (model.play[0] == 0 && model.play[1] == 0) {
					model.passCount++;
				} else {
					model.exCardNum = model.play[0];
					model.exCardsCount = model.play[1];
					model.passCount = 0;
				}
				if (players[model.turn].handIsEmpty()) {
					players[model.turn].setRank(model.newRank);
					model.newRank++;

//					System.out.println("#######" + players[turn] + "��!!!!!!!����");
//					System.out.println("########�� ��ũ��"+players[turn].getRank());	
				}
			}

			if (model.passCount == players.length - 1) {
//				System.out.println("���� ��");
				model.turn = (model.turn + 1) % players.length;
				break;
			}
			model.totalTurn++;

			int donePlayers = 0;
			for (int i = 0; i < players.length; i++) {

				if (players[i].handIsEmpty()) {
					donePlayers++;
				}
			}

//			System.out.println(donePlayers + "���� �и� ��� �����߽��ϴ�.");

			if (donePlayers == players.length - 1) {
				for (int i = 0; i < players.length; i++) {
					if (!players[i].handIsEmpty()) {
						players[i].setRank(model.newRank);
						break;
					}
				}
				model.turn = -1;
				break;
			}
		}
		return model.turn;
	}

	public void updateView() {
		Player[] players = model.getPlayers();
		int[] ranks = new int[players.length];
		String[] names = new String[players.length];
		ArrayList<Card>[] hands = new ArrayList[players.length];

		for (int i = 0; i < players.length; i++) {
			ranks[i] = players[i].getRank();
			names[i] = players[i].toString();
			hands[i] = players[i].getHand();
		}

		view.updateView(ranks, names, hands, model.exCardNum, model.exCardsCount);

	}

	public boolean askRevolution() {
		return view.askRevolution();
		
	}

//	public void askToChooseTaxCard(ArrayList<Card> hand) {
//		view.askToChooseTaxCard(hand);
//	}

}
