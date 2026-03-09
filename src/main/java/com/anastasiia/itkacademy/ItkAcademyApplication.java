package com.anastasiia.itkacademy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.anastasiia.itkacademy.task7.Order;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItkAcademyApplication {

    public static void main(String[] args) {
        List<Order> orders = List.of( // Создание списка заказов с разными продуктами
                new Order("Laptop", 1200.0),
                new Order("Smartphone", 800.0),
                new Order("Laptop", 1500.0),
                new Order("Tablet", 500.0),
                new Order("Smartphone", 900.0),
                new Order("PC", 2000.0),
                new Order("Laptop", 1300.0),
                new Order("PC", 2500.0)
        );

        Map<String, List<Order>> groupByProduct = orders.stream() // Группировка заказов по продуктам
                .collect(Collectors.groupingBy(Order::getProduct));

        Map<String, Double> sumByProduct = groupByProduct.entrySet() // Считаем общую стоимость одинаковых продуктов
                .stream()
                .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue()
                                        .stream()
                                        .mapToDouble(Order::getCost).sum()));

        List<Map.Entry<String, Double>> sortedByCostDesc = sumByProduct.entrySet()// Сортировка продуктов по убыванию стоимости
                .stream()
                .sorted((i, j) -> j.getValue().compareTo(i.getValue()))
                .toList();

        List<Map.Entry<String, Double>> sortedByCostLimited = sortedByCostDesc.stream().limit(3).toList();

        sortedByCostLimited
                .forEach(i -> System.out.printf("Общая стоимость продуктов \"%s\" равна %.1f%n", i.getKey(), i.getValue()));
    }
}