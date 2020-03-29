package com.leyou.common.api.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;

import javax.annotation.PostConstruct;

/**
 * @author
 * @date 2020/3/29
 */
public class IdWorker {
    private Long workId = 1L;
    private Long dataCenter = 1L;

    private Snowflake snowflake;

    public IdWorker(Long workId, Long dataCenter) {
        this.workId = workId;
        this.dataCenter = dataCenter;
    }

    @PostConstruct
    public void init() {

        snowflake = IdUtil.createSnowflake(workId, dataCenter);
        if (snowflake == null) {
            throw new NullPointerException("Id生成器初始化失败");
        }
    }
    public Long nextId() {
        return snowflake.nextId();
    }
}
