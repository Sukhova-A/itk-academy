package com.anastasiia.itkacademy.controller;

public record ErrorResponse(int status, String error, String message, String path) { }