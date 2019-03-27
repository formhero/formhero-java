package io.formhero.api;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.documentinterchange.markedcontent.PDMarkedContent;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by ryan.kimber on 2018-03-13.
 */
public class AddPdfSignature {

//    private void addSignature(int x1, int y1, int x2, int y2) throws IOException, DocumentException
//    {
//        PDDocument pdf = PDDocument.load(new File("/Users/ryankimber/td-sample.pdf"));
//        PDDocumentCatalog pdCatalog = pdf.getDocumentCatalog();
//        PDAcroForm pdAcroForm = pdCatalog.getAcroForm();
//
//        if(pdAcroForm == null) {
//            pdAcroForm = new PDAcroForm(pdf);
//            PDMarkedContent markedContent = new PDMarkedContent(COSName.ACRO_FORM, pdAcroForm.getCOSObject());
//
//            // Add a new AcroForm and add that to the document
//            pdf.getDocumentCatalog().setAcroForm(pdAcroForm);
//            PDFont font = PDType1Font.HELVETICA;
//            PDResources resources = new PDResources();
//            resources.put(COSName.getPDFName("Helv"), font);
//            // Add and set the resources and default appearance at the form level
//            pdAcroForm.setDefaultResources(resources);
//            // Acrobat sets the font size on the form level to be
//            // auto sized as default. This is done by setting the font size to '0'
//            String defaultAppearanceString = "/Helv 0 Tf 0 g";
//            pdAcroForm.setDefaultAppearance(defaultAppearanceString);
//            // --- end of general AcroForm stuff ---
//        }
//
//        // Create empty signature field, it will get the name "Signature1"
//        PDSignatureField signatureField = new PDSignatureField(pdAcroForm);
//        signatureField.setAlternateFieldName("Signature1 OBJR");
//        PDAnnotationWidget widget = signatureField.getWidgets().get(0);
//        widget.setAnnotationName("Signature1 OBJR");
//        PDRectangle rect = new PDRectangle(x1, y1, x2, y2);
//        widget.setRectangle(rect);
//        PDPage page = pdf.getPage(0);
//        widget.setPage(page);
//        page.getAnnotations().add(widget);
//
//
//
//        pdAcroForm.getFields().add(signatureField);
//
//        pdf.save("/Users/ryankimber/fh-signable-sample.pdf");
//
//        for(PDField pdField : pdAcroForm.getFields()){
//            System.out.println(pdField.getFullyQualifiedName());
//        }
//
////        PdfStamper stp = new PdfStamper(pdf, new FileOutputStream("/Users/ryankimber/signature-output2.pdf"));
////        PdfFormField sig = stp.addSignature("signature-1", 1, x1, y1, x2, y2);
////        //sig.setWidget(new Rectangle(x1, y1, x2, y2), null);
////        sig.setFlags(PdfAnnotation.FLAGS_PRINT);
////        //sig.setFieldName("Signature1");
////        //sig.setPage(1);
////        sig.put(PdfName.ALT, new PdfString("signature-1-alt-tag"));
////        //stp.addAnnotation(sig, 1);
////        stp.close();
////        addAltTag(new PdfReader("/Users/ryankimber/signature-output2.pdf"), x1, y1, x2, y2, sig);
//        pdf.close();
//    }
//
//    private void addAltTag(PdfReader pdf, int x1, int y1, int x2, int y2, PdfFormField field) throws IOException, DocumentException
//    {
//        PdfDictionary catalog = pdf.getCatalog();
//        PdfDictionary structTreeRoot = catalog.getAsDict(PdfName.ACROFORM);
//        addAnnotationToAllSignatureFields(structTreeRoot);
//    }
//
//    public void addAnnotationToAllSignatureFields(PdfDictionary element) throws DocumentException {
//        if (element == null) return;
//        else if (PdfName.SIG.equals(element.get(PdfName.S))) {
//            element.put(PdfName.ALT, new PdfString(element.get(PdfName.NAME).toString()));
//        }
//        PdfArray kids = element.getAsArray(PdfName.FIELDS);
//        if (kids == null) return;
//        for (int i = 0; i < kids.size(); i++)
//            addAnnotationToAllSignatureFields(kids.getAsDict(i));
//    }
//
//    public static void main(String[] args)
//    {
//        AddPdfSignature addPdfSignature = new AddPdfSignature();
//
//        try {
//           // addPdfSignature.readFields("/Users/ryankimber/td-signable-sample.pdf");
//            long start = System.currentTimeMillis();
//            for (int i = 0; i < 1; i++) {
//                addPdfSignature.addSignature(358, 150, 565, 85);
//                if(i % 50 == 0) System.out.println("Finished " + i + " documents");
//            }
//
//            System.out.println("1000 iterations completed in " + (System.currentTimeMillis() - start) + "ms");
//            //addPdfSignature.readFields("/Users/ryankimber/signature-output.pdf");
//        }catch(Throwable t)
//        {
//            t.printStackTrace();
//        }
//    }


}
