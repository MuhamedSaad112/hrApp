package com.hrapp.global.controller.errors;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class FieldErrorVM implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String objectName;

	private final String field;

	private final String message;


}