package com.drone.management.dao;

import com.drone.management.entity.Drone;

import java.util.List;

/**
 * 无人机数据访问接口
 */
public interface DroneDao {

    /**
     * 新增无人机
     * @return 受影响行数
     */
    int insert(Drone drone);

    /**
     * 根据ID删除无人机
     * @return 受影响行数
     */
    int deleteById(Long id);

    /**
     * 更新无人机
     * @return 受影响行数
     */
    int update(Drone drone);

    /**
     * 根据ID查询无人机
     */
    Drone selectById(Long id);

    /**
     * 分页条件查询无人机列表
     * @param droneName  无人机名称（模糊匹配）
     * @param droneModel 无人机型号（模糊匹配）
     * @param status     状态（精确匹配）
     * @param offset     偏移量
     * @param limit      每页条数
     */
    List<Drone> selectByPage(String droneName, String droneModel, String status,
                             int offset, int limit);

    /**
     * 条件查询总记录数
     */
    long countByCondition(String droneName, String droneModel, String status);

    /**
     * 查询所有无人机
     */
    List<Drone> selectAll();
}
