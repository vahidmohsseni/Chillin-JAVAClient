package gameclinet;

import gameclinet.helper.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Config {

	private static Config instance = null;

	public JSONObject config;
    public Map<String, Object> inlineConfig;


    private Config() {}

    public static Config getInstance() {
        if (instance == null)
            instance = new Config();
        return instance;
    }

    public void initialize(String cfgPath, String[] args) {
		instance.parseFile(cfgPath);
		instance.parseArgs(args);
    }

	private void parseFile(String cfgPath) {
    	try {
			String fileText = new String(Files.readAllBytes(Paths.get(cfgPath)));
			instance.config = new JSONObject(fileText);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
    }

    private void parseArgs(String[] args) {

		for(int i = 1; i<args.length;i++){
			String[] s = args[i].split("\\.");
			if (s[0].equals("config")){
				String[] tmp = s[2].split("=");
				instance.config.getJSONObject(s[1]).put(tmp[0], Integer.parseInt(tmp[1]));
			}
		}
	}
}
