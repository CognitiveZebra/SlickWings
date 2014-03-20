/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.*;



public class StartScreenState extends BasicGameState {
    public static final int ID = 2;
    private Image backgroundImage, startButtonImage, titelImage;
    private Image[] groundImages;
    private SpriteSheet birdSprite, groundSprite;
    private Animation birdAnimation, groundAnimation;
    

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

    	
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
    	Image image = new Image("res/atlas.png");
    	backgroundImage = image.getSubImage(0, 0, 288, 512);
    	titelImage = image.getSubImage(702, 182, 178, 48);
    	Image groundImage1 = image.getSubImage(584, 0, 288, 112);
    	Image groundImage2 = image.getSubImage(588, 0, 288, 112);
    	Image groundImage3 = image.getSubImage(592, 0, 288, 112);
    	Image groundImage4 = image.getSubImage(596, 0, 288, 112);
    	Image groundImage5 = image.getSubImage(600, 0, 288, 112);
    	Image groundImage6 = image.getSubImage(604, 0, 288, 112);
    	int [] duration = {100, 100, 100, 100, 100, 100};
    	
    	Image [] groundImages = {groundImage1, groundImage2, groundImage3, groundImage4, groundImage5, groundImage6};
    	groundAnimation = new Animation(groundImages, duration);
    	startButtonImage = image.getSubImage(708, 236, 104, 58);
    	birdSprite = new SpriteSheet("res/bird-sprite.png", 34, 34);
    	
    	birdAnimation = new Animation(birdSprite, 200);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
    	grphcs.drawImage(backgroundImage, 0, 0);
    	grphcs.drawImage(titelImage, gc.getWidth() / 2 - titelImage.getWidth() / 2, 175 - titelImage.getHeight());
    	grphcs.drawImage(startButtonImage, gc.getWidth() / 2 - startButtonImage.getWidth() / 2, gc.getHeight() - 112 - startButtonImage.getHeight());
    	groundAnimation.draw(0, gc.getHeight() - 112);
    	birdAnimation.draw(gc.getWidth() / 2 - 34 / 2, 250 - 34);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int deltaMS) throws SlickException {
    	birdAnimation.update(deltaMS);
    	groundAnimation.update(deltaMS);
    	
    	Input input = gc.getInput();
        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            Rectangle buttonRect = new Rectangle(gc.getWidth() / 2 - 104 / 2, gc.getHeight() - 112 - 58, 104, 58);
            if (buttonRect.contains(input.getMouseX(), input.getMouseY())) {
                sbg.enterState(GameState.ID, new FadeOutTransition(), new FadeInTransition());
            }
        }
    	
    }

}
