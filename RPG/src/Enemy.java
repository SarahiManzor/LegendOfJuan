import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;

public class Enemy {

	// private int hp;
	private int posX;
	private int posY;
	private int width;
	private int height;
	private int direction;
	private boolean dead;
	private int walkTimer;
	private int walkDelay;
	private int attackTimer;
	private Image up, down, left, right;
	private int speed;
	private char nextTile;

	public Enemy(int[] pos, int w, int h) {
		// hp = 1;
		posX = pos[0];
		posY = pos[1];
		width = w;
		height = h;
		speed = 6;
		try {
			up = ImageIO.read(Resources.load("monster_up.png"));
			down = ImageIO.read(Resources.load("monster_down.png"));
			left = ImageIO.read(Resources.load("monster_left.png"));
			right = ImageIO.read(Resources.load("monster_right.png"));
		} catch (Exception e) {
		}
	}

	public int getX() {
		return posX;
	}

	public int getY() {
		return posY;
	}

	public boolean isDead() {
		if (dead == true)
			return true;
		else
			return false;
	}

	public void kill() {
		dead = true;
		posX = -1;
		posY = -1;
	}

	public void update(Player player, Maps map) {
		if (walkTimer > 0) {
			walkTimer--;
		}
		if (walkDelay > 0) {
			walkDelay--;
		}
		if (dead == false && walkTimer == 0) {
			if (walkDelay == 0)
				direction = (int) (Math.random() * 4);
			// Moves monster towards player
			int ran = (int) (Math.random() * 20) + 10;
			if (posX == player.getX() && posY == player.getY()) {
				player.hit();
			} else if (player.getY() - posY > -4 && player.getY() - posY < 0
					&& player.getX() - posX > -4 && player.getX() - posX < 4) {
				direction = 0;
				if (walkDelay == 1 && map.getTile(posX, posY - 1) == ' ') {
					posY -= 1;
					walkTimer = 4;
				} else if (walkDelay == 0)
					walkDelay = 8;
			} else if (player.getY() - posY < 4 && player.getY() - posY > 0
					&& player.getX() - posX > -4 && player.getX() - posX < 4) {
				direction = 1;
				if (walkDelay == 1 && map.getTile(posX, posY + 1) == ' ') {
					posY += 1;
					walkTimer = 4;
				} else if (walkDelay == 0)
					walkDelay = 8;
			} else if (player.getX() - posX > -4 && player.getX() - posX < 0
					&& player.getY() - posY > -4 && player.getY() - posY < 4) {
				direction = 2;
				if (walkDelay == 1 && map.getTile(posX - 1, posY) == ' ') {
					posX -= 1;
					walkTimer = 4;
				} else if (walkDelay == 0)
					walkDelay = 8;
			} else if (player.getX() - posX < 4 && player.getX() - posX > 0
					&& player.getY() - posY > -4 && player.getY() - posY < 4) {
				direction = 3;
				if (walkDelay == 1 && map.getTile(posX + 1, posY) == ' ') {
					posX += 1;
					walkTimer = 4;
				} else if (walkDelay == 0)
					walkDelay = 8;
			} else if (direction == 0 && map.getTile(posX, posY - 1) == ' ') {
				if (posY > 2 && walkDelay == 0) {
					posY -= 1;
					walkTimer = 4;
					walkDelay = ran;
				}
			} else if (direction == 1 && map.getTile(posX, posY + 1) == ' ') {
				if (posY < map.getRows() - 2 && walkDelay == 0) {
					posY += 1;
					walkTimer = 4;
					walkDelay = ran;
				}
			}

			else if (direction == 2 && map.getTile(posX - 1, posY) == ' ') {
				if (posX > 2 && walkDelay == 0) {
					posX -= 1;
					walkTimer = 4;
					walkDelay = ran;
				}
			}

			else if (direction == 3 && map.getTile(posX + 1, posY) == ' ') {
				if (posX < map.getCols() - 2 && walkDelay == 0) {
					posX += 1;
					walkTimer = 4;
					walkDelay = ran;
				}
			}

		}

		if (dead == true && walkTimer == 0) {
			kill();
		}
	}

	public void paint(Graphics g) {
		if (direction == 0) {
			g.drawImage(up, posX * width, posY * height - 5
					- (height * (speed - walkTimer)) / speed + height, posX
					* width + 19, posY * height - 5
					- (height * (speed - walkTimer)) / speed + height + 22,
					(walkTimer / (speed / 2)) * 19, 0,
					(walkTimer / (speed / 2)) * 19 + 19, 22, null);
		} else if (direction == 1) {
			g.drawImage(down, posX * width, posY * height - 5
					+ (height * (speed - walkTimer)) / speed - height, posX
					* width + 19, posY * height - 5
					+ (height * (speed - walkTimer)) / speed - height + 22,
					(walkTimer / (speed / 2)) * 19, 0,
					(walkTimer / (speed / 2)) * 19 + 19, 22, null);
		} else if (direction == 2) {
			g.drawImage(left, posX * width - (width * (speed - walkTimer))
					/ speed + width, posY * height - 5, posX * width
					- (width * (speed - walkTimer)) / speed + width + 19, posY
					* height - 5 + 22, (walkTimer / (speed / 2)) * 19, 0,
					(walkTimer / (speed / 2)) * 19 + 19, 22, null);
		} else if (direction == 3) {
			g.drawImage(right, posX * width + (width * (speed - walkTimer))
					/ speed - width, posY * height - 5, posX * width
					+ (width * (speed - walkTimer)) / speed - width + 19, posY
					* height - 5 + 22, (walkTimer / (speed / 2)) * 19, 0,
					(walkTimer / (speed / 2)) * 19 + 19, 22, null);
		}
	}
}
