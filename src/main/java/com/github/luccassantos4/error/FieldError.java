package com.github.luccassantos4.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class FieldError {
	private String field;
	private String message;
}
