public class FloatingNumberConverter {
    public static void main(String[] args) {
        final char[] chars = buildCharactersUsedInPassedBaseArray(13);
        for (char aChar : chars) {
            System.out.print(aChar +" ");
        }
    }
    private static char[] buildCharactersUsedInPassedBaseArray(int initialBaseOfTheInputNumber) {
        char[] arrayOfCharactersToReturn = new char[initialBaseOfTheInputNumber];
        for (int i = 0; i < initialBaseOfTheInputNumber; i++) {
            arrayOfCharactersToReturn[i] = Character.toUpperCase(Character.forDigit(i, initialBaseOfTheInputNumber));
        }
        return arrayOfCharactersToReturn;
    }
}
