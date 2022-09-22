import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener{
	
	static final int SCREEN_WIDTH = 1275;
	static final int SCREEN_HEIGHT = 625;
	
//	Units are the boxes of the 2d grid.
//	So unit size is the size of the boxes.
	static final int UNIT_SIZE = 25;
	
//	Game Units give the number of units.
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
	
//	More the delay more the snake will run slow
	static final int DELAY = 100;
	
//	x and y array contains the coordinates of the body of the snake
	static final int x[] = new int[GAME_UNITS];
	static final int y[] = new int[GAME_UNITS];
	
	int bodyParts = 3; //	Initial length of the snake
	int applesEaten; //	Basically the score
	
	int appleX; // appleX and appleY contains coordinates of the apple generated
	int appleY;
	
	char direction = 'R'; // Initial direction of the snake
	boolean running = false;
	
	Timer timer; // timer starts instantiating the action events
	Random random; // To generate the random numbers / for random location of the apple
	
	GamePanel() { // Creating the game panel constructor
		
//		To generate the random numbers / for random location of the apple
		random = new Random();
		this.setBackground(Color.black); // Setting background color
		
//		Setting the size of the window
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		
//		Sets the focusable state of this Component
		this.setFocusable(true);
		
//		Receives Key Events
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}

	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		draw(g);
		
	}
	public void draw(Graphics g) {
		
		if(running) {
//			for(int i = 0; i<SCREEN_WIDTH/UNIT_SIZE; i++) {
//				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
//			}
//			for(int i = 0; i<SCREEN_HEIGHT/UNIT_SIZE; i++) {
//				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
//			}
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			for(int i = 0; i < bodyParts; i++) {
				
				if(i == 0) {
					g.setColor(Color.green);
				}
				else{
					g.setColor(new Color(45, 180, 0));
				}
				g.fill3DRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE, true);
			}
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		}
		else {
			timer.stop();
			gameOver(g);
		}
	}
	public void newApple() {
		
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
	}
	public void move() {
		
		for(int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	public void checkApple() {
		
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	public void checkCollision() {
		
		for(int i = bodyParts; i > 0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		
		if(x[0] < 0) {
			running = false;
		}
		else if(x[0] > (SCREEN_WIDTH - UNIT_SIZE)) {
			running = false;
		}
		else if(y[0] < 0) {	
			running = false;
		}
		else if(y[0] > (SCREEN_HEIGHT - UNIT_SIZE)) {
			running = false;
		}
	}
	public void gameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Your Final Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Your Final Score: "+applesEaten))/2, SCREEN_HEIGHT/3);
		
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (SCREEN_WIDTH - metrics1.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkApple();
			checkCollision();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
			break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
			break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
			break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
			break;
			}
		}
	}
}
