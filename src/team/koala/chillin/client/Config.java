package team.koala.chillin.client;

import team.koala.chillin.client.helper.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Config {

	private static Config instance = null;

	private Map<String, Function<String, Object>> inlineConfig;
	public JSONObject config;


	private Config() {}

	private void initInlineConfig() {
		instance.inlineConfig = new HashMap<String, Function<String, Object>>() {
			{
				put("net.host", String::valueOf);
				put("net.port", Integer::parseInt);
				put("ai.agent_name", String::valueOf);
				put("ai.team_nickname", String::valueOf);
				put("ai.token", String::valueOf);
			}
		};
	}

	public static Config getInstance() {
		if (instance == null)
			instance = new Config();

		instance.initInlineConfig();
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

		for(int i = 1; i < args.length; i++) {
			Matcher m = r.matcher(args[i]);
			if (!m.find())
				continue;

			JSONObject tmp = instance.config;
			String[] keyList = m.group(1).split("\\.");
			for(int j = 0; j < keyList.length - 1; j++)
				tmp = tmp.getJSONObject(keyList[j]);

			tmp.put(
				keyList[keyList.length - 1],
				instance.inlineConfig.get(m.group(1)).apply(m.group(m.groupCount()))
			);
		}
	}
}
