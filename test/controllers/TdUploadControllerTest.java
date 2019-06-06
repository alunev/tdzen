package controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import play.mvc.Http;
import play.mvc.Result;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static play.libs.Files.*;
import static play.mvc.Http.HttpVerbs.POST;


/**
 * @author red
 * @since 0.0.1
 */
@RunWith(MockitoJUnitRunner.class)
public class TdUploadControllerTest {
    @Test
    public void uploadEmptyFile() throws IOException {
        File file = new File("./empty.txt");
        file.createNewFile();

        Http.MultipartFormData.FilePart<TemporaryFile> filePart = new Http.MultipartFormData.FilePart<>(
                "tdFile",
                "account.csv",
                "application/octet-stream",
                singletonTemporaryFileCreator().create(file.toPath()),
                Files.size(file.toPath())
        );

        Http.Request request = new Http.RequestBuilder()
                .method(POST)
                .bodyMultipart(Collections.emptyMap(), List.of(filePart))
                .build();


        Result result = new TdUploadController().upload(request);

        assertThat(result.status()).isEqualTo(Http.Status.SEE_OTHER);
    }
}