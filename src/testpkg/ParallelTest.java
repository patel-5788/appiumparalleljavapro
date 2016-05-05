package testpkg;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class ParallelTest {
	
	public  AndroidDriver<MobileElement> driver;
	
	public static DesiredCapabilities cap;
	
	@BeforeTest(alwaysRun=true)
	@Parameters({"port","device"})
	public void testsetup(String port,String device) throws MalformedURLException, InterruptedException{
				
		String path = "D://Appium//apkfiles//com.gorillalogic.monkeytalk.demo1.apk";
		File file = new File(path);
		
		cap = new DesiredCapabilities();
		
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		
		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.0.1");
		
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, device);
		
		cap.setCapability(MobileCapabilityType.APP, file);
		
		driver = new AndroidDriver<>(new URL("http://localhost:"+port+"/wd/hub"), cap);
		
		System.out.println("session id is---"+driver.getSessionId());
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
	
	@Test
	public void ValidLoginTest() throws InterruptedException{
		
        driver.findElementById("com.gorillalogic.monkeytalk.demo1:id/login_usr").sendKeys("admin");

        Thread.sleep(5000L);
		driver.findElementById("com.gorillalogic.monkeytalk.demo1:id/login_pwd").sendKeys("admin");
		
		driver.tap(1, driver.findElementById("com.gorillalogic.monkeytalk.demo1:id/login_btn"), 1);
		
		String name = driver.findElementById("com.gorillalogic.monkeytalk.demo1:id/logout_txt").getText();
		
		Assert.assertTrue(name.contains("admin"));
		
	}
	
	@Test
	public void InvalidValidLoginTest() throws InterruptedException{
		
		List<MobileElement> tabs = driver.findElementsById("com.gorillalogic.monkeytalk.demo1:id/tab_txt");
		for(int i=0;i<=3;i++){
			if(tabs.get(i).getText().contains("forms")){
				tabs.get(i).click();
				driver.findElementById("com.gorillalogic.monkeytalk.demo1:id/forms_checkbox").click();
		        
		        driver.findElementById("com.gorillalogic.monkeytalk.demo1:id/forms_radio_c").click();
			}
			if(tabs.get(i).getText().contains("web")){
				tabs.get(i).click();
				driver.findElementByAccessibilityId("Hello!").clear();
				driver.findElementByAccessibilityId("Hello!").sendKeys("this is arvind");
				driver.findElementByAccessibilityId("B").click();
				driver.findElementByAccessibilityId("GO!").click(); 				
				
			}
			
		}
        
   	}
	
	@AfterTest
	public void quit(){
		driver.quit();
	}
	
	
	}
