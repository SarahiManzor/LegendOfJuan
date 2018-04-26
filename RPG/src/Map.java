import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

class Maps {
	private char map[][]; // 2d array for the map
	private int width, height; // Width and height of the blocks
	private int mapX;
	private int mapY;

	private Image current;
	private Image wall, door, dirt, boulder, water, bushes, key, bomb, money,
			spikes, grass, pavement, rubble, health, weaponMan, finsMan,
			glovesMan, lavaMan, king;

	public Maps(int cols, int rows, int blockwidth, int blockheight, int mapX,
			int mapY) {
		this.mapX = mapX;
		this.mapX = mapY;
		map = new char[cols][rows];
		width = blockwidth;
		height = blockheight;

		open(mapX, mapY);
		try {
			dirt = ImageIO.read(Resources.load("dirt.png"));
			loadImages();
		} catch (Exception e) {
		}

	}

	public void loadImages() throws IOException {
		wall = ImageIO.read(Resources.load("wall.png"));
		dirt = ImageIO.read(Resources.load("dirt.png"));
		bushes = ImageIO.read(Resources.load("bush.png"));
		grass = ImageIO.read(Resources.load("grass.png"));
		pavement = ImageIO.read(Resources.load("pavement.png"));
		boulder = ImageIO.read(Resources.load("boulder.png"));
		spikes = ImageIO.read(Resources.load("spikes.png"));
		water = ImageIO.read(Resources.load("water.png"));
		door = ImageIO.read(Resources.load("door.png"));
		key = ImageIO.read(Resources.load("key.png"));
		health = ImageIO.read(Resources.load("heart.png"));
		bomb = ImageIO.read(Resources.load("bomb.png"));
		money = ImageIO.read(Resources.load("money.png"));
		rubble = ImageIO.read(Resources.load("rubble.png"));
		weaponMan = ImageIO.read(Resources.load("weaponMan.png"));
		finsMan = ImageIO.read(Resources.load("finsMan.png"));
		lavaMan = ImageIO.read(Resources.load("lavaMan.png"));
		glovesMan = ImageIO.read(Resources.load("glovesMan.png"));
		king = ImageIO.read(Resources.load("king.png"));
	}

	public char getTile(int col, int row) {
		return map[col][row];
	}

	public void setTile(int col, int row, char t) {
		map[col][row] = t;
	}

	public void open(int x, int y) {
		mapX = x;
		mapY = y;
		String line;
		try {
			FileReader fr = new FileReader("maps/map_" + x + "_" + y);
			BufferedReader br = new BufferedReader(fr); // Reads the files
														// specifics
			for (int row = 0; row < map[0].length; row++) {
				line = br.readLine(); // Reads the first line
				for (int col = 0; col < map.length; col++)
					map[col][row] = line.charAt(col); // Reads each character
			}
			br.close(); // Closes the reader
		} catch (Exception e) {

		}
	}

	public int getX() {
		return mapX;
	}

	public int getY() {
		return mapY;
	}

	public int getCols() {
		return map.length;
	}

	public int getRows() {
		return map[0].length;
	}

	public int getEnemySize() {
		int monsters = 0;
		for (int row = 0; row < map[0].length; row++)
			for (int col = 0; col < map.length; col++)
				if (map[col][row] == 'E')
					monsters++;
		return monsters;
	}

	public int[] getEnemy() {
		int pos[] = new int[2];
		pos[0] = -1;
		pos[1] = -1;
		for (int row = 0; row < map[0].length; row++)
			for (int col = 0; col < map.length; col++)
				if (map[col][row] == 'E') {
					pos[0] = col;
					pos[1] = row;
					map[col][row] = ' ';
					return pos;
				}
		return pos;
	}

	public void reset() {
		open(0, 0);
	}

	public void paint(Graphics g) // displays the map on the screen
	{
		current = dirt;
		for (int col = 0; col < map.length; col++)
			for (int row = 0; row < map[0].length; row++) {
				if (map[col][row] == 'W') // wall
					current = wall;
				else if (map[col][row] == 'D') // door
					current = door;
				else if (map[col][row] == ' ') // Dirt
					current = dirt;
				else if (map[col][row] == 'S') // MoveableStone
					current = boulder;
				else if (map[col][row] == 'H') // Water
					current = water;
				else if (map[col][row] == 'T') // Bushes
					current = bushes;
				else if (map[col][row] == 'E') // Enemy
					current = dirt;
				else if (map[col][row] == 'K') // Key
					current = key;
				else if (map[col][row] == 'B') // Bomb
					current = bomb;
				else if (map[col][row] == 'M') // Money
					current = money;
				else if (map[col][row] == 'N') // Spikes(needles)
					current = spikes;
				else if (map[col][row] == 'G') // Grass
					current = grass;
				else if (map[col][row] == 'P') // Pavement
					current = pavement;
				else if (map[col][row] == 'R') // Rubble
					current = rubble;
				else if (map[col][row] == 'L') // Health
					current = health;
				else if (map[col][row] == 'Z') // Weapon man
					current = weaponMan;
				else if (map[col][row] == 'X') // Fins man
					current = finsMan;
				else if (map[col][row] == 'C') // Gloves man
					current = glovesMan;
				else if (map[col][row] == 'V') // lavaMan
					current = lavaMan;
				else if (map[col][row] == 'J') // King
					current = king;

				// g.fillRect(col * width, row * height, width, height); // draw
				// block
				g.drawImage(current, col * width, row * height, null);

			}
	}
}