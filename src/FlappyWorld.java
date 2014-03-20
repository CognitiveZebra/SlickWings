import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sound.sampled.ReverbType;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class FlappyWorld {
	private Image backgroundImage, getReadyImage, tutorialImage, birdImage, ones, tens, hundreds, thousands;
	private Image[] numbers;

	private boolean isRunning;
	final long jumpDelay = 20;
	long startTime;
	private Rectangle groundPosition;
	float verticalSpeed = 0.0f;
	private int score = 0;

	private List<Pipe> pipeList;
	private List<Ground> groundList;
	private int updates;
	private boolean gameOver;
	private boolean flapped;
	private Bird flappy;

	private boolean isStarted;

	public FlappyWorld() {
		flappy = new Bird(this);

	}

	public void init(GameContainer container) throws SlickException {
		Image image = new Image("res/atlas.png");
		
		numbers = new Image[10];
		
		numbers[0] = image.getSubImage(992, 120, 24, 36);
		numbers[1] = image.getSubImage(272, 910, 16, 36);
		numbers[2] = image.getSubImage(584, 320, 24, 36);
		numbers[3] = image.getSubImage(612, 320, 24, 36);
		numbers[4] = image.getSubImage(640, 320, 24, 36);
		numbers[5] = image.getSubImage(668, 320, 24, 36);
		numbers[6] = image.getSubImage(584, 368, 24, 36);
		numbers[7] = image.getSubImage(612, 368, 24, 36);
		numbers[8] = image.getSubImage(640, 368, 24, 36);
		numbers[9] = image.getSubImage(668, 368, 24, 36);
						

		pipeList = new ArrayList<Pipe>();
		groundList = new ArrayList<Ground>();

		backgroundImage = image.getSubImage(0, 0, 288, 512);
		getReadyImage = image.getSubImage(590, 118, 184, 50);
		getReadyImage.setAlpha(1);
		tutorialImage = image.getSubImage(584, 182, 114, 98);
		tutorialImage.setAlpha(1);

		groundPosition = new Rectangle(0, container.getHeight() - 112, 288, 112);
		Ground ground = new Ground(flappy);
		ground.init(container);
		groundList.add(ground);
		flappy.init(container);
		isRunning = false;
	}

	public void render(GameContainer container, Graphics g) {
		g.drawImage(backgroundImage, 0, 0);

		
		
		if (!isStarted) {
			g.drawImage(getReadyImage,
					container.getWidth() / 2 - getReadyImage.getWidth() / 2,
					175 - getReadyImage.getHeight());
			g.drawImage(tutorialImage,
					container.getWidth() / 2 - tutorialImage.getWidth() / 2,
					container.getHeight() / 2 - tutorialImage.getHeight() / 2);
		} 
			
		for (Pipe p : pipeList) {
			p.render(container, g);
		}
		for (Ground gr : groundList) {
			gr.render(container, g);
		}
		if (isGameOver()) {

		}
		drawScore(container,g);
		flappy.render(container, g);

	}

	private void drawScore(GameContainer container,Graphics g) {
		int one = score%10;
		int ten = score/10%10;
		int hundred = score/100%10;
		int thousand = score/1000%10;
		
		
							ones 		= numbers[one];
		if (ten != 0) 		tens 		= numbers[ten];
		if (hundred !=0)	hundreds 	= numbers[hundred];
		if (thousand != 0)	thousands	= numbers[thousand];
		
		if(thousand != 0 && hundred != 0 && ten != 0) {
			g.drawImage(thousands,	container.getWidth() / 2 - (thousands.getWidth()+hundreds.getWidth()), 	125 - getReadyImage.getHeight());
			g.drawImage(hundreds, 	container.getWidth() / 2 - (hundreds.getWidth()), 	125 - getReadyImage.getHeight());
			g.drawImage(tens, 		container.getWidth() / 2 , 		125 - getReadyImage.getHeight());
			g.drawImage(ones, 		container.getWidth() / 2 + (ones.getWidth()), 		125 - getReadyImage.getHeight());
		} else if (hundred != 0 && ten != 0) {
			g.drawImage(hundreds, container.getWidth() / 2 -(hundreds.getWidth()+tens.getWidth()/2), 	125 - getReadyImage.getHeight());
			g.drawImage(tens, container.getWidth() / 2 - tens.getWidth()/2, 		125 - getReadyImage.getHeight());
			g.drawImage(ones, container.getWidth() / 2 + (tens.getWidth()/2), 		125 - getReadyImage.getHeight());
		} else if (ten != 0) {
			g.drawImage(tens, container.getWidth() / 2 - tens.getWidth(), 		125 - getReadyImage.getHeight());
			g.drawImage(ones, container.getWidth() / 2, 		125 - getReadyImage.getHeight());
		} else {
			g.drawImage(ones, container.getWidth() / 2 - ones.getWidth()/2, 		125 - getReadyImage.getHeight());
		}
		
		
		
		
	}

	public void update(GameContainer container, int deltaMS)
			throws SlickException {

		updates++;
		if (!isCrashed()) {
			updatePipes(container, deltaMS);
			updateGround(container, deltaMS);
		}

		Input input = container.getInput();

		if (input.isKeyPressed(Input.KEY_SPACE) && !isGameOver()) {
			isRunning = true;
			isStarted = true;
			flapped = true;
		}
		flappy.update(container, deltaMS, flapped, isRunning, isCrashed());
		flapped = false;

		if (isCrashed()) {
			isRunning = false;
		}
		
		updateScore();

		if (groundList.get(groundList.size() - 1).getX() == -48) {
			Ground ground = new Ground(flappy);
			ground.init(container);
			groundList.add(ground);
		}

		if (isRunning) {
			if (pipeList.isEmpty()) {
				Pipe pipe = new Pipe(flappy);
				pipe.init(container);
				pipeList.add(pipe);
			} else {
				if (pipeList.get(pipeList.size() - 1).getX() == 144) {
					Pipe pipe = new Pipe(flappy);
					pipe.init(container);
					pipeList.add(pipe);
				}
			}
		}

	}

	private void updateGround(GameContainer container, int deltaMS) {

		Iterator<Ground> g = groundList.iterator();
		while (g.hasNext()) {
			Ground ground = g.next();
			if (ground.isRemoved()) {
				groundList.remove(g);
			} else {
				ground.update(container, deltaMS);
			}

		}

	}

	private void updatePipes(GameContainer container, int deltaMS) {

		Iterator<Pipe> p = pipeList.iterator();
		while (p.hasNext()) {
			Pipe pipe = p.next();
			if (pipe.isRemoved()) {
				pipeList.remove(p);

			} else {
				pipe.update(container, deltaMS);
			}

		}

	}

	public boolean isGameOver() {
		return gameOver;
	}
	
	public void updateScore() {
		for (Pipe p : pipeList) {
			if (flappy.getBirdPosition().intersects(p.getPassPosition())) {
				score = pipeList.indexOf(p) + 1;
			}
		}
	}

	public boolean isCrashed() {
		if (flappy.getBirdPosition().intersects(groundPosition)) {
			return true;
		} else {
			for (Pipe p : pipeList) {
				if (flappy.getBirdPosition().intersects(p.getUpPipePosition()) 
						|| flappy.getBirdPosition().intersects(p.getDownPipePosition())) {
					return true;
				}
			}
			return false;
		}

	}

	public Rectangle getGroundPosition() {
		return groundPosition;
	}

}
