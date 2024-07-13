package com.ustsinau.chapter2_2.repository.impl;

import com.ustsinau.chapter2_2.models.Label;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JdbcLabelRepositoryImplTest {


    @Mock
    private JdbcLabelRepositoryImpl mockJdbcLabelRepositoryImpl;
//    @InjectMocks
//    private JdbcLabelRepository jdbcLabelRepository;
    Label label1 = new Label("Test 1");
    Label label2 = new Label("Test 2");

    @BeforeEach
    void prepare() {
        //   System.out.println("BeforeEach: " + this);
        //   MockitoAnnotations.openMocks(this);
        //     mockJdbcLabelRepository = Mockito.mock(JdbcLabelRepository.class);
        //   label = new Label();
        //    label.setName("Test");

    }

    @Test
    void testCreate() {

        mockJdbcLabelRepositoryImpl.create(label1);
        mockJdbcLabelRepositoryImpl.create(label2);


        verify(mockJdbcLabelRepositoryImpl).create(label1);
        verify(mockJdbcLabelRepositoryImpl, times(2)).create(Mockito.any(Label.class));
        verify(mockJdbcLabelRepositoryImpl, never()).update(label1);
    }


    @Test
    void update() {

        mockJdbcLabelRepositoryImpl.update(label1);

        verify(mockJdbcLabelRepositoryImpl).update(label1);
    }

    //
    @Test
    void delete() {
        mockJdbcLabelRepositoryImpl.delete(1l);

        verify(mockJdbcLabelRepositoryImpl).delete(1l);

    }

    @Test
    void testGetById() {
        // возврат при любых значениях аргумента
        when(mockJdbcLabelRepositoryImpl.getById(Mockito.anyLong())).thenReturn(label1);  // всегда возвращает label
        Label label3 = mockJdbcLabelRepositoryImpl.getById(1l);
        assertEquals(label1, label3);

        // возврат для конкректных аргументов
        when(mockJdbcLabelRepositoryImpl.getById(Mockito.eq(1l))).thenReturn(label1);
        Label label4 = mockJdbcLabelRepositoryImpl.getById(1l);
        assertEquals(label1, label4);
        // верификация - был ли вызван метод с определенными аргументами
        mockJdbcLabelRepositoryImpl.getById(3l);
        verify(mockJdbcLabelRepositoryImpl).getById(Mockito.eq(3l));
    }

    @Test
    void testGetAll() {
        List<Label> labels = new ArrayList<>();
        labels.add(label1);
        labels.add(label2);

        assertEquals(2, labels.size());
        assertNotNull(labels);
        assertEquals(false, labels.isEmpty());

        var labels2 = labels;
        given(mockJdbcLabelRepositoryImpl.getAll()).willReturn(labels);
        mockJdbcLabelRepositoryImpl.getAll();
        assertEquals(labels2, labels);


        mockJdbcLabelRepositoryImpl.getAll();
        verify(mockJdbcLabelRepositoryImpl, times(2)).getAll();


    }


    @AfterEach
    void afterEach() {
        //    System.out.println("After each: " + this);
    }
}