package com.iecube.pptxsplit.model.course.mapper;

import com.iecube.pptxsplit.model.course.entity.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {
    Integer addCourse(Course course);

    List<Course> allCourse();
}
