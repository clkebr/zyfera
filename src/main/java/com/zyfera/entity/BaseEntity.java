package com.zyfera.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, updatable = false)
	@CreatedDate
	private LocalDateTime insertDateTime;
	@Column(nullable = false, updatable = false)
	private Long insertUserId;

	@Column(nullable = false)
	@LastModifiedDate
	private LocalDateTime lastUpdateDateTime;
	@Column(nullable = false)
	private Long lastUpdateUserId;

	private Boolean isDeleted = false;

	@PrePersist
	public void onPrePersist() {
		this.insertUserId = 1L;
		this.lastUpdateUserId = 1L;
	}

	@PreUpdate
	public void onPreUpdate() {
		this.lastUpdateUserId = 1L;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		BaseEntity that = (BaseEntity) o;
		return getId() != null && Objects.equals(getId(), that.getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
