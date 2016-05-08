package com.github.jsha1.minesweeper.common;

/**
 * A basic 2-tuple class (similar to apache commons Pair).
 * Adding this to avoid dependency on external libraries
 *
 * @param <S>
 * @param <T>
 */
public class Pair<S, T> {
    private S x;
    private T y;

    public Pair(S x, T y) {
        this.x = x;
        this.y = y;
    }

    public S getX() {
        return x;
    }

    public T getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        if (x != null ? !x.equals(pair.x) : pair.x != null) return false;
        return y != null ? y.equals(pair.y) : pair.y == null;

    }

    @Override
    public int hashCode() {
        int result = x != null ? x.hashCode() : 0;
        result = 31 * result + (y != null ? y.hashCode() : 0);
        return result;
    }
}