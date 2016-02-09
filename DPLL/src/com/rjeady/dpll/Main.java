package com.rjeady.dpll;

import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws TautologicalClauseException {

        DPLL dpll = new DPLL();

        if (args.length == 0) {
            try {
                System.out.println("Running tests");
                System.out.println();

                String test1 = "{P,Q} {!Q} {!R}";
                System.out.println(test1);
                OutputResult(dpll.solve(Clause.parse(test1.split(" ")), new Model()));
                System.out.println("Expected result was model P => true, Q => false, R => false");
                System.out.println();

                String test2 = "{!Q,R} {!R,P} {!R,Q} {!P,Q,R} {P,Q} {!P,!Q}";
                System.out.println(test2);
                OutputResult(dpll.solve(Clause.parse(test2.split(" ")), new Model()));
                System.out.println("Expected result was contradiction.");
                System.out.println();

                String test3 = "{!P,!Q,R} {P,Q,R} {!R} {!P,Q} {!Q,P}";
                System.out.println(test3);
                OutputResult(dpll.solve(Clause.parse(test3.split(" ")), new Model()));
                System.out.println("Expected result was contradiction.");
                System.out.println();

                String test4 = "{!P,!R,Q} {!P,!Q,!R} {!P,R} {!R,P} {Q,P}";
                System.out.println(test4);
                OutputResult(dpll.solve(Clause.parse(test4.split(" ")), new Model()));
                System.out.println("Expected result was model P => false, Q => true, R => false");

            } catch (ParseException | TautologicalClauseException e) {
            }
        } else {
            try {
                OutputResult(dpll.solve(Clause.parse(args), new Model()));
            } catch (ParseException | TautologicalClauseException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }

    public static void OutputResult(Model model) {
        String output;
        if (model == null) {
            output = "contradiction found";
        }
        else if (model.size() == 0) {
            output = "universally valid";
        }
        else {
            output = "model found: " + model.toString();
        }
        System.out.println(output);
    }
}



