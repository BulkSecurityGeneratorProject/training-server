package com.training.web.rest;

import com.training.TrainingApp;

import com.training.domain.Forum;
import com.training.repository.ForumRepository;
import com.training.service.ForumService;
import com.training.service.dto.ForumDTO;
import com.training.service.mapper.ForumMapper;
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
 * Test class for the ForumResource REST controller.
 *
 * @see ForumResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrainingApp.class)
public class ForumResourceIntTest {

    private static final String DEFAULT_FORUM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FORUM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FORUM_IMG = "AAAAAAAAAA";
    private static final String UPDATED_FORUM_IMG = "BBBBBBBBBB";

    private static final String DEFAULT_FORUN_INTRODUCE = "AAAAAAAAAA";
    private static final String UPDATED_FORUN_INTRODUCE = "BBBBBBBBBB";

    private static final String DEFAULT_RESERVED_ONE = "AAAAAAAAAA";
    private static final String UPDATED_RESERVED_ONE = "BBBBBBBBBB";

    private static final String DEFAULT_RESERVED_TWO = "AAAAAAAAAA";
    private static final String UPDATED_RESERVED_TWO = "BBBBBBBBBB";

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private ForumMapper forumMapper;

    @Autowired
    private ForumService forumService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restForumMockMvc;

    private Forum forum;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ForumResource forumResource = new ForumResource(forumService);
        this.restForumMockMvc = MockMvcBuilders.standaloneSetup(forumResource)
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
    public static Forum createEntity(EntityManager em) {
        Forum forum = new Forum()
            .forumName(DEFAULT_FORUM_NAME)
            .forumImg(DEFAULT_FORUM_IMG)
            .forunIntroduce(DEFAULT_FORUN_INTRODUCE)
            .reservedOne(DEFAULT_RESERVED_ONE)
            .reservedTwo(DEFAULT_RESERVED_TWO);
        return forum;
    }

    @Before
    public void initTest() {
        forum = createEntity(em);
    }

    @Test
    @Transactional
    public void createForum() throws Exception {
        int databaseSizeBeforeCreate = forumRepository.findAll().size();

        // Create the Forum
        ForumDTO forumDTO = forumMapper.toDto(forum);
        restForumMockMvc.perform(post("/api/forums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forumDTO)))
            .andExpect(status().isCreated());

        // Validate the Forum in the database
        List<Forum> forumList = forumRepository.findAll();
        assertThat(forumList).hasSize(databaseSizeBeforeCreate + 1);
        Forum testForum = forumList.get(forumList.size() - 1);
        assertThat(testForum.getForumName()).isEqualTo(DEFAULT_FORUM_NAME);
        assertThat(testForum.getForumImg()).isEqualTo(DEFAULT_FORUM_IMG);
        assertThat(testForum.getForunIntroduce()).isEqualTo(DEFAULT_FORUN_INTRODUCE);
        assertThat(testForum.getReservedOne()).isEqualTo(DEFAULT_RESERVED_ONE);
        assertThat(testForum.getReservedTwo()).isEqualTo(DEFAULT_RESERVED_TWO);
    }

    @Test
    @Transactional
    public void createForumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = forumRepository.findAll().size();

        // Create the Forum with an existing ID
        forum.setId(1L);
        ForumDTO forumDTO = forumMapper.toDto(forum);

        // An entity with an existing ID cannot be created, so this API call must fail
        restForumMockMvc.perform(post("/api/forums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Forum in the database
        List<Forum> forumList = forumRepository.findAll();
        assertThat(forumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllForums() throws Exception {
        // Initialize the database
        forumRepository.saveAndFlush(forum);

        // Get all the forumList
        restForumMockMvc.perform(get("/api/forums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(forum.getId().intValue())))
            .andExpect(jsonPath("$.[*].forumName").value(hasItem(DEFAULT_FORUM_NAME.toString())))
            .andExpect(jsonPath("$.[*].forumImg").value(hasItem(DEFAULT_FORUM_IMG.toString())))
            .andExpect(jsonPath("$.[*].forunIntroduce").value(hasItem(DEFAULT_FORUN_INTRODUCE.toString())))
            .andExpect(jsonPath("$.[*].reservedOne").value(hasItem(DEFAULT_RESERVED_ONE.toString())))
            .andExpect(jsonPath("$.[*].reservedTwo").value(hasItem(DEFAULT_RESERVED_TWO.toString())));
    }

    @Test
    @Transactional
    public void getForum() throws Exception {
        // Initialize the database
        forumRepository.saveAndFlush(forum);

        // Get the forum
        restForumMockMvc.perform(get("/api/forums/{id}", forum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(forum.getId().intValue()))
            .andExpect(jsonPath("$.forumName").value(DEFAULT_FORUM_NAME.toString()))
            .andExpect(jsonPath("$.forumImg").value(DEFAULT_FORUM_IMG.toString()))
            .andExpect(jsonPath("$.forunIntroduce").value(DEFAULT_FORUN_INTRODUCE.toString()))
            .andExpect(jsonPath("$.reservedOne").value(DEFAULT_RESERVED_ONE.toString()))
            .andExpect(jsonPath("$.reservedTwo").value(DEFAULT_RESERVED_TWO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingForum() throws Exception {
        // Get the forum
        restForumMockMvc.perform(get("/api/forums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateForum() throws Exception {
        // Initialize the database
        forumRepository.saveAndFlush(forum);
        int databaseSizeBeforeUpdate = forumRepository.findAll().size();

        // Update the forum
        Forum updatedForum = forumRepository.findOne(forum.getId());
        // Disconnect from session so that the updates on updatedForum are not directly saved in db
        em.detach(updatedForum);
        updatedForum
            .forumName(UPDATED_FORUM_NAME)
            .forumImg(UPDATED_FORUM_IMG)
            .forunIntroduce(UPDATED_FORUN_INTRODUCE)
            .reservedOne(UPDATED_RESERVED_ONE)
            .reservedTwo(UPDATED_RESERVED_TWO);
        ForumDTO forumDTO = forumMapper.toDto(updatedForum);

        restForumMockMvc.perform(put("/api/forums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forumDTO)))
            .andExpect(status().isOk());

        // Validate the Forum in the database
        List<Forum> forumList = forumRepository.findAll();
        assertThat(forumList).hasSize(databaseSizeBeforeUpdate);
        Forum testForum = forumList.get(forumList.size() - 1);
        assertThat(testForum.getForumName()).isEqualTo(UPDATED_FORUM_NAME);
        assertThat(testForum.getForumImg()).isEqualTo(UPDATED_FORUM_IMG);
        assertThat(testForum.getForunIntroduce()).isEqualTo(UPDATED_FORUN_INTRODUCE);
        assertThat(testForum.getReservedOne()).isEqualTo(UPDATED_RESERVED_ONE);
        assertThat(testForum.getReservedTwo()).isEqualTo(UPDATED_RESERVED_TWO);
    }

    @Test
    @Transactional
    public void updateNonExistingForum() throws Exception {
        int databaseSizeBeforeUpdate = forumRepository.findAll().size();

        // Create the Forum
        ForumDTO forumDTO = forumMapper.toDto(forum);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restForumMockMvc.perform(put("/api/forums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forumDTO)))
            .andExpect(status().isCreated());

        // Validate the Forum in the database
        List<Forum> forumList = forumRepository.findAll();
        assertThat(forumList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteForum() throws Exception {
        // Initialize the database
        forumRepository.saveAndFlush(forum);
        int databaseSizeBeforeDelete = forumRepository.findAll().size();

        // Get the forum
        restForumMockMvc.perform(delete("/api/forums/{id}", forum.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Forum> forumList = forumRepository.findAll();
        assertThat(forumList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Forum.class);
        Forum forum1 = new Forum();
        forum1.setId(1L);
        Forum forum2 = new Forum();
        forum2.setId(forum1.getId());
        assertThat(forum1).isEqualTo(forum2);
        forum2.setId(2L);
        assertThat(forum1).isNotEqualTo(forum2);
        forum1.setId(null);
        assertThat(forum1).isNotEqualTo(forum2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ForumDTO.class);
        ForumDTO forumDTO1 = new ForumDTO();
        forumDTO1.setId(1L);
        ForumDTO forumDTO2 = new ForumDTO();
        assertThat(forumDTO1).isNotEqualTo(forumDTO2);
        forumDTO2.setId(forumDTO1.getId());
        assertThat(forumDTO1).isEqualTo(forumDTO2);
        forumDTO2.setId(2L);
        assertThat(forumDTO1).isNotEqualTo(forumDTO2);
        forumDTO1.setId(null);
        assertThat(forumDTO1).isNotEqualTo(forumDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(forumMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(forumMapper.fromId(null)).isNull();
    }
}
