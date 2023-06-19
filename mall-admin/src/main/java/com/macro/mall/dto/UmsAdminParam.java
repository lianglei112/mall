package com.macro.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.checkerframework.checker.units.qual.A;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/18 20:03
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UmsAdminParam {

    @NotEmpty
    @ApiModelProperty(value = "用戶名", required = true)
    private String username;

    @NotEmpty
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(value = "头像", required = true)
    private String icon;

    @Email
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @ApiModelProperty(value = "用户昵称", required = true)
    private String nickName;

    @ApiModelProperty(value = "备注信息", required = true)
    private String note;

}
