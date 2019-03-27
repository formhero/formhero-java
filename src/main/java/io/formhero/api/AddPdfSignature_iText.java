package io.formhero.api;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by ryan.kimber on 2018-03-13.
 */
public class AddPdfSignature_iText {

    public static final String SRC = "/Users/ryankimber/td-sample.pdf";
    public static final String INTERMEDIATE = "/Users/ryankimber/signature-added-intermediate.pdf";
    public static final String DEST = "/Users/ryankimber/signature-output-itext-5.5.11-final.pdf";

    public static void main(String[] args) throws IOException, DocumentException {
        AddPdfSignature_iText test = new AddPdfSignature_iText();
        test.addSignatureToPdf(358, 150, 565, 85);
        test.manipulatePdf(INTERMEDIATE, DEST);
    }

    public void addSignatureToPdf(int x1, int y1, int x2, int y2) throws IOException, DocumentException
    {
        Document document = new Document();
        PdfReader reader = new PdfReader(SRC);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(INTERMEDIATE));
        writer.setPdfVersion(PdfWriter.VERSION_1_7);
        writer.setTagged();
        writer.setViewerPreferences(PdfWriter.DisplayDocTitle);
        document.addLanguage("en-US");
        document.addTitle("FormHero's TD Signature Test");
        writer.createXmpMetadata();
        document.open();
        PdfImportedPage page = writer.getImportedPage(reader, 1);
        PdfContentByte cb = writer.getDirectContent();
        document.newPage();
        cb.addTemplate(page, 0, 0);
        //PdfStamper stp = new PdfStamper(pdf, new FileOutputStream(INTERMEDIATE));
        PdfFormField signature = PdfFormField.createSignature(writer);
        Rectangle signatureBox = new Rectangle(x1, y1, x2, y2);
        signature.setWidget(signatureBox, PdfAnnotation.HIGHLIGHT_INVERT);
        signature.setFieldName("signature-1");
        //signature.setFlags(PdfAnnotation.FLAGS_PRINT);

        writer.addAnnotation(signature);
        //PdfFormField sig = document.stp.addSignature("signature-1", 1, x1, y1, x2, y2);
        signature.setAccessibleAttribute(PdfName.ALTERNATE, new PdfString("Ryan Signature Field"));
        signature.put(PdfName.ALT, new PdfString("signature-1-alt-tag"));

        document.close();
        writer.close();
        reader.close();
    }

    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfDictionary catalog = reader.getCatalog();
        PdfDictionary structTreeRoot = catalog.getAsDict(PdfName.ACROFORM);
        manipulate(structTreeRoot);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        stamper.close();
    }

    public void manipulate(PdfDictionary element) {
        if (element == null)
            return;
        if (PdfName.ACROFORM.equals(element.get(PdfName.FIELDS))) {
            manipulate(element);
        }
        if(PdfName.SIG.equals(element.get(PdfName.NAME)))
        {
            element.put(PdfName.ALT, new PdfString("Figure without an Alt description"));
        }
        PdfArray kids = element.getAsArray(PdfName.FIELDS);
        if (kids == null) return;
        for (int i = 0; i < kids.size(); i++)
            manipulate(kids.getAsDict(i));
    }
}
