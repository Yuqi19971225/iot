/*
 * Copyright (c) 2022. Pnoker. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iot.common.dto;

import com.iot.common.base.Converter;
import com.iot.common.bean.Pages;
import com.iot.common.model.DriverEvent;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author pnoker
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class DriverEventDto implements Serializable, Converter<DriverEvent, DriverEventDto> {
    private static final long serialVersionUID = 1L;

    private String serviceName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pages page;

    @Override
    public void convertDtoToDo(DriverEvent driverEvent) {
        BeanUtils.copyProperties(this, driverEvent);
    }

    @Override
    public void convertDoToDto(DriverEvent driverEvent) {
        BeanUtils.copyProperties(driverEvent, this);
    }
}
