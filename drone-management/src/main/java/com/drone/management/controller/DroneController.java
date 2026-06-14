package com.drone.management.controller;

import com.drone.management.common.PageResult;
import com.drone.management.common.Result;
import com.drone.management.entity.Drone;
import com.drone.management.service.DroneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * 无人机信息管理控制器
 */
@Controller
@RequestMapping("/drone")
public class DroneController {

    private static final Logger log = LoggerFactory.getLogger(DroneController.class);

    @Autowired
    private DroneService droneService;

    // ==================== 页面 ====================

    /** 无人机列表页 */
    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "") String droneName,
                       @RequestParam(defaultValue = "") String droneModel,
                       @RequestParam(defaultValue = "") String status,
                       @RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "10") int pageSize,
                       Model model) {
        PageResult<Drone> pageResult = droneService.listDrones(
                droneName, droneModel, status, pageNum, pageSize);

        model.addAttribute("page", pageResult);
        model.addAttribute("droneName", droneName);
        model.addAttribute("droneModel", droneModel);
        model.addAttribute("status", status);
        return "drone/list";
    }

    /** 新增无人机页面 */
    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("drone", new Drone());
        return "drone/add";
    }

    /** 编辑无人机页面 */
    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        Drone drone = droneService.getDroneById(id);
        model.addAttribute("drone", drone);
        return "drone/edit";
    }

    /** 无人机详情页 */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Drone drone = droneService.getDroneById(id);
        model.addAttribute("drone", drone);
        return "drone/detail";
    }

    // ==================== 操作 ====================

    /** 新增无人机提交 */
    @PostMapping("/add")
    public String doAdd(@Valid Drone drone, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // 校验失败，将错误信息返回页面
            redirectAttributes.addFlashAttribute("error",
                    bindingResult.getFieldError() != null
                            ? bindingResult.getFieldError().getDefaultMessage()
                            : "表单校验失败");
            redirectAttributes.addFlashAttribute("drone", drone);
            return "redirect:/drone/add";
        }
        droneService.addDrone(drone);
        redirectAttributes.addFlashAttribute("success", "新增无人机成功");
        return "redirect:/drone/list";
    }

    /** 更新无人机提交 */
    @PostMapping("/edit")
    public String doUpdate(@Valid Drone drone, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error",
                    bindingResult.getFieldError() != null
                            ? bindingResult.getFieldError().getDefaultMessage()
                            : "表单校验失败");
            return "redirect:/drone/edit/" + drone.getId();
        }
        droneService.updateDrone(drone);
        redirectAttributes.addFlashAttribute("success", "更新无人机成功");
        return "redirect:/drone/list";
    }

    /** 删除无人机 (AJAX) */
    @PostMapping("/delete")
    @ResponseBody
    public Result<?> doDelete(@RequestParam Long id) {
        try {
            droneService.deleteDrone(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            log.error("删除无人机失败: {}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
