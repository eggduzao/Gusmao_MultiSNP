<%--
    Document   : Method - ESNP2
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
    
    <body id="method-esnp2">
    
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
                <h1>Entropy Based SNP-SNP Interaction Method - ESNP2</h1>

				<div id="esnp2-main">
				
					<h2 style="color: #1d0b70;"> Method Overview </h2>
	
					<div id="esnp2-intro-text"> 
						There are two versions of ESNP2 (see session “References” [6]). The ESNP2-standard (ESNP2-S) aims to detect the interactions the same way PIA and MDR do, and the ESNP2-model-option (ESNP2-Mx) tests the interaction against various two-locus genetic models. The version available here is the ESNP2-S.
						<br><br>
						ESNP2 standard uses the entropy to analyze which combinations better explain the disease. The strategy also analyzes all possible attribute combinations. The algorithm begins by calculating the entropy of each individual feature, using the following equation:
					</div>

					<div id="esnp2-img1"> </div>

					<div id="esnp2-text1"> 
						Then it proceeds selecting a combination of features and populating the genotype-phenotype table using case-controls status for all possible value that the combination can assume the same way PIA and MDR does. 
						<br><br>
						Then the entropy of interactions of possible combinations is computed by the following equation:
					</div>
					
					<div id="esnp2-img2"> </div>
					
					<div id="esnp2-text2"> 
						The final score Delta R, that represents the interaction effects, is then given by:
					</div>
					
					<div id="esnp2-img3"> </div>
			
					<br><br>

					<div id="esnp2-parameters">
					
						<h2 style="color: #1d0b70;"> Parameter Details </h2>
						
						<ul>
							<li>
								<b>Base Path:</b> Defines the path and the name of the base in the user’s PC.
							</li>
							<li>
								<b>Use Pathway:</b> Check if the base contains information about pathway of each feature, for more details see session “Data Input Format”.
							</li>
							<li>
								<b>Order:</b> Interaction order. Ex: if the value passed is 2, then the algorithm will search for combination between 2 features.
							</li>
							<li>
								<b>No. Top Results:</b> The number of top combinations that will be shown in the result screen. If it is larger than the total number of combinations, the result will truncate to exhibit only the maximum number of combinations. The pathway analysis is restricted to that number of combinations.
							</li>
							
						</ul>

					</div>
					
					<div id="esnp2-results-shown">
					
						<h2 style="color: #1d0b70;"> Results Shown </h2>
					
						The first two light and dark blue tables are respectively the parameters used in the testing and the analysis time divided by each algorithm part and the total time.
						<br><br>
						Then it's shown the top combinations of features returned by each scoring function. If pathway analysis was requested then another table will be shown with the results for that analysis.
					</div>

				</div>

            </div>
            <div id="bottom">
              <br><br>
              <!-- 2010. Eduardo Gade Gusm&atilde;o. &emsp; egg@cin.ufpe.br -->  
            </div>
        </div>
    </body>
</html>
