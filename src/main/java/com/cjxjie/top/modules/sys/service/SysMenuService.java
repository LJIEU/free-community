package com.cjxjie.top.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.modules.sys.entity.SysMenuEntity;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-06 17:36:34
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SysMenuEntity> getUserMenuId(Long userId);

    List<SysMenuEntity> queryNotButtonList();

    List<SysMenuEntity> queryListParentId(Long menuId);

    void delete(Long menuId);
}

