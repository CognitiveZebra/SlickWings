

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class GameStateController extends StateBasedGame {
    public GameStateController() {
        super("Flappy Bird <3");
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        addState(new LogoState());
        addState(new StartScreenState());
        addState(new GameState());
        addState(new GameOverState());
    }

}
