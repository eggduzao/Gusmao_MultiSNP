<%-- 
    Document   : Index (home)
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
        <link rel="stylesheet" href="style/index.css" type="text/css" />
    </head>
    
    <body id="index">
    
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
                <h1>Polymorphism Interaction Tool</h1>
                <div id="main_img1"></div>
                <div id="main_text">
                    <br>
					
					&emsp;&emsp;&emsp;Single Nucleotide Polymorphisms are the most common type of genetic variation in humans.<br> 
					Recent studies show that SNPs can be associated to the risk of development of complex diseases such as cancer. 
					But the individual contribution of each SNP seems to be very small compared 
					to the contribution <br> of SNP combinations.
					<br>
					
					&emsp;&emsp;&emsp;Among various computational approaches to identify interacting SNPs in a context of 
					genotypic individual-level data, this web-tool gives the user the possibility to analyze four of them:
					Polymorphism Interaction Analysis v.2, Multifactor Dimensionality Reduction, Entropy-Based SNP-SNP Interaction Method and
					Multi-Approach SNP-SNP Interaction Analysis (novel). The main goal of these methods is to search and rank the combinations 
					of features (SNPs or environmental factors) that most likely associates with the disease. In addition, they calculate the 
					analysis time, allowing the user to make a performance analysis.
					<br><br>
					
					&emsp;&emsp;&emsp;<b>The site offers: <br>
   					ANALYSIS  : Automatic execution of analysis using different approaches.<br>
   					METHODS   : A brief yet robust explanation of the methods, including their input data format.<br>
   					REFERENCES: Pointers to papers that present the methods in detail.<br>
					</b><br><br>

                </div>
                <!--
                <br><h3>Team:</h3>
                  
                <div id="main_credits">
                    <b>Developer:</b> Eduardo Gade Gusm&atilde;o &emsp;&emsp;
                        <a href="http://www.cin.ufpe.br/~egg/"> Website </a> &emsp;
                        <a href="http://lattes.cnpq.br/7258504396971325">CV Lattes </a>
                    <br>
                    <b>Project Coordinator:</b> Katia Guimar&atilde;es &emsp;&emsp;
                        <a href="http://www.cin.ufpe.br/~katiag/"> Website </a> &emsp;
                        <a href="http://lattes.cnpq.br/8994178236264483">CV Lattes </a>
                    <br>
                </div>
                -->
            </div>
            
            <div id="bottom">
              <br><br>
              <!-- 2010. Eduardo Gade Gusm&atilde;o. &emsp; egg@cin.ufpe.br -->  
            </div>
        </div>
    </body>
</html>
