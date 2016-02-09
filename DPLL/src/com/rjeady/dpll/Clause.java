package com.rjeady.dpll;

import java.text.ParseException;
import java.util.*;

public class Clause implements Iterable<Literal> {
    private HashMap<Atom, Boolean> atomMap;

    public Clause(List<Literal> literals) throws TautologicalClauseException {
        atomMap = new HashMap<>();
        for (Literal l : literals) {
            if (this.atomMap.put(l.atom, l.positive) != null) {
                // tautology.
                throw new TautologicalClauseException("Tautology in literal " + l.atom);
            }
        }
    }

    public Clause(Clause c) {
        atomMap = new HashMap<>(c.atomMap);
    }

    public boolean isEmpty() {
        return atomMap.isEmpty();
    }

    public boolean isUnit() {
        return atomMap.size() == 1;
    }

    public Literal first() {
        Map.Entry<Atom, Boolean> first = atomMap.entrySet().iterator().next();
        return new Literal(first.getKey(), first.getValue());
    }

    public void delete(Literal l) {
        atomMap.remove(l.atom);
    }

    public boolean contains(Literal l) {
        return Boolean.valueOf(l.positive).equals(atomMap.get(l.atom));
    }

    public boolean contains(Atom a) {
        return atomMap.containsKey(a);
    }

    public static Clause parse(String s) throws TautologicalClauseException, ParseException {
        // remove spaces
        s = s.replace(" ", "");
        // assume string is wrapped in { }, remove them
        s = s.replace("{","");
        s = s.replace("}", "");
        // split on commas
        String[] items = s.split(",");
        ArrayList<Literal> literals = new ArrayList<>(items.length);
        for (String t : items) {
            if (t.length() == 1)
                literals.add(new Literal(new Atom(t), true));
            else if (t.length() == 2 && (t.charAt(0) == '¬' || t.charAt(0) == '!'))
                literals.add(new Literal(new Atom(t.charAt(1)), false));
            else
                throw new ParseException("Could not parse literal: " + t, 0);
        }

        return new Clause(literals);
    }

    public static List<Clause> parse(String[] strings) throws TautologicalClauseException, ParseException {
        List<Clause> clauses = new ArrayList<>();
        for (String s : strings) {
            try {
                clauses.add(Clause.parse(s));
            } catch (TautologicalClauseException e) {
                throw new TautologicalClauseException(e.getMessage() + ", clause " + s, e);
            }
        }
        return clauses;
    }

    @Override
    public Iterator<Literal> iterator() {
        return new Iterator<Literal>() {
            private Iterator<Map.Entry<Atom, Boolean>> mapIterator = atomMap.entrySet().iterator();
            @Override
            public boolean hasNext() {
                return mapIterator.hasNext();
            }

            @Override
            public Literal next() {
                Map.Entry<Atom, Boolean> e = mapIterator.next();
                return new Literal(e.getKey(), e.getValue());
            }
        };
    }
}
