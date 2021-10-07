/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Models.InfoModel;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class ProcessWeka {

    public ProcessWeka() {
    }
    
    public List<InfoModel> GetData() {
        CSVLoader loader = new CSVLoader();
        try {
            loader.setSource(new File("./Data/DataMini.csv"));
            Instances it = loader.getDataSet();
            it.setClassIndex(it.numAttributes() - 1);
            String[] options = new String[4];
            options[0] = "-C";
            options[1] = "0.1";
            options[2] = "-M";
            options[3] = "2";
            J48 mytree = new J48();
            mytree.setOptions(options);

            mytree.buildClassifier(it);
            
           
            System.out.println(mytree.toString());

        } catch (IOException ex) {
            Logger.getLogger(ProcessWeka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProcessWeka.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
