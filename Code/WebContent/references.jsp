<%--
    Document   : References
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
        <link rel="stylesheet" href="style/references.css" type="text/css" />
    </head>
    
    <body id="references">
    
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
                <h1>References</h1>
                <div id="ref_box">
                    <ul>
                        <li>
                            Polymorphism Interaction Analysis (PIA): <br>
                            <b>[1]&emsp;</b> Mechanic LE, Luke BT, Goodman JE, Chanock SJ, Harris CC.
                                             <i>“Polymorphism Interaction Analysis (PIA):
                                             a method for investigating complex gene-gene interactions”.</i>
                                             BMC Bioinformatics 2008. &emsp;
                                             <a href="http://www.biomedcentral.com/1471-2105/9/146">biomed</a>
                                             &emsp;
                                     <a href="http://www.ncbi.nlm.nih.gov/pmc/articles/PMC2335300/?tool=pubmed">
                                             pubmed</a>
                                             <br>
                            <b>[2]&emsp;</b> Goodman JE, Mechanic LE, Luke BT, Ambs S, Chanock S, Harris CC. 
                                             <i>“Exploring SNP-SNP interactions and colon cancer risk using
                                             polymorphism interaction analysis”.</i>
                                             Int J Cancer. 2006 April 1; 118(7): 1790–1797.
                                             &emsp;
                       <a href="http://www3.interscience.wiley.com/journal/112101272/abstract?CRETRY=1&SRETRY=0">
                                             interscience</a>
                                             &emsp;
                                      <a href="http://www.ncbi.nlm.nih.gov/pmc/articles/PMC1451415/?tool=pubmed">
                                             pubmed</a>
                            <br><br>
                        </li>
                        <li>
                            Multifactor Dimensionality Reduction (MDR): <br>
                            <b>[3]&emsp;</b> Velez DR, White BC, Motsinger AA, Bush WS, Ritchie MD, Williams SM,
                                             Moore JH. <i>“A Balanced Accuracy Function for Epistasis Modeling in
                                             Imbalanced Datasets using Multifactor Dimensionality Reduction”.</i>
                                             Genetic Epidemiology, 2007, 31:306-315. &emsp;
                                         <a href="http://www3.interscience.wiley.com/journal/114129060/abstract">
                                             interscience</a>
                                             &emsp;
                                             <a href="http://www.ncbi.nlm.nih.gov/pubmed/17323372">
                                             pubmed</a>
                                             <br>
                            <b>[4]&emsp;</b> Hahn LW, Ritchie MD, Moore JH. <i>“Multifactor dimensionality
                                             reduction software for detecting gene-gene and gene-environment
                                             interactions”.</i> Bioinformatics, 2003, vol.19 no.3, 376-382.&emsp;
                                  <a href="http://bioinformatics.oxfordjournals.org/cgi/content/short/19/3/376">
                                             Oxford Journals</a>
                                             &emsp;
                                             <a href="http://www.ncbi.nlm.nih.gov/pubmed/12584123">
                                             pubmed</a>
                                             <br>
                            <b>[5]&emsp;</b> Ritchie MD, Hahn LW, Roodi N, Bailey LR, Dupont WD, Parl FF,
                                             Moore JH. <i>“Multifactor-Dimensionality Reduction Reveals
                                             High-Order Interactions among Estrogen-Metabolism Genes in Sporadic
                                             Breast Cancer”.</i> Am. J. Hum. Genet, 2001, 69:38-147.&emsp;
                                             <a href="http://www.cell.com/AJHG/retrieve/pii/S0002929707614530">
                                             AJHG</a>
                                             &emsp;
                                             <a href="http://www.ncbi.nlm.nih.gov/pubmed/11404819">
                                             pubmed</a>
                            <br><br>
                        </li>
                        <li>
                            Entropy-Based SNP-SNP Interaction Method (ESNP2): <br>
                            <b>[6]&emsp;</b> Dong C, Chu X, Wang Y, Wang Y, Jin L, Shi T, Huang W, Li Y. 
                            				<i>“Exploration of gene-gene interaction effects using entropy-based 
                            				methods”.</i> European Journal of Human Genetics 2007, 16:229-235.&emsp;
                                        <a href="http://www.nature.com/ejhg/journal/v16/n2/abs/5201921a.html">
                                             EJHG</a>
                                             &emsp;
                                             <a href="http://www.ncbi.nlm.nih.gov/pubmed/17971837">
                                             pubmed</a>
                            <br><br>
                        </li>
                    </ul>
                </div>
            </div>
            <div id="bottom">
              <br><br>
              <!-- 2010. Eduardo Gade Gusm&atilde;o. &emsp; egg@cin.ufpe.br -->  
            </div>
        </div>
    </body>
</html>
