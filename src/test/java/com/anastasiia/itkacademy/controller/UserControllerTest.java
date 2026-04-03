package com.anastasiia.itkacademy.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import com.anastasiia.itkacademy.model.Order;
import com.anastasiia.itkacademy.model.OrderStatus;
import com.anastasiia.itkacademy.model.Product;
import com.anastasiia.itkacademy.model.User;
import com.anastasiia.itkacademy.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Получение всех пользователей без имейлов и заказов")
    void getAllUsers_ShouldReturnAllUsersWithoutOrders() throws Exception {
        //given
        User user1 = new User();
        user1.setId(UUID.randomUUID());
        user1.setName("Ololo");
        user1.setMail("ololo@example.com");

        User user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setName("Trololo");
        user2.setMail("trololo@example.com");

        //when
        when(userService.findAll()).thenReturn(List.of(user1, user2));

        //then
        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(user1.getId().toString()))
                .andExpect(jsonPath("$[0].name").value("Ololo"))
                .andExpect(jsonPath("$[0].mail").doesNotExist())
                .andExpect(jsonPath("$[0].orders").doesNotExist())
                .andExpect(jsonPath("$[1].name").value("Trololo"))
                .andExpect(jsonPath("$[1].mail").doesNotExist())
                .andExpect(jsonPath("$[1].orders").doesNotExist());
    }

    @Test
    @DisplayName("Получения пользователя по id вместе с заказами")
    void getUserById_ShouldReturnUserWithOrders() throws Exception {
        //given
        Product product = new Product();
        product.setUuid(UUID.randomUUID());
        product.setName("milk");
        product.setPrice(BigInteger.valueOf(120));

        Order order = new Order();
        order.setUuid(UUID.randomUUID());
        order.setStatus(OrderStatus.NEW);
        order.setTotalAmount(BigInteger.valueOf(120));
        order.setProducts(List.of(product));

        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        user.setName("Ololo");
        user.setMail("ololo@example.com");
        user.setOrders(List.of(order));
        //when
        when(userService.findById(userId)).thenReturn(user);

        //then
        mockMvc.perform(get("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.name").value("Ololo"))
                .andExpect(jsonPath("$.mail").value("ololo@example.com"))
                .andExpect(jsonPath("$.orders").isArray())
                .andExpect(jsonPath("$.orders", hasSize(1)))
                .andExpect(jsonPath("$.orders[0].status").value(OrderStatus.NEW.toString()))
                .andExpect(jsonPath("$.orders[0].totalAmount").value(120));
    }

    @Test
    @DisplayName("Ошибка при поиске несуществующего пользователя")
    void getUserById_ShouldThrowEntityNotFoundException() throws Exception {
        UUID id = UUID.randomUUID();
        String message = String.format("User with id %s not found", id);
        //when
        when(userService.findById(id))
                .thenThrow(new EntityNotFoundException(message));

        //then
        mockMvc.perform(get("/api/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value(message));
    }
}