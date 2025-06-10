package com.sis.fems.a.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface TestMapper {
    Date selectNow();

}
