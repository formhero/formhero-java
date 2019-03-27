package io.formhero.api;

import com.pdflib.pdflib;

/**
 * Created by ryan.kimber on 2018-03-14.
 */
public class AddPdfTagsWithPdfLib
{
    private static AddPdfTagsWithPdfLib singleton;
    private static pdflib pdfLib;

    private static pdflib getPdfLib() throws Exception {
        if(pdfLib != null) return pdfLib;
        else {
            pdfLib = new pdflib();
            return pdfLib;
        }
    }

    private static void addTagsToPdfFields(String inputPath, String outputPath) throws Exception
    {

    }


    public static void main(String[] rgs)
    {
        try {
            addTagsToPdfFields("/Users/ryankimber/td-sample.pdf", "/Users/ryankimber/pdf-lib-output.pdf");
        }
        catch(Throwable t) {
            t.printStackTrace();;
        }
    }
}
