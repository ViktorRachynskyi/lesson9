package qa.guru;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import qa.guru.domain.User;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;

public class JSONTests {

    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    void readJsonTest() throws Exception {
        Gson gson = new Gson();
        try (InputStream is = classLoader.getResourceAsStream("files/user.json")) {
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            User jsonObject = gson.fromJson(json, User.class);
            assertThat(jsonObject.name).isEqualTo("Viktor");
            assertThat(jsonObject.contacts.email).isEqualTo("viktor@gmail.com");
            assertThat(jsonObject.work).isTrue();
        }
    }
}
