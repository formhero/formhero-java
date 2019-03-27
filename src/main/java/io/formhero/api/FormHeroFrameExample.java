package io.formhero.api;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSObject;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.ConsoleEvent;
import com.teamdev.jxbrowser.chromium.events.ConsoleListener;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import com.teamdev.jxbrowser.chromium.events.ScriptContextListener;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Arrays;

public class FormHeroFrameExample implements ScriptContextListener {

    private Thread ourThread;
    private boolean isRunning = true;
    private static JTextArea textArea = new JTextArea();
    private static JFrame debugFrame = new JFrame( "Data Captured in the Java Application");

    public static void main(String[] args) {
        final Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame("FormHero Inside a Java JWT / JavaFX / SWT Application");

        textArea.setEditable(false);
        textArea.setFont(new Font("Serif", Font.BOLD, 14));
        textArea.setLineWrap(false);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.GRAY);
        JScrollPane debugPanel = new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        debugFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        debugPanel.setBackground(Color.WHITE);
        debugPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        debugPanel.setPreferredSize(new Dimension(400, 700));

        debugFrame.add(debugPanel, BorderLayout.CENTER);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(1100, 700);
        frame.setLocation(25, 25);
        frame.setVisible(true);

        debugFrame.setSize(450, 600);
        debugFrame.setLocation(1125, 25);

        FormHeroFrameExample gme = new FormHeroFrameExample();
        browser.addScriptContextListener(gme);

        //What this should do...
        //it should load a page that's waiting for us to tell it what page to go to
        //When the scripts are ready, we call formhero.launchForm(...), but this launchForm takes place directly
        //If the form requires authentication, we should make a call to create the session (which we could actually do from Java or javascript)
        browser.loadURL("https://formhero.formhero.cloud/#/start/sales-demos/shoppers-drug-mart-flu-shot");
    }

    public void onScriptContextCreated(ScriptContextEvent event) {
        final Browser browser = event.getBrowser();

        browser.addConsoleListener(new ConsoleListener() {
            public void onMessage(ConsoleEvent event) {
            System.out.println(event.getLevel() + ": " + event.getMessage());
            }
        });

        ourThread = new Thread(new Runnable() {
            public void run() {
                try
                {
                    ourThread.sleep(3000);
                    debugFrame.setVisible(true);
                    while(isRunning)
                    {
                        JSValue jsValue = browser.executeJavaScriptAndReturnValue("angular.element(document.body).injector().get('$rootScope').session");
                        //JSONObject
                        JSObject jsObject = jsValue.asObject().getProperty("collectedData").asObject();
                        String collectedDataJson = jsObject.toJSONString().replaceAll(",", ",\r\n\t").replaceAll("\\{", "{\r\n\t").replaceAll("\\}", "\r\n}");
                        textArea.setText(collectedDataJson);
                        ourThread.sleep(2500);
                    }

                }
                catch(Throwable t)
                {
                    t.printStackTrace();
                }
            }
        });
        ourThread.start();

    }

    private void shutdown()
    {
        isRunning = false;
    }

    public void onScriptContextDestroyed(ScriptContextEvent event) {
        shutdown();
    }
}
