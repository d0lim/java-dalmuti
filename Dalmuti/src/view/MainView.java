package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Card;

public class MainView extends JFrame {
	PlayerPanel[] playerPanels;
	int numOfImg = 14;
	String imagePath;
	Image image;
	Image scaledImage;
	ImageIcon[] imageIcons;
	BoardPanel boardPanel;
	static int revolution = -1;

	public MainView() {
		super("Dalmuti");

		try {
			imageIcons = new ImageIcon[numOfImg];

			for (int i = 1; i < numOfImg; i++) {
				imagePath = "res/card" + i + ".png";
				image = ImageIO.read(new File(imagePath));
				scaledImage = image.getScaledInstance(72, 96, 0);
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

		this.setPreferredSize(new Dimension(1600, 900));
		this.setVisible(true);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainView mainview = new MainView();
//		boolean revo = askRevolution();
//		System.out.println(revo);
	}

	public void updateView(int[] ranks, String[] names, ArrayList<Card>[] hands, int exCardNum, int exCardsCount) {
		// TODO Auto-generated method stub
		int meIndex = 0;
		int[] locations = new int[ranks.length];

		for (int i = 0; i < names.length; i++) {
			if (names[i].equals("Player 나")) {
				meIndex = i;
//				0 1 2나 3 -> 2 3 0 1
			}
		}

		for (int i = 0; i < locations.length; i++) {
			locations[(meIndex + i) % locations.length] = i;
		}

		for (int i = 0; i < ranks.length; i++) {
			int index = locations[i];
			playerPanels[index].rankLabel.setText("랭크 : " + ranks[i]);
			playerPanels[index].nameLabel.setText("이름 : " + names[i]);
			if (playerPanels[index].leftCardsLabel != null) {
				playerPanels[index].leftCardsLabel.setText("남은 카드 수: " + hands[i].size());
			} else {
				for (int j = 0; j < playerPanels[index].cardLabels.length; j++) {
					playerPanels[index].cardLabels[j].setIcon(null);
				}

				for (int j = 0; j < hands[i].size(); j++) {
					int cardNum = hands[i].get(j).getNumber();
					playerPanels[index].cardLabels[j].setIcon(imageIcons[cardNum]);
				}

			}

		}
		boardPanel.setExCards(exCardNum, exCardsCount);
	}

	public synchronized boolean askRevolution() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();

		JFrame revoFrame = new JFrame();
		revoFrame.setVisible(true);
		revoFrame.setLocation(screenSize.width / 2 - 300, screenSize.height / 2 - 100);
		revoFrame.setSize(600, 200);
		revoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel revoLabel = new JLabel("<html>조커  2장을  보유하여  혁명을  할  수  있습니다.<br/>혁명을  하시겠습니까?</html>");
		revoLabel.setVerticalAlignment(SwingConstants.CENTER);
		revoLabel.setHorizontalAlignment(SwingConstants.LEFT);

		JButton yesBtn = new JButton("YES");
		JButton noBtn = new JButton("NO");
		yesBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				revolution = 1;
				revoFrame.dispose();
			}
		});
		noBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				revolution = 0;
				revoFrame.dispose();
			}
		});

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
		btnPanel.add(yesBtn);
		btnPanel.add(noBtn);

		JPanel revoPanel = new JPanel();
		revoPanel.setLayout(new BoxLayout(revoPanel, BoxLayout.Y_AXIS));
		revoPanel.add(revoLabel);
		revoPanel.add(btnPanel);

		revoFrame.add(revoPanel);
		
		while (revolution == -1) {
		    try {
				wait();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		notifyAll();		
		
		boolean revo = false;
		if (revolution == 0) {
			revo = false;
		}
		else if(revolution == 1) {
			revo = true;
		}
		return revo;
	}
}
