package com.iot.common.base;

/**
 * @description Converter
 * @date
 */
public interface Converter<DO, DTO> {

    /**
     * @param d: Dto对象
     * @return void
     * @description DTO 转 DO
     * @date
     */
    void convertDtoToDo(DO d);

    /**
     * @param d: Do对象
     * @return void
     * @description DO 转 DTO
     * @date
     */
    void convertDoToDto(DO d);
}
