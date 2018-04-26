import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class RPG extends JFrame implements KeyListener, ActionListener {

	public static boolean enemyCheck;
	private int cols = 31;
	private int rows = 26;
	private int width = 19;
	private int height = 19;

	private boolean playerUp = false;
	private boolean playerDown = false;
	private boolean playerLeft = false;
	private boolean playerRight = false;

	private int enemyMoveTimer = 0;
	private int startTimer = 0;

	private Timer t = new Timer(50, this);

	private int gameOverTimer;

	Enemy enemies[] = new Enemy[0];

	Maps map = new Maps(cols, rows, width, height, 0, 0);
	Player player = new Player(width, height);

	public RPG() {
		t.start();
		addKeyListener(this);

		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());

		DrawArea board = new DrawArea(6 + width * cols, 29 + height * rows);

		content.add(board, BorderLayout.NORTH);

		gameOverTimer = 0;
		// Basic JFrame
		setContentPane(content);
		pack();
		setResizable(false);
		setTitle("The Legend of Juan");
		setSize(6 + width * cols, 29 + height * rows);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	class DrawArea extends JPanel {
		public DrawArea(int w, int h) {
			this.setPreferredSize(new Dimension(w, h));
		}

		public void paintComponent(Graphics g) {
			map.paint(g);
			player.paint(g);
			for (int i = 0; i < enemies.length; i++)
				enemies[i].paint(g);
			player.paintExtras(g);
			if (player.getGameOver() == true) {
				gameOverTimer += 5;
				int alpha = gameOverTimer;
				if (alpha > 255) {
					alpha = 255;
				}
				g.setColor(new Color(255, 255, 255, alpha));
				g.fillRect(0, 0, 600, 700);
				if (gameOverTimer > 255) {
					alpha = gameOverTimer - 255;
					if (alpha > 255) {
						alpha = 255;
					}
					g.setColor(new Color(0, 0, 0, alpha));
					g.drawString("Game Over", 250, 250);
					g.drawString("By: Manzor Sarahi", 250, 290);
					g.drawString("     Arsalan Khuwaja", 250, 310);
					g.drawString("     Mohammad Ali Sabowala", 250, 330);
				}
				if (gameOverTimer > 510) {
					alpha = gameOverTimer - 510;
					if (alpha > 255) {
						alpha = 255;
					}
					g.setColor(new Color(0, 0, 0, alpha));
					g.drawString("Press R to play again.", 250, 450);
				}
			}
			if (startTimer < 510) {
				int alpha;
				if (startTimer < 255) {
					alpha = startTimer;
				} else
					alpha = 510 - startTimer;
				if (alpha > 255) {
					alpha = 255;
				}
				g.setFont(new Font("Calibri", Font.BOLD, 35));
				g.setColor(new Color(255, 255, 255, alpha));
				g.drawString("The Legend", 50, 150);
				g.drawString("of Juan", 50, 180);
			}
		}

	}

	public void actionPerformed(ActionEvent arg0) {
		if (player.getGameOver() == false) {
			int enemiesOnScreen = 0;
			for (int i = 0; i < enemies.length; i++)
				if (enemies[i].isDead() == false)
					enemiesOnScreen++;

			player.updateTimers(map, enemiesOnScreen);

			startTimer += 5;

			if (playerUp == true) {
				player.up(map);
			} else if (playerDown == true) {
				player.down(map);
			} else if (playerLeft == true) {
				player.left(map);
			} else if (playerRight == true) {
				player.right(map);
			}
			for (int i = 0; i < enemies.length; i++) {
				enemies[i].update(player, map);
			}

			// Checks for enemies
			if (enemyCheck == true) {
				enemies = new Enemy[map.getEnemySize()];
				for (int i = 0; i < enemies.length; i++) {
					enemies[i] = new Enemy(map.getEnemy(), width, height);
				}
				enemyCheck = false;
			}
		}
		repaint();
	}

	public void keyPressed(KeyEvent key) {
		switch (key.getKeyCode()) {
		case (KeyEvent.VK_W):
			playerUp = true;
			break;
		case (KeyEvent.VK_S):
			playerDown = true;
			break;
		case (KeyEvent.VK_A):
			playerLeft = true;
			break;
		case (KeyEvent.VK_D):
			playerRight = true;
			break;
		case (KeyEvent.VK_H):
			player.attack(map, enemies);
			break;
		case (KeyEvent.VK_R):
			reset();
			break;
		}
		repaint();
	}

	public void keyReleased(KeyEvent key) {
		switch (key.getKeyCode()) {
		case (KeyEvent.VK_W):
			playerUp = false;
			break;
		case (KeyEvent.VK_S):
			playerDown = false;
			break;
		case (KeyEvent.VK_A):
			playerLeft = false;
			break;
		case (KeyEvent.VK_D):
			playerRight = false;
			break;
		}
		repaint();
	}

	public void keyTyped(KeyEvent key) {
	}

	public void reset() {
		map = new Maps(cols, rows, width, height, 0, 0);
		player = new Player(width, height);
		gameOverTimer = 0;
		enemyCheck = true;
	}

	public static void main(String[] args) {
		RPG window = new RPG(); // Creates a window
		window.setVisible(true); // Sets visibility to true
	}
}
