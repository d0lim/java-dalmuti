package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Card;

public class MainView extends JFrame {
	PlayerPanel[] playerPanels;
	int numOfImg = 14;
	String imagePath;
	Image image;
	Image scaledImage;
	ImageIcon[] imageIcons;
	BoardPanel boardPanel;

	public MainView() {
		super("Dalmuti");

		try {
			imageIcons = new ImageIcon[numOfImg];

			for (int i = 1; i < numOfImg; i++) {
				imagePath = "res/card" + i + ".png";
				image = ImageIO.read(new File(imagePath));
				scaledImage = image.getScaledInstance(60, 80, 0);
				imageIcons[i] = new ImageIcon(scaledImage);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		JLabel infoLabel = new JLabel("정보        ...");
		infoLabel.setPreferredSize(new Dimension(200, 500));

		JPanel infoPanel = new JPanel();
//		infoPanel.setSize(200,900);
		infoPanel.setLayout(new BorderLayout());
		infoPanel.add(infoLabel);
		infoPanel.setBackground(Color.white);

		JButton confirmBtn = new JButton("확인");
		confirmBtn.setVisible(true);
		confirmBtn.setPreferredSize(new Dimension(60, 126));
		infoPanel.add(confirmBtn, BorderLayout.SOUTH);

		playerPanels = new PlayerPanel[4];
		playerPanels[0] = new PlayerPanel(BorderLayout.SOUTH);
		playerPanels[1] = new PlayerPanel(BorderLayout.WEST);
		playerPanels[2] = new PlayerPanel(BorderLayout.NORTH);
		playerPanels[3] = new PlayerPanel(BorderLayout.EAST);

		boardPanel = new BoardPanel();

		JPanel mainPanel = new JPanel();
		mainPanel.setSize(1000, 900);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(playerPanels[0], BorderLayout.SOUTH);
		mainPanel.add(playerPanels[1], BorderLayout.WEST);
		mainPanel.add(playerPanels[2], BorderLayout.NORTH);
		mainPanel.add(playerPanels[3], BorderLayout.EAST);
		mainPanel.add(boardPanel, BorderLayout.CENTER);

		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(infoPanel, BorderLayout.EAST);

		this.setPreferredSize(new Dimension(1200, 900));
		this.setVisible(true);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainView mainview = new MainView();

	}

	public void updateView(int[] ranks, String[] names, ArrayList<Card>[] hands, int exCardNum, int exCardsCount) {
		// TODO Auto-generated method stub
		for (int i = 0; i < ranks.length; i++) {
			playerPanels[i].rankLabel.setText("" + ranks[i]);
			playerPanels[i].nameLabel.setText("" + names[i]);
			if (playerPanels[i].leftCardsLabel != null) {
				playerPanels[i].leftCardsLabel.setText("남은 카드 수: " + hands[i].size());
			} else {
				for (int j = 0; j < playerPanels[i].cardLabels.length; j++) {
					playerPanels[i].cardLabels[j].setIcon(null);
				}

				for (int j = 0; j < hands[i].size(); j++) {
					int cardNum = hands[i].get(j).getNumber();
					playerPanels[i].cardLabels[j].setIcon(imageIcons[cardNum]);
				}

			}

			System.out.println(hands[i].size());
			System.out.println(hands[i]);
		}
		boardPanel.setExCards(exCardNum, exCardsCount);
	}
}
