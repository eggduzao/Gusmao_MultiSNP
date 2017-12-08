<%--
    Document   : Analysis - ESNP2
    Created on : 26/06/2010, 18:39:06
    Author     : Eduardo Gade (egg@cin.ufpe.br)
--%>

<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>

    <head>
        <title>Polymorphism Interaction Analysis</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <script src="javascript/aplication.js" type="text/javascript"></script>
        <script src="javascript/dropdowntabs.js" type="text/javascript"></script>
        <script src="javascript/analysis.js" type="text/javascript"></script>
        <link rel="stylesheet" href="style/aplication.css" type="text/css" />
        <link rel="stylesheet" href="style/glowtabs.css" type="text/css" />
        <link rel="stylesheet" href="style/analysis.css" type="text/css" />
    </head>
    
    <body id="analysis-esnp2">
    
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
                
                <div id="esnp2-analysis"> 

					<f:view>
	
		                <h:form id="formAnalysis" styleClass="form" enctype="multipart/form-data">
							
							<h:outputText id="init" value="#{analysisBean.init}"/>
							
							<div class="space20"></div>
							
							<div class="comprimento-4 primeiro">
		                        <div class="general-title">Parameters:</div>
		                    </div>
		
		                    <div class="comprimento-13 primeiro">
		                        <label for="path"><h:outputText styleClass="help" value="Base Path" onclick="helpBasePath();"/></label>
		                        <t:inputFileUpload id="fileupload" value="#{analysisBean.upFile}" size="80"/>
		                    </div>
		
		                    <div class="comprimento-4 primeiro">
		                        <label for="order"><h:outputText styleClass="help" value="Order" onclick="helpOrder();"/></label>
		                        <h:inputText id="order" value="#{analysisBean.order}"/>
		                    </div>
		                    <div class="comprimento-4">
		                        <label for="top"><h:outputText styleClass="help" value="No. Top Results" onclick="helpTopResults();"/></label>
		                        <h:inputText id="top" value="#{analysisBean.top}"/>
		                    </div>
		                    <div class="comprimento-4">
		                    	<label for="pathwayUsage"><h:outputText styleClass="help" value="Pathway Usage" onclick="helpPathwayUsage();"/></label>
		                        <h:selectBooleanCheckbox id="usePathway" value="#{analysisBean.usePathway}" />
								Use Pathway
		                    </div>
		                    
		                    <div class="comprimento-11 primeiro">
		                        <label for="output-method-label"><h:outputText styleClass="help" value="Output" onclick="helpOutput();"/></label>
		                        <h:selectOneRadio id="select-output" value="#{analysisBean.outputMethod}" onclick="showEmail();">
									<f:selectItem id="screen" itemLabel="Exhibit on Screen" itemValue="screen" />
									<f:selectItem id="download" itemLabel="Download" itemValue="download" />
									<f:selectItem id="email" itemLabel="Send via Email" itemValue="email"/>
								</h:selectOneRadio>
		                    </div>
		                    
		                    <div id="emailSelection" class="comprimento-8" style="display: none;">
		                    	<label for="pathwayUsage"><h:outputText styleClass="help" value="Email" onclick="helpEmail();"/></label>
		                    	<h:inputText id="emailAddress" value="#{analysisBean.email}"/>
							</div>
		                    
		                    <div class="space80"></div>
		                    
		                    <div class="comprimento-4 primeiro">
		                        <div id="parameter-title" class="param-title" onclick="openOrCloseParameters();" onmouseover="highlight();" onmouseout="fadelight();">
		                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Advanced ESNP2 Parameters
		                        </div>
		                    </div>
		                    
		                    <div id="esnp2-parameter" class="form" style="display: none;">
		                    
			                    <div class="comprimento-12">
			                        <b>This analysis don't contain any advanced parameters.</b>
			                    </div>
			                    
			                </div>
			                
			                <div class="space60"></div>
		
		                    <div class="comprimento-6 primeiro">
		                        <h:commandButton id="botaoAnalyze" styleClass="button" value="Analyze" action="#{analysisBean.result}" onclick="return check();"/>
		                    </div>
		
		                </h:form>
	
					</f:view>
					
				</div>

            </div>
            <div id="bottom">
              <br><br>
              <!-- 2010. Eduardo Gade Gusm&atilde;o. &emsp; egg@cin.ufpe.br -->  
            </div>
        </div>
    </body>
</html>
