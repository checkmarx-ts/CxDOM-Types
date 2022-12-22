# Summary

For Java Programming Language, and other programming languages, there would be some XML files in the project. 

A list of XML files:
* pom.xml
* web.xml
* struts.xml
* struts-config.xml
* *-validation.xml
* Info.plist
* faces-config.xml

You might need to get some information from the XML files by using CxQL. CxQL has cxXPath queries to support this feature.

# XML Example

Here is an example pom.xml file:

```XML
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.example</groupId>
	<artifactId>actuator-demo</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>
	<name>actuator-demo</name>
	<description>Spring Boot Actuator Demo Project</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.5.12</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>

		<!-- https://mvnrepository.com/artifact/org.apache.commons/commons-text -->
		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-text</artifactId>
			<version>1.9</version>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
</project>
```

# The Query

Here is the source code of the query **Find_Config_via_Xml**, you shall override the one Java/Cx/General/**Find_Config_via_Xml**:

```c#
// Find configuration information from XML files.
// First parameter is the file pattern to capture the XML files, for example "*pom.xml".
// Second parameter is the XPath pattern to find the tag or attribute from the XML files.
if (param.Length == 2){
	string files = param[0] as string;
	string query = param[1] as string;
	if(!string.IsNullOrEmpty(query)){
		foreach (CxXmlDoc doc in cxXPath.GetXmlFiles(files)){
			XPathNodeIterator nodeIterator = doc.CreateNavigator().Select(query);	
			while (nodeIterator.MoveNext()){		
				result.Add(cxXPath.CreateXmlNode(nodeIterator.Current.Clone(), doc, 2, false));			
			}	
		}
	}
}
```

# Example To Find a Version Tag from POM

In order to get version tag for groupId "org.apache.commons" and artificatId "commons-text", we should construct the xPath like this:
```
/*[local-name()='project']/*[local-name()='dependencies']/*[local-name()='dependency'][*[local-name()='groupId'] = 'org.apache.commons' and *[local-name()='artifactId'] = 'commons-text']/*[local-name()='version']
```

How to construct the XPath, we can use the following query:

```c#
string groupId = "org.apache.commons";
string artifactId = "commons-text";
Func<string, string> ln = (name) => $"*[local-name() = '{name}']";
Func<string, string> elem = (name) => "/" + ln(name);
string dependencyVersion = elem("project") + elem("dependencies") + elem("dependency") + 
	"[" + ln("groupId") + " = '" + groupId + "' and " + ln("artifactId") + " = '" + artifactId + "'" + "]" + elem("version");
```

Please note that we can use the **and** operator to combine the conditions when filter the **dependency** tags. Then we have the **version** tag which is a child of **dependency** tag.

**Notice: If you copy the above code to CxAudit, CxAudit would remove the $ sign, you have to add it manually.**

Run the following CxQL query for a project which contains the above pom.xml file will return a CxList object with the **version** tag:
```c#
string groupId = "org.apache.commons";
string artifactId = "commons-text";
Func<string, string> ln = (name) => $"*[local-name() = '{name}']";
Func<string, string> elem = (name) => "/" + ln(name);
string dependencyVersion = elem("project") + elem("dependencies") + elem("dependency") + 
	"[" + ln("groupId") + " = '" + groupId + "' and " + ln("artifactId") + " = '" + artifactId + "'" + "]" + elem("version");
CxList version = Find_Config_via_Xml("*pom.xml", dependencyVersion);
result = version;
```
**Notice: If you copy the above code to CxAudit, CxAudit would remove the $ sign, you have to add it manually.**

# Example to find attributes from an xml file

The XML file 

```XML
<?xml version="1.0" encoding="utf-8"?>
<resources>

    <string name="app_name">10006_HardCodedSensitiveInformationInResourceFile</string>
    <string name="action_settings">Settings</string>
    <string name="hello_world">Hello world!</string>
    <string name="password">admin23456</string>
    <string name="secret">admin23456</string>
    <string name="himitsu">admin23456</string>
    <string name="pass">admin23456</string>
    <string name="pass2">input password</string>
    <string name="hash1">50764755a9cd0f12619ec1bbc7f93eb2</string>
    <string name="hash2">50764755a9cd0f12619ec1bbc7f93eb212345678</string>
    <string name="hash3">50764755a9cd0f12619ec1bbc7f93eb250764755a9cd0f12619ec1bbc7f93eb2</string>
</resources>
```

The XPath
```
/*[local-name() ='resources']/*[local-name() ='string']/@name
```

The CxQL query
```c#
Func<string, string> attribute = (name) => $"/@{name}";
Func<string, string> ln = (name) => $"*[local-name() = '{name}']";
Func<string, string> elem = (name) => "/" + ln(name);
string attributePath = elem("resources") + elem("string") + attribute("name");
result = Find_Config_via_Xml("*.xml", attributePath);
```
**Notice: If you copy the above code to CxAudit, CxAudit would remove the $ sign, you have to add it manually.**


# The value of the tag or attribute

You can use the following CxQL query to have a look at your tag value or attribute value

```c#
foreach (CxList node in result) {
	CxXmlNode finalXmlNode = node.GetFirstGraph() as CxXmlNode;
	string nodeValue = finalXmlNode.Value;
	cxLog.WriteDebugMessage(nodeValue);
}
```