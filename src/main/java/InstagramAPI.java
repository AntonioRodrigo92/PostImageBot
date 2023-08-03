import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;

public class InstagramAPI {
    private IGClient client;

    public InstagramAPI(String user, String pass) throws IGLoginException {
        client = IGClient.builder()
                .username(user)
                .password(pass)
                .login();
    }

    public void postPic(byte[] img, String caption) {
        client.actions()
                .timeline()
                .uploadPhoto(img, caption)
                .thenAccept(response -> {
                    System.out.println("Successfully uploaded photo!");
                })
                .join();
    }

}
