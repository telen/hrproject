package com.mohress.training.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.mohress.training.dto.student.StudentRequestDto;
import com.mohress.training.dto.teacher.TeacherRequestDto;

/**
 * check
 * Created by qx.wang on 2017/8/17.
 */
public class Checker {


    public static void checkNewTeacher(TeacherRequestDto teacherRequestDto) {
        Preconditions.checkArgument(teacherRequestDto != null);
        BusiVerify.verifyNotEmpty(teacherRequestDto.getAddress(), "住址为空");
//        BusiVerify.verifyNotEmpty(teacherRequestDto.getAgencyId(), "机构ID为空");
        BusiVerify.verify(teacherRequestDto.getGender() != null, "性别为空");
        BusiVerify.verify(teacherRequestDto.getBirthday() != null, "生日为空");
        BusiVerify.verifyNotEmpty(teacherRequestDto.getEducation(), "教育为空");
        BusiVerify.verifyNotEmpty(teacherRequestDto.getIdNumber(), "身份证为空");
        BusiVerify.verifyNotEmpty(teacherRequestDto.getMobile(), "手机为空");
        BusiVerify.verifyNotEmpty(teacherRequestDto.getNationality(), "民族为空");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(teacherRequestDto.getName()));
    }

    public static void checkNewStudent(StudentRequestDto studentRequestDto) {
        Preconditions.checkNotNull(studentRequestDto);
        Preconditions.checkArgument(studentRequestDto.getGender() != null);
        Preconditions.checkArgument(studentRequestDto.getInsuredStatus() != null);
        Preconditions.checkArgument(studentRequestDto.getMaritalStatus() != null);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(studentRequestDto.getEducation()));
        Preconditions.checkArgument(!Strings.isNullOrEmpty(studentRequestDto.getEmail()));
        Preconditions.checkArgument(!Strings.isNullOrEmpty(studentRequestDto.getFingerprint()));
        BusiVerify.verifyNotEmpty(studentRequestDto.getGraduationSchool(), "毕业学校为空");
        BusiVerify.verifyNotEmpty(studentRequestDto.getIdNumber(), "身份证号码为空");
        BusiVerify.verifyNotEmpty(studentRequestDto.getMobile(), "手机号为空");
        BusiVerify.verifyNotEmpty(studentRequestDto.getNationality(), "民族为空");
        BusiVerify.verifyNotEmpty(studentRequestDto.getPhysicalCondition(), "健康状况为空");
        BusiVerify.verifyNotEmpty(studentRequestDto.getPoliticalOutlook(), "政治面貌为空");
        BusiVerify.verifyNotEmpty(studentRequestDto.getCourseId(), "课程ID为空");
    }
}
