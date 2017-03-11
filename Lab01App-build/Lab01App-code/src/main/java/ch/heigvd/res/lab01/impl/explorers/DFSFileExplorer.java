package ch.heigvd.res.lab01.impl.explorers;

import ch.heigvd.res.lab01.interfaces.IFileExplorer;
import ch.heigvd.res.lab01.interfaces.IFileVisitor;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 *
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {

    vistor.visit(rootDirectory);

    if (rootDirectory.isDirectory()) {

      File[] fileList = rootDirectory.listFiles();

      //gotta sort alphabetically, yet the files got to arrive first
      Arrays.sort(fileList, new Comparator<File>() {
        @Override
        public int compare(File file, File t1) {
          if(file.isDirectory() && t1.isFile()) return 1;
          if (file.isFile() && t1.isDirectory()) return -1;
          return file.getName().compareTo(t1.getName());
        }
      });

      for (File file : fileList) {
        explore(file, vistor);
      }
    }
  }
}
