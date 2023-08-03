import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import twitter4j.JSONArray;
import twitter4j.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SubRedditPost {
    private String username;
    private String password;
    private String author;
    private String title;
//    private boolean isLocked;
    private String imageUrl;
    private String permalink;

    public SubRedditPost(String username, String password, String subReddit, String searchType) {
        this.username = username;
        this.password = password;
        String json = postAsString(subReddit, searchType);
        JSONObject data = getData(json);
        this.author = data.getString( "author");
        this.title = data.getString( "title");
        this.imageUrl = data.getString("url");
        this.permalink = "www.reddit.com" + data.getString("permalink");
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPermalink() {
        return permalink;
    }

    private String postAsString (String subReddit, String searchType) {
        RestTemplate restTemplate = new RestTemplate();
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        String authToken = getAuthToken();
        headers.setBearerAuth(authToken);
        headers.put("User-Agent",
                Collections.singletonList("tomcat:com.e4developer.e4reddit-test:v1.0 (by /u/bartoszjd)"));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        String url = "https://oauth.reddit.com/r/"+subReddit+"/" + searchType + "?limit=1";
        ResponseEntity<String> response
                = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    private String getAuthToken(){
        RestTemplate restTemplate = new RestTemplate();
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setBasicAuth(username, password);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.put("User-Agent",
                Collections.singletonList("tomcat:com.e4developer.e4reddit-test:v1.0 (by /u/bartoszjd)"));
        String body = "grant_type=client_credentials";
        HttpEntity<String> request
                = new HttpEntity<>(body, headers);
        String authUrl = "https://www.reddit.com/api/v1/access_token";
        ResponseEntity<String> response = restTemplate.postForEntity(
                authUrl, request, String.class);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        try {
            map.putAll(mapper
                    .readValue(response.getBody(), new TypeReference<Map<String,Object>>(){}));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(response.getBody());
        return String.valueOf(map.get("access_token"));
    }

    private JSONObject getData (String json) {
        JSONObject obj = new JSONObject(json);
        JSONObject data = obj.getJSONObject("data");
        JSONArray child = (JSONArray)data.get("children");
        data = (JSONObject)child.get(0);
        data = (JSONObject)data.get("data");

        return data;
    }

}
