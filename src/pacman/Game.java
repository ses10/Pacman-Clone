package pacman;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{

	private boolean isRunning = false;
	public static final int WIDTH = 640, HEIGHT = 480;
	public static final String TITLE = "Pacman";
	private Thread thread;
	
	public static Player player;
	
	public Game()
	{
		Dimension dimension = new Dimension(Game.WIDTH, Game.HEIGHT);
		setPreferredSize(dimension);
		setMinimumSize(dimension);
		setMaximumSize(dimension);

		addKeyListener(this);
		setFocusable(true);
		player = new Player(Game.WIDTH/2, Game.HEIGHT/2);
	}
	
	public synchronized void start()
	{
		if(isRunning) return;
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop()
	{
		if(!isRunning) return;
		isRunning = false;
		
		try
		{
			thread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private void tick()
	{
		//System.out.println("running");
		player.tick();
	}
	
	private void render()
	{
		BufferStrategy bs = getBufferStrategy();
		if(bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		player.render(g);
		g.dispose();
		bs.show();
	}
	
	@Override
	public void run() {
		
		int fps = 0;
		double timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double targetTick = 60;
		double delta = 0;
		double timePerTick =  1000000000/targetTick;
		
		while(isRunning)
		{
			long now = System.nanoTime();
			delta+=(now-lastTime) / timePerTick;
			lastTime = now;
			
			while(delta >= 1)
			{
				tick();
				render();		
				fps++;
				delta--;
			}
			
			if(System.currentTimeMillis()-timer >= 1000)
			{
				System.out.println(fps);
				fps = 0;
				timer+=1000;
			}

		}
		stop();
		
		
	}
	
	public static void main(String arsg[])
	{
		Game game = new Game();
		JFrame frame = new JFrame(Game.TITLE);
		frame.add(game);
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		
		game.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) player.right = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT) player.left = true;
		if(e.getKeyCode() == KeyEvent.VK_UP) player.up = true;
		if(e.getKeyCode() == KeyEvent.VK_DOWN) player.down = true;
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) player.right = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT) player.left = false;
		if(e.getKeyCode() == KeyEvent.VK_UP) player.up = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN) player.down = false;
		
	}
	
}
