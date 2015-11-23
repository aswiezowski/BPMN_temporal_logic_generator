/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.kis.core.data;

/**
 *
 * @author Adam Świeżowski <adam.swiezowski+projects [at] gmail [dot] com>
 */
public class StructNode implements Node {

    private Node left, right, inner1, inner2;
    private StructNodeType type;
    @Override
    public NodeType getType() {
        return NodeType.STRUCT;
    }

    @Override
    public String toString() {
        String name = null;

        return name;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getInner1() {
        return inner1;
    }

    public void setInner1(Node inner1) {
        this.inner1 = inner1;
    }

    public Node getInner2() {
        return inner2;
    }

    public void setInner2(Node inner2) {
        this.inner2 = inner2;
    }

    public StructNodeType getStructType() {
        return type;
    }

    public void setStructType(StructNodeType type) {
        this.type = type;
    }
    
    

}
