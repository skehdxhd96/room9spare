package com.goomoong.room9backend.Service;

import com.goomoong.room9backend.Repository.FileRepository;
import com.goomoong.room9backend.domain.file.File;
import com.goomoong.room9backend.domain.file.dto.fileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    private final FileRepository fileRepository;
    private final S3Uploader s3Uploader;

    public List<File> uploadFiles(List<MultipartFile> files) {
        List<fileDto> savedFiles = new ArrayList<>();
        if (files != null) {
            savedFiles = s3Uploader.uploadFileList(files);
        }

        return fileRepository.saveAll(savedFiles.stream().map(File::create).collect(Collectors.toList()));
    }
}
