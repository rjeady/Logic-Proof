package com.rjeady.dpll;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Model {
    private Map<Atom, Boolean> map;

    public Model() {
        map = new HashMap<>();
    }

    private Model(Model m) {
        map = new HashMap<>(m.map);
    }

    public boolean truth(Atom a) {
        return map.get(a);
    }

    public void put(Atom a, boolean truth) {
        map.put(a, truth);
    }

    public Model plus(Atom a, boolean truth) {
        Model m = new Model(this);
        m.put(a, truth);
        return m;
    }

    public int size() {
        return map.size();
    }

    @Override
    public String toString() {

        StringJoiner sj = new StringJoiner(", ");

        for (Map.Entry<Atom, Boolean> e : map.entrySet()) {
            sj.add(String.format("%s => %b", e.getKey().name, e.getValue()));
        }

        return sj.toString();
    }
}
