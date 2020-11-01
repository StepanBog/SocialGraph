package ru.bogdanov.cursach.models;

public class Pair<L,R> {

    private final Integer left;
    private final Integer right;

    public Pair(Integer left, Integer right) {
        assert left != null;
        assert right != null;

        this.left = left;
        this.right = right;
    }

    public Integer getLeft() { return left; }
    public Integer getRight() { return right; }

    @Override
    public int hashCode() { return left.hashCode() ^ right.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair pairo = (Pair) o;
        return (this.left.equals(pairo.getLeft()) &&
                this.right.equals(pairo.getRight()) || (this.left.equals(pairo.getRight()) &&
                this.right.equals(pairo.getLeft())));
    }

}