import gameclinet.GameClient;
import ks.Models;

import java.io.IOException;


public class Main {


    static String configPath = System.getProperty("user.dir") + "/";
    static String configFile = "gamecfg.json";

    public static void main(String[] args) throws IOException {

        if (args.length > 0) {
            configPath += args[0];
        }
        else {
            configPath += configFile;
        }


        Models.World world = new Models.World();
        Ai ai = new Ai(world);



        GameClient app = new GameClient(configPath);
        app.registerAI(ai);
        app.run();

    }
}
