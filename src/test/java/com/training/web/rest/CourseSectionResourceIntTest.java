package com.training.web.rest;

import com.training.TrainingApp;

import com.training.domain.CourseSection;
import com.training.repository.CourseSectionRepository;
import com.training.service.CourseSectionService;
import com.training.service.dto.CourseSectionDTO;
import com.training.service.mapper.CourseSectionMapper;
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
 * Test class for the CourseSectionResource REST controller.
 *
 * @see CourseSectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrainingApp.class)
public class CourseSectionResourceIntTest {

    private static final String DEFAULT_COURSE_SECTION_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_SECTION_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_RESERVED_ONE = "AAAAAAAAAA";
    private static final String UPDATED_RESERVED_ONE = "BBBBBBBBBB";

    private static final String DEFAULT_RESERVED_TWO = "AAAAAAAAAA";
    private static final String UPDATED_RESERVED_TWO = "BBBBBBBBBB";

    @Autowired
    private CourseSectionRepository courseSectionRepository;

    @Autowired
    private CourseSectionMapper courseSectionMapper;

    @Autowired
    private CourseSectionService courseSectionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCourseSectionMockMvc;

    private CourseSection courseSection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CourseSectionResource courseSectionResource = new CourseSectionResource(courseSectionService);
        this.restCourseSectionMockMvc = MockMvcBuilders.standaloneSetup(courseSectionResource)
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
    public static CourseSection createEntity(EntityManager em) {
        CourseSection courseSection = new CourseSection()
            .courseSectionTitle(DEFAULT_COURSE_SECTION_TITLE)
            .reservedOne(DEFAULT_RESERVED_ONE)
            .reservedTwo(DEFAULT_RESERVED_TWO);
        return courseSection;
    }

    @Before
    public void initTest() {
        courseSection = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourseSection() throws Exception {
        int databaseSizeBeforeCreate = courseSectionRepository.findAll().size();

        // Create the CourseSection
        CourseSectionDTO courseSectionDTO = courseSectionMapper.toDto(courseSection);
        restCourseSectionMockMvc.perform(post("/api/course-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseSectionDTO)))
            .andExpect(status().isCreated());

        // Validate the CourseSection in the database
        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeCreate + 1);
        CourseSection testCourseSection = courseSectionList.get(courseSectionList.size() - 1);
        assertThat(testCourseSection.getCourseSectionTitle()).isEqualTo(DEFAULT_COURSE_SECTION_TITLE);
        assertThat(testCourseSection.getReservedOne()).isEqualTo(DEFAULT_RESERVED_ONE);
        assertThat(testCourseSection.getReservedTwo()).isEqualTo(DEFAULT_RESERVED_TWO);
    }

    @Test
    @Transactional
    public void createCourseSectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courseSectionRepository.findAll().size();

        // Create the CourseSection with an existing ID
        courseSection.setId(1L);
        CourseSectionDTO courseSectionDTO = courseSectionMapper.toDto(courseSection);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseSectionMockMvc.perform(post("/api/course-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseSectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CourseSection in the database
        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCourseSections() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList
        restCourseSectionMockMvc.perform(get("/api/course-sections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseSection.getId().intValue())))
            .andExpect(jsonPath("$.[*].courseSectionTitle").value(hasItem(DEFAULT_COURSE_SECTION_TITLE.toString())))
            .andExpect(jsonPath("$.[*].reservedOne").value(hasItem(DEFAULT_RESERVED_ONE.toString())))
            .andExpect(jsonPath("$.[*].reservedTwo").value(hasItem(DEFAULT_RESERVED_TWO.toString())));
    }

    @Test
    @Transactional
    public void getCourseSection() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get the courseSection
        restCourseSectionMockMvc.perform(get("/api/course-sections/{id}", courseSection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(courseSection.getId().intValue()))
            .andExpect(jsonPath("$.courseSectionTitle").value(DEFAULT_COURSE_SECTION_TITLE.toString()))
            .andExpect(jsonPath("$.reservedOne").value(DEFAULT_RESERVED_ONE.toString()))
            .andExpect(jsonPath("$.reservedTwo").value(DEFAULT_RESERVED_TWO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCourseSection() throws Exception {
        // Get the courseSection
        restCourseSectionMockMvc.perform(get("/api/course-sections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourseSection() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);
        int databaseSizeBeforeUpdate = courseSectionRepository.findAll().size();

        // Update the courseSection
        CourseSection updatedCourseSection = courseSectionRepository.findOne(courseSection.getId());
        // Disconnect from session so that the updates on updatedCourseSection are not directly saved in db
        em.detach(updatedCourseSection);
        updatedCourseSection
            .courseSectionTitle(UPDATED_COURSE_SECTION_TITLE)
            .reservedOne(UPDATED_RESERVED_ONE)
            .reservedTwo(UPDATED_RESERVED_TWO);
        CourseSectionDTO courseSectionDTO = courseSectionMapper.toDto(updatedCourseSection);

        restCourseSectionMockMvc.perform(put("/api/course-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseSectionDTO)))
            .andExpect(status().isOk());

        // Validate the CourseSection in the database
        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeUpdate);
        CourseSection testCourseSection = courseSectionList.get(courseSectionList.size() - 1);
        assertThat(testCourseSection.getCourseSectionTitle()).isEqualTo(UPDATED_COURSE_SECTION_TITLE);
        assertThat(testCourseSection.getReservedOne()).isEqualTo(UPDATED_RESERVED_ONE);
        assertThat(testCourseSection.getReservedTwo()).isEqualTo(UPDATED_RESERVED_TWO);
    }

    @Test
    @Transactional
    public void updateNonExistingCourseSection() throws Exception {
        int databaseSizeBeforeUpdate = courseSectionRepository.findAll().size();

        // Create the CourseSection
        CourseSectionDTO courseSectionDTO = courseSectionMapper.toDto(courseSection);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCourseSectionMockMvc.perform(put("/api/course-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseSectionDTO)))
            .andExpect(status().isCreated());

        // Validate the CourseSection in the database
        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCourseSection() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);
        int databaseSizeBeforeDelete = courseSectionRepository.findAll().size();

        // Get the courseSection
        restCourseSectionMockMvc.perform(delete("/api/course-sections/{id}", courseSection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseSection.class);
        CourseSection courseSection1 = new CourseSection();
        courseSection1.setId(1L);
        CourseSection courseSection2 = new CourseSection();
        courseSection2.setId(courseSection1.getId());
        assertThat(courseSection1).isEqualTo(courseSection2);
        courseSection2.setId(2L);
        assertThat(courseSection1).isNotEqualTo(courseSection2);
        courseSection1.setId(null);
        assertThat(courseSection1).isNotEqualTo(courseSection2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseSectionDTO.class);
        CourseSectionDTO courseSectionDTO1 = new CourseSectionDTO();
        courseSectionDTO1.setId(1L);
        CourseSectionDTO courseSectionDTO2 = new CourseSectionDTO();
        assertThat(courseSectionDTO1).isNotEqualTo(courseSectionDTO2);
        courseSectionDTO2.setId(courseSectionDTO1.getId());
        assertThat(courseSectionDTO1).isEqualTo(courseSectionDTO2);
        courseSectionDTO2.setId(2L);
        assertThat(courseSectionDTO1).isNotEqualTo(courseSectionDTO2);
        courseSectionDTO1.setId(null);
        assertThat(courseSectionDTO1).isNotEqualTo(courseSectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(courseSectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(courseSectionMapper.fromId(null)).isNull();
    }
}
