package com.liye.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liye
 * @create 2021-01-14-15:59
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectSystemException extends RuntimeException {
    private Integer code;//状态吗

    private String msg;//异常信息
}
