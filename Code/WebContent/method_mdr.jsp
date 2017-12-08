<%--
    Document   : Method - MDR
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
    
    <body id="method-mdr">
    
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
                <h1>Multifactor Dimensionality Reduction - MDR</h1>

				<div id="mdr-main">
				
					<h2 style="color: #1d0b70;"> Method Overview </h2>
	
					<div id="mdr-intro-text"> 
						The Multifactor Dimensionality Reduction (see session “References” [3-5]) is based on the idea of reduce the dimensionality of each attribute, so that this new attribute is made easier and faster to be predicted. It performs a cross-validation strategy to select, based on the balanced accuracy predictor, the best model that explains the splitting between cases and controls.
					</div>

					<div id="mdr-img1"> </div>
					<div id="mdr-img1-subtitle">
						Image 1 - Hahn LW, et. al. Multifactor dimensionality reduction software for detecting gene-gene and gene-environment interactions. Fig.1.
					</div>

					<div id="mdr-text"> 
						As shown on the image 1, the algorithm starts by dividing the data into equal parts (cross-validation intervals). For each interval we choose the N factors (where N is the order of the interaction) next to be examined (it examines all the possible combinations) and put the data in a genotype-phenotype table (step 3). In the Step 4 each cell is categorized as “low-risk” if the case/control ratio is smaller than a certain threshold or “high-risk” if it is higher than that threshold, or “empty” if the cell contains no information. Then the classification error is calculated for each of the possible factor combinations and the lowest error is chosen to be the model that best explains the data in that cross-validation turn. In Step 6, the testing data that was separated is used to calculate the prediction error of that cross validation turn for the best model chosen by its classification error. This procedure is done a couple of times and the best classification combination is ranked. In addiction is shown the best prediction error of the best model for each cross-validation turn.
					</div>
	
					<div id="mdr-parameters">
						
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
							<li>
								<b>Shuffle:</b> It represents the number of times the data will be shuffled at the start of each cross-validation step.
							</li>
							<li>
								<b>Fract:</b> Represents how much of the data will be used as the test data in the cross-validation analysis. It must be a float between 0 and 1. Ex: If 0.1 is passed, then 10% of the data will be used as test at each cross validation interval testing.
							</li>
							<li>
								<b>NTime:</b> Tells how much cross-validations repetitions will be performed. The TP, FP, TN, FN numbers are summed for all the cross-validation repetitions in each feature combination.
							</li>
							<li>
								<b>Threshold:</b> A floating value representing the threshold used on the categorization of each cell of the genotype-phenotype table as “low-risk” and “high-risk”.
							</li>
						</ul>

					</div>

					<div id="mdr-results-shown">
						
						<h2 style="color: #1d0b70;"> Results Shown </h2>
						
						The first two light and dark blue tables are respectively the parameters used in the testing and the analysis time divided by each algorithm part and the total time.
						<br><br>
						It is shown two values bellow these tables that represent the mean balanced accuracy for all of the combinations calculated for the classification and prediction steps.
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
