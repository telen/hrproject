package com.mohress.training.dto.mclass;

import com.mohress.training.dto.student.GraduateDto;
import lombok.Data;

import java.util.List;

/**
 * 班级结业Dto
 *
 */
@Data
public class ClassGraduateDto {

    private String agencyId;

    private String classId;

    private List<GraduateDto> graduateList;
}
