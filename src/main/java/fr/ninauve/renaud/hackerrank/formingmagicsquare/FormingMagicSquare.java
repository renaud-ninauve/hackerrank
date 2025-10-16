package fr.ninauve.renaud.hackerrank.formingmagicsquare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FormingMagicSquare {
  private static List<List<List<Integer>>> ALL_SQUARES = createAll();

  public static int formingMagicSquare(List<List<Integer>> s) {
    int minCost = Integer.MAX_VALUE;
    for (List<List<Integer>> magicSquare : ALL_SQUARES) {
      int cost = cost(s, magicSquare);
      minCost = Math.min(minCost, cost);
    }
    return minCost;
  }

  public static List<List<List<Integer>>> createAll() {
    List<List<List<Integer>>> all = new ArrayList<List<List<Integer>>>(9);
    List<List<Integer>> magic0 = FormingMagicSquare.magicSquareStartingAt(2, 1);
    all.add(magic0);
    all.add(transform(magic0, new VerticalMirorTransformation()));
    all.add(transform(magic0, new HorizontalMirorTransformation()));
    all.add(transform(magic0, new TopLeftMirorTransformation()));
    all.add(transform(magic0, new TopRightMirorTransformation()));
    all.add(transform(magic0, RotationTransformation.rotation90()));
    all.add(transform(magic0, RotationTransformation.rotation180()));
    all.add(transform(magic0, RotationTransformation.rotation270()));
    return all;
  }

  public static List<List<Integer>> transform(List<List<Integer>> matrix, Transformation transformation) {
    List<List<Integer>> result = init();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Coordinates target = transformation.transform(new Coordinates(i, j));
        set(result, target.i, target.j, get(matrix, i, j));
      }
    }
    return result;
  }

  public interface Transformation {
    Coordinates transform(Coordinates coordinates);
  }

  public static class VerticalMirorTransformation implements Transformation {
    @Override
    public Coordinates transform(Coordinates coordinates) {
      return new Coordinates(mirror(coordinates.i), coordinates.j);
    }
  }

  public static class HorizontalMirorTransformation implements Transformation {
    @Override
    public Coordinates transform(Coordinates coordinates) {
      return new Coordinates(coordinates.i, mirror(coordinates.j));
    }
  }

  public static class TopRightMirorTransformation implements Transformation {
    @Override
    public Coordinates transform(Coordinates coordinates) {
      if (coordinates.equals(new Coordinates(0, 0))) {
        return new Coordinates(2, 2);
      }
      if (coordinates.equals(new Coordinates(2, 2))) {
        return new Coordinates(0, 0);
      }
      if (coordinates.equals(new Coordinates(1, 0))) {
        return new Coordinates(2, 1);
      }
      if (coordinates.equals(new Coordinates(2, 1))) {
        return new Coordinates(1, 0);
      }
      if (coordinates.equals(new Coordinates(0, 1))) {
        return new Coordinates(1, 2);
      }
      if (coordinates.equals(new Coordinates(1, 2))) {
        return new Coordinates(0, 1);
      }
      return coordinates;
    }
  }

  public static class TopLeftMirorTransformation implements Transformation {
    @Override
    public Coordinates transform(Coordinates coordinates) {
      if (coordinates.equals(new Coordinates(1, 0))) {
        return new Coordinates(0, 1);
      }
      if (coordinates.equals(new Coordinates(0, 1))) {
        return new Coordinates(1, 0);
      }
      if (coordinates.equals(new Coordinates(2, 0))) {
        return new Coordinates(0, 2);
      }
      if (coordinates.equals(new Coordinates(0, 2))) {
        return new Coordinates(2, 0);
      }
      if (coordinates.equals(new Coordinates(2, 1))) {
        return new Coordinates(1, 2);
      }
      if (coordinates.equals(new Coordinates(1, 2))) {
        return new Coordinates(2, 1);
      }
      return coordinates;
    }
  }

  public static class RotationTransformation implements Transformation {
    private final Transformation rotation;

    public RotationTransformation(Transformation rotation) {
      this.rotation = rotation;
    }

    public static Transformation rotation90() {
      return new RotationTransformation(new Transformation() {
        @Override
        public Coordinates transform(Coordinates coordinates) {
          return new Coordinates(-coordinates.j, coordinates.i);
        }
      });
    }

    public static Transformation rotation180() {
      return new RotationTransformation(new Transformation() {
        @Override
        public Coordinates transform(Coordinates coordinates) {
          return new Coordinates(-coordinates.i, -coordinates.j);
        }
      });
    }

    public static Transformation rotation270() {
      return new RotationTransformation(new Transformation() {
        @Override
        public Coordinates transform(Coordinates coordinates) {
          return new Coordinates(coordinates.j, -coordinates.i);
        }
      });
    }

    @Override
    public Coordinates transform(Coordinates coordinates) {
      int iTranslated = coordinates.i - 1;
      int jTranslated = coordinates.j - 1;

      Coordinates rotated = rotation.transform(new Coordinates(iTranslated, jTranslated));

      return new Coordinates(
          rotated.i + 1,
          rotated.j + 1);
    }
  }

  public static class Coordinates {
    public int i;
    public int j;

    public Coordinates(int i, int j) {
      this.i = i;
      this.j = j;
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) return false;
      Coordinates that = (Coordinates) o;
      return i == that.i && j == that.j;
    }

    @Override
    public int hashCode() {
      return Objects.hash(i, j);
    }
  }

  public static int mirror(int value) {
    return -(value - 1) + 1;
  }

  public static List<List<Integer>> init() {
    List<List<Integer>> matrix = new ArrayList<List<Integer>>(3);
    for (int it = 0; it < 3; it++) {
      List<Integer> row = Arrays.asList(null, null, null);
      matrix.add(row);
    }
    return matrix;
  }

  public static List<List<Integer>> magicSquareStartingAt(int i0, int j0) {
    int value0 = 1;
    List<List<Integer>> magicSquare = new ArrayList<List<Integer>>(3);
    for (int it = 0; it < 3; it++) {
      List<Integer> row = Arrays.asList(null, null, null);
      magicSquare.add(row);
    }
    set(magicSquare, i0, j0, value0);
    int i = i0;
    int j = j0;
    int nextValue = nextValue(value0);
    while (nextValue != value0) {
      Integer currentValue = get(magicSquare, wrapCoordinate(i + 1), wrapCoordinate(j - 1));
      if (currentValue == null) {
        i = wrapCoordinate(i + 1);
        j = wrapCoordinate(j - 1);
      } else {
        i = wrapCoordinate(i - 1);
      }
      set(magicSquare, i, j, nextValue);
      nextValue = nextValue(nextValue);
    }
    return magicSquare;
  }

  private static int wrapCoordinate(int value) {
    if (value < 0) {
      return value + 3;
    }
    if (value >= 3) {
      return value - 3;
    }
    return value;
  }

  public static int nextValue(int value) {
    return value < 9 ? value + 1 : 10 - (value + 1) + 1;
  }

  public static Integer get(List<List<Integer>> s, int i, int j) {
    return s.get(j).get(i);
  }

  public static void set(List<List<Integer>> s, int i, int j, Integer value) {
    List<Integer> row = s.get(j);
    row.set(i, value);
  }

  public static int cost(List<List<Integer>> a, List<List<Integer>> b) {
    int result = 0;
    for (int j = 0; j < a.size(); j++) {
      for (int i = 0; i < a.get(0).size(); i++) {
        int valueA = get(a, i, j);
        int valueB = get(b, i, j);
        if (valueA != valueB) {
          result += Math.abs(valueA - valueB);
        }
      }
    }
    return result;
  }
}
