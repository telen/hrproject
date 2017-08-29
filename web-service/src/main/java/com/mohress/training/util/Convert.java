package com.mohress.training.util;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mohress.training.dto.agency.AgencyItemDto;
import com.mohress.training.dto.attendance.AttendanceItemDto;
import com.mohress.training.dto.attendance.AttendanceStatisticItemDto;
import com.mohress.training.dto.course.CourseItemDto;
import com.mohress.training.dto.mclass.ClassItemDto;
import com.mohress.training.dto.student.GraduateDto;
import com.mohress.training.dto.student.StudentItemDto;
import com.mohress.training.dto.teacher.TeacherItemDto;
import com.mohress.training.entity.TblCourse;
import com.mohress.training.entity.TblTeacher;
import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.entity.attendance.TblAttendance;
import com.mohress.training.entity.attendance.TblAttendanceStatistics;
import com.mohress.training.entity.mclass.TblClass;
import com.mohress.training.entity.student.TblExamScore;
import com.mohress.training.entity.student.TblStudent;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * bean之间的转换
 * Created by qx.wang on 2017/8/15.
 */
public class Convert {
    private static final Splitter SPLITTER = Splitter.on(",").omitEmptyStrings().trimResults();

    /**
     * 转换机构列表展示
     */
    public static List<AgencyItemDto> convertAgency(List<TblAgency> tblAgencies) {
        if (CollectionUtils.isEmpty(tblAgencies)) {
            return null;
        }
        return Lists.newArrayList(Lists.transform(tblAgencies, new Function<TblAgency, AgencyItemDto>() {
            @Override
            public AgencyItemDto apply(TblAgency input) {
                AgencyItemDto dto = new AgencyItemDto();
                BeanUtils.copyProperties(input, dto);
                return dto;
            }
        }));
    }

    public static List<TeacherItemDto> convertTeacher(List<TblTeacher> tblTeachers) {
        if (CollectionUtils.isEmpty(tblTeachers)) {
            return null;
        }

        return Lists.transform(tblTeachers, new Function<TblTeacher, TeacherItemDto>() {
            @Override
            public TeacherItemDto apply(TblTeacher input) {
                TeacherItemDto dto = new TeacherItemDto();
                BeanUtils.copyProperties(input, dto);
                return dto;
            }
        });
    }

    public static List<ClassItemDto> convertClass(List<TblClass> tblClasses) {
        if (CollectionUtils.isEmpty(tblClasses)) {
            return null;
        }

        return Lists.newArrayList(Lists.transform(tblClasses, new Function<TblClass, ClassItemDto>() {
            @Override
            public ClassItemDto apply(TblClass input) {
                ClassItemDto dto = new ClassItemDto();
                BeanUtils.copyProperties(input, dto, "startTime", "endTime", "onClassTime", "offClassTime");
                dto.setStartTime(input.getStartTime().getTime());
                dto.setEndTime(input.getEndTime().getTime());
                dto.setOnClassTime(input.getOnClassTime().getTime());
                dto.setOffClassTime(input.getOffClassTime().getTime());
                return dto;
            }
        }));

    }

    public static List<StudentItemDto> convertStudent(List<TblStudent> tblStudents) {
        if (CollectionUtils.isEmpty(tblStudents)) {
            return null;
        }

        return Lists.transform(tblStudents, new Function<TblStudent, StudentItemDto>() {
            @Override
            public StudentItemDto apply(TblStudent input) {
                StudentItemDto dto = new StudentItemDto();
                BeanUtils.copyProperties(input, dto);
                Date birthday = input.getBirthday();
                Date schoolDate = input.getSchoolDate();
                Date dropout = input.getDropout();
                if (birthday != null) {
                    dto.setBirthday(birthday.getTime());
                }
                if (schoolDate != null) {
                    dto.setSchoolDate(schoolDate.getTime());
                }
                if (dropout != null) {
                    dto.setDropout(dropout.getTime());
                }
                return dto;
            }
        });
    }

    public static List<CourseItemDto> convertCourse(List<TblCourse> tblCourses) {
        if (CollectionUtils.isEmpty(tblCourses)) {
            return null;
        }

        return Lists.transform(tblCourses, new Function<TblCourse, CourseItemDto>() {
            @Override
            public CourseItemDto apply(TblCourse input) {
                CourseItemDto dto = new CourseItemDto();
                BeanUtils.copyProperties(input, dto);
                return dto;
            }
        });
    }

    public static List<AttendanceItemDto> convertAttendance(List<TblAttendance> tblAgencies) {
        if (CollectionUtils.isEmpty(tblAgencies)) {
            return null;
        }

        return Lists.transform(tblAgencies, new Function<TblAttendance, AttendanceItemDto>() {
            @Override
            public AttendanceItemDto apply(TblAttendance input) {
                AttendanceItemDto dto = new AttendanceItemDto();
                BeanUtils.copyProperties(input, dto);
                parseTime(dto, input.getAttendanceTime());
                return dto;
            }

            private void parseTime(AttendanceItemDto dto, String attendanceTime) {
                if (Strings.isNullOrEmpty(attendanceTime)) {
                    return;
                }
                List<String> attendanceTimes = SPLITTER.splitToList(attendanceTime);

                try {
                    dto.setStartTime(Long.valueOf(attendanceTimes.get(0)));
                    dto.setEndTime(Long.valueOf(attendanceTimes.get(attendanceTimes.size() - 1)));
                } catch (Exception e) {

                }
            }
        });
    }


    public static TblExamScore graduateRequestDto2TblExamScore(GraduateDto graduateDto){
        BusiVerify.verifyNotEmpty(graduateDto.getStudentId(), "学生Id不能为空");
        BusiVerify.verifyNotEmpty(graduateDto.getTheoryScore(), "理论成绩不能为空");
        BusiVerify.verifyNotEmpty(graduateDto.getPracticeScore(), "实践成绩不能为空");
        BusiVerify.verifyNotEmpty(graduateDto.getCertificate(), "结业证书不能为空");

        TblExamScore tblExamScore = new TblExamScore();

        tblExamScore.setStudentId(graduateDto.getStudentId());
        tblExamScore.setTheoryScore(new BigDecimal(graduateDto.getTheoryScore()));
        tblExamScore.setPracticeScore(new BigDecimal(graduateDto.getPracticeScore()));
        tblExamScore.setCertificate(graduateDto.getCertificate());
        tblExamScore.setCertificateId("");
        return tblExamScore;
    }
    public static List<AttendanceStatisticItemDto> convertStatistic(List<TblAttendanceStatistics> statisticItems) {
        if (CollectionUtils.isEmpty(statisticItems)) {
            return null;
        }

        return Lists.newArrayList(Lists.transform(statisticItems, new Function<TblAttendanceStatistics, AttendanceStatisticItemDto>() {
            @Override
            public AttendanceStatisticItemDto apply(TblAttendanceStatistics input) {
                AttendanceStatisticItemDto dto = new AttendanceStatisticItemDto();
                BeanUtils.copyProperties(input, dto);
                return dto;
            }
        }));
    }
}
