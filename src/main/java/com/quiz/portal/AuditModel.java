package com.quiz.portal;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditModel<U> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @CreatedBy
    private U createdBy;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime createdDate;

    @LastModifiedBy
    private U lastModifiedBy;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime lastModifiedDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public U getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s")
    public Long getCreatedDateTimeStamp() {
        if (createdDate == null) {
            return 0L;
        }

        return createdDate.toEpochSecond(OffsetDateTime.now().getOffset());
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public U getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s")
    public Long getLastModifiedDateTimeStamp() {
        if (lastModifiedDate == null) {
            return 0L;
        }

        return lastModifiedDate.toEpochSecond(OffsetDateTime.now().getOffset());
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
