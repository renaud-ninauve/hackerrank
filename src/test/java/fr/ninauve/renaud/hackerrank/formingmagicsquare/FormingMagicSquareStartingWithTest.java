package fr.ninauve.renaud.hackerrank.formingmagicsquare;

import fr.ninauve.renaud.hackerrank.formingmagicsquare.FormingMagicSquare.RotationTransformation;
import fr.ninauve.renaud.hackerrank.formingmagicsquare.FormingMagicSquare.TopLeftMirorTransformation;
import fr.ninauve.renaud.hackerrank.formingmagicsquare.FormingMagicSquare.TopRightMirorTransformation;
import fr.ninauve.renaud.hackerrank.formingmagicsquare.FormingMagicSquare.VerticalMirorTransformation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FormingMagicSquareStartingWithTest {

  @ParameterizedTest
  @CsvSource(delimiterString = " -> ", value = {"1 -> 2", "2 -> 3", "8 -> 9", "9 -> 1"})
  void nextValue(int value, int nextValue) {
    assertThat(FormingMagicSquare.nextValue(value)).isEqualTo(nextValue);
  }

  @Test
  void create_2_1() {
    List<List<Integer>> actual = FormingMagicSquare.magicSquareStartingAt(2, 1);

    assertThat(actual.get(0)).containsExactly(2, 7, 6);
    assertThat(actual.get(1)).containsExactly(9, 5, 1);
    assertThat(actual.get(2)).containsExactly(4, 3, 8);
  }

  @Test
  void create_2_0() {
    List<List<Integer>> actual = FormingMagicSquare.magicSquareStartingAt(2, 0);

    assertThat(actual.get(0)).containsExactly(9, 5, 1);
    assertThat(actual.get(1)).containsExactly(4, 3, 8);
    assertThat(actual.get(2)).containsExactly(2, 7, 6);
  }

  @Test
  void create_0_1() {
    List<List<Integer>> actual = FormingMagicSquare.magicSquareStartingAt(0, 1);

    assertThat(actual.get(0)).containsExactly(6, 2, 7);
    assertThat(actual.get(1)).containsExactly(1, 9, 5);
    assertThat(actual.get(2)).containsExactly(8, 4, 3);
  }

  @Test
  void create_all() {
    List<List<List<Integer>>> actual = FormingMagicSquare.createAll();
    for (List<List<Integer>> actualSquare : actual) {
      assertTrue(isMagic(actualSquare));
    }
  }

  boolean isMagic(List<List<Integer>> matrix) {
    for (int i = 0; i < 3; i++) {
      int sum = FormingMagicSquare.get(matrix, i, 0) + FormingMagicSquare.get(matrix, i, 1) + FormingMagicSquare.get(matrix, i, 2);
      if (sum != 15) {
        return false;
      }
    }
    for (int j = 0; j < 3; j++) {
      int sum = FormingMagicSquare.get(matrix, 0, j) + FormingMagicSquare.get(matrix, 1, j) + FormingMagicSquare.get(matrix, 2, j);
      if (sum != 15) {
        return false;
      }
    }
    return true;
  }

  @ParameterizedTest
  @CsvSource(delimiterString = " -> ", value = {
      "0 -> 2",
      "1 -> 1",
      "2 -> 0"
  })
  void miror(int value, int expected) {
    assertThat(FormingMagicSquare.mirror(value))
        .isEqualTo(expected);
  }

  @Test
  void verticalMiror() {
    List<List<Integer>> matrix = List.of(
        List.of(1, 2, 3),
        List.of(4, 5, 6),
        List.of(7, 8, 9)
    );

    List<List<Integer>> actual = FormingMagicSquare.transform(matrix, new VerticalMirorTransformation());

    assertThat(actual.get(0)).containsExactly(3, 2, 1);
    assertThat(actual.get(1)).containsExactly(6, 5, 4);
    assertThat(actual.get(2)).containsExactly(9, 8, 7);
  }

  @Test
  void topRightMiror() {
    List<List<Integer>> matrix = List.of(
        List.of(1, 2, 3),
        List.of(4, 5, 6),
        List.of(7, 8, 9)
    );

    List<List<Integer>> actual = FormingMagicSquare.transform(matrix, new TopRightMirorTransformation());

    assertThat(actual.get(0)).containsExactly(9, 6, 3);
    assertThat(actual.get(1)).containsExactly(8, 5, 2);
    assertThat(actual.get(2)).containsExactly(7, 4, 1);
  }

  @Test
  void topLeftMiror() {
    List<List<Integer>> matrix = List.of(
        List.of(1, 2, 3),
        List.of(4, 5, 6),
        List.of(7, 8, 9)
    );

    List<List<Integer>> actual = FormingMagicSquare.transform(matrix, new TopLeftMirorTransformation());

    assertThat(actual.get(0)).containsExactly(1, 4, 7);
    assertThat(actual.get(1)).containsExactly(2, 5, 8);
    assertThat(actual.get(2)).containsExactly(3, 6, 9);
  }

  @Test
  void rotate_90() {
    List<List<Integer>> matrix = List.of(
        List.of(1, 2, 3),
        List.of(4, 5, 6),
        List.of(7, 8, 9)
    );

    List<List<Integer>> actual = FormingMagicSquare.transform(matrix, RotationTransformation.rotation90());

    assertThat(actual.get(0)).containsExactly(7, 4, 1);
    assertThat(actual.get(1)).containsExactly(8, 5, 2);
    assertThat(actual.get(2)).containsExactly(9, 6, 3);
  }

  @Test
  void rotate_180() {
    List<List<Integer>> matrix = List.of(
        List.of(1, 2, 3),
        List.of(4, 5, 6),
        List.of(7, 8, 9)
    );

    List<List<Integer>> actual = FormingMagicSquare.transform(matrix, RotationTransformation.rotation180());

    assertThat(actual.get(0)).containsExactly(9, 8, 7);
    assertThat(actual.get(1)).containsExactly(6, 5, 4);
    assertThat(actual.get(2)).containsExactly(3, 2, 1);
  }

  @Test
  void rotate_270() {
    List<List<Integer>> matrix = List.of(
        List.of(1, 2, 3),
        List.of(4, 5, 6),
        List.of(7, 8, 9)
    );

    List<List<Integer>> actual = FormingMagicSquare.transform(matrix, RotationTransformation.rotation270());

    assertThat(actual.get(0)).containsExactly(3, 6, 9);
    assertThat(actual.get(1)).containsExactly(2, 5, 8);
    assertThat(actual.get(2)).containsExactly(1, 4, 7);
  }
}