
package com.lvhaifeng.cloud.common.annotation;

/**
 * @author ace
 * @create 2018/1/22.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD,ElementType.TYPE,ElementType.FIELD})
public @interface ModifiedUserId {
}
