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
package pl.edu.agh.kis.core.utilities;

import java.awt.Color;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author Jakub Piotrowski
 */
public class ColorStyledDocument extends DefaultStyledDocument {

    private static final String LOGIC_FORMULAS_NAME = "sequence|parallel_split|synchronization|exclusive_choice|simple_merge|multiple_choice|multiple_merge|discriminator|synchronizing_merge|implicit_termination";
    private static final String PATTERNS_NAME = "sequence|synchronization|simple_merge|multiple_choice|parallel_split|exclusive_choice|implicit_termination|multiple_merge|discriminator|synchronizing_merge";
    private final StyleContext cont = StyleContext.getDefaultStyleContext();
    private final AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
    private final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
    private String coloredFormulas;

    public ColorStyledDocument(boolean isLogicFormulas) {
        if(isLogicFormulas) {
            coloredFormulas = LOGIC_FORMULAS_NAME;
        } else {
            coloredFormulas = PATTERNS_NAME;
        }
    }
    @Override
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        super.insertString(offset, str, a);

        String text = getText(0, getLength());
        int before = findLastNonWordChar(text, offset);
        if (before < 0) {
            before = 0;
        }
        int after = findFirstNonWordChar(text, offset + str.length());
        int wordL = before;
        int wordR = before;

        while (wordR <= after) {
            if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                if (text.toLowerCase().substring(wordL, wordR).matches("(\\W)*(" + coloredFormulas + ")")) {
                    setCharacterAttributes(wordL, wordR - wordL, attr, false);
                } else {
                    setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
                }
                wordL = wordR;
            }
            wordR++;
        }
    }

    @Override
    public void remove(int offs, int len) throws BadLocationException {
        super.remove(offs, len);

        String text = getText(0, getLength());
        int before = findLastNonWordChar(text, offs);
        if (before < 0) {
            before = 0;
        }
        int after = findFirstNonWordChar(text, offs);

        if (text.toLowerCase().substring(before, after).matches("(\\W)*(" + coloredFormulas + ")")) {
            setCharacterAttributes(before, after - before, attr, false);
        } else {
            setCharacterAttributes(before, after - before, attrBlack, false);
        }
    }

    private int findLastNonWordChar(String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

    private int findFirstNonWordChar(String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }
}
