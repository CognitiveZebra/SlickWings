import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;


public class Ground {
	private float x,y;
	private Image ground;
	private Rectangle groundrect;
	private boolean removed;
	private Bird flappy;
	
	public Ground(Bird flappy) {
		this.flappy = flappy;
	}
	public void init(GameContainer container) throws SlickException {
		Image image = new Image("res/atlas.png");
		Random random = new Random();
		ground = image.getSubImage(584, 0, 336, 112);
		x = 0;
		y = (container.getHeight() - 112);
		groundrect = new Rectangle(0, container.getHeight() - 112, 288, 112);
		
	}
	
	public void render(GameContainer container, Graphics g) {
		g.drawImage(ground, x, y);
	}
	
	public void update(GameContainer container, int deltaMS) {
		
		x -= 2;
		
		
		
		if (x < - 336) {
			setRemoved();
		}
	}
	
	public void setRemoved() {
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public float getX(){
		return x;
	}


	

}
