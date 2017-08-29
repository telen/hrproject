package com.mohress.training.service.attendance;

import com.mohress.training.dao.TblAgencyDao;
import com.mohress.training.dao.TblAttendanceStatisticsDao;
import com.mohress.training.dao.TblClassDao;
import com.mohress.training.dao.TblClassMemberDao;
import com.mohress.training.entity.TblCourse;
import com.mohress.training.entity.TblTeacher;
import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.entity.attendance.TblAttendance;
import com.mohress.training.entity.attendance.TblAttendanceStatistics;
import com.mohress.training.entity.mclass.TblClass;
import com.mohress.training.entity.mclass.TblClassMember;
import com.mohress.training.service.BaseManageService;
import com.mohress.training.service.course.CourseQuery;
import com.mohress.training.service.course.CourseServiceImpl;
import com.mohress.training.service.teacher.TeacherQuery;
import com.mohress.training.service.teacher.TeacherServiceImpl;
import com.mohress.training.util.BusiVerify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 考勤统计
 * Created by qx.wang on 2017/8/24.
 */
@Slf4j
@Service
public class AttendanceStatisticsService implements BaseManageService {

    @Resource
    private TblClassDao tblClassDao;

    @Resource
    private TblAgencyDao tblAgencyDao;

    @Resource
    private TblClassMemberDao tblClassMemberDao;

    @Resource
    private CourseServiceImpl courseServiceImpl;

    @Resource
    private TeacherServiceImpl teacherServiceImpl;

    @Resource
    private AttendanceServiceImpl attendanceServiceImpl;

    @Resource
    private TblAttendanceStatisticsDao tblAttendanceStatisticsDao;

    @Override
    public <T> void newModule(T statistics) {
        BusiVerify.verify(tblAttendanceStatisticsDao.insertSelective((TblAttendanceStatistics) statistics) > 0, "新增考勤统计记录SQL失败");
    }

    @Override
    public void delete(List<String> ids) {

    }

    @Override
    public <T> void update(T t) {

    }

    @Override
    public <T, M> List<T> query(M query) {
        return (List<T>) tblAttendanceStatisticsDao.selectByQuery((AttendanceStatisticsQuery) query);
    }

    @Override
    public <T, M> List<T> queryByKeyword(M m) {
        return null;
    }

    public void buildStatistics(Date startTime) {
        List<TblClass> tblClasses = tblClassDao.selectByRangeTime(startTime);
        //针对每一个班级，查询其所有学生，根据班级开班时间与学生考勤记录，统计其记录
        for (TblClass tblClass : tblClasses) {
            int classMemCount = 0, beLateCount = 0, leaveEarlyCount = 0, absentCount = 0, addedCount = 0, normalCount = 0;

            //查询课程
            List<TblCourse> tblCourses = courseServiceImpl.query(buildCourseQuery(tblClass.getCourseId()));
            if (CollectionUtils.isEmpty(tblClasses)) {
                log.error("为查询到该course {}", tblClass.getCourseId());
                continue;
            }

            //查询教师信息
            List<TblTeacher> tblTeachers = teacherServiceImpl.query(buildTeacherQuery(tblCourses.get(0).getTeacherId()));

            if (CollectionUtils.isEmpty(tblTeachers)) {
                log.error("未查询到teacher {}", tblCourses.get(0).getTeacherId());
                continue;
            }

            String teacherId = tblTeachers.get(0).getTeacherId(), teacherName = tblTeachers.get(0).getName();
            String classNo = tblClass.getClassId(), classname = tblClass.getClassname();

            List<TblClassMember> classMembers = tblClassMemberDao.selectByClassId(tblClass.getClassId());
            //组装统计数量
            if (!CollectionUtils.isEmpty(classMembers)) {
                classMemCount = classMembers.size();
                //针对每个学生，判断其是否
                for (TblClassMember member : classMembers) {
                    if (hasSpecialDays(member, tblClass, TblAttendance.Status.BE_LATE)) {
                        beLateCount++;
                    }
                    if (hasSpecialDays(member, tblClass, TblAttendance.Status.LEAVE_EARLY)) {
                        leaveEarlyCount++;
                    }
                    if (hasSpecialDays(member, tblClass, TblAttendance.Status.LEAVE)) {//todo 缺勤
                        absentCount++;
                    }
                    if (hasSpecialDays(member, tblClass, TblAttendance.Status.PATCH_CLOCK)) {
                        addedCount++;
                    }
                    if (hasSpecialDays(member, tblClass, TblAttendance.Status.NORMAL)) {
                        normalCount++;
                    }
                }
            }

            TblAgency agency = tblAgencyDao.selectByAgencyId(tblClass.getAgencyId());
            TblAttendanceStatistics statistics = new TblAttendanceStatistics();
            if (agency != null) {
                statistics.setAgencyId(agency.getAgencyId());
                statistics.setAgencyName(agency.getAgencyName());
            }

            statistics.setTotalCount(classMemCount);
            statistics.setTeacherId(teacherId);
            statistics.setTeacherName(teacherName);
            statistics.setClassId(classNo);
            statistics.setClassname(classname);
            statistics.setAbsentCount(absentCount);
            statistics.setAddedCount(addedCount);
            statistics.setAbsentCount(absentCount);
            statistics.setBeLateCount(beLateCount);
            statistics.setLeaveEarlyCount(leaveEarlyCount);
            statistics.setNormalCount(normalCount);
            TblAttendanceStatistics dbStatistic = tblAttendanceStatisticsDao.selectByClassId(statistics.getClassId());
            if (dbStatistic == null) {
                BusiVerify.verify(tblAttendanceStatisticsDao.insertSelective(statistics) > 0, "新增考勤统计SQL异常");
            } else {
                statistics.setId(dbStatistic.getId());
                BusiVerify.verify(tblAttendanceStatisticsDao.updateByPrimaryKeySelective(statistics) > 0, "更新考勤统计SQL失败");
            }
        }
    }

    private boolean hasSpecialDays(TblClassMember member, TblClass tblClass, int status) {
        List<TblAttendance> attendances = attendanceServiceImpl.queryByStudentId
                (member.getStudentId(), status, tblClass.getStartTime(), tblClass.getEndTime());
        return !CollectionUtils.isEmpty(attendances);
    }

    private TeacherQuery buildTeacherQuery(String teacherId) {
        TeacherQuery query = new TeacherQuery();
        query.setTeacherId(teacherId);
        query.setPageIndex(0);
        query.setPageSize(10);
        return query;
    }

    private CourseQuery buildCourseQuery(String courseId) {
        CourseQuery query = new CourseQuery();
        query.setPageIndex(0);
        query.setPageSize(10);
        query.setCourseId(courseId);
        return query;
    }
}
