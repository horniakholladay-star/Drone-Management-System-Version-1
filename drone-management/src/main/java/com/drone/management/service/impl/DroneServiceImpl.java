package com.drone.management.service.impl;

import com.drone.management.common.PageResult;
import com.drone.management.dao.DroneDao;
import com.drone.management.entity.Drone;
import com.drone.management.exception.BusinessException;
import com.drone.management.service.DroneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 无人机业务服务实现
 */
@Service
public class DroneServiceImpl implements DroneService {

    private static final Logger log = LoggerFactory.getLogger(DroneServiceImpl.class);

    @Autowired
    private DroneDao droneDao;

    @Override
    @Transactional
    public void addDrone(Drone drone) {
        // 检查序列号唯一性
        // (简化处理: 此处未做唯一性校验, 可在 DAO 层增加 queryBySerialNumber)
        drone.setCreateTime(new Date());
        drone.setUpdateTime(new Date());
        if (drone.getStatus() == null || drone.getStatus().isEmpty()) {
            drone.setStatus("正常");
        }
        int rows = droneDao.insert(drone);
        if (rows <= 0) {
            throw new BusinessException("新增无人机失败");
        }
        log.info("新增无人机成功: {}", drone.getDroneName());
    }

    @Override
    @Transactional
    public void deleteDrone(Long id) {
        Drone existing = droneDao.selectById(id);
        if (existing == null) {
            throw new BusinessException(404, "无人机不存在, ID=" + id);
        }
        int rows = droneDao.deleteById(id);
        if (rows <= 0) {
            throw new BusinessException("删除无人机失败");
        }
        log.info("删除无人机成功: ID={}", id);
    }

    @Override
    @Transactional
    public void updateDrone(Drone drone) {
        Drone existing = droneDao.selectById(drone.getId());
        if (existing == null) {
            throw new BusinessException(404, "无人机不存在, ID=" + drone.getId());
        }
        drone.setUpdateTime(new Date());
        int rows = droneDao.update(drone);
        if (rows <= 0) {
            throw new BusinessException("更新无人机失败");
        }
        log.info("更新无人机成功: ID={}", drone.getId());
    }

    @Override
    public Drone getDroneById(Long id) {
        Drone drone = droneDao.selectById(id);
        if (drone == null) {
            throw new BusinessException(404, "无人机不存在, ID=" + id);
        }
        return drone;
    }

    @Override
    public PageResult<Drone> listDrones(String droneName, String droneModel, String status,
                                        int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<Drone> list = droneDao.selectByPage(droneName, droneModel, status, offset, pageSize);
        long total = droneDao.countByCondition(droneName, droneModel, status);
        return PageResult.success(list, total, pageNum, pageSize);
    }

    @Override
    public List<Drone> listAll() {
        return droneDao.selectAll();
    }
}
