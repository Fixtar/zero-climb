package org.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "S3Entity")
public class S3Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "filename")
    private String fileName;

    @Column(name = "isStore")
    private boolean isStore;

    @Builder
    public S3Entity(String memberId, String fileName, boolean isStore){
        this.memberId = memberId;
        this.fileName = fileName;
        this.isStore = isStore;
    }

    public void setStore(boolean store) {
        this.isStore = store;
    }
}
