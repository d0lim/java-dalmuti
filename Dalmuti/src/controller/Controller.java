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

			if (model.someoneWantsRevolution()) {
				model.revolution();
				System.out.println("������ �ߴ�");

			} else {
				System.out.println("���� �ȱ�");

				model.collectTaxes();
			}

			System.out.println(nGames + "��° �����Դϴ�.");
			model.playGame();
			model.aggregateScore();

		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controller controller = new Controller();
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

		view.updateView(ranks, names, hands, model.getExCardNum(), model.getExCardsCount());

	}

}
