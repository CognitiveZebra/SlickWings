import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;


public class Pipe {
	
	private float x,y;
	private static final float SPACING = 92;
	private Image pipeUp, pipeDown;
	private Rectangle upRect, downRect, passRect;
	private boolean removed;
	private Bird flappy;
	
	public Pipe(Bird flappy) {
		this.flappy = flappy;
	}
	public void init(GameContainer container) throws SlickException {
		Image image = new Image("res/atlas.png");
		Random random = new Random();
		int rand = random.nextInt(220)+50;
		pipeUp = image.getSubImage(112, 646, 52, 320);
		pipeDown = image.getSubImage(168, 646, 52, 320);
		x = 288;
		y = (-320 + rand);
		upRect = new Rectangle(x, y, 52, 320);
		downRect = new Rectangle(x, y +320 + SPACING, 52, 320);
		passRect = new Rectangle(x + 52/2, y + 320, 3, SPACING);
		
	}
	
	public void render(GameContainer container, Graphics g) {
		g.drawImage(pipeUp, x, y);
		g.drawImage(pipeDown, x, y + 320 + SPACING);
	}
	
	public void update(GameContainer container, int deltaMS) {

		x -= 2;
			
		upRect.setLocation(x, y);
		downRect.setLocation(x, y +320 + SPACING);
		passRect.setLocation(x + 52/2, y + 320);
		
		if (x < - 52) {
			setRemoved();
		}
	}
	
	public Rectangle getUpPipePosition() {
		return upRect;
	}
	
	public Rectangle getDownPipePosition() {
		return downRect;
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
	public Rectangle getPassPosition() {
		// TODO Auto-generated method stub
		return passRect;
	}
	


}
