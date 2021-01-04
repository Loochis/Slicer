package Files;

import Standard.Main;
import Standard.Shapes.Shape3;
import Standard.Math3.Tri3;
import Standard.Math3.Vector3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ModelParser {


    public static Shape3 ModelFromFile(Main main, String fileName) {
        Scanner s = null; // Try-catch on s
        try {
            s = new Scanner(new File(Main.DIR + fileName)); // If this fails, s will remain null
        } catch (FileNotFoundException eFile) {

        }

        if (s == null)
            return null;

        List<Vector3> verts = new ArrayList<>();
        List<Vector3> normals = new ArrayList<>();
        List<Tri3> tris = new ArrayList<>();

        int lineNum = 0;
        while(s.hasNextLine()) {
            String currentLine = s.nextLine();
            lineNum++;
            String[] splitStr = currentLine.split(" ");

            if (splitStr[0].toLowerCase().matches("v")) {
                verts.add(new Vector3(Double.parseDouble(splitStr[1]), Double.parseDouble(splitStr[2]), Double.parseDouble(splitStr[3])));
            } else if (splitStr[0].toLowerCase().matches("vn")) {
                normals.add(new Vector3(Double.parseDouble(splitStr[1]), Double.parseDouble(splitStr[2]), Double.parseDouble(splitStr[3])));
            } else if (splitStr[0].toLowerCase().matches("f")) {
                Vector3[] v3 = verts.toArray(new Vector3[0]);
                try {
                    int[] vertIndex = new int[splitStr.length - 1];
                    for (int i = 0; i < vertIndex.length; i++) {
                        vertIndex[i] = Integer.parseInt(splitStr[i + 1].split("//")[0]) - 1;
                    }
                    for (int i = 0; i < vertIndex.length - 2; i++) {
                        tris.add(new Tri3(v3[vertIndex[0]], v3[vertIndex[i+1]], v3[vertIndex[i+2]]));
                    }

                    // Construct triangle

                    //tris.add(new Tri3(v3[n1 - 1], v3[n2 - 1], v3[n3 - 1]));

                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    try {
                        int i1 = Integer.parseInt(splitStr[1]);
                        int i2 = Integer.parseInt(splitStr[2]);
                        int i3 = Integer.parseInt(splitStr[3]);

                        // Construct triangle
                        tris.add(new Tri3(v3[i1 - 1], v3[i2 - 1], v3[i3 - 1]));

                    } catch (NumberFormatException e1) {
                        System.err.println("Could not parse line: " + lineNum);
                    }
                }
            }
        }

        return new Shape3(tris.toArray(new Tri3[0]), new Vector3(0, 0, 0), main.matrix3);
    }
}
