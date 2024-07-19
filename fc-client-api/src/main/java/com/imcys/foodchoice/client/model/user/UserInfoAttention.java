package com.imcys.foodchoice.client.model.user;

import com.imcys.foodchoice.common.core.model.BaseEntity;
import lombok.Data;

@Data
public class UserInfoAttention extends BaseEntity {
    private Integer userId;
    private Integer attId;
}
