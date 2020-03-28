package com.leyou.user.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author
 * @date 2020/3/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageBody<T> implements Serializable {
    private T data;
}
