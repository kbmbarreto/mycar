package com.lambdateam.mycar.h2.service;

import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.ComponentModel;
import com.lambdateam.mycar.repository.ComponentRepository;
import com.lambdateam.mycar.service.ComponentService;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.lambdateam.mycar.exception.ExpiredJwtException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class ComponentServiceTest {

    @Autowired
    private ComponentRepository repository;
    private ComponentService service;

    ComponentModel componentModel = new ComponentModel();

    @BeforeEach
    public void setup() {
        componentModel.setComponent("Filtro de óleo");

        service = new ComponentService(repository);
    }

    @Test
    public void shouldFindAllComponents() throws ExpiredJwtException {
        service.createComponent(componentModel);

        Iterable<ComponentModel> componentsList = service.findAllComponents();
        ComponentModel savedComponent = componentsList.iterator().next();

        assertThat(savedComponent).isNotNull();
    }

    @Test
    public void shouldAddComponent() throws ExpiredJwtException {

        service.createComponent(componentModel);

        Iterable<ComponentModel> componentsList = service.findAllComponents();
        ComponentModel savedComponent = componentsList.iterator().next();

        assertThat(componentModel).isEqualTo(savedComponent);
    }

    @Test
    public void shouldUpdateComponent() throws ExpiredJwtException {
        ComponentModel savedComponent  = service.createComponent(componentModel);
        savedComponent.setComponent("Óleo de motor");

        service.updateComponent(savedComponent.getId(), savedComponent);
        ComponentModel foundComponent = service.findComponentById(savedComponent.getId());

        assertThat(foundComponent.getComponent()).isEqualTo("Óleo de motor");
    }

    @Test
    public void shouldDeleteComponent() {
        assertThrows(NotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                ComponentModel savedComponent  = service.createComponent(componentModel);

                service.deleteComponentById(savedComponent.getId());
                ComponentModel foundComponent = service.findComponentById(savedComponent.getId());

                assertThat(foundComponent).isNull();
            }
        });
    }

    @Test
    public void shouldFindComponentById() throws ExpiredJwtException {
        ComponentModel savedComponent  = service.createComponent(componentModel);

        ComponentModel foundComponent = service.findComponentById(savedComponent.getId());
        assertThat(foundComponent).isNotNull();
    }

    @Test
    public void shouldNotFindComponentById() {
        assertThrows(NotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                ComponentModel foundComponent = service.findComponentById(Long.getLong("9999", 9999));
                assertThat(foundComponent).isNull();
            }
        });

    }
}