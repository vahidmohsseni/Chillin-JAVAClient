package gameclinet;


import java.io.IOException;

public class GameClient {


    private Core core;
    public GameClient(String configPath) throws IOException {


        Config.getConfigIns().initialize(configPath);


        core = new Core();


    }

    public void registerAI(BaseAI ai) {
        core.registerAI(ai);

    }

    public void run() {
        if (!core.connect()){
            return;
        }
        if (!core.join()){
            return;
        }
        core.loop();

    }
}
