import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class pong extends JPanel implements ActionListener {
	
	private int WINDOW_WIDTH = 500;
	private int WINDOW_HEIGHT = 500;
	private int TICK = 15;
	
	private int player1_x;
	private int player1_y;
	private int player1_score;
	private int player2_x;
	private int player2_y;
	private int player2_score;
	private int PADDLE_HEIGHT;
	private int PADDLE_WIDTH;
	
	private int ball_x;
	private int ball_y;
	private int BALL_HEIGHT;
	private int BALL_WIDTH;
	
	private Image playerBar;
	private Image circleBall;
	private Timer timer;
	
	//(x,y), first is right paddles, second is left paddles, third is ball
	private int [][] velocities = {{0},{0},{2,2}};
	
	public pong() {
		
		this.setVisible(true);
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.addKeyListener((KeyListener) new TAdapter());
		this.setFocusable(true);
		
		loadGraphics();
		init();
	}

	private void init() {
		player1_x = 50;
		player1_y = 50;
		player1_score = 0;
		player2_x = 450;
		player2_y = 50;
		player2_score = 0;
		
		PADDLE_HEIGHT = 100;
		PADDLE_WIDTH = 10;
		
		BALL_HEIGHT = 20;
		BALL_WIDTH = 20;
		
		ball_x = 250;
		ball_y = 250;
		
		timer = new Timer(TICK, this);
		timer.start();
	}
	
	public void loadGraphics() {
		ImageIcon playerBarImage = new ImageIcon(getClass().getResource("/res/black_bar.png"));
		playerBar = playerBarImage.getImage();
		playerBar = playerBar.getScaledInstance(10, 100, Image.SCALE_DEFAULT);
		
		ImageIcon circleBallImage = new ImageIcon(getClass().getResource("/res/black_circle.png"));
		circleBall = circleBallImage.getImage();
		circleBall = circleBall.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
	}
	
	private void checkPoint() {
		if (ball_x < 0) {
			player2_score += 1;
			ball_reset();
		}
		if (ball_x + BALL_WIDTH > 500) {
			player1_score += 1;
			ball_reset();
		}
			
	}
	
	private void ball_reset() {
		ball_x = 250; 		  ball_y = 250;
		velocities[2][0] = 2; velocities[2][1] = 2;
	}
	
	private void movement() {
		
		player2_y += velocities[0][0];
		player1_y += velocities[1][0];
		ball_x += velocities[2][0];
		ball_y += velocities[2][1];
				
	}
	
	private void collision() {
		//Paddle upper and lower boundaries
		if (player2_y < 0)
			player2_y = 0 ;
		if (player2_y > WINDOW_HEIGHT-PADDLE_HEIGHT)
			player2_y = WINDOW_HEIGHT-PADDLE_HEIGHT;
		if (player1_y < 0)
			player1_y = 0;
		if (player1_y > WINDOW_HEIGHT-PADDLE_HEIGHT)
			player1_y = WINDOW_HEIGHT-PADDLE_HEIGHT;
		
		//Ball upper,lower collision with increase in speed
		if (ball_y < 0 || ball_y > WINDOW_HEIGHT - BALL_HEIGHT) { 
			velocities[2][1] *= -1;
			if (velocities[2][1] > 0) 
				velocities[2][1] += 1;
			else
				velocities[2][1] -= 1; 
		}

		//Ball left paddle collision with increase in speed
		if (ball_x > player1_x && ball_x < player1_x + PADDLE_WIDTH) 
				if (ball_y > player1_y && ball_y < player1_y + PADDLE_HEIGHT) {
					velocities[2][0] *= -1;
					if (velocities[2][0] > 0) 
						velocities[2][0] += 1;
					else
						velocities[2][0] -= 1; 
				}
		//Ball right paddle collision with increase in speed
		if (ball_x + BALL_WIDTH < player2_x + PADDLE_WIDTH && ball_x + BALL_WIDTH > player2_x) 
			if (ball_y > player2_y && ball_y < player2_y + PADDLE_HEIGHT) {
				velocities[2][0] *= -1;
				if (velocities[2][0] > 0) 
					velocities[2][0] += 1;
				else
					velocities[2][0] -= 1; 
			}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	private void draw(Graphics g) {
		g.drawImage(playerBar, player1_x, player1_y, this);
		g.drawImage(playerBar, player2_x, player2_y, this);
		g.drawImage(circleBall, ball_x, ball_y, this);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
		g.drawString(Integer.toString(player1_score), 200, 200);
		g.drawString(Integer.toString(player2_score), 300, 200);
		g.drawLine(250, 0, 250, 500);
		Toolkit.getDefaultToolkit().sync();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		checkPoint();
		collision();
		movement();
		repaint();
	}
	
	
	private class TAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			int keyP = e.getKeyCode();
			
			if (keyP == KeyEvent.VK_DOWN) {
				velocities[0][0] = 10;
			}
			if (keyP == KeyEvent.VK_UP) {
				velocities[0][0] = -10;
			}
			if (keyP == KeyEvent.VK_S) {
				velocities[1][0] = 10;
			}
			if (keyP == KeyEvent.VK_W) {
				velocities[1][0] = -10;
			}	
		}
		@Override
		public void keyReleased(KeyEvent e) {
			int keyR = e.getKeyCode();
			
			if (keyR == KeyEvent.VK_DOWN || keyR == KeyEvent.VK_UP) {
				velocities[0][0] = 0;
			}
			if (keyR == KeyEvent.VK_S || keyR == KeyEvent.VK_W) {
				velocities[1][0] = 0;
			}
		}
	}
}


