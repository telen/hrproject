package com.mohress.training.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.mohress.training.dto.agency.AgencyRequestDto;
import com.mohress.training.dto.attendance.AttendanceRequestDto;
import com.mohress.training.dto.course.CourseRequestDto;
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
//        Preconditions.checkArgument(!Strings.isNullOrEmpty(studentRequestDto.getFingerprint()));
        BusiVerify.verifyNotEmpty(studentRequestDto.getIdNumber(), "身份证号码为空");
        BusiVerify.verifyNotEmpty(studentRequestDto.getMobile(), "手机号为空");
        BusiVerify.verifyNotEmpty(studentRequestDto.getNationality(), "民族为空");
//        BusiVerify.verifyNotEmpty(studentRequestDto.getPoliticalOutlook(), "政治面貌为空");
        BusiVerify.verifyNotEmpty(studentRequestDto.getCourseId(), "课程ID为空");
    }

    public static void checkNewCourse(CourseRequestDto courseRequestDto) {
        BusiVerify.verify(!Strings.isNullOrEmpty(courseRequestDto.getCourseBrief()),"课程描述为空");
        BusiVerify.verify(!Strings.isNullOrEmpty(courseRequestDto.getProfession()),"所属专业为空");
        BusiVerify.verify(!Strings.isNullOrEmpty(courseRequestDto.getTeacherId()),"教师名称为空");
        BusiVerify.verify(!Strings.isNullOrEmpty(courseRequestDto.getTeachingMaterial()),"所用教材为空");
        BusiVerify.verify(courseRequestDto.getPeriod() != null,"课程描述为空");
    }

    public static void checkNewAgency(AgencyRequestDto agencyRequestDto) {
        BusiVerify.verifyNotEmpty(agencyRequestDto.getAddress(),"机构地址为空");
        BusiVerify.verifyNotEmpty(agencyRequestDto.getAgencyHead(),"机构负责人为空");
        BusiVerify.verifyNotEmpty(agencyRequestDto.getAgencyMail(),"机构邮箱为空");
        BusiVerify.verifyNotEmpty(agencyRequestDto.getAgencyMobile(),"机构联系方式为空");
        BusiVerify.verifyNotEmpty(agencyRequestDto.getAgencyName(),"机构名称为空");
        BusiVerify.verifyNotEmpty(agencyRequestDto.getSuperiorDepartment(),"机构上级部门为空");
    }

    public static void checkNewAttendance(AttendanceRequestDto dto) {
        BusiVerify.verifyNotEmpty(dto.getCourseId(),"打卡课程为空");
        BusiVerify.verifyNotEmpty(dto.getUserId(),"打卡UserId为空");
    }
}
