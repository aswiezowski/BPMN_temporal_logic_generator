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

import pl.edu.agh.kis.exceptions.DuplicateDefinitionException;
import pl.edu.agh.kis.exceptions.BodyArgumentsException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pl.edu.agh.kis.exceptions.BadHeaderException;

/**
 *
 * @author Adam Świeżowski, Jakub Piotrowski
 */
public class TemporalLogicParser {

    private final static Map<StructNodeType, Pattern> PATTERNS;
    private final static Pattern whiteChars = Pattern.compile("\\s*");

    private enum State {

        HEADER, BODY
    }

    static {
        PATTERNS = new HashMap<>();
        String args2 = " *\\( *(\\w+) *, *(\\w+) *\\) *: *";
        String args3 = " *\\( *(\\w+) *, *(\\w+) *, *(\\w+) *\\) *: *";
        String args4 = " *\\( *(\\w+) *, *(\\w+) *, *(\\w+) *, *(\\w+) *\\) *: *";
        PATTERNS.put(StructNodeType.SYNCHRONIZING_MERGE, Pattern.compile(StructNodeType.SYNCHRONIZING_MERGE.toString() + args4, Pattern.CASE_INSENSITIVE));
        PATTERNS.put(StructNodeType.DISCRIMINATOR, Pattern.compile(StructNodeType.DISCRIMINATOR.toString() + args4, Pattern.CASE_INSENSITIVE));
        PATTERNS.put(StructNodeType.MULTIPLE_MERGE, Pattern.compile(StructNodeType.MULTIPLE_MERGE.toString() + args4, Pattern.CASE_INSENSITIVE));
        PATTERNS.put(StructNodeType.IMPLICIT_TERMINATION, Pattern.compile(StructNodeType.IMPLICIT_TERMINATION.toString() + args3, Pattern.CASE_INSENSITIVE));
        PATTERNS.put(StructNodeType.MULTIPLE_CHOICE, Pattern.compile(StructNodeType.MULTIPLE_CHOICE.toString() + args3, Pattern.CASE_INSENSITIVE));
        PATTERNS.put(StructNodeType.EXCLUSIVE_CHOICE, Pattern.compile(StructNodeType.EXCLUSIVE_CHOICE.toString() + args3, Pattern.CASE_INSENSITIVE));
        PATTERNS.put(StructNodeType.PARALLEL_SPLIT, Pattern.compile(StructNodeType.PARALLEL_SPLIT.toString() + args3, Pattern.CASE_INSENSITIVE));
        PATTERNS.put(StructNodeType.SYNCHRONIZATION, Pattern.compile(StructNodeType.SYNCHRONIZATION.toString() + args3, Pattern.CASE_INSENSITIVE));
        PATTERNS.put(StructNodeType.SIMPLE_MERGE, Pattern.compile(StructNodeType.SIMPLE_MERGE.toString() + args3, Pattern.CASE_INSENSITIVE));
        PATTERNS.put(StructNodeType.SEQUENCE, Pattern.compile(StructNodeType.SEQUENCE.toString() + args2, Pattern.CASE_INSENSITIVE));
    }

    public TemporalLogicParser() {
    }

    private Entry<StructNodeType, LogicFormula> parseHeader(String line) {
        Entry<StructNodeType, LogicFormula> patternEntry = null;
        LogicFormula formula = null;
        for (Entry<StructNodeType, Pattern> entry : PATTERNS.entrySet()) {
            Matcher m = entry.getValue().matcher(line);
            if (m.matches()) {
                int argsCount = StructNodeType.getArgsCount(entry.getKey());
                if (argsCount == 2) {
                    formula = new LogicFormula(m.group(1), null, null, m.group(2), null);
                } else if (argsCount == 3) {
                    formula = new LogicFormula(m.group(1), m.group(2), null, m.group(3), null);
                } else if (argsCount == 4) {
                    formula = new LogicFormula(m.group(1), m.group(2), m.group(3), m.group(4), null);
                }
                patternEntry = new AbstractMap.SimpleEntry(entry.getKey(), formula);
                break;
            }
        }
        return patternEntry;
    }

    private boolean addBody(Entry<StructNodeType, LogicFormula> patternEntry, String line) throws BodyArgumentsException {
        boolean valid = true;
        int argCount = StructNodeType.getArgsCount(patternEntry.getKey());
        LogicFormula formula = patternEntry.getValue();
        Matcher m = whiteChars.matcher(line);
        if (m.matches()) {
            valid = false;
        }

        if (valid) {
            patternEntry.getValue().appendBody(line);
        }

        return valid;
    }

    public HashMap<StructNodeType, LogicFormula> parse(String temporalLogic) throws BodyArgumentsException, DuplicateDefinitionException, BadHeaderException {
        HashMap<StructNodeType, LogicFormula> logicDef = new HashMap<>();
        Scanner scanner = new Scanner(temporalLogic);
        Integer lineNumber = 0;
        State state = State.HEADER;
        Entry<StructNodeType, LogicFormula> patternEntry = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lineNumber++;
            if (line.startsWith("#")) {
                continue;
            }
            if (state == State.HEADER) {
                patternEntry = parseHeader(line);
                if (patternEntry == null) {
                    throw new BadHeaderException(lineNumber);
                }
                if (logicDef.containsKey(patternEntry.getKey())) {
                    throw new DuplicateDefinitionException(lineNumber);
                }
                state = State.BODY;
            } else if (state == State.BODY) {
                try {
                    boolean valid = addBody(patternEntry, line);
                    if (valid == false) {
                        logicDef.put(patternEntry.getKey(), patternEntry.getValue());
                        state = State.HEADER;
                    }
                } catch (BodyArgumentsException ex) {
                    ex.setLine(lineNumber);
                    throw ex;
                }
            }

        }
        scanner.close();

        return logicDef;
    }
}
