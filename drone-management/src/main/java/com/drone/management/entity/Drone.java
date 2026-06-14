package com.drone.management.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * 无人机实体类
 * 属性由 AI 自动生成，涵盖无人机核心业务字段
 */
public class Drone {

    /** 主键ID */
    private Long id;

    /** 无人机名称 */
    @NotBlank(message = "无人机名称不能为空")
    @Size(max = 100, message = "无人机名称长度不能超过100个字符")
    private String droneName;

    /** 无人机型号 */
    @NotBlank(message = "无人机型号不能为空")
    @Size(max = 50, message = "无人机型号长度不能超过50个字符")
    private String droneModel;

    /** 制造商 */
    @NotBlank(message = "制造商不能为空")
    @Size(max = 100, message = "制造商长度不能超过100个字符")
    private String manufacturer;

    /** 序列号 */
    @NotBlank(message = "序列号不能为空")
    @Size(max = 50, message = "序列号长度不能超过50个字符")
    private String serialNumber;

    /** 无人机类型: 多旋翼, 固定翼, 直升机, 垂直起降 */
    @NotBlank(message = "无人机类型不能为空")
    @Size(max = 20, message = "无人机类型长度不能超过20个字符")
    private String droneType;

    /** 重量(kg) */
    @NotNull(message = "重量不能为空")
    @DecimalMin(value = "0.01", message = "重量必须大于0")
    @DecimalMax(value = "10000.00", message = "重量不能超过10000kg")
    private Double weight;

    /** 最大飞行高度(m) */
    @NotNull(message = "最大飞行高度不能为空")
    @DecimalMin(value = "0.0", message = "最大飞行高度必须大于等于0")
    private Double maxAltitude;

    /** 最大飞行速度(km/h) */
    @NotNull(message = "最大飞行速度不能为空")
    @DecimalMin(value = "0.0", message = "最大飞行速度必须大于等于0")
    private Double maxSpeed;

    /** 续航时间(min) */
    @NotNull(message = "续航时间不能为空")
    @Min(value = 0, message = "续航时间必须大于等于0")
    private Integer endurance;

    /** 最大航程(km) */
    @DecimalMin(value = "0.0", message = "最大航程必须大于等于0")
    private Double range;

    /** 相机类型 */
    @Size(max = 50, message = "相机类型长度不能超过50个字符")
    private String cameraType;

    /** 载荷能力(kg) */
    @DecimalMin(value = "0.0", message = "载荷能力必须大于等于0")
    private Double payloadCapacity;

    /** 电池容量(mAh) */
    @Min(value = 0, message = "电池容量必须大于等于0")
    private Integer batteryCapacity;

    /** 状态: 正常, 维护中, 故障, 停用 */
    @NotBlank(message = "状态不能为空")
    @Size(max = 10, message = "状态长度不能超过10个字符")
    private String status;

    /** 购买日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date purchaseDate;

    /** 描述 */
    @Size(max = 500, message = "描述长度不能超过500个字符")
    private String description;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    // ==================== Getters / Setters ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDroneName() {
        return droneName;
    }

    public void setDroneName(String droneName) {
        this.droneName = droneName;
    }

    public String getDroneModel() {
        return droneModel;
    }

    public void setDroneModel(String droneModel) {
        this.droneModel = droneModel;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDroneType() {
        return droneType;
    }

    public void setDroneType(String droneType) {
        this.droneType = droneType;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getMaxAltitude() {
        return maxAltitude;
    }

    public void setMaxAltitude(Double maxAltitude) {
        this.maxAltitude = maxAltitude;
    }

    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Integer getEndurance() {
        return endurance;
    }

    public void setEndurance(Integer endurance) {
        this.endurance = endurance;
    }

    public Double getRange() {
        return range;
    }

    public void setRange(Double range) {
        this.range = range;
    }

    public String getCameraType() {
        return cameraType;
    }

    public void setCameraType(String cameraType) {
        this.cameraType = cameraType;
    }

    public Double getPayloadCapacity() {
        return payloadCapacity;
    }

    public void setPayloadCapacity(Double payloadCapacity) {
        this.payloadCapacity = payloadCapacity;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Drone{" +
                "id=" + id +
                ", droneName='" + droneName + '\'' +
                ", droneModel='" + droneModel + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", droneType='" + droneType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
