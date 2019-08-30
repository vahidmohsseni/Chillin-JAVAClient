package gameclinet;


public class GameClient {

    private Core core;


    public GameClient(String configPath, String[] args) {
        Config.getInstance().initialize(configPath, args);
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
