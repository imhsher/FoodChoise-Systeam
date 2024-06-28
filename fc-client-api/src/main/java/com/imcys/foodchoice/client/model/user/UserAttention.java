package com.imcys.foodchoice.client.model.user;

import com.imcys.foodchoice.common.core.model.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAttention extends BaseEntity {
    private Integer userId;
    private Integer attentionNum;
    private Integer attentionState;
}
