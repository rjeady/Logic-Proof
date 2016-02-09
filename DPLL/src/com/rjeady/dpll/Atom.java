package com.rjeady.dpll;

public class Atom {
    public String name;
    public Atom(String name) {
        this.name = name;
    }
    public Atom(char c) {
        this.name = String.valueOf(c);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Atom atom = (Atom) o;

        return name != null ? name.equals(atom.name) : atom.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
