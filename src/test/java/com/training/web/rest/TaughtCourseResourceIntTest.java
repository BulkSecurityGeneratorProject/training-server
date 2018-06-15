package com.training.web.rest;

import com.training.TrainingApp;

import com.training.domain.TaughtCourse;
import com.training.repository.TaughtCourseRepository;
import com.training.service.TaughtCourseService;
import com.training.service.dto.TaughtCourseDTO;
import com.training.service.mapper.TaughtCourseMapper;
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
 * Test class for the TaughtCourseResource REST controller.
 *
 * @see TaughtCourseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrainingApp.class)
public class TaughtCourseResourceIntTest {

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_RESERVED_ONE = "AAAAAAAAAA";
    private static final String UPDATED_RESERVED_ONE = "BBBBBBBBBB";

    private static final String DEFAULT_RESERVED_TWO = "AAAAAAAAAA";
    private static final String UPDATED_RESERVED_TWO = "BBBBBBBBBB";

    @Autowired
    private TaughtCourseRepository taughtCourseRepository;

    @Autowired
    private TaughtCourseMapper taughtCourseMapper;

    @Autowired
    private TaughtCourseService taughtCourseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTaughtCourseMockMvc;

    private TaughtCourse taughtCourse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaughtCourseResource taughtCourseResource = new TaughtCourseResource(taughtCourseService);
        this.restTaughtCourseMockMvc = MockMvcBuilders.standaloneSetup(taughtCourseResource)
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
    public static TaughtCourse createEntity(EntityManager em) {
        TaughtCourse taughtCourse = new TaughtCourse()
            .active(DEFAULT_ACTIVE)
            .reservedOne(DEFAULT_RESERVED_ONE)
            .reservedTwo(DEFAULT_RESERVED_TWO);
        return taughtCourse;
    }

    @Before
    public void initTest() {
        taughtCourse = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaughtCourse() throws Exception {
        int databaseSizeBeforeCreate = taughtCourseRepository.findAll().size();

        // Create the TaughtCourse
        TaughtCourseDTO taughtCourseDTO = taughtCourseMapper.toDto(taughtCourse);
        restTaughtCourseMockMvc.perform(post("/api/taught-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taughtCourseDTO)))
            .andExpect(status().isCreated());

        // Validate the TaughtCourse in the database
        List<TaughtCourse> taughtCourseList = taughtCourseRepository.findAll();
        assertThat(taughtCourseList).hasSize(databaseSizeBeforeCreate + 1);
        TaughtCourse testTaughtCourse = taughtCourseList.get(taughtCourseList.size() - 1);
        assertThat(testTaughtCourse.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testTaughtCourse.getReservedOne()).isEqualTo(DEFAULT_RESERVED_ONE);
        assertThat(testTaughtCourse.getReservedTwo()).isEqualTo(DEFAULT_RESERVED_TWO);
    }

    @Test
    @Transactional
    public void createTaughtCourseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taughtCourseRepository.findAll().size();

        // Create the TaughtCourse with an existing ID
        taughtCourse.setId(1L);
        TaughtCourseDTO taughtCourseDTO = taughtCourseMapper.toDto(taughtCourse);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaughtCourseMockMvc.perform(post("/api/taught-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taughtCourseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaughtCourse in the database
        List<TaughtCourse> taughtCourseList = taughtCourseRepository.findAll();
        assertThat(taughtCourseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTaughtCourses() throws Exception {
        // Initialize the database
        taughtCourseRepository.saveAndFlush(taughtCourse);

        // Get all the taughtCourseList
        restTaughtCourseMockMvc.perform(get("/api/taught-courses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taughtCourse.getId().intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].reservedOne").value(hasItem(DEFAULT_RESERVED_ONE.toString())))
            .andExpect(jsonPath("$.[*].reservedTwo").value(hasItem(DEFAULT_RESERVED_TWO.toString())));
    }

    @Test
    @Transactional
    public void getTaughtCourse() throws Exception {
        // Initialize the database
        taughtCourseRepository.saveAndFlush(taughtCourse);

        // Get the taughtCourse
        restTaughtCourseMockMvc.perform(get("/api/taught-courses/{id}", taughtCourse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taughtCourse.getId().intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.reservedOne").value(DEFAULT_RESERVED_ONE.toString()))
            .andExpect(jsonPath("$.reservedTwo").value(DEFAULT_RESERVED_TWO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTaughtCourse() throws Exception {
        // Get the taughtCourse
        restTaughtCourseMockMvc.perform(get("/api/taught-courses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaughtCourse() throws Exception {
        // Initialize the database
        taughtCourseRepository.saveAndFlush(taughtCourse);
        int databaseSizeBeforeUpdate = taughtCourseRepository.findAll().size();

        // Update the taughtCourse
        TaughtCourse updatedTaughtCourse = taughtCourseRepository.findOne(taughtCourse.getId());
        // Disconnect from session so that the updates on updatedTaughtCourse are not directly saved in db
        em.detach(updatedTaughtCourse);
        updatedTaughtCourse
            .active(UPDATED_ACTIVE)
            .reservedOne(UPDATED_RESERVED_ONE)
            .reservedTwo(UPDATED_RESERVED_TWO);
        TaughtCourseDTO taughtCourseDTO = taughtCourseMapper.toDto(updatedTaughtCourse);

        restTaughtCourseMockMvc.perform(put("/api/taught-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taughtCourseDTO)))
            .andExpect(status().isOk());

        // Validate the TaughtCourse in the database
        List<TaughtCourse> taughtCourseList = taughtCourseRepository.findAll();
        assertThat(taughtCourseList).hasSize(databaseSizeBeforeUpdate);
        TaughtCourse testTaughtCourse = taughtCourseList.get(taughtCourseList.size() - 1);
        assertThat(testTaughtCourse.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testTaughtCourse.getReservedOne()).isEqualTo(UPDATED_RESERVED_ONE);
        assertThat(testTaughtCourse.getReservedTwo()).isEqualTo(UPDATED_RESERVED_TWO);
    }

    @Test
    @Transactional
    public void updateNonExistingTaughtCourse() throws Exception {
        int databaseSizeBeforeUpdate = taughtCourseRepository.findAll().size();

        // Create the TaughtCourse
        TaughtCourseDTO taughtCourseDTO = taughtCourseMapper.toDto(taughtCourse);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTaughtCourseMockMvc.perform(put("/api/taught-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taughtCourseDTO)))
            .andExpect(status().isCreated());

        // Validate the TaughtCourse in the database
        List<TaughtCourse> taughtCourseList = taughtCourseRepository.findAll();
        assertThat(taughtCourseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTaughtCourse() throws Exception {
        // Initialize the database
        taughtCourseRepository.saveAndFlush(taughtCourse);
        int databaseSizeBeforeDelete = taughtCourseRepository.findAll().size();

        // Get the taughtCourse
        restTaughtCourseMockMvc.perform(delete("/api/taught-courses/{id}", taughtCourse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TaughtCourse> taughtCourseList = taughtCourseRepository.findAll();
        assertThat(taughtCourseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaughtCourse.class);
        TaughtCourse taughtCourse1 = new TaughtCourse();
        taughtCourse1.setId(1L);
        TaughtCourse taughtCourse2 = new TaughtCourse();
        taughtCourse2.setId(taughtCourse1.getId());
        assertThat(taughtCourse1).isEqualTo(taughtCourse2);
        taughtCourse2.setId(2L);
        assertThat(taughtCourse1).isNotEqualTo(taughtCourse2);
        taughtCourse1.setId(null);
        assertThat(taughtCourse1).isNotEqualTo(taughtCourse2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaughtCourseDTO.class);
        TaughtCourseDTO taughtCourseDTO1 = new TaughtCourseDTO();
        taughtCourseDTO1.setId(1L);
        TaughtCourseDTO taughtCourseDTO2 = new TaughtCourseDTO();
        assertThat(taughtCourseDTO1).isNotEqualTo(taughtCourseDTO2);
        taughtCourseDTO2.setId(taughtCourseDTO1.getId());
        assertThat(taughtCourseDTO1).isEqualTo(taughtCourseDTO2);
        taughtCourseDTO2.setId(2L);
        assertThat(taughtCourseDTO1).isNotEqualTo(taughtCourseDTO2);
        taughtCourseDTO1.setId(null);
        assertThat(taughtCourseDTO1).isNotEqualTo(taughtCourseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(taughtCourseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(taughtCourseMapper.fromId(null)).isNull();
    }
}
