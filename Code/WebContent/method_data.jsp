<%--
    Document   : Method - Data
    Created on : 26/06/2010, 18:39:06
    Author     : Eduardo Gade (egg@cin.ufpe.br)
--%>

<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>

    <head>
        <title>Polymorphism Interaction Analysis</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <script src="javascript/aplication.js" type="text/javascript"></script>
        <script src="javascript/dropdowntabs.js" type="text/javascript"></script>
        <link rel="stylesheet" href="style/aplication.css" type="text/css" />
        <link rel="stylesheet" href="style/glowtabs.css" type="text/css" />
        <link rel="stylesheet" href="style/method.css" type="text/css" />
    </head>
    
    <body id="method-data">
    
        <div id="frame">
            <div id="top-image"></div>
            <div id="imgbox1"></div>
            <div id="imgbox2"></div>
            <div id="imgbox3"></div>
            <div id="support"><b>Support:</b></div>
            <div id="logofederal" onclick="openUFPE();" style="cursor: pointer;"></div>
            <div id="logocin" onclick="openCIN();" style="cursor: pointer;"></div>
            <div id="funding"><b>Funding:</b></div>
            <div id="logofacepe" onclick="openFACEPE();" style="cursor: pointer;"></div>
            <div id="logocnpq" onclick="openCNPQ();" style="cursor: pointer;"></div>
            <div id="blueline"><div id="bluedegrade"></div></div>
        </div>
        
        <div id="glowmenu" class="glowingtabs">
	        <ul id="menu">
	            <li><a href="index.jsp" title="Home"><span>Home</span></a></li>
	            <li><a href="#" title="Analysis" rel="dropmenu1_d"><span>Analysis</span></a></li>
	            <li><a href="#" title="Methods" rel="dropmenu2_d"><span>Methods</span></a></li>
	            <li><a href="references.jsp" title="References"><span>References</span></a></li>
	        </ul>
        </div>

        <!--1st drop down menu -->
        <div id="dropmenu1_d" class="dropmenudiv_d">
            <a href="analysis_pia.jsp">PIA</a>
            <a href="analysis_mdr.jsp">MDR</a>
            <a href="analysis_esnp2.jsp">ESNP2</a>
            <a href="analysis_mass.jsp">MASS</a>
			<a href="analysis_comparative.jsp">Comparative Analysis</a>
        </div>

        <!--2nd drop down menu -->
        <div id="dropmenu2_d" class="dropmenudiv_d" style="width: 150px;">
            <a href="method_pia.jsp">PIA</a>
            <a href="method_mdr.jsp">MDR</a>
            <a href="method_esnp2.jsp">ESNP2</a>
            <a href="method_mass.jsp">MASS</a>
            <a href="method_data.jsp">Input Data Format</a>
        </div>

        <script type="text/javascript">
            //SYNTAX: tabdropdown.init("menu_id", [integer OR "auto"])
            tabdropdown.init("glowmenu", "auto");
        </script>

        <div id="content">
            <div id="main-content">
                <h1>Input Data Format Description</h1>

				<div id="data-text"> 
					The data should follow a specific format. As the Image 1 at the end of this page shows, the first line is the header line. It should contain M values, where M is the number of features, describing the name of each feature, plus one string naming the last column that should contain the disease status. The second line (in the image 1) is optional. It is the pathway header and should contain M values containing the pathway associated with the SNP in question, plus one string naming the last column again. And then follows N lines where N is the population size. Each of these lines contain M+1 values, M values of the features that are integers or strings representing the category associated with that individual and the last value representing the disease status (0 for control and 1 for case). All the values can be separated by any of these characters, that should be used only to separate and cannot be used on the name or value strings: blank space, tabulation, “,”, “|”, “/” and “\”. Any of these characters can be used and many of them can be used on the same file, but each separation should contain only one (two empty spaces is wrong).
				</div>
				<div id="data-image"> </div>
				<div id="data-image-subtitle"> 
					Image 1 - Data Input Example
				</div>

            </div>
            <div id="bottom">
              <br><br>
              <!-- 2010. Eduardo Gade Gusm&atilde;o. &emsp; egg@cin.ufpe.br -->  
            </div>
        </div>
    </body>
</html>
