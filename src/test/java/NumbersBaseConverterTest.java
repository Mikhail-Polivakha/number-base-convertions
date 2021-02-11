import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class NumbersBaseConverterTest {

    @Nested
    class FloatingNumbersConversions {

        @Test
        public void first() {
            final String result = NumbersBaseConverter.convertFloatNumberFromOneBaseToAnother("6.1", 10, 2);
            Assertions.assertEquals(110.00011010, Float.parseFloat(result), 0.0001);
        }

        @Test
        void second() {
            final String result = NumbersBaseConverter.convertFloatNumberFromOneBaseToAnother("12.43", 10, 6);
            System.out.println(result);
            Assertions.assertEquals(20.23251403, Float.parseFloat(result), 0.0001);
        }

        @Test
        void third() {
            final String result = NumbersBaseConverter.convertFloatNumberFromOneBaseToAnother("12314.88382", 10, 14);
            System.out.println(result);
            final char[] expectedResultAsCharArray = "46B8.C532B871".toCharArray();
            for (int i = 0; i < 11; i++) {
                if (expectedResultAsCharArray[i] != result.charAt(i)) throw new AssertionError();
            }
        }

        @Test
        void fourth() {
            final String result = NumbersBaseConverter.convertFloatNumberFromOneBaseToAnother("782.0301", 10, 8);
            System.out.println(result);
            Assertions.assertEquals(1416.01732242, Float.parseFloat(result), 0.0001);
        }

        @Test
        void fifth() {
            final String result = NumbersBaseConverter.convertFloatNumberFromOneBaseToAnother("3237.1", 10, 4);
            System.out.println(result);
            Assertions.assertEquals(String.valueOf(302211.0121212), result);
        }

        @Test
        void sixth() {
            final String result = NumbersBaseConverter.convertFloatNumberFromOneBaseToAnother(
                    "123.E83", 16, 10);
            Assertions.assertEquals(291.90698242, Float.parseFloat(result), 0.0001);
        }

        @Test
        void seventh() {
            final String result = NumbersBaseConverter.convertFloatNumberFromOneBaseToAnother("437.B3A", 16, 10);
            Assertions.assertEquals(1079.70166016, Float.parseFloat(result), 0.0001);
        }

        @Test
        void eight() {
            final String result = NumbersBaseConverter.convertFloatNumberFromOneBaseToAnother("11723.AE33", 16, 10);
            Assertions.assertEquals(71459.68046570, Float.parseFloat(result), 0.1);
        }
    }

    @Nested
    class convertNumberFromOneBaseToAnother {

        @Test
        void first() {
            final String result = NumbersBaseConverter.convertNumberFromOneBaseToAnother("10000", 2, 10);
            Assertions.assertEquals(16, Integer.valueOf(result));
        }

        @Test
        void second() {
            Assertions.assertThrows(NumberFormatException.class,
                                    () -> NumbersBaseConverter.convertNumberFromOneBaseToAnother("1231", 2, 10));
        }

        @Test
        void third() {
            final String result = NumbersBaseConverter.convertNumberFromOneBaseToAnother("36F", 16, 7);
            Assertions.assertEquals(2364, Integer.parseInt(result));
        }

        @Test
        void fourth() {
            Assertions.assertThrows(NumberFormatException.class, () -> NumbersBaseConverter.convertNumberFromOneBaseToAnother("123B", 10, 4));
        }

        @Test
        void fifth() {
            final String result = NumbersBaseConverter.convertNumberFromOneBaseToAnother("123B", 13, 8);
            Assertions.assertEquals(5031, Integer.valueOf(result));
        }

        @Test
        void sixth() {
            final String result = NumbersBaseConverter.convertNumberFromOneBaseToAnother("44784", 10, 16);
            Assertions.assertEquals("AEF0", result);
        }
    }

    @Nested
    class get10BasedNumberTests {
        @DisplayName("Test with base less then 10 and number is correct")
        @Test
        public void firstTest() {
            final Long result = NumbersBaseConverter.get10BaseNumberFromPassedNumber(String.valueOf(1234), 7);
            Assertions.assertEquals(466, result);
        }

        @DisplayName("Test with base less then 10 and number is invalid for this base")
        @Test
        public void secondTest() {
            Assertions.assertThrows(
                    NumberFormatException.class,
                    () -> NumbersBaseConverter.get10BaseNumberFromPassedNumber("169", 7)
            );
        }

        @DisplayName("pass invalid number")
        @Test
        public void thirdTest() {
            Assertions.assertThrows(
                    NumberFormatException.class,
                    () -> NumbersBaseConverter.get10BaseNumberFromPassedNumber("123Q", 7)
            );
        }

        @Test
        public void fourthTest() {
            final Long result = NumbersBaseConverter.get10BaseNumberFromPassedNumber("36F", 16);
            Assertions.assertEquals(879, result);
        }
    }
}