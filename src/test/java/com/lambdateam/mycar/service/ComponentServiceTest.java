package com.lambdateam.mycar.service;

import com.lambdateam.mycar.model.ComponentModel;
import com.lambdateam.mycar.repository.ComponentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lambdateam.mycar.exception.ExpiredJwtException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ComponentServiceTest {

    @Mock
    private ComponentRepository componentRepository;

    @InjectMocks
    private ComponentService underTest;


    @Test
    void canFindAllComponents() throws ExpiredJwtException {
        // when
        underTest.findAllComponents();
        // then
        verify(componentRepository).findAll();
    }

    @Test
    void canAddComponent() throws ExpiredJwtException {
        // given
        ComponentModel component = new ComponentModel(
                Long.getLong("9999", 9999),
                "Filtro de óleo"
        );

        // when
        underTest.createComponent(component);

        // then
        ArgumentCaptor<ComponentModel> componentDtoArgumentCaptor = ArgumentCaptor.forClass(
                ComponentModel.class
        );
        verify(componentRepository).save(componentDtoArgumentCaptor.capture());
        ComponentModel capturedComponent = componentDtoArgumentCaptor.getValue();

        assertThat(capturedComponent).isEqualTo(component);
    }
}