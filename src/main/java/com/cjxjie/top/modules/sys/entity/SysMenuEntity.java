package com.cjxjie.top.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 菜单管理
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @since 2022-09-06 17:36:34
 */
@Data
@TableName("sys_menu")
public class SysMenuEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 获取子节点
	 */
	@TableField(exist = false)
	public List<SysMenuEntity> list = new ArrayList<>();

	/**
	 * 父节点名称
	 */
	@TableField(exist = false)
	public String parentName;

	/**
	 * 以树形式打开
	 */
	@TableField(exist=false)
	private Boolean open;

    /**
	 * 菜单ID
	 */
	@TableId
	@JsonSerialize(using = ToStringSerializer.class)
	private Long menuId;
	/**
	 * 父菜单ID，一级菜单为0
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 菜单URL
	 */
	private String url;
	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 */
	private String perms;
	/**
	 * 类型   0：目录   1：菜单   2：按钮
	 */
	private Integer type;
	/**
	 * 菜单图标
	 */
	private String icon;
	/**
	 * 排序
	 */
	private Integer orderNum;

}
