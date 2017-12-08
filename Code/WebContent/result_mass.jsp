<%--
    Document   : Result - MASS
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
        <script src="javascript/result.js" type="text/javascript"></script>
        <link rel="stylesheet" href="style/aplication.css" type="text/css" />
        <link rel="stylesheet" href="style/glowtabs.css" type="text/css" />
        <link rel="stylesheet" href="style/result.css" type="text/css" />
    </head>
    
    <body id="result-mass" onload="renderPathwayMASS();">
    
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
            
				<h1>MASS Results</h1>
				
				<div id="mass-result">
	
					<f:view>
					
						<h:inputHidden id="showPathway" value="#{resultBean.showPathwayAnalysis}"/>
		
						<div id="half-container">
							<div id="parameter-title">Parameters</div>
							<div id="half-scroll">
								<h:dataTable value="#{resultBean.generalPlusMassParamList}" var="dataItem" id="half-table-param" rowClasses="list-row-even,list-row-odd">
								    <h:column>
								    	<f:facet name="header"></f:facet>
								        <h:outputText value="#{dataItem.t1}" style="font-weight: bold;"/>
										<h:outputText value="#{dataItem.t2}"/>
								    </h:column>
								</h:dataTable>
							</div>
						</div>
						
						<div id="half-spacing"></div>
						
						<div id="half-container">
							<div id="time-title">Time Analysis</div>
							<div id="half-scroll">
								<h:dataTable value="#{resultBean.massTimeList}" var="dataItem" id="half-table-time" rowClasses="list-row-even,list-row-odd">
								    <h:column>
								    	<f:facet name="header"></f:facet>
								        <h:outputText value="#{dataItem.t1}" style="font-weight: bold;"/>
										<h:outputText value="#{dataItem.t2}" />   
								    </h:column>
								</h:dataTable>
							</div>
						</div>
						
						<div id="space"></div>
						
						<div id="full-container">
							<div id="snps-title">SNP Combination Ranking</div>
							<div id="full-scroll">
								<h:dataTable value="#{resultBean.massResultList}" var="dataItemS" id="full-table-snps" rowClasses="list-row-even,list-row-odd">
								    
								    <h:column>
								        <f:facet name="header">
								            <h:outputText value="Gain"/>
								        </f:facet>
								        <h:outputText value="#{dataItemS.massGainSnps}" style="font-weight: bold;"/>
								        <f:verbatim><br></f:verbatim>
										<h:outputText value="#{dataItemS.massGainScore}"/> 
								    </h:column>
								    
								    <h:column>
								        <f:facet name="header">
								            <h:outputText value="Chi-Square"/>
								        </f:facet>
								        <h:outputText value="#{dataItemS.massChiSnps}" style="font-weight: bold;"/>
								        <f:verbatim><br></f:verbatim>
										<h:outputText value="#{dataItemS.massChiScore}"/>   
								    </h:column>
								    
								    <h:column>
								        <f:facet name="header">
								            <h:outputText value="Gini"/>
								        </f:facet>
								        <h:outputText value="#{dataItemS.massGiniSnps}" style="font-weight: bold;"/>  
								        <f:verbatim><br></f:verbatim>
										<h:outputText value="#{dataItemS.massGiniScore}"/>   
								    </h:column>
								    
								    <h:column>
								        <f:facet name="header">
								            <h:outputText value="APD"/>
								        </f:facet>
								        <h:outputText value="#{dataItemS.massApdSnps}" style="font-weight: bold;"/>  
								        <f:verbatim><br></f:verbatim>
										<h:outputText value="#{dataItemS.massApdScore}"/>   
								    </h:column>
								    
								    <h:column>
								        <f:facet name="header">
								            <h:outputText value="Overall"/>
								        </f:facet>
								        <h:outputText value="#{dataItemS.massOverallSnps}" style="font-weight: bold;"/>  
								        <f:verbatim><br></f:verbatim>
										<h:outputText value="#{dataItemS.massOverallScore}"/>   
								    </h:column>
								    
								</h:dataTable>
							</div>
						</div>
						
						<div id="space"></div>
						
						<div id="full-container-pathway" style="display:none;">
							<div id="pathway-title">Pathway Analysis</div>
							<div id="full-scroll">
								<h:dataTable value="#{resultBean.massPathwayList}" var="dataItemS" id="full-table-pathway" rowClasses="list-row-even,list-row-odd">
								    
									<h:column>
								        <f:facet name="header">
								            <h:outputText value="Gain"/>
								        </f:facet>
								        <h:outputText value="#{dataItemS.massGainSnps}" style="font-weight: bold;"/>
								        <f:verbatim><br></f:verbatim>
										<h:outputText value="#{dataItemS.massGainScore}"/> 
								    </h:column>
								    
								    <h:column>
								        <f:facet name="header">
								            <h:outputText value="Chi-Square"/>
								        </f:facet>
								        <h:outputText value="#{dataItemS.massChiSnps}" style="font-weight: bold;"/>
								        <f:verbatim><br></f:verbatim>
										<h:outputText value="#{dataItemS.massChiScore}"/>   
								    </h:column>
								    
								    <h:column>
								        <f:facet name="header">
								            <h:outputText value="Gini"/>
								        </f:facet>
								        <h:outputText value="#{dataItemS.massGiniSnps}" style="font-weight: bold;"/>  
								        <f:verbatim><br></f:verbatim>
										<h:outputText value="#{dataItemS.massGiniScore}"/>   
								    </h:column>
								    
								    <h:column>
								        <f:facet name="header">
								            <h:outputText value="APD"/>
								        </f:facet>
								        <h:outputText value="#{dataItemS.massApdSnps}" style="font-weight: bold;"/>  
								        <f:verbatim><br></f:verbatim>
										<h:outputText value="#{dataItemS.massApdScore}"/>   
								    </h:column>
								    
								    <h:column>
								        <f:facet name="header">
								            <h:outputText value="Overall"/>
								        </f:facet>
								        <h:outputText value="#{dataItemS.massOverallSnps}" style="font-weight: bold;"/>  
								        <f:verbatim><br></f:verbatim>
										<h:outputText value="#{dataItemS.massOverallScore}"/>   
								    </h:column>
								    									
								</h:dataTable>
							</div>
						</div>
						
						<div id="space"></div>
	
						<h:form id="center-form-download">
							<h:commandButton id="botaoDownloadXls" value="Download Excel" styleClass="button-download" action="#{resultBean.downloadXls}"/>
							<h:commandButton id="botaoDownloadTxt" value="Download Text" styleClass="button-download" action="#{resultBean.downloadTxt}"/>
							<h:commandButton id="botaoReturn" value="Return" styleClass="button-download" action="#{resultBean.returnToAnalysis}"/>
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
