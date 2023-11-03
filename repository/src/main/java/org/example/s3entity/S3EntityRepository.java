package org.example.s3entity;


import org.example.entity.S3Entity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface S3EntityRepository extends JpaRepository<S3Entity, Long> {

        S3Entity getS3EntityByFileName(String fileName);

        void deleteByFileName(String fileName);

}
