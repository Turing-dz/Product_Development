package com.hckj.business.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private String desc;
    /**
     * 不写入堆栈信息，简化异常日志，提高性能
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
