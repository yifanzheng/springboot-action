package com.example.jpa.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditor implements Serializable {

   private static final long serialVersionUID = -8636810082784692918L;

   @CreatedDate
   @Temporal(TIMESTAMP)
   private Date createdDate;

   @LastModifiedDate
   @Temporal(TIMESTAMP)
   private Date lastModifiedDate;

   public Date getCreatedDate() {
      return createdDate;
   }

   public void setCreatedDate(Date createdDate) {
      this.createdDate = createdDate;
   }

   public Date getLastModifiedDate() {
      return lastModifiedDate;
   }

   public void setLastModifiedDate(Date lastModifiedDate) {
      this.lastModifiedDate = lastModifiedDate;
   }
}