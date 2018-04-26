import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;

public class Player {
	private int posX;
	private int posY;
	private int width;
	private int height;
	private int health;
	private int maxHealth;
	private int gold;
	private int keys;
	private int bombs;
	private int fuel;
	private String moveables = " GPKBLMN";
	private String facing;
	private boolean fins;
	private boolean gloves;
	private boolean weapon;
	private boolean lavaPower;
	private Image up, down, left, right, attack, attackBoost, hp, bomb, key,
			money, hpBlack;
	private int speed;

	private int weaponManTimer;
	private int finsManTimer;
	private int glovesManTimer;
	private int lavaManTimer;
	private int talkTimer;
	private int kingTimer;
	private int walkTimer;
	private int actionTimer;
	private int fuelTimer;
	private int damageTimer;
	private int victoryTimer;
	
	private boolean gameOver;

	private char upTile, downTile, leftTile, rightTile;

	public Player(int w, int h) {
		gameOver = false;
		weaponManTimer = 0;
		finsManTimer = 0;
		glovesManTimer = 0;
		lavaManTimer = 0;
		kingTimer = 0;
		walkTimer = 0;
		actionTimer = 0;
		fuelTimer = 0;
		damageTimer = 0;
		victoryTimer = 0;
		fuel = 30;

		weapon = false;
		fins = false;
		gloves = false;
		lavaPower = false;
		facing = "up";
		health = 8;
		maxHealth = 8;
		gold = 0;
		keys = 0;
		width = w;
		height = h;
		posX = 15;
		posY = 24;
		speed = 4;
		try {
			up = ImageIO.read(Resources.load("player_up.png"));
			down = ImageIO.read(Resources.load("player_down.png"));
			left = ImageIO.read(Resources.load("player_left.png"));
			right = ImageIO.read(Resources.load("player_right.png"));
			attack = ImageIO.read(Resources.load("attack.png"));
			attackBoost = ImageIO.read(Resources.load("attackBoost.png"));
			hp = ImageIO.read(Resources.load("heartIcon.png"));
			hpBlack = ImageIO.read(Resources.load("hpBlack.png"));
			bomb = ImageIO.read(Resources.load("bombIcon.png"));
			key = ImageIO.read(Resources.load("keyIcon.png"));
			money = ImageIO.read(Resources.load("moneyIcon.png"));
		} catch (Exception e) {

		}
	}

	// If up is pressed
	public void up(Maps map) {
		if (walkTimer == 0 && actionTimer == 0 && facing.equals("up")
				&& talkTimer == 0) {
			if (posY <= 0) {// First checks steps into new map
				posY = map.getRows() - 1;
				map.open(map.getX(), map.getY() + 1);
				RPG.enemyCheck = true;
			} else {
				upTile = map.getTile(posX, posY - 1);
				if (moveables.indexOf(upTile) >= 0) {// Move
					posY -= 1;
					walkTimer = speed;
				} else if (upTile == 'S' && gloves == true
						&& map.getTile(posX, posY) != 'W') {
					// Pushable Boulder Contact
					if (map.getTile(posX, posY - 2) == ' ') {
						walkTimer = speed;
						map.setTile(posX, posY - 1, ' ');
						map.setTile(posX, posY - 2, 'S');
						posY -= 1;
					}
				} else if (fins == true && upTile == 'H') {
					posY -= 1;
					walkTimer = speed;
				} else if (upTile == 'D') {// Door contact
					if (keys > 0) {
						walkTimer = speed;
						posY -= 1;
						keys--;
						map.setTile(posX, posY, ' ');
					}
				} else if (upTile == 'Z') {
					weapon = true;
					speed = 4;
					weaponManTimer++;
					talkTimer = 160;
				} else if (upTile == 'X') {
					fins = true;
					finsManTimer++;
					talkTimer = 160;
				} else if (upTile == 'C') {
					gloves = true;
					glovesManTimer++;
					talkTimer = 160;
				} else if (upTile == 'V') {
					lavaPower = true;
					lavaManTimer++;
					talkTimer = 160;
				}
			}
		} else if (walkTimer == 0)
			facing = "up";
	}

	// If down is pressed
	public void down(Maps map) {
		if (walkTimer == 0 && actionTimer == 0 && facing.equals("down")
				&& talkTimer == 0) {
			if (posY >= map.getRows() - 1) {
				posY = 0;
				map.open(map.getX(), map.getY() - 1);
				RPG.enemyCheck = true;
			} else {
				downTile = map.getTile(posX, posY + 1);
				if (moveables.indexOf(downTile) >= 0) {
					posY += 1;
					walkTimer = speed;
				} else if (downTile == 'S' && gloves == true
						&& map.getTile(posX, posY) != 'W') {
					if (map.getTile(posX, posY + 2) == ' ') {
						walkTimer = speed;
						map.setTile(posX, posY + 1, ' ');
						map.setTile(posX, posY + 2, 'S');
						posY += 1;
					}
				} else if (fins == true && downTile == 'H') {
					posY += 1;
					walkTimer = speed;
				} else if (downTile == 'D') {
					if (keys > 0) {
						posY += 1;
						walkTimer = speed;
						keys--;
						map.setTile(posX, posY, ' ');
					}
				}
			}
		} else if (walkTimer == 0)
			facing = "down";
	}

	// If left is pressed
	public void left(Maps map) {
		if (walkTimer == 0 && actionTimer == 0 && facing.equals("left")
				&& talkTimer == 0) {
			if (posX <= 0) {
				posX = map.getCols() - 1;
				map.open(map.getX() - 1, map.getY());
				RPG.enemyCheck = true;
			} else {
				leftTile = map.getTile(posX - 1, posY);
				if (moveables.indexOf(leftTile) >= 0) {
					posX -= 1;
					walkTimer = speed;
				} else if (leftTile == 'S' && gloves == true
						&& map.getTile(posX, posY) != 'W') {
					if (map.getTile(posX - 2, posY) == ' ') {
						walkTimer = speed;
						map.setTile(posX - 1, posY, ' ');
						map.setTile(posX - 2, posY, 'S');
						posX -= 1;
					}
				} else if (fins == true && leftTile == 'H') {
					posX -= 1;
					walkTimer = speed;
				} else if (leftTile == 'D') {
					if (keys > 0) {
						posX -= 1;
						walkTimer = speed;
						keys--;
						map.setTile(posX, posY, ' ');
					}
				}
			}
		} else if (walkTimer == 0)
			facing = "left";
	}

	// If right is pressed
	public void right(Maps map) {
		if (walkTimer == 0 && actionTimer == 0 && facing.equals("right")
				&& talkTimer == 0) {
			if (posX >= map.getCols() - 1) {
				posX = 0;
				map.open(map.getX() + 1, map.getY());
				RPG.enemyCheck = true;
			} else {
				rightTile = map.getTile(posX + 1, posY);
				if (moveables.indexOf(rightTile) >= 0) {
					posX += 1;
					walkTimer = speed;
				} else if (rightTile == 'S' && gloves == true
						&& map.getTile(posX, posY) != 'W') {
					if (map.getTile(posX + 2, posY) == ' ') {
						walkTimer = speed;
						map.setTile(posX + 1, posY, ' ');
						map.setTile(posX + 2, posY, 'S');
						posX += 1;
					}
				} else if (fins == true && rightTile == 'H') {
					posX += 1;
					walkTimer = speed;
				} else if (rightTile == 'D') {
					if (keys > 0) {
						posX += 1;
						walkTimer = speed;
						keys--;
						map.setTile(posX, posY, ' ');
					}
				}
			}
		} else if (walkTimer == 0)
			facing = "right";

	}

	// If attack key is pressed
	public void attack(Maps map, Enemy[] enemies) {
		if (weapon == true && map.getTile(posX, posY) != 'H'
				&& actionTimer == 0 && fuel > 0 && walkTimer == 0) {
			actionTimer = speed;
			fuel--;
			int ran = (int) (Math.random() * 15);
			if (facing.equals("up")) {
				upTile = map.getTile(posX, posY - 1);
				for (int i = 0; i < enemies.length; i++) {
					if (enemies[i].getX() == posX
							&& enemies[i].getY() == posY - 1)
						enemies[i].kill();
				}
				if (upTile == 'R' && lavaPower == true) {
					map.setTile(posX, posY - 1, ' ');
				} else if (upTile == 'T')
					if (ran < 1)
						map.setTile(posX, posY - 1, 'L');
					else
						map.setTile(posX, posY - 1, ' ');
			} else if (facing.equals("down")) {
				downTile = map.getTile(posX, posY + 1);
				for (int i = 0; i < enemies.length; i++) {
					if (enemies[i].getX() == posX
							&& enemies[i].getY() == posY + 1)
						enemies[i].kill();
				}
				if (downTile == 'R' && lavaPower == true) {
					map.setTile(posX, posY + 1, ' ');
				} else if (downTile == 'T')
					if (ran < 1)
						map.setTile(posX, posY + 1, 'L');
					else
						map.setTile(posX, posY + 1, ' ');
			} else if (facing.equals("left")) {
				leftTile = map.getTile(posX - 1, posY);
				for (int i = 0; i < enemies.length; i++) {
					if (enemies[i].getX() == posX - 1
							&& enemies[i].getY() == posY)
						enemies[i].kill();
				}
				if (leftTile == 'R' && lavaPower == true) {
					map.setTile(posX - 1, posY, ' ');
				} else if (leftTile == 'T')
					if (ran < 1)
						map.setTile(posX - 1, posY, 'L');
					else
						map.setTile(posX - 1, posY, ' ');
			} else if (facing.equals("right")) {
				rightTile = map.getTile(posX + 1, posY);
				for (int i = 0; i < enemies.length; i++) {
					if (enemies[i].getX() == posX + 1
							&& enemies[i].getY() == posY)
						enemies[i].kill();
				}
				if (rightTile == 'R' && lavaPower == true) {
					map.setTile(posX + 1, posY, ' ');
				} else if (rightTile == 'T')
					if (ran < 1)
						map.setTile(posX + 1, posY, 'L');
					else
						map.setTile(posX + 1, posY, ' ');
				if(victoryTimer>200&&rightTile=='J'){
					map.setTile(posX + 1, posY, ' ');
					gameOver=true;
				}
			}
		}

	}

	// Checks block that player is standing on and acts accordingly
	public void checkStatus(Maps map) {
		if (map.getTile(posX, posY) == 'N' && lavaPower == false) {
			if (damageTimer == 0) {
				health--;
				damageTimer = 8;
			}
		} else if (map.getTile(posX, posY) == 'N' && lavaPower == true) {
		} else if (map.getTile(posX, posY) == 'H') {
		} else if (map.getTile(posX, posY) == 'G') {
		} else if (map.getTile(posX, posY) == 'P') {
		} else {
			if (map.getTile(posX, posY) == 'K') {
				keys++;
			} else if (map.getTile(posX, posY) == 'B') {
				bombs++;
			} else if (map.getTile(posX, posY) == 'M') {
				gold++;
			} else if (map.getTile(posX, posY) == 'L') {
				if (health < maxHealth)
					health++;
			} else if (map.getTile(posX, posY) == 'D') {
			}
			map.setTile(posX, posY, ' ');
		}
	}

	public void setPos(int x, int y) {
		posX = x;
		posY = y;
	}

	public int getHealth() {
		return health;
	}

	public int getX() {
		return posX;
	}

	public int getY() {
		return posY;
	}
	
	public boolean getGameOver(){
		return gameOver;
	}

	public void hit() {
		if (damageTimer == 0) {
			health--;
			damageTimer = 10;
		}
	}

	public void reset() {
		weaponManTimer = 0;
		finsManTimer = 0;
		glovesManTimer = 0;
		lavaManTimer = 0;
		kingTimer = 0;
		walkTimer = 0;
		actionTimer = 0;
		fuelTimer = 0;
		fuel = 30;

		weapon = false;
		fins = false;
		gloves = false;
		lavaPower = false;
		facing = "up";
		health = 8;
		maxHealth = 8;
		posX = 15;
		posY = 24;
		speed = 6;
	}

	public void updateTimers(Maps map,int enemiesLength) {
		if (walkTimer < 2) {
			checkStatus(map);
		}
		if (walkTimer > 0) {
			walkTimer--;
		}
		if (actionTimer > 0) {
			actionTimer--;
		}
		if (talkTimer > 0) {
			talkTimer--;
		}
		if (weaponManTimer > 0) {
			weaponManTimer++;
		}
		if (finsManTimer > 0) {
			finsManTimer++;
		}
		if (glovesManTimer > 0) {
			glovesManTimer++;
		}
		if (lavaManTimer > 0) {
			lavaManTimer++;
		}
		if (kingTimer > 0) {
			kingTimer++;
		}
		if (fuel < 30) {
			fuelTimer++;
			if (fuelTimer % 50 == 0) {
				fuel++;
			}
		}
		if (damageTimer > 0) {
			damageTimer--;
		}

		if (kingTimer > 200) {
		}

		if (map.getX() == 6) {
			if (kingTimer == 0) {
				if (map.getTile(posX + 29, posY) == 'J') {
					map.setTile(0, 12, 'W');
					kingTimer++;
					talkTimer = 160;
				}
			} else if (kingTimer > 200) {
				map.setTile(30, 12, ' ');
			}
		} else if (map.getX() == 7 && map.getTile(0, 12) == ' ') {
			posX++;
			map.setTile(0, 12, 'W');
		}else if(map.getX()==7&&enemiesLength==0){
			victoryTimer++;
		}

		if(health<=0){
			gameOver=true;
		}
	}

	public void paintExtras(Graphics g) {
		g.setColor(Color.black);
		g.setFont(new Font("Times New Roman", Font.BOLD, 15));

		// Draw Health

		for (int i = 0; i < maxHealth; i++)
			g.drawImage(hpBlack, 25 * i, 0, null);

		for (int i = 0; i < health; i++)
			g.drawImage(hp, 25 * i, 0, null);

		if (weapon == true && lavaPower == false) {
			g.setColor(Color.green.darker());
			g.fillRect(440, 2, 64, 15);
			g.setColor(Color.black);
			g.fillRect(442, 4, 60, 11);
			g.setColor(Color.green.brighter());
			g.fillRect(442, 4, 2 * fuel, 11);
		} else if (lavaPower == true) {
			g.setColor(Color.magenta.darker());
			g.fillRect(440, 2, 64, 15);
			g.setColor(Color.black);
			g.fillRect(442, 4, 60, 11);
			g.setColor(Color.magenta.brighter());
			g.fillRect(442, 4, 2 * fuel, 11);
		}
		// Prints Text
		g.setFont(new Font("Century Gothic", Font.BOLD, 16));
		if (weaponManTimer > 0 && weaponManTimer < 200) {
			g.setColor(Color.white);
			g.fillRect(140, 320, 300, 35);
			g.setColor(Color.black);
			g.drawRect(140, 320, 300, 35);

			if (weaponManTimer > 0 && weaponManTimer < 40)
				g.drawString("Hey you!", 150, 340);
			else if (weaponManTimer > 40 && weaponManTimer < 80)
				g.drawString("The king is in trouble...", 150, 340);
			else if (weaponManTimer > 80 && weaponManTimer < 120)
				g.drawString("Me and my brothers need your help.", 150, 340);
			else if (weaponManTimer > 120 && weaponManTimer < 160)
				g.drawString("This will help you on your journey.", 150, 340);
			else if (weaponManTimer > 160 && weaponManTimer < 200)
				g.drawString("(You can now attack with H!)", 150, 340);
		}
		if (finsManTimer > 0 && finsManTimer < 200) {
			g.setColor(Color.white);
			g.fillRect(140, 320, 300, 35);
			g.setColor(Color.black);
			g.drawRect(140, 320, 300, 35);

			if (finsManTimer > 0 && finsManTimer < 40)
				g.drawString("Why hello!", 150, 340);
			else if (finsManTimer > 40 && finsManTimer < 80)
				g.drawString("My brother told me about you.", 150, 340);
			else if (finsManTimer > 80 && finsManTimer < 120)
				g.drawString("You will need this to cross the", 150, 340);
			else if (finsManTimer > 120 && finsManTimer < 160)
				g.drawString("waters south-east of here.", 150, 340);
			else if (finsManTimer > 160 && finsManTimer < 200)
				g.drawString("(You can now walk on water!)", 150, 340);
		}
		if (glovesManTimer > 0 && glovesManTimer < 200) {
			g.setColor(Color.white);
			g.fillRect(140, 320, 300, 35);
			g.setColor(Color.black);
			g.drawRect(140, 320, 300, 35);

			if (glovesManTimer > 0 && glovesManTimer < 40)
				g.drawString("Are you who my brothers spoke of?", 150, 340);
			else if (glovesManTimer > 40 && glovesManTimer < 80)
				g.drawString("You must be... Take this!", 150, 340);
			else if (glovesManTimer > 80 && glovesManTimer < 120)
				g.drawString("You need this to push boulders.", 150, 340);
			else if (glovesManTimer > 120 && glovesManTimer < 160)
				g.drawString("Please save the king!", 150, 340);
			else if (glovesManTimer > 160 && glovesManTimer < 200)
				g.drawString("(You can now push boulders)", 150, 340);
		}
		if (lavaManTimer > 0 && lavaManTimer < 240) {
			g.setColor(Color.white);
			g.fillRect(140, 320, 300, 35);
			g.setColor(Color.black);
			g.drawRect(140, 320, 300, 35);

			if (lavaManTimer > 0 && lavaManTimer < 40)
				g.drawString("Finally you have arrived!", 150, 340);
			else if (lavaManTimer > 40 && lavaManTimer < 80)
				g.drawString("Take this and save the king!", 150, 340);
			else if (lavaManTimer > 80 && lavaManTimer < 120)
				g.drawString("This will give you the ability", 150, 340);
			else if (lavaManTimer > 120 && lavaManTimer < 160)
				g.drawString("to walk on lava and destroy rubble.", 150, 340);
			else if (lavaManTimer > 160 && lavaManTimer < 200)
				g.drawString("(You are now immune to lava,", 150, 340);
			else if (lavaManTimer > 200 && lavaManTimer < 240)
				g.drawString("and can destroy rubble!)", 150, 340);
		}
		if (kingTimer > 0 && kingTimer < 200) {
			g.setColor(Color.white);
			g.fillRect(140, 320, 300, 35);
			g.setColor(Color.black);
			g.drawRect(140, 320, 300, 35);

			if (kingTimer > 0 && kingTimer < 40)
				g.drawString("You fool...", 150, 340);
			else if (kingTimer > 40 && kingTimer < 80)
				g.drawString("You actually came...", 150, 340);
			else if (kingTimer > 80 && kingTimer < 120)
				g.drawString("I was never in trouble!", 150, 340);
			else if (kingTimer > 120 && kingTimer < 160)
				g.drawString("You fell right into my trap!", 150, 340);
			else if (kingTimer > 160 && kingTimer < 200)
				g.drawString("Prepare to meet your doom!", 150, 340);
		}
		if (victoryTimer > 0 && victoryTimer < 200&&gameOver==false) {
			g.setColor(Color.white);
			g.fillRect(140, 320, 300, 35);
			g.setColor(Color.black);
			g.drawRect(140, 320, 300, 35);

			if (victoryTimer > 0 && victoryTimer < 40)
				g.drawString("WHAT?!?!?!?!?!?!?", 150, 340);
			else if (victoryTimer > 40 && victoryTimer < 80)
				g.drawString("You killed them all?!?!?!", 150, 340);
			else if (victoryTimer > 80 && victoryTimer < 120)
				g.drawString("I always knew this day would come...", 150, 340);
			else if (victoryTimer > 120 && victoryTimer < 160)
				g.drawString("Goodbye world...", 150, 340);
			else if (victoryTimer > 160 && victoryTimer < 200)
				g.drawString("(Slay the King)", 150, 340);
		}
		if (damageTimer > 0) {
			int alpha = (8 - damageTimer) * 16;
			if (alpha > 128) {
				alpha = 128;
			}
			g.setColor(new Color(200, 0, 0, 128 - alpha));
			g.fillRect(0, 0, 600, 700);
		}
	}

	public void paint(Graphics g) // displays the player on the
									// screen
	{
		if (facing.equals("up")) {
			if (actionTimer > 0) {
				if (lavaPower == false) {
					g.drawImage(attack, posX * width + 2, (posY - 1) * height,
							posX * width + 15, (posY - 1) * height + 26,
							(actionTimer / (speed / 2)) * 13, 0,
							(actionTimer / (speed / 2)) * 13 + 13, 21, null);
				}
				if (lavaPower == true) {
					g.drawImage(attackBoost, posX * width + 2, (posY - 1)
							* height, posX * width + 15, (posY - 1) * height
							+ 26, (actionTimer / (speed / 2)) * 13, 0,
							(actionTimer / (speed / 2)) * 13 + 13, 21, null);
				}
			}
			g.drawImage(up, posX * width, posY * height - 5
					- (height * (speed - walkTimer)) / speed + height, posX
					* width + 21, posY * height - 5
					- (height * (speed - walkTimer)) / speed + height + 26,
					(walkTimer / (speed / 2)) * 21, 0,
					(walkTimer / (speed / 2)) * 21 + 21, 26, null);

		} else if (facing.equals("down")) {
			g.drawImage(down, posX * width, posY * height - 5
					+ (height * (speed - walkTimer)) / speed - height, posX
					* width + 21, posY * height - 5
					+ (height * (speed - walkTimer)) / speed - height + 26,
					(walkTimer / (speed / 2)) * 21, 0,
					(walkTimer / (speed / 2)) * 21 + 21, 26, null);
			if (actionTimer > 0) {
				if (lavaPower == false) {
					g.drawImage(attack, posX * width + 3, (posY + 1) * height
							- 6, posX * width + 16, (posY + 1) * height + 20,
							(actionTimer / (speed / 2)) * 13, 0,
							(actionTimer / (speed / 2)) * 13 + 13, 21, null);
				} else if (lavaPower == true) {
					g.drawImage(attackBoost, posX * width + 3, (posY + 1)
							* height - 6, posX * width + 16, (posY + 1)
							* height + 20, (actionTimer / (speed / 2)) * 13, 0,
							(actionTimer / (speed / 2)) * 13 + 13, 21, null);
				}
			}

		} else if (facing.equals("left")) {
			g.drawImage(left, posX * width - (width * (speed - walkTimer))
					/ speed + width, posY * height - 5, posX * width
					- (width * (speed - walkTimer)) / speed + width + 21, posY
					* height - 5 + 26, (walkTimer / (speed / 2)) * 21, 0,
					(walkTimer / (speed / 2)) * 21 + 21, 26, null);
			if (actionTimer > 0) {
				if (lavaPower == false) {
					g.drawImage(attack, (posX - 1) * width + 6, posY * height
							- 5, (posX - 1) * width + 19, posY * height + 21,
							(actionTimer / (speed / 2)) * 13, 0,
							(actionTimer / (speed / 2)) * 13 + 13, 21, null);
				} else if (lavaPower == true) {
					g.drawImage(attackBoost, (posX - 1) * width + 6, posY
							* height - 5, (posX - 1) * width + 19, posY
							* height + 21, (actionTimer / (speed / 2)) * 13, 0,
							(actionTimer / (speed / 2)) * 13 + 13, 21, null);
				}
			}
		} else if (facing.equals("right")) {
			g.drawImage(right, posX * width + (width * (speed - walkTimer))
					/ speed - width, posY * height - 5, posX * width
					+ (width * (speed - walkTimer)) / speed - width + 21, posY
					* height - 5 + 26, (walkTimer / (speed / 2)) * 21, 0,
					(walkTimer / (speed / 2)) * 21 + 21, 26, null);
			if (actionTimer > 0) {
				if (lavaPower == false) {
					g.drawImage(attack, (posX + 1) * width, posY * height - 5,
							(posX + 1) * width + 13, posY * height + 21,
							(actionTimer / (speed / 2)) * 13, 0,
							(actionTimer / (speed / 2)) * 13 + 13, 21, null);
				} else if (lavaPower == true) {
					g.drawImage(attackBoost, (posX + 1) * width, posY * height
							- 5, (posX + 1) * width + 13, posY * height + 21,
							(actionTimer / (speed / 2)) * 13, 0,
							(actionTimer / (speed / 2)) * 13 + 13, 21, null);
				}
			}
		}

	}
}
