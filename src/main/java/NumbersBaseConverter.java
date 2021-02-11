/**
 * This utility class provides an API for conversion numbers form one radix to another.
 * This class is capable for converting numbers without floating part, as well as
 * with floating part. For the better explanation see documentations of methods:
 *
 * {@link #convertFloatNumberFromOneBaseToAnother(String, int, int)} - related for operations with numbers which contain floating numbers,
 * {@link #convertNumberFromOneBaseToAnother(String, int, int)} - related for operations with numbers which does NOT contain floating part
 *
 * @author Mikhail Polivakha
 * @since 11.02.2021
 */
public class NumbersBaseConverter {

    /**
     * Method that converts number with floating part from on radix to another. Both Radixes passed as parameters.
     * Number should be passed as string (see explanation) in parameter #numberYouWantToConvert documentation.
     *
     * @param numberYouWantToConvert - as the name suggests, number you want to convert as {@link String}, because
     *                               if number you want to convert actually has the radix more the 10, then obviously
     *                               in such a number without any prohibition can present letters in UpperCase, for
     *                               example 'A' or 'E', depends on initial number radix
     * @param initialNumberBase - radix of the first parameter, I mean radix of the number you want to convert
     * @param resultNumberBase - radix in which you want to convert passed number
     * @return
     */
    public static String convertFloatNumberFromOneBaseToAnother(String numberYouWantToConvert, int initialNumberBase, int resultNumberBase) {
        final String[] integerAndFloatPartOfNumber = numberYouWantToConvert.split("\\.");
        final String integerPart = integerAndFloatPartOfNumber[0];
        final String floatPart = integerAndFloatPartOfNumber[1];
        final String integerPartOfResultNumber = convertNumberFromOneBaseToAnother(integerPart, initialNumberBase, resultNumberBase);
        final String floatingPartOfResultNumber = convertFloatPartOfNumberFromOneRadixToAnother(floatPart, initialNumberBase, resultNumberBase);
        return integerPartOfResultNumber + "." + floatingPartOfResultNumber;
    }

    private static String convertFloatPartOfNumberFromOneRadixToAnother(String floatingPart, int initialNumberBase, int resultNumberBase) {
        String resultFloatPart;
        if (initialNumberBase == 10) {
            resultFloatPart = getFloatingPartOfNumberWithInitial10Radix(floatingPart, resultNumberBase);
        } else {
            String resultFloatPartAsIntegerIn10Radix = getResultFloatPartAsStringIn10Radix(floatingPart, initialNumberBase).split("\\.")[1];
            resultFloatPart = getFloatingPartOfNumberWithInitial10Radix(resultFloatPartAsIntegerIn10Radix, resultNumberBase);
        }
        return resultFloatPart;
    }

    private static String getResultFloatPartAsStringIn10Radix(String floatingPart, int initialNumberBase) {
        final char[] digitsOfNumberFloatingPartAsChars = floatingPart.toCharArray();
        float resultFloatPartAsIntegerIn10Radix = 0;
        for (int positionOfDigit = 0; positionOfDigit < digitsOfNumberFloatingPartAsChars.length; positionOfDigit++) {
            resultFloatPartAsIntegerIn10Radix += get10RadixValueFromSymbol(digitsOfNumberFloatingPartAsChars[positionOfDigit]) *
                    Math.pow(initialNumberBase, (-1) * (positionOfDigit + 1));
        }
        return String.valueOf(resultFloatPartAsIntegerIn10Radix);
    }

    private static String getFloatingPartOfNumberWithInitial10Radix(String floatingPart, int resultNumberBase) {
        StringBuilder resultFloatPart = new StringBuilder();
        int defaultNumberOfDigitsAfterDot = 7;
        Float floatingPartOfNumberForCalculations = Float.parseFloat("0." + floatingPart);
        for (int i = 0; i < defaultNumberOfDigitsAfterDot; i++) {
            floatingPartOfNumberForCalculations = floatingPartOfNumberForCalculations * resultNumberBase;
            resultFloatPart.append(getCharacterFromNumber(floatingPartOfNumberForCalculations.intValue(), resultNumberBase));
            floatingPartOfNumberForCalculations -= floatingPartOfNumberForCalculations.intValue();
            if (!doPassedFloatNumberHaveFloatPart(floatingPartOfNumberForCalculations)) break;
        }
        return resultFloatPart.toString();
    }

    private static boolean doPassedFloatNumberHaveFloatPart(Float resultNumberAfterOneMultiplyingIteration) {
        final String[] integerAndFloatPartOfPassedNumber = String.valueOf(resultNumberAfterOneMultiplyingIteration).split("\\.");
        final String floatPartOfPassedNumber = integerAndFloatPartOfPassedNumber[1];
        return isFirstDigitOfPassedStringNotEqualsToZero(floatPartOfPassedNumber) || isPassedFloatingPartContainsMoreThenOneDigit(floatPartOfPassedNumber);
    }

    private static boolean isFirstDigitOfPassedStringNotEqualsToZero(String floatPartOfPassedNumber) {
        return Character.getNumericValue(floatPartOfPassedNumber.charAt(0)) != 0;
    }

    private static boolean isPassedFloatingPartContainsMoreThenOneDigit(String floatPartOfPassedNumber) {
        return floatPartOfPassedNumber.length() != 1;
    }

    public static String convertNumberFromOneBaseToAnother(String numberYouWantToConvert, int initialNumberBase, int resultNumberBase) {
        if (resultNumberBase == 10) {
            return String.valueOf(get10BaseNumberFromPassedNumber(numberYouWantToConvert, initialNumberBase));
        } else {
            final Long inputNumberIn10Base = get10BaseNumberFromPassedNumber(numberYouWantToConvert, initialNumberBase);
            return convert10BaseNumberIntoNumberWithPassedBase(resultNumberBase, inputNumberIn10Base);
        }
    }

    private static String convert10BaseNumberIntoNumberWithPassedBase(int resultNumberBase, Long inputNumberIn10Base) {
        long dividerResult = inputNumberIn10Base;
        StringBuilder resultNumber = new StringBuilder();
        while (dividerResult > resultNumberBase) {
            char remainer = getCharacterFromNumber(dividerResult % resultNumberBase, resultNumberBase);
            dividerResult /= resultNumberBase;
            resultNumber.append(remainer);
        }
        return resultNumber.append(dividerResult).reverse().toString();
    }

    private static char getCharacterFromNumber(long number, int radix) {
        return Character.toUpperCase(Character.forDigit((int) number, radix));
    }
    public static Long get10BaseNumberFromPassedNumber(String numberYouWantToConvertTo10BaseAsString, int initialBaseOfTheInputNumber) {

        verifyNumberForPresenceIllegalSymbolsInSuchRadixAndIfSoThrowException(numberYouWantToConvertTo10BaseAsString, initialBaseOfTheInputNumber);

        if (initialBaseOfTheInputNumber > 10) {
            return calculate10BasedLongNumberFromNumberWhichBaseIsHigherThen10(numberYouWantToConvertTo10BaseAsString, initialBaseOfTheInputNumber);
        } else {
            return calculate10BasedLongNumberFromNumberWhichBaseIsLessOrEqualTo10(
                    Long.valueOf(numberYouWantToConvertTo10BaseAsString),
                    initialBaseOfTheInputNumber
            );
        }
    }

    private static void verifyNumberForPresenceIllegalSymbolsInSuchRadixAndIfSoThrowException(String numberYouWantToConvertTo10BaseAsString,
                                                                                              int initialBaseOfTheInputNumber) {
        final char[] charactersLegalInPassedRadix = buildCharactersAllowedInPassedBaseArray(initialBaseOfTheInputNumber);
        for (char symbolInNumber : numberYouWantToConvertTo10BaseAsString.toCharArray()) {
            boolean isSymbolInNumberLegal = false;
            for (char legalCharacter : charactersLegalInPassedRadix) {
                if (legalCharacter == symbolInNumber) {
                    isSymbolInNumberLegal = true;
                    break;
                }
            }
            if (!isSymbolInNumberLegal) {
                throwExceptionAboutPassedSymbolIsNotAllowedInPassedRadix(symbolInNumber, initialBaseOfTheInputNumber);
            }
        }
    }

    private static void throwNumberFormatExceptionWithMessageAboutInvalidSymbolInNumber(char invalidSymbol, int radixInWhichPassedSymbolIsInvalid) {
        throw new NumberFormatException("This is impossible, that number with radix '" + radixInWhichPassedSymbolIsInvalid + "' will contain " +
                                                " symbol: '" + invalidSymbol + "', and hence you see this exception message");
    }

    private static Long calculate10BasedLongNumberFromNumberWhichBaseIsHigherThen10(String numberYouWantToConvertTo10Base, int initialBaseOfTheInputNumber) {
        char[] charactersAllowedInInputNumberBase = buildCharactersAllowedInPassedBaseArray(initialBaseOfTheInputNumber);
        final char[] charactersOfNumberWeWantToConvert = numberYouWantToConvertTo10Base.toCharArray();
        long result10BaseNumber = 0L;
        for (int indexFromTheBeginning = 0; indexFromTheBeginning < charactersOfNumberWeWantToConvert.length; indexFromTheBeginning++) {
            final char currentSymbol = charactersOfNumberWeWantToConvert[charactersOfNumberWeWantToConvert.length - (indexFromTheBeginning + 1)];
            verifySymbolIsPresentInTheAllowedCharactersInThisBaseArrayOrElseThrowException(
                    charactersAllowedInInputNumberBase,
                    currentSymbol,
                    initialBaseOfTheInputNumber
            );
            int valueOfTheCurrentCharIn10Radix = get10RadixValueFromSymbol(currentSymbol);
            result10BaseNumber += valueOfTheCurrentCharIn10Radix * Math.pow(initialBaseOfTheInputNumber, indexFromTheBeginning);
        }
        return result10BaseNumber;
    }

    private static int get10RadixValueFromSymbol(char currentSymbol) {
        if (!Character.isDigit(currentSymbol)) {
            switch (currentSymbol) {
                case 'A':
                    return 10;
                case 'B':
                    return 11;
                case 'C':
                    return 12;
                case 'D':
                    return 13;
                case 'E':
                    return 14;
                case 'F':
                    return 15;
                default:
                    throwExceptionAboutNumberRadixIsHigherThen16(currentSymbol);
            }
        } else {
            return Character.getNumericValue(currentSymbol);
        }
        return 0;
    }

    private static void throwExceptionAboutNumberRadixIsHigherThen16(char currentSymbol) {
        throw new NumberFormatException("Symbol '" + currentSymbol + "' cannot be a part of an 16 or less radix number. " +
                                                "As mentioned in documentation, this library is working only with numbers less or equal then 16");
    }

    private static void verifySymbolIsPresentInTheAllowedCharactersInThisBaseArrayOrElseThrowException(
            char[] charactersAllowedInInputNumberBase,
            char currentSymbolToVerify,
            int initialBaseOfTheInputNumber) {
        boolean isPassedCharAllowed = false;
        for (char charInArrayWithAllowedSymbols : charactersAllowedInInputNumberBase) {
            if (isPassedCharAllowed) return;
            if (charInArrayWithAllowedSymbols == currentSymbolToVerify) isPassedCharAllowed = true;
        }
        if (!isPassedCharAllowed) {
            throwExceptionAboutPassedSymbolIsNotAllowedInPassedRadix(currentSymbolToVerify, initialBaseOfTheInputNumber);
        }
    }

    private static void throwExceptionAboutPassedSymbolIsNotAllowedInPassedRadix(char invalidSymbol, int radix) {
        throw new NumberFormatException("You passed a number with symbol '" + invalidSymbol + "', but " + "in number with radix '" +
                                                radix + "' such a symbol is not allowed");
    }

    private static char[] buildCharactersAllowedInPassedBaseArray(int initialBaseOfTheInputNumber) {
        char[] arrayOfCharactersToReturn = new char[initialBaseOfTheInputNumber];
        for (int i = 0; i < initialBaseOfTheInputNumber; i++) {
            arrayOfCharactersToReturn[i] = Character.toUpperCase(Character.forDigit(i, initialBaseOfTheInputNumber));
        }
        return arrayOfCharactersToReturn;
    }

    private static Long calculate10BasedLongNumberFromNumberWhichBaseIsLessOrEqualTo10(Long numberYouWantToConvertTo10Base, int inputNumberBase) {
        final String numberToConvertAsString = String.valueOf(numberYouWantToConvertTo10Base);
        final char[] digitsOfInputNumberAsCharArray = numberToConvertAsString.toCharArray();
        return calculate10BasedLongNumberFromCharDigitsArray(digitsOfInputNumberAsCharArray, inputNumberBase);
    }

    private static Long calculate10BasedLongNumberFromCharDigitsArray(char[] digits, int inputNumberBase) {
        long resultNumberToReturn = 0L;
        for (int indexOfDigitInNumberFromTheBeginning = 0; indexOfDigitInNumberFromTheBeginning < digits.length; indexOfDigitInNumberFromTheBeginning++) {
            int currentDigit = Character.getNumericValue(digits[digits.length - (indexOfDigitInNumberFromTheBeginning + 1)]);
             resultNumberToReturn += currentDigit * (int) Math.pow(inputNumberBase, indexOfDigitInNumberFromTheBeginning);
        }
        return resultNumberToReturn;
    }
}
