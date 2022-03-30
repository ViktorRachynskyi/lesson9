package qa.guru;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;

public class JSONTests {

    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    void readJsonTest() throws Exception {
        Gson gson = new Gson();
        try (InputStream is = classLoader.getResourceAsStream("files/file.json")) {
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
            assertThat(jsonObject.get("text").getAsString()).isEqualTo("hello");
            assertThat(jsonObject.get("file").getAsJsonObject().get("file_name").getAsString()).isEqualTo("test");
        }

    }
}
