package com.training.web.rest;

import com.training.TrainingApp;

import com.training.domain.Orders;
import com.training.repository.OrdersRepository;
import com.training.service.OrdersService;
import com.training.service.dto.OrdersDTO;
import com.training.service.mapper.OrdersMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.training.web.rest.TestUtil.sameInstant;
import static com.training.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrdersResource REST controller.
 *
 * @see OrdersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrainingApp.class)
public class OrdersResourceIntTest {

    private static final Integer DEFAULT_ORDER_NUMBER = 1;
    private static final Integer UPDATED_ORDER_NUMBER = 2;

    private static final ZonedDateTime DEFAULT_DATE_OF_PAYMENT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_OF_PAYMENT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_PAY_THE_AMOUNT = 1;
    private static final Integer UPDATED_PAY_THE_AMOUNT = 2;

    private static final String DEFAULT_THE_DRAWEE = "AAAAAAAAAA";
    private static final String UPDATED_THE_DRAWEE = "BBBBBBBBBB";

    private static final String DEFAULT_PAY_FOR_COURSES = "AAAAAAAAAA";
    private static final String UPDATED_PAY_FOR_COURSES = "BBBBBBBBBB";

    private static final String DEFAULT_RESERVED_ONE = "AAAAAAAAAA";
    private static final String UPDATED_RESERVED_ONE = "BBBBBBBBBB";

    private static final String DEFAULT_RESERVED_TWO = "AAAAAAAAAA";
    private static final String UPDATED_RESERVED_TWO = "BBBBBBBBBB";

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrdersMockMvc;

    private Orders orders;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrdersResource ordersResource = new OrdersResource(ordersService);
        this.restOrdersMockMvc = MockMvcBuilders.standaloneSetup(ordersResource)
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
    public static Orders createEntity(EntityManager em) {
        Orders orders = new Orders()
            .orderNumber(DEFAULT_ORDER_NUMBER)
            .dateOfPayment(DEFAULT_DATE_OF_PAYMENT)
            .payTheAmount(DEFAULT_PAY_THE_AMOUNT)
            .theDrawee(DEFAULT_THE_DRAWEE)
            .payForCourses(DEFAULT_PAY_FOR_COURSES)
            .reservedOne(DEFAULT_RESERVED_ONE)
            .reservedTwo(DEFAULT_RESERVED_TWO);
        return orders;
    }

    @Before
    public void initTest() {
        orders = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrders() throws Exception {
        int databaseSizeBeforeCreate = ordersRepository.findAll().size();

        // Create the Orders
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);
        restOrdersMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isCreated());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeCreate + 1);
        Orders testOrders = ordersList.get(ordersList.size() - 1);
        assertThat(testOrders.getOrderNumber()).isEqualTo(DEFAULT_ORDER_NUMBER);
        assertThat(testOrders.getDateOfPayment()).isEqualTo(DEFAULT_DATE_OF_PAYMENT);
        assertThat(testOrders.getPayTheAmount()).isEqualTo(DEFAULT_PAY_THE_AMOUNT);
        assertThat(testOrders.getTheDrawee()).isEqualTo(DEFAULT_THE_DRAWEE);
        assertThat(testOrders.getPayForCourses()).isEqualTo(DEFAULT_PAY_FOR_COURSES);
        assertThat(testOrders.getReservedOne()).isEqualTo(DEFAULT_RESERVED_ONE);
        assertThat(testOrders.getReservedTwo()).isEqualTo(DEFAULT_RESERVED_TWO);
    }

    @Test
    @Transactional
    public void createOrdersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordersRepository.findAll().size();

        // Create the Orders with an existing ID
        orders.setId(1L);
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdersMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList
        restOrdersMockMvc.perform(get("/api/orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orders.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].dateOfPayment").value(hasItem(sameInstant(DEFAULT_DATE_OF_PAYMENT))))
            .andExpect(jsonPath("$.[*].payTheAmount").value(hasItem(DEFAULT_PAY_THE_AMOUNT)))
            .andExpect(jsonPath("$.[*].theDrawee").value(hasItem(DEFAULT_THE_DRAWEE.toString())))
            .andExpect(jsonPath("$.[*].payForCourses").value(hasItem(DEFAULT_PAY_FOR_COURSES.toString())))
            .andExpect(jsonPath("$.[*].reservedOne").value(hasItem(DEFAULT_RESERVED_ONE.toString())))
            .andExpect(jsonPath("$.[*].reservedTwo").value(hasItem(DEFAULT_RESERVED_TWO.toString())));
    }

    @Test
    @Transactional
    public void getOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get the orders
        restOrdersMockMvc.perform(get("/api/orders/{id}", orders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orders.getId().intValue()))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER))
            .andExpect(jsonPath("$.dateOfPayment").value(sameInstant(DEFAULT_DATE_OF_PAYMENT)))
            .andExpect(jsonPath("$.payTheAmount").value(DEFAULT_PAY_THE_AMOUNT))
            .andExpect(jsonPath("$.theDrawee").value(DEFAULT_THE_DRAWEE.toString()))
            .andExpect(jsonPath("$.payForCourses").value(DEFAULT_PAY_FOR_COURSES.toString()))
            .andExpect(jsonPath("$.reservedOne").value(DEFAULT_RESERVED_ONE.toString()))
            .andExpect(jsonPath("$.reservedTwo").value(DEFAULT_RESERVED_TWO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrders() throws Exception {
        // Get the orders
        restOrdersMockMvc.perform(get("/api/orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);
        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();

        // Update the orders
        Orders updatedOrders = ordersRepository.findOne(orders.getId());
        // Disconnect from session so that the updates on updatedOrders are not directly saved in db
        em.detach(updatedOrders);
        updatedOrders
            .orderNumber(UPDATED_ORDER_NUMBER)
            .dateOfPayment(UPDATED_DATE_OF_PAYMENT)
            .payTheAmount(UPDATED_PAY_THE_AMOUNT)
            .theDrawee(UPDATED_THE_DRAWEE)
            .payForCourses(UPDATED_PAY_FOR_COURSES)
            .reservedOne(UPDATED_RESERVED_ONE)
            .reservedTwo(UPDATED_RESERVED_TWO);
        OrdersDTO ordersDTO = ordersMapper.toDto(updatedOrders);

        restOrdersMockMvc.perform(put("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isOk());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate);
        Orders testOrders = ordersList.get(ordersList.size() - 1);
        assertThat(testOrders.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testOrders.getDateOfPayment()).isEqualTo(UPDATED_DATE_OF_PAYMENT);
        assertThat(testOrders.getPayTheAmount()).isEqualTo(UPDATED_PAY_THE_AMOUNT);
        assertThat(testOrders.getTheDrawee()).isEqualTo(UPDATED_THE_DRAWEE);
        assertThat(testOrders.getPayForCourses()).isEqualTo(UPDATED_PAY_FOR_COURSES);
        assertThat(testOrders.getReservedOne()).isEqualTo(UPDATED_RESERVED_ONE);
        assertThat(testOrders.getReservedTwo()).isEqualTo(UPDATED_RESERVED_TWO);
    }

    @Test
    @Transactional
    public void updateNonExistingOrders() throws Exception {
        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();

        // Create the Orders
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrdersMockMvc.perform(put("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isCreated());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);
        int databaseSizeBeforeDelete = ordersRepository.findAll().size();

        // Get the orders
        restOrdersMockMvc.perform(delete("/api/orders/{id}", orders.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Orders.class);
        Orders orders1 = new Orders();
        orders1.setId(1L);
        Orders orders2 = new Orders();
        orders2.setId(orders1.getId());
        assertThat(orders1).isEqualTo(orders2);
        orders2.setId(2L);
        assertThat(orders1).isNotEqualTo(orders2);
        orders1.setId(null);
        assertThat(orders1).isNotEqualTo(orders2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdersDTO.class);
        OrdersDTO ordersDTO1 = new OrdersDTO();
        ordersDTO1.setId(1L);
        OrdersDTO ordersDTO2 = new OrdersDTO();
        assertThat(ordersDTO1).isNotEqualTo(ordersDTO2);
        ordersDTO2.setId(ordersDTO1.getId());
        assertThat(ordersDTO1).isEqualTo(ordersDTO2);
        ordersDTO2.setId(2L);
        assertThat(ordersDTO1).isNotEqualTo(ordersDTO2);
        ordersDTO1.setId(null);
        assertThat(ordersDTO1).isNotEqualTo(ordersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ordersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ordersMapper.fromId(null)).isNull();
    }
}
