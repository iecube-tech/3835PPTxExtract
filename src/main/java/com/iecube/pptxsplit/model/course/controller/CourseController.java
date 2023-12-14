package com.iecube.pptxsplit.model.course.controller;

import com.iecube.pptxsplit.basecontroller.BaseController;
import com.iecube.pptxsplit.model.course.entity.Course;
import com.iecube.pptxsplit.model.course.service.CourseService;
import com.iecube.pptxsplit.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/all")
    public JsonResult<List> allCourse(){
        List<Course> courseList = courseService.allCourse();
        return new JsonResult<>(OK, courseList);
    }

    @PostMapping("/add")
    public JsonResult<List> addCourse(String name){
        Course course = new Course();
        course.setName(name);
        courseService.addCourse(course);
        List<Course> courseList = courseService.allCourse();
        return new JsonResult<>(OK, courseList);
    }
}
