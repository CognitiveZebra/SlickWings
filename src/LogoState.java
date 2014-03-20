/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.*;



public class LogoState extends BasicGameState {
    public static final int ID = 1;
    Image logoImage;
    final long logoDuration = 2000;
    long startTime;
    
    @Override
    public void init(GameContainer container, StateBasedGame arg1) throws SlickException {
    	logoImage = new Image("res/LaunchImage.png");
    	startTime = container.getTime();
    }

    @Override
    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
    	new FadeInTransition(Color.black);
    	logoImage.draw(container.getWidth() / 2 - logoImage.getWidth() / 2, container.getHeight() / 2 - logoImage.getHeight() / 2);
    }

    @Override
    public void update(GameContainer container, StateBasedGame sbg, int deltaMS) throws SlickException {
    	Input input = container.getInput();
        boolean skipToStart = input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) || input.isKeyDown(Input.KEY_SPACE) || input.isKeyDown(Input.KEY_ESCAPE);
        boolean goToStartScreen = startTime + logoDuration < container.getTime();
        if (skipToStart || goToStartScreen) {
            sbg.enterState(StartScreenState.ID, new FadeOutTransition(Color.black, 400), new FadeInTransition());
        }
    }

    @Override
    public int getID() {
        return ID;
    }
}
