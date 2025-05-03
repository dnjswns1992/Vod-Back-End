package com.example.StreamCraft.dto.s3part;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * AWS S3 멀티파트 업로드의 단일 파트 정보를 담는 DTO
 * - ETag: 각 파트의 식별자 (S3에서 응답으로 반환됨)
 * - PartNumber: 해당 파트의 번호 (1부터 시작)
 */
@Data
public class UploadPartDto {
    @JsonProperty("ETag")
    private String ETag;
    @JsonProperty("PartNumber")
    private int partNumber;
}
