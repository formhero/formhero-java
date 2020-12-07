package com.formhero.api;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

import com.teamdev.jxbrowser.browser.*;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.event.EngineCrashed;
import com.teamdev.jxbrowser.frame.Frame;
import com.teamdev.jxbrowser.js.*;
import com.teamdev.jxbrowser.navigation.Navigation;
import com.teamdev.jxbrowser.view.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Optional;


public class FormHeroFrameExample {

    private static JTextArea textArea = new JTextArea();
    private Frame formFrame;

    public static void main(String[] args) {

        FormHeroFrameExample fhfe = new FormHeroFrameExample();
        try {
            fhfe.initialize();
            HashMap<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("user.firstName", "Daniel");
            dataMap.put("user.lastName", "Harrison");
            fhfe.launchForm("formhero", "demos", "pdf-in-browser-demo", dataMap);
        }
        catch(IOException ioe)
        {
            System.getLogger("fhJavaExample").log(System.Logger.Level.ERROR, "Error during initilization: " + ioe.getMessage());
        }
    }

    private void initialize() throws IOException {
        // Creating and running Chromium engine
        Engine engine = Engine.newInstance(EngineOptions.newBuilder(HARDWARE_ACCELERATED).licenseKey("1BNDJDKIKHVJNHY1MYG9QVHN3NQWAE0ET2FGVHCHETFGQYTJUBVYEXV4K9GPCMWTAV354R").build());

        engine.on(EngineCrashed.class, event -> {
            // You should probably do something to notify the rest of your application that the browser crashed.
            int exitCode = event.exitCode();
        });

        final Browser browser = engine.newBrowser();
        BrowserView browserView = BrowserView.newInstance(browser);
        JFrame frame = new JFrame("FormHero Work Flow insided a Java JWT / JavaFX / SWT Application");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(1100, 700);
        frame.setLocation(25, 25);
        frame.setVisible(true);

        Navigation navigation = browser.navigation();

        //To Implementers: This URL should be parameterized in case we need to put a CNAME in front of it.
        navigation.loadUrlAndWait("https://media.formhero.com/java-api/index.html", Duration.ofSeconds(10));

        Optional<Frame> jsFrame = browser.mainFrame();
        //Once we're here -- we need to tell the browser to be able to invoke the launch form and get our callbacks...
        this.formFrame = jsFrame.get();
        JsObject window = this.formFrame.executeJavaScript("window");
        this.formFrame.executeJavaScript(getBridgingJavaScriptFromFile());
        window.putProperty("hostApplication", this);
    }

    private String getBridgingJavaScriptFromFile() throws IOException {
      return new String(Files.readAllBytes(Paths.get("src/main/resources/bridging-javascript.js")));
    }

    private void launchForm(String org, String team, String form, HashMap<String, Object> dataMap) {
        JsObject mapObject = this.formFrame.executeJavaScript("new Object()");
        dataMap.forEach((k, v) -> {
            mapObject.putProperty(k, v);
        });

        JsFunction launchFormFn = this.formFrame.executeJavaScript("launchFormFromJava");
        launchFormFn.invoke(null, org, team, form, mapObject);
    }

    @JsAccessible
    public void onFormProgress(JsObject status) {
        System.out.println("Status Message:" + status);
    }

    @JsAccessible
    public void onFormSubmission(JsObject status) {
        System.out.println("Submission happened:" + status);
    }

    @JsAccessible
    public void onFormCancel(JsObject status) {
        System.out.println("Cancel happened:" + status);
    }

    @JsAccessible void onClose(JsObject status) {
        System.out.println("Close happened:" + status);
    }
}
