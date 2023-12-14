package com.iecube.pptxsplit.model.course.mapper;

import com.iecube.pptxsplit.model.course.entity.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CourseMapperTests {
    @Autowired
    private CourseMapper courseMapper;

    @Test
    public void insert(){
        Course course = new Course();
        course.setName("课程2");
        Integer row = courseMapper.addCourse(course);
        System.out.println(row);
        System.out.println(course.getId());
    }

    @Test
    public void allCourse(){
        List<Course> courseList = courseMapper.allCourse();
        System.out.println(courseList);
    }
}
