package com.example.StreamCraft.Request;

import com.example.StreamCraft.dto.s3part.UploadPartDto;
import lombok.Data;

import java.util.List;
@Data
public class CompleteMultipartUploadRequestCustom {

    private String fileName;
    private String uploadId;
    private List<UploadPartDto> completedParts;
}
