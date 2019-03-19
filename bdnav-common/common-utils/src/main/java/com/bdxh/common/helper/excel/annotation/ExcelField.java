/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bdxh.common.helper.excel.annotation;

import com.bdxh.common.helper.excel.converter.DefaultConvertible;
import com.bdxh.common.helper.excel.converter.ReadConvertible;
import com.bdxh.common.helper.excel.converter.WriteConvertible;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能说明: 用来在对象的属性上加入的annotation，通过该annotation说明某个属性所对应的标题
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelField {

    /**
     * 属性的标题名称
     *
     * @return 表头名
     */
    String title();

    /**
     * 写数据转换器
     *
     * @return 写入Excel数据转换器
     * @see WriteConvertible
     */
    Class<? extends WriteConvertible> writeConverter()
            default DefaultConvertible.class;

    /**
     * 读数据转换器
     *
     * @return 读取Excel数据转换器
     * @see ReadConvertible
     */
    Class<? extends ReadConvertible> readConverter()
            default DefaultConvertible.class;

    /**
     * 在excel的顺序
     *
     * @return 列表顺序
     */
    int order() default 9999;

    /**
     * 是否是日期转数字
     *
     * @return
     */
    boolean isTime2number() default false;

    /**
     * 描述: 对应的枚举名称
     */
    String enumName() default "";
}
