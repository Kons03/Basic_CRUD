package com.springboot.demo_project.repository;

import com.springboot.demo_project.models.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;
import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    List<RoleEntity> findByIdIn(Collection<Long> ids);
}
