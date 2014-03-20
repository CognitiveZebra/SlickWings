import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;


public class Bird {
	private static final float TERMINAL_VELOCITY = 15;

	float birdY;

	private SpriteSheet birdSprite;										
	private Animation birdAnimation, groundAnimation;
	private Rectangle birdPosition;
	private Image birdImage;
	private FlappyWorld world;
	private float vertSpeed;
	
	public Bird() {
		super();
	}
	
	public Bird(FlappyWorld world) {
		this();
		this.world = world;
	}
	
	public void init(GameContainer container) throws SlickException {
		vertSpeed = 0;

		
		
		birdImage = new Image("res/bird-sprite.png");
		birdSprite = new SpriteSheet(birdImage, 34, 34);
		birdAnimation = new Animation(birdSprite, 200);
		birdY = (200);
		birdPosition = new Rectangle(container.getWidth() / 3 - 34 / 2, birdY + 5 , 34, 24);
	}
	
	public void render(GameContainer container, Graphics g) {
		birdImage.setRotation(90);
		birdAnimation.draw(container.getWidth() / 3 - 34 / 2, birdY);
	}
	public void update(GameContainer container, int deltaMS, boolean flapped, boolean isRunning, boolean isCrashed) throws SlickException {
		if (isRunning && !isCrashed) {
			if (flapped) {
				vertSpeed = -6f;
			}
			birdY += vertSpeed;
			vertSpeed = Math.min(TERMINAL_VELOCITY,vertSpeed += 0.5f);
			birdPosition.setY(birdY + 5);
		} else if (isCrashed){
			stopAnimation();
			if (birdY < world.getGroundPosition().getY() - 29) {
				birdY += vertSpeed;
				vertSpeed = Math.min(TERMINAL_VELOCITY,vertSpeed += 0.5f);
			} else {
				birdY = world.getGroundPosition().getY() - 29;
			}
		}
		
		

	}
	
	


	public void stopAnimation() {
		birdAnimation.stop();
		
	}
	
	public Rectangle getBirdPosition() {
		return birdPosition;
	}

}
