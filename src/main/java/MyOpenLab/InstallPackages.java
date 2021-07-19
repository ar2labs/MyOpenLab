package MyOpenLab;

import static VisualLogic.Tools.settings;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InstallPackages implements Runnable {

  public FormUpdate owner;

  InstallPackages(FormUpdate aThis) {
    this.owner = aThis;
  }

  @Override
  public void run() {
    try {
      try {

        Path myTempDir = Files.createTempDirectory("myopenlab_");

        for (MyTableRow row : owner.list1) {

          if (row.isSelected()) {
            String type = row.getType();
            String domain = settings.getRepository_domain();
            String source =
                domain
                    + "/repository/get_package.php?type="
                    + row.getType()
                    + "&package_name="
                    + row.getName();

            source = source.replaceAll(" ", "%20");

            String dest = myTempDir.toString() + "/" + row.getName() + ".zip";

            owner.log("download " + row.getName() + "/package.zip");

            try {
              Tools2.getPackageZip(source, dest, settings);

              String zipFilePath = dest;

              String destDir = owner.myopenlabpath + "/Elements/" + type + "/" + row.getName();

              owner.log("unzip " + row.getName());
              UnzipFiles unzipper = new UnzipFiles();

              unzipper.unzip(zipFilePath, destDir);
            } catch (Exception ex) {
              // Some errors occurred
              ex.printStackTrace();
            }
          }
        }

        Tools2.deleteFolder(new File(myTempDir.toString()));
      } catch (Exception ex) {
        Logger.getLogger(FormUpdate.class.getName()).log(Level.SEVERE, null, ex);
      }

      Thread.sleep(1000);

      owner.log("finished");
      owner.initTable1();
      owner.initTable2();
      owner.owner.reinitPackage();
    } catch (InterruptedException ex) {
      Logger.getLogger(InstallPackages.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
