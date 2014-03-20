

import org.newdawn.slick.AppGameContainer;

public class MainClass {

    public static void main(String[] args) throws Exception {
        System.out.println("I'm a little teapot. That is all.");

        AppGameContainer app = new AppGameContainer(new GameStateController());
        app.setTargetFrameRate(60);
        app.setDisplayMode(288, 512, false);
        app.start();
    }
}
