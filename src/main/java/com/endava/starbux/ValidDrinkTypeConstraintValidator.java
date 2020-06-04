package com.endava.starbux;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidDrinkTypeConstraintValidator implements ConstraintValidator<ValidDrinkType, String> {
   public void initialize(ValidDrinkType constraint) {
   }

   public boolean isValid(String value, ConstraintValidatorContext context) {
      try {
         DrinkType.valueOf(value);
         return true;
      } catch (Exception e) {
         context.buildConstraintViolationWithTemplate("Unexpected drinkType value: " + value);
         return false;
      }
   }
}
