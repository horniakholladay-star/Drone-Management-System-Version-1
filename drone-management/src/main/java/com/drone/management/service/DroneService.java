package com.drone.management.service;

import com.drone.management.common.PageResult;
import com.drone.management.entity.Drone;

import java.util.List;

/**
 * 无人机业务服务接口
 */
public interface DroneService {

    /**
     * 新增无人机
     */
    void addDrone(Drone drone);

    /**
     * 根据ID删除无人机
     */
    void deleteDrone(Long id);

    /**
     * 更新无人机
     */
    void updateDrone(Drone drone);

    /**
     * 根据ID查询无人机
     */
    Drone getDroneById(Long id);

    /**
     * 分页条件查询
     */
    PageResult<Drone> listDrones(String droneName, String droneModel, String status,
                                 int pageNum, int pageSize);

    /**
     * 查询所有无人机
     */
    List<Drone> listAll();
}
