package com.drone.management.dao.impl;

import com.drone.management.dao.DroneDao;
import com.drone.management.entity.Drone;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 无人机数据访问实现 — 基于 MyBatis SqlSession
 */
@Repository
public class DroneDaoImpl implements DroneDao {

    private static final String NAMESPACE = "com.drone.management.dao.DroneDao";

    @Autowired
    private SqlSession sqlSession;

    @Override
    public int insert(Drone drone) {
        return sqlSession.insert(NAMESPACE + ".insert", drone);
    }

    @Override
    public int deleteById(Long id) {
        return sqlSession.delete(NAMESPACE + ".deleteById", id);
    }

    @Override
    public int update(Drone drone) {
        return sqlSession.update(NAMESPACE + ".update", drone);
    }

    @Override
    public Drone selectById(Long id) {
        return sqlSession.selectOne(NAMESPACE + ".selectById", id);
    }

    @Override
    public List<Drone> selectByPage(String droneName, String droneModel, String status,
                                    int offset, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("droneName", droneName);
        params.put("droneModel", droneModel);
        params.put("status", status);
        params.put("offset", offset);
        params.put("limit", limit);
        return sqlSession.selectList(NAMESPACE + ".selectByPage", params);
    }

    @Override
    public long countByCondition(String droneName, String droneModel, String status) {
        Map<String, Object> params = new HashMap<>();
        params.put("droneName", droneName);
        params.put("droneModel", droneModel);
        params.put("status", status);
        return sqlSession.selectOne(NAMESPACE + ".countByCondition", params);
    }

    @Override
    public List<Drone> selectAll() {
        return sqlSession.selectList(NAMESPACE + ".selectAll");
    }
}
