package com.example.springsecurity04.Request;

import com.example.springsecurity04.Dto.Request.CompleteMultipartUploadRequestCustomDto;
import lombok.Data;

import java.util.List;
@Data
public class CompleteMultipartUploadRequestCustom {

    private String fileName;
    private String uploadId;
    private List<CompleteMultipartUploadRequestCustomDto> completedParts;
}
