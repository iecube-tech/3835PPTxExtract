package com.iecube.pptxsplit.model.course.service;

import com.iecube.pptxsplit.model.course.entity.Course;

import java.util.List;

public interface CourseService {
    void addCourse(Course course);

    List<Course> allCourse();
}
