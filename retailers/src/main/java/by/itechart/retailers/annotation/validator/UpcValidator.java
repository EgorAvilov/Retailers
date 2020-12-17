package by.itechart.retailers.annotation.validator;

import by.itechart.retailers.annotation.Upc;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UpcValidator implements ConstraintValidator<Upc, Long> {
    @Override
    public boolean isValid(Long upc, ConstraintValidatorContext context) {
        final long LARGEST_POSSIBLE_UPC_CODE = 999999999999L;
        long codeValue = upc;
        if ((codeValue > 0) && (codeValue < LARGEST_POSSIBLE_UPC_CODE)) {
            int[] codeValues = new int[12];
            for (int i = codeValues.length - 1; i >= 0; i--) {
                codeValues[i] = (int) (codeValue % 10);
                codeValue /= 10;
            }
            int oddSum = 0;
            int evenSum = 0;
            for (int i = 0; i < codeValues.length; i += 2) {
                oddSum += codeValues[i];
            }
            for (int i = 1; i < codeValues.length - 1; i += 2) {
                evenSum += codeValues[i];
            }
            int calcCheckDigit = (10 - ((evenSum + 3 * oddSum) % 10)) % 10;
            return calcCheckDigit == codeValues[11];
        } else {
            return false;
        }
    }
}
