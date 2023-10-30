package com.macro.mall.dto;

import com.macro.mall.model.SmsFlashPromotionSession;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/16 21:45
 * @deprecated 包含商品数量的场次信息
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SmsFlashPromotionSessionDetail extends SmsFlashPromotionSession {

    @ApiModelProperty("商品数量")
    private Long productCount;

}
