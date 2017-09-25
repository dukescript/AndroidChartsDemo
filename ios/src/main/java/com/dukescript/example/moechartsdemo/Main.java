package com.dukescript.example.moechartsdemo;

import com.dukescript.example.webui.WebChart;
import com.dukescript.presenters.iOS;

import org.moe.natj.general.NatJ;
import org.moe.natj.general.Pointer;
import org.moe.natj.general.ann.RegisterOnStartup;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCClassName;
import org.moe.natj.objc.ann.Selector;

import java.util.concurrent.Executor;

import apple.NSObject;
import apple.coregraphics.struct.CGPoint;
import apple.coregraphics.struct.CGRect;
import apple.coregraphics.struct.CGSize;
import apple.foundation.NSDictionary;
import apple.uikit.UIApplication;
import apple.uikit.UIButton;
import apple.uikit.UIColor;
import apple.uikit.UILabel;
import apple.uikit.UIScreen;
import apple.uikit.UIViewController;
import apple.uikit.UIWebView;
import apple.uikit.UIWindow;
import apple.uikit.c.UIKit;
import apple.uikit.enums.UIButtonType;
import apple.uikit.enums.UIControlEvents;
import apple.uikit.enums.UIControlState;
import apple.uikit.enums.UITextAlignment;
import apple.uikit.enums.UIViewAutoresizing;
import apple.uikit.protocol.UIApplicationDelegate;

@RegisterOnStartup
public class Main extends NSObject implements UIApplicationDelegate {

    private UIButton left;
    private UIButton right;
    private UIWebView webView;
    private UILabel label;
    private WebChart chart;

    public static void main(String[] args) {
        UIKit.UIApplicationMain(0, null, null, Main.class.getName());
    }

    @Selector("alloc")
    public static native Main alloc();

    protected Main(Pointer peer) {
        super(peer);
    }

    private UIWindow window;

    @Override
    public boolean applicationDidFinishLaunchingWithOptions(UIApplication application, NSDictionary launchOptions) {
        CGRect bounds = UIScreen.mainScreen().bounds();
        double tenthHeight = bounds.size().height() / 10;
        CGSize buttonSize = new CGSize(bounds.size().width() / 3, tenthHeight);
        left = UIButton.buttonWithType(UIButtonType.RoundedRect);
        left.setFrame(new CGRect(bounds.origin(), buttonSize));
        left.setTitleForState("Blue++", UIControlState.Normal);
        left.setTitleColorForState(UIColor.whiteColor(), UIControlState.Normal);
        left.setBackgroundColor(UIColor.blueColor());
        left.addTargetActionForControlEvents((source, event) -> {
            chart.plusBlue();
        }, UIControlEvents.TouchDown);

        right = UIButton.buttonWithType(UIButtonType.RoundedRect);
        right.setFrame(new CGRect(new CGPoint(bounds.size().width() * 2 / 3, bounds.origin().y()), buttonSize));
        right.setTitleForState("Red++", UIControlState.Normal);
        right.setTitleColorForState(UIColor.whiteColor(), UIControlState.Normal);
        right.setBackgroundColor(UIColor.redColor());
        right.addTargetActionForControlEvents((source, event) -> {
            chart.plusRed();
        }, UIControlEvents.TouchDown);

        CGRect webViewBounds = new CGRect(new CGPoint(0, tenthHeight), new CGSize(bounds.size().width(), bounds.size().height() - 2 * tenthHeight));
        webView = UIWebView.alloc().initWithFrame(webViewBounds);
        webView.setAutoresizingMask(UIViewAutoresizing.FlexibleBottomMargin | UIViewAutoresizing.FlexibleHeight | UIViewAutoresizing.FlexibleLeftMargin | UIViewAutoresizing.FlexibleRightMargin | UIViewAutoresizing.FlexibleTopMargin | UIViewAutoresizing.FlexibleWidth);
        Executor withView = iOS.configure("GPLv3", webView, "chart.html");

        label = UILabel.alloc().initWithFrame(new CGRect(new CGPoint(0, bounds.size().height() - tenthHeight), new CGSize(bounds.size().width(), tenthHeight)));
        label.setText("Click the chart!");
        label.setTextAlignment(UITextAlignment.Center);

        window.setRootViewController(MainController.alloc());
        window.setBackgroundColor(UIColor.whiteColor());
        window.rootViewController().setView(webView);

        window.addSubview(left);
        window.addSubview(right);
        window.addSubview(webView);
        window.addSubview(label);
        window.makeKeyAndVisible();

        chart = new iOSWebChart(withView);

        return true;
    }


    @Override
    public void setWindow(UIWindow value) {
        window = value;
    }

    @Override
    public UIWindow window() {
        return window;
    }


    @Runtime(value = ObjCRuntime.class)
    @RegisterOnStartup
    @ObjCClassName(value = "MainController")
    static final class MainController extends UIViewController {

        static {
            NatJ.register();
        }

        protected MainController(Pointer pntr) {
            super(pntr);
        }

        @Selector(value = "alloc")
        public static native MainController alloc();

        @Override
        public boolean prefersStatusBarHidden() {
            return true;
        }

        @Override
        public boolean shouldAutorotate() {
            return true;
        }

        @Override
        public boolean shouldAutomaticallyForwardRotationMethods() {
            return false;
        }
    }

    private final class iOSWebChart extends WebChart {

        public iOSWebChart(Executor withView) {
            super(withView);
        }

        @Override
        protected void setText(String text) {
            label.setText(text);
        }

        @Override
        protected void runOnUiThread(Runnable command) {
            command.run();
        }
    }
}
