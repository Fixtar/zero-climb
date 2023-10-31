package org.example.file;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import java.net.URL;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.entity.S3Entity;
import org.example.file.dto.PreSignedUrlDto;
import org.example.s3entity.S3EntityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    private final S3EntityRepository s3EntityRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public PreSignedUrlDto generatePreSignedUrl(String memberId) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 180; //180분 추가
        expiration.setTime(expTimeMillis);

        UUID uuid = UUID.randomUUID();
        String fileName = "videos/" + uuid;
        URL preSignedUrl = amazonS3Client.generatePresignedUrl(bucket, fileName, expiration, HttpMethod.PUT);

        //db 만들어서 누가 요청했는지(아이디), 실제로 업로드가 이루어 졌는지 확인.(게시글 업로드할 때 체크)
        S3Entity s3 = S3Entity.builder()
                .memberId(memberId)
                .fileName(fileName)
                .isStore(false)
                .build();
        s3EntityRepository.save(s3);

        return PreSignedUrlDto.builder()
                .preSignedUrl(preSignedUrl.toString())
                .fileName(fileName)
                .build();
    }

}
