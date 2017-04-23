package pacman;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends Rectangle{
	
	public boolean right,left,up,down;
	private int speed = 4;
	
	public Player(int x, int y) {
		// TODO Auto-generated constructor stub
		setBounds(x,y,32,32);
	}
	
	public void tick()
	{
		//System.out.println(x);
		if(right)x+=speed;
		if(left)x-=speed;
		if(up)y-=speed;
		if(down)y+=speed;
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.yellow);
		g.fillRect(x, y, width, height);
	}
}
