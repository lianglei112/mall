package com.macro.mall.dto;

import com.macro.mall.model.UmsMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/20/ 9:31
 * @description 获取所有菜单返回树型列表
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UmsMenuNode extends UmsMenu {

    /**
     * 子级菜单
     */
    @ApiModelProperty(value = "子级菜单")
    private List<UmsMenuNode> children;

}
