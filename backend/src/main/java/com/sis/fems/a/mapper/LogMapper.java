package com.sis.fems.a.mapper;

import org.apache.ibatis.annotations.Param;

public interface LogMapper {
    void insertLog(@Param("errCode") String errCode, @Param("errComment") String errComment);
}
