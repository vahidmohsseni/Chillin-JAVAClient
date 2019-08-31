package gameclinet;

import gameclinet.helper.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Config {

	private static Config instance = null;
	private Map<String, Consumer<String[]>> inlineConfig = null;

	public JSONObject config;


    private Config() {}

    private void initConfig(){
		instance.inlineConfig = new HashMap<String, Consumer<String[]>>() {
			{
				put("net.host", instance::parseStr);
				put("net.port", instance::parseInt);
				put("ai.agent_name", instance::parseStr);
				put("ai.team_nickname", instance::parseStr);
				put("ai.token", instance::parseStr);
			}};


	}

	private void parseStr(String[] s){
		JSONObject tmp = instance.config;
		String[] keyList = s[0].split("\\.");
		for(int i = 0; i<keyList.length-1; i++){
			tmp = tmp.getJSONObject(keyList[i]);
		}
		tmp.put(keyList[keyList.length-1], s[1]);
	}
	private void parseInt(String[] s){
		JSONObject tmp = instance.config;
		String[] keyList = s[0].split("\\.");
		for(int i = 0; i<keyList.length-1; i++){
			tmp = tmp.getJSONObject(keyList[i]);
		}
		tmp.put(keyList[keyList.length-1], Integer.parseInt(s[1]));
	}


    public static Config getInstance() {
        if (instance == null)
            instance = new Config();

        instance.initConfig();
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
		String pattern = "config\\.(.+)(\\.(.+))*\\=(.+)?";
		Pattern r = Pattern.compile(pattern);

		for(int i = 1; i<args.length;i++) {

			Matcher m = r.matcher(args[i]);

			if (m.find()) {
				instance.inlineConfig.get(m.group(1)).accept(new String[]{m.group(1), m.group(m.groupCount())});
			}
		}
	}

}
