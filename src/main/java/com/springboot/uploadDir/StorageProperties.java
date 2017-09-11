package com.springboot.uploadDir;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

@Data
@ConfigurationProperties("storage")
public class StorageProperties {
    private String location = "upload-dir" + File.separator + "picture";
    private String Filelocation = "upload-dir" + File.separator + "file";

}
