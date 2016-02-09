package com.rjeady.dpll;

public class Literal {
    public Atom atom;
    public boolean positive;

    public Literal(Atom atom, boolean positive) {
        this.atom = atom;
        this.positive = positive;
    }

    public Literal not() {
        return new Literal(atom, !positive);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Literal literal = (Literal) o;

        if (positive != literal.positive) return false;
        return atom != null ? atom.equals(literal.atom) : literal.atom == null;

    }

    @Override
    public int hashCode() {
        int result = atom != null ? atom.hashCode() : 0;
        result = 31 * result + (positive ? 1 : 0);
        return result;
    }
}
