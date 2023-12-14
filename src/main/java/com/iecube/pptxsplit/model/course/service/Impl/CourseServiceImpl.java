package com.iecube.pptxsplit.model.course.service.Impl;

import com.iecube.pptxsplit.baseservice.ex.InsertException;
import com.iecube.pptxsplit.model.course.entity.Course;
import com.iecube.pptxsplit.model.course.mapper.CourseMapper;
import com.iecube.pptxsplit.model.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public void addCourse(Course course) {
        Integer row = courseMapper.addCourse(course);
        if(row != 1){
            throw new InsertException("插入数据异常");
        }
    }

    @Override
    public List<Course> allCourse() {
        List<Course> courseList = courseMapper.allCourse();
        return courseList;
    }
}
