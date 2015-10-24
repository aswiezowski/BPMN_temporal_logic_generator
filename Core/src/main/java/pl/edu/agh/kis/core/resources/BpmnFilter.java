/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.kis.core.resources;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Jakub Piotrowski
 */
public class BpmnFilter extends FileFilter{

    //Accept all directories and all bpmn files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(Utils.bpmn)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    //The description of this filter
    public String getDescription() {
        return "BPMN files";
    }
}
