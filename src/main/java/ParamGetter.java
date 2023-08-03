import org.yaml.snakeyaml.nodes.ScalarNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ParamGetter {
    private Map<String, String> params;

    public ParamGetter(String fileLocation) throws FileNotFoundException {
        params = new HashMap<>();
        init(fileLocation);
    }

    public String getRedditUsername() {
        return params.get("redditUsername");
    }

    public String getRedditPassword() {
        return params.get("redditPassword");
    }

    public String getSubReddit() {
        return params.get("subReddit");
    }

    public String getInstagramUser() {
        return params.get("instagramUser");
    }

    public String getInstagramPassword() {
        return params.get("instagramPassword");
    }

    private void init (String fileLocation) throws FileNotFoundException {
        File f = new File(fileLocation);
        Scanner sc = new Scanner(f);
        while (sc.hasNextLine()) {
            populateObject(sc.nextLine());
        }
    }

    private void populateObject (String line) {
        String[] part = line.split(" = ");
        String key = part[0];
        String value = part[1];
        params.put(key, value);
    }

}
