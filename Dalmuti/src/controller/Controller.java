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
		model.designateRanks();

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

		Scanner sc = new Scanner(System.in);
		int k;

		for (int nGames = 0; nGames < model.NUMBER_OF_GAMES; nGames++) {
			Arrays.sort(model.getPlayers());
			model.clearHands();
			model.handOutCards();

			k = sc.nextInt();
			view.updateView(ranks, names, hands, model.getExCardNum(), model.getExCardsCount());

			if (model.someoneWantsRevolution()) {
				model.revolution();

				for (int i = 0; i < players.length; i++) {
					System.out.println(players[i]);
				}

			} else {
				System.out.println("세금 걷기");

				model.collectTaxes();
			}

			System.out.println(nGames + "번째 게임입니다.");
			model.playGame();
			model.aggregateScore();

			k = sc.nextInt();
			view.updateView(ranks, names, hands, model.getExCardNum(), model.getExCardsCount());
		}

		for (int i = 0; i < players.length; i++) {
			System.out.println("점수 집계!");
			System.out.println((i + 1) + "등은 " + players[i] + " 점수는 " + players[i].getScore());
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controller controller = new Controller();
	}

}
