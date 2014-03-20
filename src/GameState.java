
import java.util.Random;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.*;



public class GameState extends BasicGameState {
    Random random = new Random();
    public static final int ID = 3;
    private final int updatesPerSecond = 40;
    private final int msPerUpdate = 1000 / updatesPerSecond;
    private final float musicVolume = 0.6f;
    private FlappyWorld flappyWorld;


    public GameState() {
    }

    void startGame(GameContainer container) throws SlickException {
        flappyWorld = new FlappyWorld();
        flappyWorld.init(container);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        if (flappyWorld != null) {
            flappyWorld.render(container, g);
        }
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        startGame(container);
    }


    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (flappyWorld != null) {
            flappyWorld.update(container, delta);
            
            if (flappyWorld.isGameOver()) {
            	GameOverState state = (GameOverState) game.getState(GameOverState.ID);
                game.enterState(GameOverState.ID, new FadeOutTransition(Color.black, 1600), new FadeInTransition());
            }
        }
    }

    @Override
    public int getID() {
        return ID;
    }

}
