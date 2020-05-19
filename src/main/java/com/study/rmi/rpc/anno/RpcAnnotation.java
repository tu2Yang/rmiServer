package com.study.rmi.rpc.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 作用范围
@Target(ElementType.TYPE)
// 生命周期
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcAnnotation {

    Class<?> value();

}
