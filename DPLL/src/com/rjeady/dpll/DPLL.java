package com.rjeady.dpll;

import java.util.*;

public class DPLL {

    public Model solve(List<Clause> clauses, Model model) {

        if (clauses.isEmpty())
            return model;

        for (Clause c : clauses)
            if (c.isEmpty())
                return null;

        // unit clauses
        Clause unit;
        while ((unit = getUnitClause(clauses)) != null) {
            Literal unitLiteral = unit.first();
            unitPropagate(unitLiteral, clauses);
            model.put(unitLiteral.atom, unitLiteral.positive);
        }

        // pure literals
        // find which literals are pure (we need to check every clause)
        HashMap<Atom, Boolean> pureLiterals = new HashMap<>();
        HashSet<Atom> impureAtoms = new HashSet<>();
        for (Clause c : clauses) {
            for (Literal l : c) {
                if (!impureAtoms.contains(l.atom) && Boolean.valueOf(!l.positive).equals(pureLiterals.put(l.atom, l.positive))) {
                    // this literal is not pure
                    pureLiterals.remove(l.atom);
                    impureAtoms.add(l.atom);
                }
            }
        }
        // delete all clauses containing them
        clauses = deleteClausesWithPureLiterals(pureLiterals.keySet(), clauses);

        // add pure literals to model
        for (Map.Entry<Atom, Boolean> e : pureLiterals.entrySet()) {
            model.put(e.getKey(), e.getValue());
        }

        // check for a solution
        if (clauses.isEmpty())
            return model;

        for (Clause c : clauses)
            if (c.isEmpty())
                return null;

        // only impure literals remain
        Atom caseAtom = impureAtoms.iterator().next();

        Model caseTrue = solve(unitPropagateCopy(new Literal(caseAtom, true), clauses), model.plus(caseAtom, true));
        if (caseTrue == null) {
            Model caseFalse = solve(unitPropagateCopy(new Literal(caseAtom, false), clauses), model.plus(caseAtom, false));
            return caseFalse;
        } else {
            return caseTrue;
        }
    }

    private List<Clause> deleteClausesWithPureLiterals(Set<Atom> pureLiterals, List<Clause> clauses) {
        List<Clause> newClauses = new ArrayList<>(clauses.size());

        for (Clause c : clauses) {
            boolean hasPureLiteral = false;
            for (Atom a : pureLiterals) {
                if (c.contains(a)) {
                    hasPureLiteral = true;
                    break;
                }
            }
            if (!hasPureLiteral)
                newClauses.add(c);
        }

        return newClauses;
    }

    private Clause getUnitClause(List<Clause> clauses) {
        for (Clause c : clauses)
            if (c.isUnit())
                return c;
        return null;
    }

    private List<Clause> unitPropagateCopy(Literal literal, List<Clause> clauses) {
        List<Clause> newClauses = new ArrayList<>(clauses.size());
        for (Clause c : clauses)
            newClauses.add(new Clause(c));
        unitPropagate(literal, newClauses);
        return newClauses;
    }

    private void unitPropagate(Literal literal, List<Clause> clauses) {
        for (Iterator<Clause> i = clauses.iterator(); i.hasNext(); ) {
            Clause c = i.next();
            if (c.contains(literal))
                i.remove();
            else
                c.delete(literal.not());
        }
    }
}
