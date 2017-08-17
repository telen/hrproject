package com.mohress.training.dto.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 教师列表返回值
 * Created by qx.wang on 2017/8/15.
 */
@Data
@AllArgsConstructor
public class TeacherListResponseDto implements Serializable {

    private List<TeacherItemDto> teacherItemDtoList;

}
