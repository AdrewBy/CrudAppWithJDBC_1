package com.ustsinau.chapter2_2.service.impl;

import com.ustsinau.chapter2_2.models.Label;
import com.ustsinau.chapter2_2.repository.LabelRepository;
import com.ustsinau.chapter2_2.repository.impl.JdbcLabelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LabelServiceImplTest {

    @Mock
    private LabelRepository labelRepository = new JdbcLabelRepository();

    @InjectMocks
    private LabelServiceImpl labelService ;

    private Label label;
    private String name;
    private long id;

    @BeforeEach
    void setUp() {
        id = 1L;
        name = "Test Label";
        label = new Label(id, name);
    }

    @Test
    void createLabel() {
        // Мокируем метод create
        when(labelRepository.create(any(Label.class))).thenReturn(label);

        // Вызываем метод, который тестируем
        Label result = labelService.createLabel(name);

        // Проверяем результат
        assertEquals(label, result);

        // Проверяем, что метод create был вызван один раз с любым объектом Label
        verify(labelRepository, times(1)).create(any(Label.class));
    }


}
