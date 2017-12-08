<%--
    Document   : Analysis - Comparative
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
    
    <body id="analysis-comparative">
    
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
            
                <h1>Comparative Analysis</h1>

				<div id="comparative-analysis"> 

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
		                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Advanced Parameters
		                        </div>
		                    </div>
		                    
		                    <div id="comparative-parameter" class="form" style="display: none;">
		                    
		                    	<div class="space20"></div>
							
								<div class="comprimento-8 primeiro">
			                        <div class="general-title"> PIA Parameters </div>
			                    </div>
			                    
			                    <div class="comprimento-4 primeiro">
			                        <label for="shufflePia"><h:outputText styleClass="help" value="Shuffle" onclick="helpShufflePIA();"/></label>
			                        <h:inputText id="shufflePia" value="#{analysisBean.shufflePia}"/>
			                    </div>
			
			                    <div class="comprimento-4">
			                        <label for="fractPia"><h:outputText styleClass="help" value="Fract" onclick="helpFractPIA();"/></label>
			                        <h:inputText id="fractPia" value="#{analysisBean.fractPia}"/>
			                    </div>
			                    <div class="comprimento-4">
			                        <label for="ntimePia"><h:outputText styleClass="help" value="Ntime" onclick="helpNtimePIA();"/></label>
			                        <h:inputText id="ntimePia" value="#{analysisBean.ntimePia}"/>
			                    </div>
			                    <div class="comprimento-4 primeiro">
			                        <label for="ratslPia"><h:outputText styleClass="help" value="Ratsl" onclick="helpRatslPIA();"/></label>
			                        <h:inputText id="ratslPia" value="#{analysisBean.ratslPia}"/>
			                    </div>
			                    
			                    <div class="comprimento-4">
			                        <label for="ifractPia"><h:outputText styleClass="help" value="Ifract" onclick="helpIfractPIA();"/></label>
			                        <h:selectBooleanCheckbox id="ifractPia" value="#{analysisBean.ifractPia}" />
									Enable Ifract
			                    </div>
			                    <div class="comprimento-4">
			                        <label for="itrainPia"><h:outputText styleClass="help" value="Itrain" onclick="helpItrainPIA();"/></label>
			                        <h:selectBooleanCheckbox id="itrainPia" value="#{analysisBean.itrainPia}" />
									Enable Itrain
			                    </div>
			
			                    <div class="comprimento-4">
			                        <label for="lootrPia"><h:outputText styleClass="help" value="Lootr" onclick="helpLootrPIA();"/></label>
									<h:selectOneMenu id="lootrPia" value="#{analysisBean.lootrPia}">
			                              <f:selectItem itemLabel="Maximum Rules" itemValue="Maximum Rules" />
			                              <f:selectItem itemLabel="Leave One Out" itemValue="Leave One Out"/>
			                              <f:selectItem itemLabel="PIA v1" itemValue="PIA v1"/>
			                        </h:selectOneMenu>
			                    </div>
			
			                    <div class="space80"></div>
			
								<div class="comprimento-8 primeiro">
			                        <div class="general-title"> MDR Parameters </div>
			                    </div>
			                    
			                    <div class="comprimento-4 primeiro">
			                        <label for="shuffleMdr"><h:outputText styleClass="help" value="Shuffle" onclick="helpShuffleMDR();"/></label>
			                        <h:inputText id="shuffleMdr" value="#{analysisBean.shuffleMdr}"/>
			                    </div>
			
			                    <div class="comprimento-4">
			                        <label for="fractMdr"><h:outputText styleClass="help" value="Fract" onclick="helpFractMDR();"/></label>
			                        <h:inputText id="fractMdr" value="#{analysisBean.fractMdr}"/>
			                    </div>
			                    <div class="comprimento-4 primeiro">
			                        <label for="ntimeMdr"><h:outputText styleClass="help" value="Ntime" onclick="helpNtimeMDR();"/></label>
			                        <h:inputText id="ntimeMdr" value="#{analysisBean.ntimeMdr}"/>
			                    </div>
			                    <div class="comprimento-4">
			                        <label for="thresholdMdr"><h:outputText styleClass="help" value="Threshold" onclick="helpTMDR();"/></label>
			                        <h:inputText id="thresholdMdr" value="#{analysisBean.thresholdMdr}"/>
			                    </div>
			
								<div class="space80"></div>
			
								<div class="comprimento-8 primeiro">
			                        <div class="general-title"> MASS Parameters </div>
			                    </div>
			                    
			                    <div class="comprimento-4 primeiro">
			                        <label for="ratslMass"><h:outputText styleClass="help" value="Ratsl" onclick="helpRatslMASS();"/></label>
			                        <h:inputText id="ratslMass" value="#{analysisBean.ratslMass}"/>
			                    </div>
			
			                    <div class="comprimento-8">
			                        <label for="assignMass"><h:outputText styleClass="help" value="Simple Assignment" onclick="helpAssignMASS();"/></label>
			                        <h:selectBooleanCheckbox id="assignMass" value="#{analysisBean.simpleAssignMass}" />
									Enable Simple Assignment
			                    </div>
			                    
			                    <div class="comprimento-4 primeiro">
			                        <label for="useGainMass"><h:outputText styleClass="help" value="Use Gain" onclick="helpUseGainMASS();"/></label>
			                        <h:selectBooleanCheckbox id="useGainMass" value="#{analysisBean.useGainMass}" />
									Enable Gain
			                    </div>
		                    
		                    	<div class="comprimento-4">
			                        <label for="useChiMass"><h:outputText styleClass="help" value="Use Chi" onclick="helpUseChiMASS();"/></label>
			                        <h:selectBooleanCheckbox id="useChiMass" value="#{analysisBean.useChiMass}" />
									Enable Chi
			                    </div>
			                    
			                    <div class="comprimento-4">
			                        <label for="useGiniMass"><h:outputText styleClass="help" value="Use Gini" onclick="helpUseGiniMASS();"/></label>
			                        <h:selectBooleanCheckbox id="useGiniMass" value="#{analysisBean.useGiniMass}" />
									Enable Gini
			                    </div>
			                    
			                    <div class="comprimento-4">
			                        <label for="useApdMass"><h:outputText styleClass="help" value="Use APD" onclick="helpUseApdMASS();"/></label>
			                        <h:selectBooleanCheckbox id="useApdMass" value="#{analysisBean.useApdMass}" />
									Enable APD
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
