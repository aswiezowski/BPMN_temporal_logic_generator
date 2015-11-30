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

/**
 *
 * @author Adam Świeżowski, Jakub Piotrowski
 */
public class LogicFormula {
    private String left, inner1, inner2, right;
    private String body;

    public LogicFormula() {
    }

    public LogicFormula(String left, String inner1, String inner2, String rght, String body) {
        this.left = left;
        this.inner1 = inner1;
        this.inner2 = inner2;
        this.right = rght;
        this.body = body;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getInner1() {
        return inner1;
    }

    public void setInner1(String inner1) {
        this.inner1 = inner1;
    }

    public String getInner2() {
        return inner2;
    }

    public void setInner2(String inner2) {
        this.inner2 = inner2;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String rght) {
        this.right = rght;
    }

    public String getBody() {
        return body;
    }
    
    public void appendBody(String s){
        if(body==null){
            body = new String();
        }
        body+=s;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
    
    
    
    
}
