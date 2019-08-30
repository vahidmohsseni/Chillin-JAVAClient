package gameclinet;

import gameclinet.helper.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;


public class Config {

    public JSONObject config;
    public Map<String, Object> inlineConfig;

    private static Config configIns = null;
    private Config() {
    }

    public static Config getConfigIns() {
        if (configIns == null){
            configIns = new Config();
        }
        return configIns;

    }


    public void initialize(String cfg_path) throws IOException {
        String file_text = new String(Files.readAllBytes(Paths.get(cfg_path)));
        config = parser(file_text);

    }


    private JSONObject parser(String fileText) {

        return new JSONObject(fileText);

    }


}