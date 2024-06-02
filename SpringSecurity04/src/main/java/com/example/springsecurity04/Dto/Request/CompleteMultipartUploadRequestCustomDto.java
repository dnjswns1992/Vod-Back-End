package com.example.springsecurity04.Dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CompleteMultipartUploadRequestCustomDto {
    @JsonProperty("ETag")
    private String ETag;
    @JsonProperty("PartNumber")
    private int partNumber;
}
