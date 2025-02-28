package com.example.middle_roadmap.repository;


import com.example.middle_roadmap.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, String> {
    void deleteByIdIn(List<Long> ids);
}
