/*
 * Copyright (c) 2015, pl.edu.agh
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package pl.edu.agh.kis.core.data;

import java.util.Map;
import pl.edu.agh.kis.exceptions.BadPatternException;
import pl.edu.agh.kis.exceptions.NoLogicPatternDefinition;

/**
 *
 * @author Adam Świeżowski, Jakub Piotrowski
 */
public class StructNode implements Node {

    private Node left, right, inner1, inner2;
    private StructNodeType type;

    public StructNode(Node left, Node right, Node inner1, Node inner2, StructNodeType type) throws BadPatternException {
        this.left = left;
        setRight(right);
        this.inner1 = inner1;
        this.inner2 = inner2;
        this.type = type;
    }

    public StructNode() {
    }
    
    @Override
    public NodeType getType() {
        return NodeType.STRUCT;
    }

    @Override
    public String toString() {
        String name = null;
        //TODO: String formatting
        name = type.toString()+"("+left.toString()+(inner1==null?"":","+inner1.toString())+(inner2==null?"":","+inner2.toString())+","+right.toString()+")";
        return name;
    }
    
    @Override
    public String toTemporalLogic() throws NoLogicPatternDefinition{
        String temporalLogic = null;
        int argsCount = StructNodeType.getArgsCount(type);
        Map<StructNodeType, LogicFormula> logicDef = TemporalLogicDefinition.getLogicDef();
        LogicFormula def = logicDef.get(type);
        if(def==null){
            throw new NoLogicPatternDefinition("No "+type.toString()+" definition");
        }
        temporalLogic = def.getBody();
        if(argsCount >= 2 ){
            temporalLogic = temporalLogic.replaceAll(def.getLeft(), "("+left.toTemporalLogic()+")");
            temporalLogic = temporalLogic.replaceAll(def.getRight(), "("+right.toTemporalLogic()+")");
        }
        if(argsCount >= 3){
            temporalLogic = temporalLogic.replaceAll(def.getInner1(), "("+inner1.toTemporalLogic()+")");
        }
        if(argsCount >= 4){
            temporalLogic = temporalLogic.replaceAll(def.getInner2(), "("+inner2.toTemporalLogic()+")");
        }
        return temporalLogic;
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

    public void setRight(Node right) throws BadPatternException {
        if(right==null){
            throw new BadPatternException();
        }
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
