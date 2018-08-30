package com.optc.optcdbmobile.data.database.filters.sql_syntax;

public class SyntaxBuilder {

    private Statement statement;

    private SyntaxBuilder() {
        statement = new Statement();
    }

    public static SyntaxBuilder create() {
        return new SyntaxBuilder();
    }


    public SyntaxBuilder select(String what) {
        statement.add(new Select(new GeneralString(what)));
        return this;
    }

    public SyntaxBuilder from(String from) {
        statement.add(new From(new GeneralString(from)));
        return this;
    }

    private SyntaxBuilder where(String variable, Predicate expression) {
        statement.add(new Where(variable, expression));
        return this;
    }

    public SyntaxBuilder whereEqualValue(String variable, String right) {
        return where(variable, new EqualPredicate(new GeneralString(right)));
    }

    public SyntaxBuilder whereEqualExpression(String variable, Statement statement) {
        GroupStatement groupStatement = new GroupStatement();
        groupStatement.add(statement);
        return where(variable, new EqualPredicate(groupStatement));
    }

    public SyntaxBuilder whereNotEqualValue(String variable, String right) {
        return where(variable, new NotEqualPredicate(new GeneralString(right)));
    }

    public SyntaxBuilder whereNotEqualExpression(String variable, Statement statement) {
        GroupStatement groupStatement = new GroupStatement();
        groupStatement.add(statement);
        return where(variable, new NotEqualPredicate(groupStatement));
    }

    public SyntaxBuilder whereLesserValue(String variable, String right) {
        return where(variable, new LesserPredicate(new GeneralString(right)));
    }

    public SyntaxBuilder whereLesserExpression(String variable, Expression right) {
        return where(variable, new LesserPredicate(right));
    }

    public SyntaxBuilder whereGreaterValue(String variable, String right) {
        return where(variable, new GreaterPredicate(new GeneralString(right)));
    }

    public SyntaxBuilder whereGreaterExpression(String variable, Expression right) {
        return where(variable, new GreaterPredicate(right));
    }

    public SyntaxBuilder whereIn(String variable, Statement statement) {
        GroupStatement groupStatement = new GroupStatement();
        groupStatement.add(statement);
        return where(variable, new InPredicate(groupStatement));
    }

    public SyntaxBuilder whereNotIn(String variable, Statement statement) {
        GroupStatement groupStatement = new GroupStatement();
        groupStatement.add(statement);
        return where(variable, new InPredicate(groupStatement));
    }

    public SyntaxBuilder whereExists(String variable, Statement statement) {
        GroupStatement groupStatement = new GroupStatement();
        groupStatement.add(statement);
        return where(variable, new ExistsPredicate(groupStatement));
    }

    public SyntaxBuilder whereNotExists(String variable, Statement statement) {
        GroupStatement groupStatement = new GroupStatement();
        groupStatement.add(statement);
        return where(variable, new NotPredicate(new ExistsPredicate(groupStatement)));
    }

    public SyntaxBuilder and(Expression expression) {
        AndConnector and = new AndConnector(expression);
        if (and.acceptNext(expression) && and.acceptPrev(statement.getLast())) {
            statement.add(and);
        } else {
            throw new IllegalStateException(String.format("%s can't connect:\nprev:%s\nnext:%s", AndConnector.class.getSimpleName(), statement.getLast().getClass().getSimpleName(), expression.getClass().getSimpleName()));
        }
        return this;
    }

    public SyntaxBuilder or(Expression expression) {
        OrConnector or = new OrConnector(expression);
        if (or.acceptNext(expression) && or.acceptPrev(statement.getLast())) {
            statement.add(or);
        } else {
            throw new IllegalStateException(String.format("%s can't connect:\nprev:%s\nnext:%s", OrConnector.class.getSimpleName(), statement.getLast().getClass().getSimpleName(), expression.getClass().getSimpleName()));
        }
        return this;
    }

    public String build() {
        return statement.getContent();
    }

    public Statement get() {
        return statement;
    }
}
