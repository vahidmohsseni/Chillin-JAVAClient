package gameclinet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONString;


public class Config {

    public JSONObject config;
    public Map<String, Object> inline_config;

    private static Config config_ins = null;
    private Config() {
    }

    public static Config getConfigIns() {
        if (config_ins == null){
            config_ins = new Config();
        }
        return config_ins;

    }


    public void initialize(String cfg_path) throws IOException {

        String file_text = new String(Files.readAllBytes(Paths.get(cfg_path)));
        config = parser(file_text);

    }


    private JSONObject parser(String file_text) {

        return new JSONObject(file_text);

    }


}