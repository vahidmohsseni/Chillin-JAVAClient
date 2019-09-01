package team.koala.chillin.client;


public class GameClient {

	private Core core;


	public GameClient(String configPath, String[] args) {
		Config.getInstance().initialize(configPath, args);
		core = new Core();
	}

	public void registerAI(AbstractAI ai) {
		core.registerAI(ai);
	}

	public void run() {
		if (!core.connect())
			return;
		if (!core.join())
			return;
		core.loop();
	}
}
