package com.training.web.rest;

import com.training.TrainingApp;

import com.training.domain.Course;
import com.training.repository.CourseRepository;
import com.training.service.CourseService;
import com.training.service.dto.CourseDTO;
import com.training.service.mapper.CourseMapper;
import com.training.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.training.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CourseResource REST controller.
 *
 * @see CourseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrainingApp.class)
public class CourseResourceIntTest {

    private static final String DEFAULT_COURSE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_IMG = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_IMG = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_PHASE = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_PHASE = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_TEACHER = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_TEACHER = "BBBBBBBBBB";

    private static final Integer DEFAULT_COURSE_TIME = 1;
    private static final Integer UPDATED_COURSE_TIME = 2;

    private static final Integer DEFAULT_COURSE_PEOPLE = 1;
    private static final Integer UPDATED_COURSE_PEOPLE = 2;

    private static final Integer DEFAULT_COURSE_STAR = 1;
    private static final Integer UPDATED_COURSE_STAR = 2;

    private static final Integer DEFAULT_COURSE_PRICE = 1;
    private static final Integer UPDATED_COURSE_PRICE = 2;

    private static final Integer DEFAULT_COURSE_RECOMMEND_INDEX = 1;
    private static final Integer UPDATED_COURSE_RECOMMEND_INDEX = 2;

    private static final Integer DEFAULT_COURSE_NEW_RECOMMENDATIONS = 1;
    private static final Integer UPDATED_COURSE_NEW_RECOMMENDATIONS = 2;

    private static final String DEFAULT_RESERVED_ONE = "AAAAAAAAAA";
    private static final String UPDATED_RESERVED_ONE = "BBBBBBBBBB";

    private static final String DEFAULT_RESERVED_TWO = "AAAAAAAAAA";
    private static final String UPDATED_RESERVED_TWO = "BBBBBBBBBB";

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCourseMockMvc;

    private Course course;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CourseResource courseResource = new CourseResource(courseService);
        this.restCourseMockMvc = MockMvcBuilders.standaloneSetup(courseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Course createEntity(EntityManager em) {
        Course course = new Course()
            .courseName(DEFAULT_COURSE_NAME)
            .courseImg(DEFAULT_COURSE_IMG)
            .courseType(DEFAULT_COURSE_TYPE)
            .courseLevel(DEFAULT_COURSE_LEVEL)
            .coursePhase(DEFAULT_COURSE_PHASE)
            .courseContent(DEFAULT_COURSE_CONTENT)
            .courseTeacher(DEFAULT_COURSE_TEACHER)
            .courseTime(DEFAULT_COURSE_TIME)
            .coursePeople(DEFAULT_COURSE_PEOPLE)
            .courseStar(DEFAULT_COURSE_STAR)
            .coursePrice(DEFAULT_COURSE_PRICE)
            .courseRecommendIndex(DEFAULT_COURSE_RECOMMEND_INDEX)
            .courseNewRecommendations(DEFAULT_COURSE_NEW_RECOMMENDATIONS)
            .reservedOne(DEFAULT_RESERVED_ONE)
            .reservedTwo(DEFAULT_RESERVED_TWO);
        return course;
    }

    @Before
    public void initTest() {
        course = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourse() throws Exception {
        int databaseSizeBeforeCreate = courseRepository.findAll().size();

        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);
        restCourseMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isCreated());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeCreate + 1);
        Course testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getCourseName()).isEqualTo(DEFAULT_COURSE_NAME);
        assertThat(testCourse.getCourseImg()).isEqualTo(DEFAULT_COURSE_IMG);
        assertThat(testCourse.getCourseType()).isEqualTo(DEFAULT_COURSE_TYPE);
        assertThat(testCourse.getCourseLevel()).isEqualTo(DEFAULT_COURSE_LEVEL);
        assertThat(testCourse.getCoursePhase()).isEqualTo(DEFAULT_COURSE_PHASE);
        assertThat(testCourse.getCourseContent()).isEqualTo(DEFAULT_COURSE_CONTENT);
        assertThat(testCourse.getCourseTeacher()).isEqualTo(DEFAULT_COURSE_TEACHER);
        assertThat(testCourse.getCourseTime()).isEqualTo(DEFAULT_COURSE_TIME);
        assertThat(testCourse.getCoursePeople()).isEqualTo(DEFAULT_COURSE_PEOPLE);
        assertThat(testCourse.getCourseStar()).isEqualTo(DEFAULT_COURSE_STAR);
        assertThat(testCourse.getCoursePrice()).isEqualTo(DEFAULT_COURSE_PRICE);
        assertThat(testCourse.getCourseRecommendIndex()).isEqualTo(DEFAULT_COURSE_RECOMMEND_INDEX);
        assertThat(testCourse.getCourseNewRecommendations()).isEqualTo(DEFAULT_COURSE_NEW_RECOMMENDATIONS);
        assertThat(testCourse.getReservedOne()).isEqualTo(DEFAULT_RESERVED_ONE);
        assertThat(testCourse.getReservedTwo()).isEqualTo(DEFAULT_RESERVED_TWO);
    }

    @Test
    @Transactional
    public void createCourseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courseRepository.findAll().size();

        // Create the Course with an existing ID
        course.setId(1L);
        CourseDTO courseDTO = courseMapper.toDto(course);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCourses() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList
        restCourseMockMvc.perform(get("/api/courses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(course.getId().intValue())))
            .andExpect(jsonPath("$.[*].courseName").value(hasItem(DEFAULT_COURSE_NAME.toString())))
            .andExpect(jsonPath("$.[*].courseImg").value(hasItem(DEFAULT_COURSE_IMG.toString())))
            .andExpect(jsonPath("$.[*].courseType").value(hasItem(DEFAULT_COURSE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].courseLevel").value(hasItem(DEFAULT_COURSE_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].coursePhase").value(hasItem(DEFAULT_COURSE_PHASE.toString())))
            .andExpect(jsonPath("$.[*].courseContent").value(hasItem(DEFAULT_COURSE_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].courseTeacher").value(hasItem(DEFAULT_COURSE_TEACHER.toString())))
            .andExpect(jsonPath("$.[*].courseTime").value(hasItem(DEFAULT_COURSE_TIME)))
            .andExpect(jsonPath("$.[*].coursePeople").value(hasItem(DEFAULT_COURSE_PEOPLE)))
            .andExpect(jsonPath("$.[*].courseStar").value(hasItem(DEFAULT_COURSE_STAR)))
            .andExpect(jsonPath("$.[*].coursePrice").value(hasItem(DEFAULT_COURSE_PRICE)))
            .andExpect(jsonPath("$.[*].courseRecommendIndex").value(hasItem(DEFAULT_COURSE_RECOMMEND_INDEX)))
            .andExpect(jsonPath("$.[*].courseNewRecommendations").value(hasItem(DEFAULT_COURSE_NEW_RECOMMENDATIONS)))
            .andExpect(jsonPath("$.[*].reservedOne").value(hasItem(DEFAULT_RESERVED_ONE.toString())))
            .andExpect(jsonPath("$.[*].reservedTwo").value(hasItem(DEFAULT_RESERVED_TWO.toString())));
    }

    @Test
    @Transactional
    public void getCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get the course
        restCourseMockMvc.perform(get("/api/courses/{id}", course.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(course.getId().intValue()))
            .andExpect(jsonPath("$.courseName").value(DEFAULT_COURSE_NAME.toString()))
            .andExpect(jsonPath("$.courseImg").value(DEFAULT_COURSE_IMG.toString()))
            .andExpect(jsonPath("$.courseType").value(DEFAULT_COURSE_TYPE.toString()))
            .andExpect(jsonPath("$.courseLevel").value(DEFAULT_COURSE_LEVEL.toString()))
            .andExpect(jsonPath("$.coursePhase").value(DEFAULT_COURSE_PHASE.toString()))
            .andExpect(jsonPath("$.courseContent").value(DEFAULT_COURSE_CONTENT.toString()))
            .andExpect(jsonPath("$.courseTeacher").value(DEFAULT_COURSE_TEACHER.toString()))
            .andExpect(jsonPath("$.courseTime").value(DEFAULT_COURSE_TIME))
            .andExpect(jsonPath("$.coursePeople").value(DEFAULT_COURSE_PEOPLE))
            .andExpect(jsonPath("$.courseStar").value(DEFAULT_COURSE_STAR))
            .andExpect(jsonPath("$.coursePrice").value(DEFAULT_COURSE_PRICE))
            .andExpect(jsonPath("$.courseRecommendIndex").value(DEFAULT_COURSE_RECOMMEND_INDEX))
            .andExpect(jsonPath("$.courseNewRecommendations").value(DEFAULT_COURSE_NEW_RECOMMENDATIONS))
            .andExpect(jsonPath("$.reservedOne").value(DEFAULT_RESERVED_ONE.toString()))
            .andExpect(jsonPath("$.reservedTwo").value(DEFAULT_RESERVED_TWO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCourse() throws Exception {
        // Get the course
        restCourseMockMvc.perform(get("/api/courses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();

        // Update the course
        Course updatedCourse = courseRepository.findOne(course.getId());
        // Disconnect from session so that the updates on updatedCourse are not directly saved in db
        em.detach(updatedCourse);
        updatedCourse
            .courseName(UPDATED_COURSE_NAME)
            .courseImg(UPDATED_COURSE_IMG)
            .courseType(UPDATED_COURSE_TYPE)
            .courseLevel(UPDATED_COURSE_LEVEL)
            .coursePhase(UPDATED_COURSE_PHASE)
            .courseContent(UPDATED_COURSE_CONTENT)
            .courseTeacher(UPDATED_COURSE_TEACHER)
            .courseTime(UPDATED_COURSE_TIME)
            .coursePeople(UPDATED_COURSE_PEOPLE)
            .courseStar(UPDATED_COURSE_STAR)
            .coursePrice(UPDATED_COURSE_PRICE)
            .courseRecommendIndex(UPDATED_COURSE_RECOMMEND_INDEX)
            .courseNewRecommendations(UPDATED_COURSE_NEW_RECOMMENDATIONS)
            .reservedOne(UPDATED_RESERVED_ONE)
            .reservedTwo(UPDATED_RESERVED_TWO);
        CourseDTO courseDTO = courseMapper.toDto(updatedCourse);

        restCourseMockMvc.perform(put("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isOk());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
        Course testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getCourseName()).isEqualTo(UPDATED_COURSE_NAME);
        assertThat(testCourse.getCourseImg()).isEqualTo(UPDATED_COURSE_IMG);
        assertThat(testCourse.getCourseType()).isEqualTo(UPDATED_COURSE_TYPE);
        assertThat(testCourse.getCourseLevel()).isEqualTo(UPDATED_COURSE_LEVEL);
        assertThat(testCourse.getCoursePhase()).isEqualTo(UPDATED_COURSE_PHASE);
        assertThat(testCourse.getCourseContent()).isEqualTo(UPDATED_COURSE_CONTENT);
        assertThat(testCourse.getCourseTeacher()).isEqualTo(UPDATED_COURSE_TEACHER);
        assertThat(testCourse.getCourseTime()).isEqualTo(UPDATED_COURSE_TIME);
        assertThat(testCourse.getCoursePeople()).isEqualTo(UPDATED_COURSE_PEOPLE);
        assertThat(testCourse.getCourseStar()).isEqualTo(UPDATED_COURSE_STAR);
        assertThat(testCourse.getCoursePrice()).isEqualTo(UPDATED_COURSE_PRICE);
        assertThat(testCourse.getCourseRecommendIndex()).isEqualTo(UPDATED_COURSE_RECOMMEND_INDEX);
        assertThat(testCourse.getCourseNewRecommendations()).isEqualTo(UPDATED_COURSE_NEW_RECOMMENDATIONS);
        assertThat(testCourse.getReservedOne()).isEqualTo(UPDATED_RESERVED_ONE);
        assertThat(testCourse.getReservedTwo()).isEqualTo(UPDATED_RESERVED_TWO);
    }

    @Test
    @Transactional
    public void updateNonExistingCourse() throws Exception {
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();

        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCourseMockMvc.perform(put("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isCreated());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);
        int databaseSizeBeforeDelete = courseRepository.findAll().size();

        // Get the course
        restCourseMockMvc.perform(delete("/api/courses/{id}", course.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Course.class);
        Course course1 = new Course();
        course1.setId(1L);
        Course course2 = new Course();
        course2.setId(course1.getId());
        assertThat(course1).isEqualTo(course2);
        course2.setId(2L);
        assertThat(course1).isNotEqualTo(course2);
        course1.setId(null);
        assertThat(course1).isNotEqualTo(course2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseDTO.class);
        CourseDTO courseDTO1 = new CourseDTO();
        courseDTO1.setId(1L);
        CourseDTO courseDTO2 = new CourseDTO();
        assertThat(courseDTO1).isNotEqualTo(courseDTO2);
        courseDTO2.setId(courseDTO1.getId());
        assertThat(courseDTO1).isEqualTo(courseDTO2);
        courseDTO2.setId(2L);
        assertThat(courseDTO1).isNotEqualTo(courseDTO2);
        courseDTO1.setId(null);
        assertThat(courseDTO1).isNotEqualTo(courseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(courseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(courseMapper.fromId(null)).isNull();
    }
}
