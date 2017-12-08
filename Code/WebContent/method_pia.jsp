<%--
    Document   : Method - PIA
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
    
    <body id="method-pia">
    
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
                <h1>Polymorphism Interaction Analysis - PIA</h1>
                
				<div id="pia-main">
				
					<h2 style="color: #1d0b70;"> Method Overview </h2>
	
					<div id="pia-intro-text"> 
						The Polymorphism Interaction Analysis (see session “References” [1,2]) is a method designed exclusively to identify possible interactions among features like SNPs or environmental factors. It is based on 7 scoring functions calculated for each possible factor combination and an overall scoring function that is calculated summing the normalized scoring functions for each combination of SNPs and/or other factors.
					</div>

					<div id="pia-container1"> 
						<div id="pia-img1"> </div>
						<div id="pia-img1-subtitle">Image 1</div>
						<div id="pia-text1"> 
							For the first 5 scores it creates a n-dimensional table (where n is the order of the interaction) that accounts for the total of cases and controls for each possible attribute values (see the image 1). Then a cross-validation is performed dividing the data in equal parts and accounting for the number of TP, FP, TN and FN using the test (and maybe the train too) data. That cross validation is repeated a certain number of times and it results in a contingency table (or confusion matrix) similar to the one in image 2. With that table it is possible to calculate the following scores:
						</div>
						<div id="pia-formula1"></div>
					</div>

					<div id="pia-container2">
						<div id="pia-text2"> 
							The other 2 scores are calculated based on two split-measures used in other methods like CART (Classification and Regression Trees). They are the Gini Index and the Absolute Probability Difference (APD). Differently than the first five scores they use the entire data instead of dividing it in training and testing sets. Suppose that we have the genotype-phenotype table as shown on image 3, the following equations gives us the Gini Index and the APD.
						</div>
						<div id="pia-img2"> </div>
						<div id="pia-img2-subtitle">Image 2</div>
						<div id="pia-formula2"></div>

						<div id="pia-text3">
							Finally, the overall score is obtained by normalizing each scoring function so that the highest value is set to 50, then summing all the score values for each combination. Note that the maximum score that can be obtained this way is 350 as there are 7 scores. If the numbers of cases and controls are the same or the fractional occupation is used, the %correct score gives the same value as the sensitivity+specificity, and then only one of them is used, making the overall maximum score of 300.
						</div>
						<div id="pia-img3"></div>
						<div id="pia-img3-subtitle">Image 3</div>
					</div>

					<br><br><br><br>
	
					<div id="pia-parameters">
						
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
								<b>Ratsl:</b> It’s a floating value that cut off some attribute combinations that does not satisfy the following criteria: Max(cases,controls) / Min(cases,controls) &lt;= Ratsl. It is used to cut the combinations extremely affected by missing data.
							</li>
							<li>
								<b>Ifract:</b> Must be 0 or 1. Where 1 means that we are using fractional occupation to populate the genotype-phenotype table and 0 means that we use the actual values.
							</li>
							<li>
								<b>ITrain:</b> Must be 0 or 1. If 1 it means that we are using the training set to calculate the contingency table (along with the testing data), if 0 it means that we are using only the testing data.
							</li>
							<li>
								<b>Lootr:</b> If the ITrain parameter is 1, it tells how the training set values are used to populate the contingency table. For detailed information on each possibility see reference [2] on session “References”.
							</li>
						</ul>

					</div>
					
					<div id="pia-results-shown">
						
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
