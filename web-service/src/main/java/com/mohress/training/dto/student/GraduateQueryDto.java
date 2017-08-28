package com.mohress.training.dto.student;

import com.mohress.training.service.BaseQuery;
import lombok.Data;

/**
 * 毕业生查询DTO
 *
 */
@Data
public class GraduateQueryDto extends BaseQuery{

    private String classId;
}
