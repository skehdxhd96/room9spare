package com.goomoong.room9backend.Service.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.goomoong.room9backend.domain.file.dto.fileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<fileDto> uploadFileList(List<MultipartFile> files) {
        return files.stream().map(this::upload).collect(Collectors.toList());
    }

    public fileDto upload(MultipartFile file) {
        String originalName = file.getOriginalFilename(); // originalName
        String extension = Optional.ofNullable(originalName) // 확장자 분리
                .filter(s -> s.contains("."))
                .map(s -> s.substring(originalName.lastIndexOf(".") + 1))
                .orElse(null);

        String fileName = UUID.randomUUID() + originalName; // 파일이름 중복방지

        boolean isImage = this.isImage(extension);

        //s3업로드
        try {
            if(isImage) {
                amazonS3Client.putObject(
                        new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
                                .withCannedAcl(CannedAccessControlList.PublicRead));
            }

        } catch(Exception e) {
            e.printStackTrace(); // 예외클래스
        }

        //Dto형식으로 반환
        return fileDto.builder()
                .originalName(originalName)
                .extension(extension)
                .fileName(fileName)
                .url(amazonS3Client.getUrl(bucket, fileName).toString())
                .build();
    }

    private boolean isImage(String extension) {
        return Optional.ofNullable(extension)
                .map(s -> s.toLowerCase().matches("png|jpeg|jpg|bmp|gif|svg"))
                .orElse(false);
    }
}
