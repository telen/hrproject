package com.mohress.training.service.teacher;

import com.google.common.base.Preconditions;
import com.mohress.training.dto.teacher.TeacherRequestDto;
import com.mohress.training.entity.TblTeacher;
import com.mohress.training.service.ModuleBiz;
import com.mohress.training.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 教师服务
 * Created by qx.wang on 2017/8/15.
 */
@Slf4j
@Service
public class TeacherBizImpl implements ModuleBiz {

    @Resource
    private TeacherService teacherServiveImpl;

    @Override
    public void newModule(Object o) {
        Preconditions.checkArgument(o != null);
        TeacherRequestDto teacherRequestDto = null;
        try {
            teacherRequestDto = JsonUtil.getInstance().convertToBean(TeacherRequestDto.class, String.valueOf(0));
        } catch (Exception e) {
            log.error("教师新增反序列化失败 {}", o, e);
        }
        teacherServiveImpl.newModule(buildTblTeacher(teacherRequestDto));
    }

    @Override
    public void delete(List<String> ids) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(ids));
        teacherServiveImpl.delete(ids);
    }

    @Override
    public void update(Object o) {
        Preconditions.checkArgument(o != null);
        TeacherRequestDto teacherRequestDto = null;
        try {
            teacherRequestDto = JsonUtil.getInstance().convertToBean(TeacherRequestDto.class, String.valueOf(0));
        } catch (Exception e) {
            log.error("教师新增反序列化失败 {}", o, e);
        }
        teacherServiveImpl.update(buildTblTeacher(teacherRequestDto));
    }

    private TblTeacher buildTblTeacher(TeacherRequestDto teacherRequestDto) {
        TblTeacher teacher = new TblTeacher();
        BeanUtils.copyProperties(teacherRequestDto,teacher);
        return teacher;
    }

}
