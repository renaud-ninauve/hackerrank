package fr.ninauve.renaud.hackerrank.formingmagicsquare;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FormingMagicSquareCostTest {

  @Test
  void cost() {
    int actual = FormingMagicSquare.cost(
        List.of(
            List.of(0, 2, 3),
            List.of(4, 0, 6),
            List.of(7, 8, 0)),
        List.of(
            List.of(1, 2, 3),
            List.of(4, 5, 6),
            List.of(7, 8, 9)));

    assertThat(actual).isEqualTo(3);
  }
}