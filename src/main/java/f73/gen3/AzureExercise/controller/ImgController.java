package f73.gen3.AzureExercise.controller;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;

@RestController
public class ImgController {

    @Value("${storageContainer.creds}")
    private String connectionString;

    public ImgController() {
    }


    @PostMapping(path = "/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        BlobContainerClient container = new BlobContainerClientBuilder().connectionString(connectionString).containerName("img").buildClient();

        BlobClient blob = container.getBlobClient(file.getOriginalFilename());
        blob.upload(file.getInputStream(),file.getSize(),true);

        return file.getOriginalFilename();
    }

    @Value("azure-blob://img/haha.jpeg")
    private Resource blobFile;

    @GetMapping("/readBlobFile")
    public String readBlobFile() throws IOException{
        return StreamUtils.copyToString(this.blobFile.getInputStream(), Charset.defaultCharset());
    }









}
