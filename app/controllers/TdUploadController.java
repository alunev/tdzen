package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.io.Files;
import com.google.inject.Inject;
import model.Transaction;
import org.assertj.core.util.Lists;
import play.Logger;
import play.Logger.ALogger;
import play.libs.Files.TemporaryFile;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class TdUploadController extends Controller {
    private static final ALogger log = Logger.of(TdUploadController.class);

    @Inject
    public TdUploadController() {

    }

    public Result upload(Http.Request request) throws IOException {
        Http.MultipartFormData<TemporaryFile> body = request.body().asMultipartFormData();
        Http.MultipartFormData.FilePart<TemporaryFile> tdFile = body.getFile("tdFile");
        if (tdFile != null) {
            log.info("Uploaded file {}", tdFile.getFilename());

            TemporaryFile file = tdFile.getRef();
            String tmpPath = "/tmp/tdfile/" + UUID.randomUUID();
            file.moveFileTo(new File(tmpPath), true);

            String content = new String(Files.toByteArray(Paths.get(tmpPath).toFile()));

            List<Transaction> list = Lists.newArrayList(
                    new Transaction("05/01/2019", "walmart", "12.78"),
                    new Transaction("05/02/2019", "svb", "1.78"),
                    new Transaction("05/03/2019", "sad", "1278.78")
            );
            JsonNode json = Json.toJson(list);

            return ok(json);
        } else {
            return badRequest().flashing("error", "Missing file");
        }
    }
}
